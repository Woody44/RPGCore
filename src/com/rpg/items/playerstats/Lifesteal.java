package com.rpg.items.playerstats;

import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class Lifesteal {
	
	public void Use(Player player, float damage, float multiplier) 
	{
		float healAmount = damage * multiplier;
		if(player.getHealth() + healAmount > player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue())
			player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
		else
			player.setHealth(player.getHealth() + healAmount);
		
		int partCount = 0;
		if(healAmount <= 1)
			partCount = 1;
		else
			partCount = (int)healAmount;
		player.getWorld().spawnParticle(Particle.HEART, player.getLocation(),partCount);
	}
}
