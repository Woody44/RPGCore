package com.woody.core.events.custom;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerProfileSaveEvent extends Event implements Cancellable{

	private boolean isCanceled;
	private static final HandlerList HANDLERS_LIST = new HandlerList();
	
	private HashMap<String, Object> data;
	private Player player;
	public PlayerProfileSaveEvent(Player _p, HashMap<String, Object> _data) {
		data = _data;
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
	
	public HashMap<String, Object> getData() 
	{
		return data;
	}
	
	public void setData(HashMap<String, Object> _data) 
	{
		data = _data;
	}

	public Player getPlayer()
	{
		return player;
	}
}