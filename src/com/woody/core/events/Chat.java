package com.woody.core.events;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.util.Vector;

import com.woody.core.Config;
import com.woody.core.events.custom.ChatBadWordEvent;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;

public class Chat extends StringManager implements Listener{

	@EventHandler
	public void OnChat(AsyncPlayerChatEvent e) 
	{
		e.setCancelled(true);
		String originalMessage = e.getMessage();

		if(originalMessage.startsWith("/"))
			return;
		
		Player sender = e.getPlayer();

		if(StringManager.HasBadWords(originalMessage))
		{
			ChatBadWordEvent event = new ChatBadWordEvent(sender);
			if(event.isMessageCancelled)
				return;
		}
		
		int lvl = PlayerManager.getOnlinePlayer(sender).getProfile().getLevel();
		
		
		if(lvl < Config.chatLvlMin && Config.restrictChat)
		{
			e.getPlayer().sendMessage(StringManager.Colorize(StringManager.FillExp(Config.chatLowLvlMessage, sender)));
			return;
		}
		
		boolean canColor = Config.colors && sender.hasPermission("woody.chat.color");
		boolean canPing = Config.allowPings && sender.hasPermission("woody.chat.ping");
		if(canColor)
			originalMessage = StringManager.Colorize(originalMessage);
		else
			originalMessage = StringManager.NoColors(originalMessage);
		
		System.out.println("[chat] " + sender.getName() + ":" +  originalMessage);
		
		if(canPing)
			for(Player receiver : Bukkit.getOnlinePlayers()) 
			{
				if(originalMessage.contains(receiver.getName()))
					receiver.playSound(receiver.getLocation(), Sound.UI_BUTTON_CLICK, 2, 0.8f);
				else if(originalMessage.contains("@"+StringManager.FillGroup("{GROUP}", receiver)))
					receiver.playSound(receiver.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 2, 1.6f);
				else if(originalMessage.contains("@everyone"))
					receiver.playSound(receiver.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 2f);
				receiver.sendMessage(Colorize(StringManager.FillChat(Config.chatMessageFormat, e.getPlayer(), originalMessage, receiver)));
			}
		else
		{
			Bukkit.broadcastMessage(Colorize(StringManager.FillChat(Config.chatMessageFormat, e.getPlayer(), originalMessage, null)));
		}
		
		if(Config.chatSound) 
		{
			e.getPlayer().getWorld().playSound(e.getPlayer().getLocation().add(new Vector(0,1,0)), Sound.ENTITY_VILLAGER_TRADE, 2, 0.75f);
		}
	}
}
