package com.woody.core.events.custom;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerSuicideEvent extends Event implements Cancellable{

	private boolean isCanceled;
	private static final HandlerList HANDLERS_LIST = new HandlerList();
	
	private Player p;
	private Location deadLoc;
	private long droppedExp;
	private  boolean cmd;
	public PlayerSuicideEvent(Player _p, Location _deadLoc, long _droppedExp, boolean _cmd) {
		p = _p;
		deadLoc = _deadLoc;
		droppedExp = _droppedExp;
		cmd = _cmd;
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
	
	public long getDroppedExp() 
	{
		return droppedExp;
	}
	
	public void setDroppedExp(long value) 
	{
		droppedExp = value;
	}
	
	public Location getDeathLocation() {
		return deadLoc;
	}
	
	public Player getPlayer() 
	{
		return p;
	}
	
	public boolean isCmd() 
	{
		return cmd;
	}
}
