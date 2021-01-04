package com.woody.core.events;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.woody.core.Config;
import com.woody.core.Main;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;

public class Chat extends StringManager implements Listener{

	@EventHandler
	public void OnChat(AsyncPlayerChatEvent e) 
	{
		e.setCancelled(true);
		String originalMessage = e.getMessage();
		
		if(originalMessage.startsWith("/login") || originalMessage.startsWith("/l") || originalMessage.startsWith("/register") || originalMessage.startsWith("/login"))
			return;
		
		if(Config.censor && StringManager.HasBadWords(originalMessage))
		{
			e.getPlayer().sendMessage(StringManager.Colorize("Ty Hultaju! Nie uzywaj takich slowek!"));
			return;
		}
		
		if(originalMessage.startsWith("/"))
			return;
		
		Player sender = e.getPlayer();
		int lvl = PlayerManager.onlinePlayers.get(sender).getLevel();
		if(lvl < Config.chatLvlMin && Config.restrictChat)
		{
			e.getPlayer().sendMessage(StringManager.Colorize(StringManager.FillExp(Config.chatLowLvlMessage, sender)));
			return;
		}
		
		boolean canColor = sender.hasPermission("core.chat.color");
		boolean canPing = Config.allowPings && sender.hasPermission("core.chat.ping");
		if(canColor)
			originalMessage = StringManager.Colorize(originalMessage);
		else
			originalMessage = StringManager.NoColors(originalMessage);
		
		Main.instance.getLogger().info("[chat] " + sender.getName() + ":" +  originalMessage);
		
		if(canPing)
			for(Player receiver : Bukkit.getOnlinePlayers()) 
			{
				if(originalMessage.contains(receiver.getName()))
					receiver.playSound(receiver.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 2, 1.6f);
				else if(originalMessage.contains("@"+StringManager.FillGroup("{GROUP}", receiver)))
					receiver.playSound(receiver.getLocation(), Sound.UI_BUTTON_CLICK, 2, 1.7f);
				else if(originalMessage.contains("@everyone"))
					receiver.playSound(receiver.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 1.8f);
				receiver.sendMessage(Colorize(StringManager.FillChat(Config.chatMessageFormat, e.getPlayer(), originalMessage, receiver)));
			}
		else
		{
			Bukkit.broadcastMessage(Colorize(StringManager.FillChat(Config.chatMessageFormat, e.getPlayer(), originalMessage, null)));
		}
	}
}
