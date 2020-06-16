package com.rpg.core.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import com.rpg.core.CoreConfig;
import com.rpg.core.Main;

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
						b.breakNaturally();
						b.setType(Material.BEDROCK);
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> { 
							b.setType(mat);
						}, 10 * 20);
					}
				}
			}
			
			
		}
		
	}
}
