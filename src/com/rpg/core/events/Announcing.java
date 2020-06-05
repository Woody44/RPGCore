package com.rpg.core.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.rpg.core.CoreConfig;
import com.rpg.core.framework.ChatManager;
import com.rpg.core.framework.DatabaseManager;
import com.rpg.core.framework.InventoryInfo;
import com.rpg.core.framework.ItemManager;
import com.rpg.core.framework.PlayerInfo;
import com.rpg.core.framework.Wallet;

public class Announcing implements Listener{
	
	@EventHandler
    public void OnJoin(PlayerJoinEvent event)
    {
		ItemManager.createItemStack(Material.ACACIA_BOAT, "xD", "a");
		String uuid = event.getPlayer().getUniqueId().toString();
		if (DatabaseManager.GetPlayerInfo(uuid) == null) {
			
			PlayerInfo pi = new PlayerInfo();
			pi.UUID = uuid;
			pi.Klasa = 0;
			pi.KlasaLevel = 0;
			pi.Experience = 0;
			InventoryInfo ii = new InventoryInfo();
			ii.UUID = uuid;
			ii.bracelet_0 = 0;
			ii.bracelet_1 = 0;
			ii.earring_0 = 0;
			ii.earring_1 = 0;
			ii.necklake_0 = 0;
			ii.ring_0 = 0;
			ii.ring_1 = 0;
			Wallet w = new Wallet();
			w.SetOwner(uuid);
			w.Money = 0;
			DatabaseManager.RegisterNewPlayer(pi, ii, w);
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
