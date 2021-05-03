package com.woody.core.events;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.woody.core.Config;
import com.woody.core.commands.CommandPowerTool;
import com.woody.core.events.custom.PlayerHitMobEvent;
import com.woody.core.events.custom.PlayerHitPlayerEvent;
import com.woody.core.types.CustomPlayer;
import com.woody.core.types.PowerToolInfo;
import com.woody.core.util.FileManager;
import com.woody.core.util.ItemManager;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;

public class Basic implements Listener{
	
	public static Runnable manaRegenTask = new Runnable(){

		@Override
		public void run() {
			for(CustomPlayer _p : PlayerManager.getOnlinePlayers().values())
			{
				_p.getProfile().addMana(0.5);
			}
		}
	};

	@EventHandler
	public void xd(PlayerJoinEvent e)
	{
		CustomPlayer _cp = null;
		if(!PlayerManager.hasPlayedBefore(e.getPlayer()))
		{
			FileConfiguration fc = FileManager.getConfig("spawn.yml");
			if(fc!= null)
			{
				Location loc = fc.getLocation("location");
				if(loc != null)
					e.getPlayer().teleport(loc);
			}

			_cp = PlayerManager.registerOnlinePlayer(e.getPlayer());
			_cp.createProfile(1);
			_cp.loadProfile(1);
			_cp.getProfile().saveAll();
		}
		else
		{
			_cp = PlayerManager.registerOnlinePlayer(e.getPlayer());
			int _lastProf = PlayerManager.getGeneral(_cp.player.getUniqueId().toString()).getInt("last-profile");
			_cp.loadProfile(_lastProf);
		}
		
		if(e.getPlayer().hasPermission("woody.helpop.read")) 
		{
			for(File f : FileManager.listFiles("helpop/")) 
			{
				FileConfiguration fc = FileManager.getConfig("helpop/"+f.getName());
				e.getPlayer().sendMessage(StringManager.Colorize("&8[&cHelpOp&8]&7 " + fc.getString("player") + ": " + fc.getString("message")));
				f.delete();
			}
		}
	}
	
	@EventHandler
    public void PlayerQuit(PlayerQuitEvent e)
    {
		PlayerManager.unregisterOnlinePlayer(e.getPlayer());
    }
	
	@EventHandler
	public void OnEntityDamagedByProjectile(ProjectileHitEvent e)
	{
		if((e.getEntity() instanceof Egg || e.getEntity() instanceof Snowball) && e.getHitEntity() instanceof LivingEntity)
		{
			LivingEntity victim = (LivingEntity)e.getHitEntity();
			if(victim != null)
				victim.damage(0.1);
		}
	}
	
	@EventHandler
	public static void OnPlayerRespawn(PlayerRespawnEvent e) 
	{
		Location bedLoc = e.getPlayer().getBedSpawnLocation();
		FileConfiguration fc = FileManager.getConfig("spawn.yml");
		Location spawnLoc = null;
		if(fc != null)
			spawnLoc = FileManager.getConfig("spawn.yml").getLocation("location");
		
		if(Config.worldModule)
		{
			if(Config.respawnWorlds.contains(e.getPlayer().getWorld().getName()))
			{
				if(bedLoc != null && Config.bedRespawn)
					e.setRespawnLocation(bedLoc);
				else
				{
					if(spawnLoc != null && spawnLoc.getWorld().getName() == e.getPlayer().getWorld().getName())
						e.setRespawnLocation(spawnLoc);
					else
						e.setRespawnLocation(e.getPlayer().getWorld().getSpawnLocation());
				}
			}
		}
		else
		{
			if(bedLoc != null && Config.bedRespawn)
				e.setRespawnLocation(bedLoc);
			else
				if(spawnLoc != null)
					e.setRespawnLocation(spawnLoc);
		}

		PlayerManager.getOnlinePlayer(e.getPlayer()).getProfile().updateExpBar();
		PlayerManager.getOnlinePlayer(e.getPlayer()).getProfile().updateFoodBar();
	}
	
	@EventHandler
	public static void PowerTool(PlayerInteractEvent e) 
	{
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			String uuid = e.getPlayer().getUniqueId().toString();
			if(CommandPowerTool.ptEntries.containsKey(uuid))
			{
				for(PowerToolInfo pti : CommandPowerTool.ptEntries.get(uuid))
				{
					if(ItemManager.isSimilar(pti.item, e.getPlayer().getInventory().getItemInMainHand() , false))
					{
						e.getPlayer().performCommand(pti.command);
						e.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void OnHunger(FoodLevelChangeEvent e)
	{
		Player p = ((Player)e.getEntity());
		if(!Config.hunger)
			e.setCancelled(true);
		
		if(p.getFoodLevel() > e.getFoodLevel())
		{
			int value = p.getFoodLevel() - ((int)((p.getFoodLevel() - e.getFoodLevel()) *  Config.hungerMultiplier));
			
			if(value < 0)
				e.setFoodLevel(0);
			else
				e.setFoodLevel(value);
		}
		else
		{
			int value = p.getFoodLevel() + (int)((e.getFoodLevel() - p.getFoodLevel()) * Config.feedMultiplier);
			if(value > 20)
			{
				p.setSaturation(p.getSaturation() + value - 20);
				e.setFoodLevel(20);
			}
			else
				e.setFoodLevel(value);
			e.setFoodLevel(value);
		}
	}

	@EventHandler
	public void DamageByEntity(EntityDamageByEntityEvent e)
	{
		Entity damager = e.getDamager(), victim = e.getEntity();
		if(damager instanceof Player)
			e.setDamage(e.getDamage() + PlayerManager.getOnlinePlayer((Player)damager).getProfile().getBaseDamage());

		if(damager instanceof Player && victim instanceof Player)
		{
			PlayerHitPlayerEvent event = new PlayerHitPlayerEvent((Player)damager, (Player)victim, e.getDamage());
			Bukkit.getPluginManager().callEvent(event);
			if(event.isCancelled())
				e.setCancelled(true);
			e.setDamage(event.getDamage());
		}

		if(damager instanceof Player && victim instanceof LivingEntity)
		{
			PlayerHitMobEvent event = new PlayerHitMobEvent((Player)damager, (LivingEntity)victim, e.getDamage());
			Bukkit.getPluginManager().callEvent(event);
			if(event.isCancelled())
				e.setCancelled(true);
			e.setDamage(event.getDamage());
		}
	}

	@EventHandler
	public void onPlayerGetDamage(EntityDamageEvent e) {
		if(!Config.hungerDamage && e.getCause().equals(DamageCause.STARVATION))
			e.setCancelled(true);
	}
}


