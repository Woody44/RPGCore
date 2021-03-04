package com.woody.core.events.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerProfileCreated extends Event implements Cancellable{

	private boolean isCancelled;
	private Player player;
	private int profileId;
	private static final HandlerList HANDLERS_LIST = new HandlerList();
	public PlayerProfileCreated(Player _p, int _id) {
		player = _p;
		profileId = _id;
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
		return profileId;
	}
	
	public Player getPlayer()
	{
		return player;
	}
}
