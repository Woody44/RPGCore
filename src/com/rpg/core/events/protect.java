package com.rpg.core.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.rpg.core.CoreConfig;
import com.rpg.core.Main;
import com.rpg.core.framework.ChatManager;
import com.rpg.core.framework.Logger;

public class protect implements Listener{
	
	@EventHandler
	public void OnExplosion(EntityExplodeEvent event) 
	{
		if(!CoreConfig.preventExplosions)
			return;
		if(!CoreConfig.preventExplosionsWorlds.contains(event.getEntity().getWorld().getName()))
			return;
		
		if(event.getEntityType() == EntityType.PRIMED_TNT) {
			event.setCancelled(true);
			List<Block> Blocks = event.blockList();
			for(Block b : Blocks) 
			{
				Material mat = b.getType();
				if(mat != Material.BEDROCK) {
					if(mat == Material.TNT) 
					{
						b.setType(Material.AIR);
						b.getWorld().spawnEntity(b.getLocation(), EntityType.PRIMED_TNT);
					}
					else 
					{
						if(CoreConfig.dropExplosions)
						{
							float chance = (float)Math.random();
							Logger.LogInfo(chance + "/" + CoreConfig.explosionsDropRate);
							if(chance <= CoreConfig.explosionsDropRate)
								b.breakNaturally();
						}
						b.setType(Material.BEDROCK);
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> { 
							b.setType(mat);
						}, 10 * 20);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void OnChestOpen(PlayerInteractEvent event)
	{
		if(CoreConfig.logChests)
			if(event.getClickedBlock().getType() == Material.CHEST)
			{
				Player player = event.getPlayer();
				Location location = event.getClickedBlock().getLocation();
				String message = ChatManager.FillVars(ChatManager.FillVars(CoreConfig.chestLoggerFormat, player), location);
				Logger.LogInfo(message);
			}
	}
}
