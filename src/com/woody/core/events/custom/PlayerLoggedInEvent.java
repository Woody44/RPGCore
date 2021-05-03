package com.woody.core.events.custom;

import com.woody.core.types.CustomPlayer;
import com.woody.core.types.Profile;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerLoggedInEvent extends Event{

	private CustomPlayer player;
	private static final HandlerList HANDLERS_LIST = new HandlerList();
	
	public PlayerLoggedInEvent(CustomPlayer _cp) {
        player = _cp;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
	
    public Profile getProfile()
    {
        return player.getProfile();
    }

	public int getProfileID() 
	{
		return player.getProfile().id;
	}
	
	public Player getPlayer()
	{
		return player.player;
	}

    public CustomPlayer getCustomPlayer()
    {
        return player;
    }
}
