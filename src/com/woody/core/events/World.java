package com.woody.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerJoinEvent;

import com.woody.core.Config;

public class World implements Listener{
	
	@EventHandler
    public void PlayerJoin(PlayerJoinEvent event)
    {
		
		Player player = event.getPlayer();
		player.setWalkSpeed((float)Config.defPlayerSpeed);
    }
	
	@EventHandler
	public void OnMobDeath(EntityDeathEvent e)
	{
		if(!Config.mobDrop)
			e.getDrops().clear();
		if(!Config.mobVanillaExp)
			e.setDroppedExp(0);
		else
			e.setDroppedExp((int)(e.getDroppedExp() * Config.mobVanillaExpMultiplier));
	}
	
	@EventHandler
	public void OnFallDamage(EntityDamageEvent e ) 
	{
		if(e.getCause() == DamageCause.FALL)
			e.setDamage(e.getDamage() * Config.fallDamageMultiplier);
	}
}
