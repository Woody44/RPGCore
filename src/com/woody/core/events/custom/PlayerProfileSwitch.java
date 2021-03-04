package com.woody.core.events.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerProfileSwitch extends Event implements Cancellable{

	private boolean isCancelled;
	private Player player;
	private int profile;
	private static final HandlerList HANDLERS_LIST = new HandlerList();
	
	public PlayerProfileSwitch(Player _p, int _prof) {
		player = _p;
		profile = _prof;
	}

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		isCancelled = arg0;
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
