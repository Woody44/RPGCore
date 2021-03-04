package com.woody.core.events.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.woody.core.types.CustomPlayer;
import com.woody.core.util.PlayerManager;
public class LevelUpEvent extends Event implements Cancellable{
	private int newLevel;
	private long xp;
	private static final HandlerList HANDLERS_LIST = new HandlerList();
	private boolean isCanceled;
	private Player player;
	public LevelUpEvent(Player _player, int _level, long _xp)
	{
		newLevel = _level;
		player = _player;
		xp = _xp;
	}
	
	@Override
	public boolean isCancelled() {
		return isCanceled;
	}

	@Override
	public void setCancelled(boolean _canceled) {
		isCanceled = _canceled;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}
	
	public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
	
	public int getLevel() 
	{
		return newLevel;
	}
	
	public long getXP() 
	{
		return xp;
	}
	
	public Player getPlayer() 
	{
		return player;
	}
	
	public CustomPlayer getCustomPlayer() 
	{
		return PlayerManager.getOnlinePlayer(player);
	}
}
