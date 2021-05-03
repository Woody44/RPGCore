package com.woody.core.events.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerProfileLoadedEvent  extends Event{

	private Player player;
	private int profile;
	private static final HandlerList HANDLERS_LIST = new HandlerList();
	
	public PlayerProfileLoadedEvent(Player _p, int _prof) {
		player = _p;
		profile = _prof;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
	
	public int getProfileID() 
	{
		return profile;
	}
	
	public Player getPlayer()
	{
		return player;
	}
}
