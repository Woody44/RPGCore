package com.woody.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.woody.core.Config;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;

public class OnJoin  implements Listener{
	
	@EventHandler
    public void PlayerJoin(PlayerJoinEvent event)
    {
		
		Player player = event.getPlayer();
		player.setWalkSpeed((float)Config.defPlayerSpeed);
		
		if(Config.announceJoin)
			if(player.hasPlayedBefore())
				event.setJoinMessage(StringManager.FillPlayer(Config.joinMessage, player));
			else
				if(Config.announceFirstJoin)
					event.setJoinMessage(StringManager.FillPlayer(Config.firstJoinMessage, player));
				else
					event.setJoinMessage(StringManager.FillPlayer(Config.joinMessage, player));
		else
			event.setJoinMessage(null);
		
		PlayerManager.registerOnlinePlayer(player);
    }
}
