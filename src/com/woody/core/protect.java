package com.woody.core;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;

import com.woody.core.util.ExternalTools;

public class protect implements Listener{
	
	@EventHandler
	public void OnEntityDamage(EntityDamageByEntityEvent event) 
	{
		if(event.getCause() == DamageCause.ENTITY_EXPLOSION || event.getCause() == DamageCause.BLOCK_EXPLOSION) {
		    if (event.getEntity().getType() == EntityType.DROPPED_ITEM) {
		        event.setCancelled(true);
		    }
		}
	}
	
	@EventHandler
	public void OnExplosion(EntityExplodeEvent event) 
	{
		if(!Config.preventExplosions)
			return;
		if(!Config.preventExplosionsWorlds.contains(event.getEntity().getWorld().getName()))
			return;
		
		event.setCancelled(true);
		List<Block> Blocks = event.blockList();
		
		int n = 1;
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
					if(Config.dropExplosions)
					{
						if(ExternalTools.Chance(Config.explosionsDropRate))
							b.breakNaturally();
					}
					if(b.getType() != Material.CRACKED_STONE_BRICKS && b.getType() != Material.INFESTED_CRACKED_STONE_BRICKS && b.getType() != Material.CRACKED_POLISHED_BLACKSTONE_BRICKS && b.getType() != Material.CRACKED_NETHER_BRICKS && b.getType() != Material.GLASS && b.getType() != Material.GLASS_PANE && b.getType() != Material.COBBLESTONE && b.getType() != Material.TALL_GRASS && b.getType() != Material.GRASS)
						b.setType(Material.BEDROCK);
					else
						b.setType(Material.AIR);
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> {
						b.setType(mat);
					}, n * (int)(20 *Config.renewTime));
				}
			}
			n ++;
		}
	}
}
