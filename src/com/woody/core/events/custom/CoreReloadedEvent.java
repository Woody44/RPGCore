package com.woody.core.events.custom;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CoreReloadedEvent extends Event{

	private static final HandlerList HANDLERS_LIST = new HandlerList();
	
	public CoreReloadedEvent() {

	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
