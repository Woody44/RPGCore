package com.rpg.core.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.rpg.core.CoreConfig;
import com.rpg.core.framework.StringManager;
import com.rpg.core.framework.FileManager;
import com.rpg.core.framework.ItemManager;
import com.rpg.core.framework.PlayerManager;

public class Announcing implements Listener{
	
	@EventHandler
    public void OnJoin(PlayerJoinEvent event)
    {
		Player player = event.getPlayer();
		String uuid = event.getPlayer().getUniqueId().toString();
		String welcomeMessage;
		
		if(PlayerManager.getPlayer(uuid) == null) 
		{
			welcomeMessage = StringManager.Colorize(StringManager.FillPlayer(CoreConfig.firstJoinMessage, event.getPlayer()));
			FileManager.CreatePlayerFile(uuid, player.getDisplayName());
			if(player.getServer().getPluginManager().isPluginEnabled("RPGLoot"))
				player.getInventory().addItem(ItemManager.createItemStack(Material.CHEST, "§6Skrzynia nowego gracza", new String[] {""}, 1));
		}
		else
		{
			welcomeMessage = StringManager.Colorize(StringManager.FillPlayer(CoreConfig.joinMessage, event.getPlayer()));
		}
		
		if (!CoreConfig.announceJoin)
			event.setJoinMessage(null);
		else
		{
			if(CoreConfig.joinMessage != null)
			{
				Bukkit.broadcastMessage(welcomeMessage);
			}
			else 
			{
				return;
			}
		}
		
		player.setWalkSpeed((float)CoreConfig.defPlayerSpeed);
		PlayerManager.UpdateExpBar(player, PlayerManager.getPlayer(uuid).experience);
    }
	
	@EventHandler
	public void OnLeft(PlayerQuitEvent event) 
	{
		event.setQuitMessage(null);
		if(CoreConfig.leftMessage != null && CoreConfig.announceLeft)
		{
			Bukkit.broadcastMessage(StringManager.Colorize(StringManager.FillPlayer(CoreConfig.leftMessage, event.getPlayer())));
		}
		else 
		{
			return;
		}
	}
}
