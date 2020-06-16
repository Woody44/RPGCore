package com.rpg.core.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.rpg.core.CoreConfig;
import com.rpg.core.framework.ChatManager;
import com.rpg.core.framework.Logger;

public class OnChestOpen  implements Listener{

	@EventHandler
	public void OnChestOpen(PlayerInteractEvent event)
	{
		if(event.getClickedBlock().getType() == Material.CHEST)
		{
			Player player = event.getPlayer();
			Location location = event.getClickedBlock().getLocation();
			String message = ChatManager.FillVars(ChatManager.FillVars(CoreConfig.chestLoggerFormat, player), location);
			Logger.LogInfo(message);
		}
	}
}
