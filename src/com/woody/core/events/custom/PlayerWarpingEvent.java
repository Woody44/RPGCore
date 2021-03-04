package com.woody.core.events.custom;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerWarpingEvent extends Event implements Cancellable{

	private boolean isCanceled;
	private static final HandlerList HANDLERS_LIST = new HandlerList();
	
	private Player p;
	private Location from, to;
	private long cooldownAfter;
	public PlayerWarpingEvent(Player _p, Location _from, Location _to, long _cooldownAfter) {
		p = _p;
		from = _from;
		to = _to;
		cooldownAfter = _cooldownAfter;
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
	
	public long getCooldown() 
	{
		return cooldownAfter;
	}
	
	public void setCooldown(long value) 
	{
		cooldownAfter = value;
	}
	
	public Location getWarpLocation() 
	{
		return to;
	}
	
	public Location getPlayerLocation() 
	{
		return from;
	}

	public Player getPlayer()
	{
		return p;
	}
}
