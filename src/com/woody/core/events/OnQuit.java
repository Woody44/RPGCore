package com.woody.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.woody.core.Config;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;

public class OnQuit implements Listener{
	
	@EventHandler
    public void PlayerQuit(PlayerQuitEvent event)
    {
		
		Player player = event.getPlayer();
		if(Config.announceLeft)
			event.setQuitMessage(StringManager.FillPlayer(Config.leftMessage, player));
		else
			event.setQuitMessage(null);
		PlayerManager.unregisterOnlinePlayer(player);
    }
}
