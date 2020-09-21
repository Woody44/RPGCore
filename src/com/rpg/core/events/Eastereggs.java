package com.rpg.core.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Eastereggs implements Listener{

	@EventHandler
	public void onIgniteBlock(PlayerInteractEvent e) 
	{
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getPlayer().getInventory().getItemInMainHand().getType() == Material.FLINT_AND_STEEL)
		{
			Location loc = e.getClickedBlock().getLocation();
			if(loc.getBlock().getType() == Material.NETHERRACK && loc.subtract(new Vector(0,1,0)).getBlock().getType() == Material.NETHERRACK && loc.subtract(new Vector(0,2,0)).getBlock().getType() == Material.GOLD_BLOCK)
			{
				loc.getWorld().strikeLightning(loc);
				Bukkit.broadcastMessage("Herobrine: uoy ees I.");
			}
		}
	}
}
