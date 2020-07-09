package com.rpg.core.events;

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

import com.rpg.core.CoreConfig;
import com.rpg.core.Main;
import com.rpg.core.framework.Misc;

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
		if(!CoreConfig.preventExplosions)
			return;
		if(!CoreConfig.preventExplosionsWorlds.contains(event.getEntity().getWorld().getName()))
			return;
		
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
						if(Misc.Chance(CoreConfig.explosionsDropRate))
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
