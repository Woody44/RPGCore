package com.woody.core.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.woody.core.Config;
import com.woody.core.util.PlayerManager;

public class Economy implements Listener{
	
	@EventHandler
    public void PlayerJoin(PlayerJoinEvent e)
    {
		if(!e.getPlayer().hasPlayedBefore())
		{
			PlayerManager.getOnlinePlayer(e.getPlayer()).getProfile().addMoney(Config.startBalance);
		}
    }
}
