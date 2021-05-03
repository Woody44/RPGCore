package com.woody.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.woody.core.Config;
import com.woody.core.util.StringManager;

public class Announce implements Listener{
	
	@EventHandler
	public void OnPlayerJoin(PlayerJoinEvent e) 
	{
		Player player = e.getPlayer();
		if(Config.announceJoin)
			if(player.hasPlayedBefore())
				if(!Config.announceFriendsOnly)
					e.setJoinMessage(StringManager.Colorize(StringManager.FillPlayer(Config.joinMessage, player)));
				else
				{
					e.setJoinMessage(null);
					e.setJoinMessage("[DEBUG] Friends Only: " + StringManager.NoColors(StringManager.FillPlayer(Config.joinMessage, player)));
				}
			else
				if(Config.announceFirstJoin)
					e.setJoinMessage(StringManager.Colorize(StringManager.FillPlayer(Config.firstJoinMessage, player)));
				else
					e.setJoinMessage(StringManager.Colorize(StringManager.FillPlayer(Config.joinMessage, player)));
		else
			e.setJoinMessage(null);
	}
	
	@EventHandler
    public void onPlayerQuit(PlayerQuitEvent e)
    {
		Player player = e.getPlayer();
		if(Config.announceLeft)
			e.setQuitMessage(StringManager.Colorize(StringManager.FillPlayer(Config.leftMessage, player)));
		else
			e.setQuitMessage(null);
    }
}
