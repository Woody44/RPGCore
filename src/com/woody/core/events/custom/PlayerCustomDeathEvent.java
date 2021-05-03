package com.woody.core.events.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerCustomDeathEvent extends Event{

	private static final HandlerList HANDLERS_LIST = new HandlerList();
	
	private long xp;
    private int money;
    private boolean suicide;
    private boolean keepInventory;
    private boolean saveLoc;
	private Player player;
    /**
     * Called when player dies. Provides data needed to use correctly Woody Core 
     * @param _p - Player which died
     * @param _lostExp - Amount of experience points to remove from player
     * @param _lostMoney - Amonut of money player losses / drops
     * @param _isSuicide - True if player killed himself / fell from a high place, etc
     */
	public PlayerCustomDeathEvent(Player _p, long _lostExp, int _lostMoney, boolean _isSuicide, boolean _keepInventory, boolean _saveDeathLocation) {
		player = _p;
        xp = _lostExp;
        money = _lostMoney;
        suicide = _isSuicide;
        keepInventory = _keepInventory;
        saveLoc = _saveDeathLocation;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS_LIST;
	}

	public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
	
	public long getLostExp() 
	{
		return xp;
	}
	
	public void setLostExp(long value) 
	{
		xp = value;
	}

	public Player getPlayer()
	{
		return player;
	}

    public int getLostMoney()
    {
        return money;
    }

    public void setLostMoney(int value)
    {
        money = value;
    }

    public boolean getKeepInventory()
    {
        return keepInventory;
    }

    public void setKeepInventory(boolean value)
    {
        keepInventory = value;
    }

    public void setLocationSave(boolean value)
    {
        saveLoc = value;
    }

    public boolean getLocationSave()
    {
        return saveLoc;
    }

    public boolean isSuicide()
    {
        return suicide;
    }
}
