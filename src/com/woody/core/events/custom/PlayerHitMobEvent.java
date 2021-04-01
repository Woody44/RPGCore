package com.woody.core.events.custom;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerHitMobEvent extends Event implements Cancellable{

	private boolean isCanceled;
	private static final HandlerList HANDLERS_LIST = new HandlerList();
	
	private Player attacker;
    private LivingEntity victim;
    private double damage;
	public PlayerHitMobEvent(Player _attacker, LivingEntity _victim, double _damage) {
        attacker = _attacker;
        victim = _victim;
        damage = _damage;
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
	
	public Player getAttacker() 
	{
		return attacker;
	}

	public LivingEntity getVictim()
	{
		return victim;
	}

    public double getDamage()
    {
        return damage;
    }

    public void setDamage(double _value)
    {
        damage = _value;
    }
}
