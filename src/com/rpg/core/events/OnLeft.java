package com.rpg.core.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.rpg.core.CoreConfig;

public class OnLeft implements Listener{
	@EventHandler
	public void OnLeft(PlayerQuitEvent event) 
	{
		if (!CoreConfig.announceLeft)
			event.setQuitMessage(null);
		else
		{
			if(CoreConfig.leftMessage != null)
			{
				event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', CoreConfig.errorColor + CoreConfig.leftMessage.replaceAll("PLAYER", event.getPlayer().getName())));
			}
			else 
			{
				return;
			}
		}
	}
}
