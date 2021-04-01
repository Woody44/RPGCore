package com.woody.core.events.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ManaRegenEvent extends Event implements Cancellable{

	private boolean isCanceled;
	private static final HandlerList HANDLERS_LIST = new HandlerList();
	
	private double amount;
	private Player player;
	public ManaRegenEvent(Player _p, double _amount) {
		amount = _amount;
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
	
	public double getMana() 
	{
		return amount;
	}
	
	public void setMana(double value) 
	{
		amount = value;
	}

	public Player getPlayer()
	{
		return player;
	}
}
