package com.woody.core.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.woody.core.util.CooldownManager;

public class Test implements Listener{
	
	@EventHandler
	public void PlayerClickEvent(PlayerInteractEvent e) 
	{
		if(e.getPlayer().getInventory().getItemInMainHand().getType() == Material.GRASS_BLOCK && CooldownManager.getCooldown(e.getPlayer(), "GRASS", true) <= 0)
		{
			CooldownManager.cooldown(e.getPlayer(), "GRASS", 3);
		}
	}

}
