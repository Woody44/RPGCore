package com.rpg.core;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class DefaultStuff implements Listener
{
	@EventHandler
	public void DefaultStuff(FoodLevelChangeEvent e)
	{
		e.setCancelled(true);
	}
}
