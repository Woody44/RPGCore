package com.woody.core.events;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import com.woody.core.Config;
import com.woody.core.commands.CommandPowerTool;
import com.woody.core.types.CustomPlayer;
import com.woody.core.types.PowerToolInfo;
import com.woody.core.types.Profile;
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
				_p.getProfile().RegenMana(0.5);
			}
		}
	};

	@EventHandler
    public void PlayerJoin(PlayerJoinEvent event)
    {
		if(!PlayerManager.hasPlayedBefore(event.getPlayer()))
		{
			FileConfiguration fc = FileManager.getConfig("spawn.yml");
			if(fc!= null)
			{
				Location loc = fc.getLocation("location");
				if(loc != null)
					event.getPlayer().teleport(loc);
			}

			PlayerManager.createNewPlayer(event.getPlayer());
		}
		
		PlayerManager.registerOnlinePlayer(event.getPlayer());
		
		if(event.getPlayer().hasPermission("woody.helpop.read")) 
		{
			for(File f : FileManager.listFiles("helpop/")) 
			{
				FileConfiguration fc = FileManager.getConfig("helpop/"+f.getName());
				event.getPlayer().sendMessage(StringManager.Colorize("&8[&cHelpOp&8]&7 " + fc.getString("player") + ": " + fc.getString("message")));
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
	public void OnPlayerDamagedByProjectile(ProjectileHitEvent e)
	{
		if((e.getEntity() instanceof Egg || e.getEntity() instanceof Snowball) && e.getHitEntity() instanceof LivingEntity)
		{
			LivingEntity victim = (LivingEntity)e.getHitEntity();
			if(victim != null)
				victim.damage(0.1);
		}
	}
	
	@EventHandler
	public static void OnPlayerDeath(PlayerDeathEvent e) 
	{
		e.getDrops().clear();
		
		if(e.getEntity() == e.getEntity().getKiller()){
			e.setDeathMessage(StringManager.Colorize("&cGracz &l" + e.getEntity().getDisplayName() + "&c popelnil samobojstwo."));
		}
		
		if(!Config.keepInventory)
			dropPlayerItems(e.getEntity());

		Profile _profile = PlayerManager.getOnlinePlayer(e.getEntity()).getProfile();
		_profile.setProperty("LatestDeathPoint", e.getEntity().getLocation(), true);
		_profile.setProperty("LatestDeathTime", System.currentTimeMillis(), true);
		_profile.setMana(_profile.getMaxBaseMana() / 2);
		_profile.save();
	}
	
	public static void dropPlayerItems(Player p) 
	{
		ItemStack[] items = p.getInventory().getContents();
		p.getInventory().setContents(new ItemStack[41]);
		for(ItemStack i : items)
		{
			if(i == null)
				continue;
			Item ie = p.getWorld().dropItem(p.getLocation(), i);
			ie.setPickupDelay(120);
			ie.setInvulnerable(true);
			ie.setGlowing(true);
		}
		PlayerManager.getOnlinePlayer(p).ClearInventory();
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
		if(!(damager instanceof Player) || !(victim instanceof LivingEntity))
			return;

		e.setDamage(e.getDamage() + PlayerManager.getOnlinePlayer((Player)damager).getProfile().baseDamage);
	}

	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e) {
		if(!Config.hungerDamage && e.getCause().equals(DamageCause.STARVATION))
			e.setCancelled(true);
	}
}


