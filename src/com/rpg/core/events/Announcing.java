package com.rpg.core.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.rpg.core.CoreConfig;
import com.rpg.core.framework.ChatManager;
import com.rpg.core.framework.DatabaseManager;
import com.rpg.core.framework.InventoryInfo;
import com.rpg.core.framework.PlayersManager;
import com.rpg.core.framework.CustomPlayer;
import com.rpg.core.framework.Wallet;

public class Announcing implements Listener{
	
	@EventHandler
    public void OnJoin(PlayerJoinEvent event)
    {
		CustomPlayer pi = new CustomPlayer();
		pi.player = event.getPlayer();
		String uuid = pi.player.getUniqueId().toString();
		pi.UUID = uuid;
		if (DatabaseManager.GetPlayerInfo(uuid) == null) {
			pi.UUID = uuid;
			pi.Klasa = 0;
			pi.KlasaLevel = 0;
			pi.Experience = 0;
			pi.wallet = new Wallet();
			pi.wallet.SetOwner(uuid);
			pi.wallet.Money = 0;
			pi.inventoryInfo = new InventoryInfo();
			pi.inventoryInfo.UUID = uuid;
			pi.inventoryInfo.bracelet_0 = 0;
			pi.inventoryInfo.bracelet_1 = 0;
			pi.inventoryInfo.earring_0 = 0;
			pi.inventoryInfo.earring_1 = 0;
			pi.inventoryInfo.necklake_0 = 0;
			pi.inventoryInfo.ring_0 = 0;
			pi.inventoryInfo.ring_1 = 0;
			DatabaseManager.RegisterNewPlayer(pi, pi.inventoryInfo, pi.wallet);
			}
		else 
		{
			pi = DatabaseManager.GetPlayerInfo(pi.player.getUniqueId().toString());
			pi.player = event.getPlayer();
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
        
		pi.player.setWalkSpeed((float)CoreConfig.defPlayerSpeed);
		//Logger.LogInfo(pi.UUID);
		PlayersManager.RegisterPlayer(pi);
    }
	
	@EventHandler
	public void OnLeft(PlayerQuitEvent event) 
	{
		PlayersManager.SendPlayerUpdate(PlayersManager.GetOnlinePlayer(event.getPlayer().getUniqueId().toString()));
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
		PlayersManager.UnregisterPlayer(event.getPlayer().getUniqueId().toString());
	}
}
