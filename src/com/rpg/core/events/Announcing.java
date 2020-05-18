package com.rpg.core.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.rpg.core.CoreConfig;
import com.rpg.core.Main;
import com.rpg.core.framework.ChatManager;
import com.rpg.core.framework.DatabaseManager;
import com.rpg.core.framework.Wallet;

public class Announcing implements Listener{
	
	@EventHandler
    public void OnJoin(PlayerJoinEvent event)
    {
		String uuid = event.getPlayer().getUniqueId().toString();
		if (DatabaseManager.GetPlayerWallet(uuid).uuid == null) {
			Wallet w = new Wallet();
			w.SetOwner(uuid);
			w.Money = 0;
			DatabaseManager.AddPlayerWallet(w);
			
			Main.LogInfo("Eco", "Tworzenie Portfela dla Gracza " + event.getPlayer().getDisplayName());
		}
		
		if (!CoreConfig.announceJoin)
			event.setJoinMessage(null);
		else
		{
			if(CoreConfig.joinMessage != null)
			{
				event.setJoinMessage(ChatManager.GetColorized(ChatManager.FillVars(CoreConfig.joinMessage, event.getPlayer())));
			}
			else 
			{
				return;
			}
		}
        //TO DO
    }
	
	@EventHandler
	public void OnLeft(PlayerQuitEvent event) 
	{
		if (!CoreConfig.announceLeft)
			event.setQuitMessage(null);
		else
		{
			if(CoreConfig.leftMessage != null)
			{
				event.setQuitMessage(ChatManager.GetColorized(ChatManager.FillVars(CoreConfig.leftMessage, event.getPlayer())));
			}
			else 
			{
				return;
			}
		}
	}

}
