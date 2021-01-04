package com.woody.core.events;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import com.woody.core.Config;
import com.woody.core.Main;
import com.woody.core.commands.CommandPowerTool;
import com.woody.core.types.PowerToolInfo;
import com.woody.core.util.FileManager;
import com.woody.core.util.ItemManager;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;

public class Basic implements Listener{
	
	@EventHandler
    public void PlayerJoin(PlayerJoinEvent event)
    {
		Main.instance.getLogger().info("Loading "  + event.getPlayer().getName() + "'s data.");
		PlayerManager.registerOnlinePlayer(event.getPlayer());
    }
	
	@EventHandler
    public void PlayerQuit(PlayerQuitEvent e)
    {
		Main.instance.getLogger().info("Saving " + e.getPlayer().getName() + "'s data.");
		PlayerManager.unregisterOnlinePlayer(e.getPlayer());
    }
	
	@EventHandler
	public void OnPlayerDamage(ProjectileHitEvent e)
	{
		if(e.getEntity() instanceof Egg || e.getEntity() instanceof Snowball)
		{
			//Projectile damager = e.getEntity();
			//LivingEntity shooter = (LivingEntity) damager.getShooter();
			LivingEntity victim = (LivingEntity)e.getHitEntity();
			if(victim != null)
				victim.damage(0.1);
		}
	}
	
	@EventHandler
	public static void OnPlayerDeath(PlayerDeathEvent e) 
	{
		//xd
		e.getDrops().clear();
		if(!PlayerManager.onlinePlayers.containsKey(e.getEntity()))
			return;
		
		saveOnDeath(e.getEntity());
		if(e.getEntity() == e.getEntity().getKiller())
			e.setDeathMessage(StringManager.Colorize("&cGracz &l" + e.getEntity().getDisplayName() + "&c popelnil samobojstwo."));
	}
	
	public static void saveOnDeath(Player p) 
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
		PlayerManager.getPlayer(p).ClearInventory();
		PlayerManager.getPlayer(p).setProperty("LatestDeathPoint", p.getLocation(), true);
		PlayerManager.getPlayer(p).setProperty("LatestDeathTime", System.currentTimeMillis(), true);
		PlayerManager.getPlayer(p).saveProfile();
	}
	
	@EventHandler
	public static void OnPlayerRespawn(PlayerRespawnEvent e) 
	{
		Location bedLoc = e.getPlayer().getBedSpawnLocation();
		FileConfiguration fc = FileManager.getConfig("spawn.yml");
		Location spawnLoc = null;
		if(fc != null)
			spawnLoc = FileManager.getConfig("spawn.yml").getLocation("location");
		
		Main.instance.getLogger().info("-|" + spawnLoc.getX() + " |-");
		
		if(Config.worldModule)
		{
			if(Config.respawnWorlds.contains(e.getPlayer().getWorld().getName()))
			{
				if(bedLoc != null && Config.bedRespawn)
					e.setRespawnLocation(bedLoc);
				else
					if(spawnLoc.getWorld().getName() == e.getPlayer().getWorld().getName())
						e.setRespawnLocation(spawnLoc);
					else
						e.setRespawnLocation(e.getPlayer().getWorld().getSpawnLocation());
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
}


