package com.woody.core.events.custom;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerSendFriendRequest extends Event implements Cancellable{

	private boolean isCanceled;
	private static final HandlerList HANDLERS_LIST = new HandlerList();
	
	private String p1, p2;
	public PlayerSendFriendRequest(String _uuid, String _uuid2) {
		p1 = _uuid;
        p2 = _uuid2;
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
	
    public String getSender()
    {
        return p1;
    }

    public String getReceiver()
    {
        return p2;
    }
}
