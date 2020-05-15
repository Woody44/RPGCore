package com.rpg.core.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import com.rpg.core.economy.Wallet;
import com.rpg.core.CoreConfig;
import com.rpg.core.DatabaseManager;

public class OnJoin implements Listener{

	@EventHandler
    public void OnJoin(PlayerJoinEvent event)
    {
		String uuid = event.getPlayer().getUniqueId().toString();
		if (DatabaseManager.GetPlayerWallet(uuid).uuid == null) {
			Wallet w = new Wallet();
			w.SetOwner(uuid);
			w.Money = 0;
			DatabaseManager.AddPlayerWallet(w);
			
			System.out.println("[RPGcore - Eco] Tworzenie Portfela dla Gracza " + event.getPlayer().getName());
		}
		
		if (!CoreConfig.announceJoin)
			event.setJoinMessage(null);
		else
		{
			if(CoreConfig.joinMessage != null)
			{
				event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', CoreConfig.otherColor + CoreConfig.joinMessage.replaceAll("PLAYER", event.getPlayer().getName())));
			}
			else 
			{
				return;
			}
		}
        //TO DO
    }
}
