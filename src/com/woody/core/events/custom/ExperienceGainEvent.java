package com.woody.core.events.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ExperienceGainEvent extends Event implements Cancellable{

	private boolean isCanceled;
	private static final HandlerList HANDLERS_LIST = new HandlerList();
	
	private long xp;
	private Player player;
	public ExperienceGainEvent(Player _p, long exp) {
		xp = exp;
		player = _p;
	}

	@Override
	public boolean isCancelled() {
		return isCanceled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		isCanceled = arg0;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
	
	public long getExp() 
	{
		return xp;
	}
	
	public void setExp(long value) 
	{
		xp = value;
	}

	public Player getPlayer()
	{
		return player;
	}
}
