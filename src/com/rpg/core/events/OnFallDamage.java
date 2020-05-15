package com.rpg.core.events;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.rpg.core.CoreConfig;

public class OnFallDamage implements Listener{
	
	public void OnFallDamage(EntityDamageEvent event ) 
	{
		if(event.getCause() == DamageCause.FALL)
			event.setDamage(event.getDamage() * CoreConfig.fallDamageMultiplier);
	}

}
