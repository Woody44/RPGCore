package com.rpg.core.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import com.rpg.core.CoreConfig;
import com.rpg.core.framework.ChatManager;
import com.rpg.core.framework.DatabaseManager;

public class Basics implements Listener
{
	@EventHandler
	public void OnHunger(FoodLevelChangeEvent e)
	{
		e.setCancelled(true);
	}
	
	@EventHandler
	public void OnFallDamage(EntityDamageEvent e ) 
	{
		if(e.getCause() == DamageCause.FALL)
		{
			e.setDamage(e.getDamage() * CoreConfig.fallDamageMultiplier);
		}
	}
	
	@EventHandler
	public void OnChat(AsyncPlayerChatEvent e) 
	{
		String originalMessage = e.getMessage();
		int lvl = DatabaseManager.GetPlayerExp(e.getPlayer().getUniqueId().toString());
		if(lvl < 10) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(ChatManager.GetColorized(ChatManager.FillVars(CoreConfig.chatLowLvlMessage, e.getPlayer(), originalMessage)));
		}
		else
			e.setFormat(ChatManager.GetColorized(ChatManager.FillVars(CoreConfig.chatMessageFormat, e.getPlayer(), originalMessage)));
	}
	
	@EventHandler
	public void OnMobDeath(EntityDeathEvent e)
	{
		e.getDrops().clear();
		e.setDroppedExp(0);
	}
}
