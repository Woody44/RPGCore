package com.woody.core.events.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ChatBadWordEvent extends Event implements Cancellable {

    public boolean isMessageCancelled = false;
    private Player sender;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    
    public ChatBadWordEvent(Player _sender)
    {
        sender = _sender;
    }
    
    @Override
    public boolean isCancelled() {
        return isMessageCancelled;
    }

    @Override
    public void setCancelled(boolean arg0) {
        isMessageCancelled = arg0;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public Player getSender()
    {
        return sender;
    }
}
