package com.rpg.items.playerstats;

import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class Lifesteal {
	
	public void Use(Player player, float damage, float multiplier) 
	{
		float healAmount = damage * multiplier;
		if(player.getHealth() + healAmount > player.getMaxHealth())
			player.setHealth(player.getMaxHealth());
		player.setHealth(player.getHealth() + healAmount);
		player.sendRawMessage("Healed by " + healAmount);
		
		int partCount = 0;
		if(healAmount <= 1)
			partCount = 1;
		else
			partCount = (int)healAmount;
		player.getWorld().spawnParticle(Particle.HEART, player.getLocation(),partCount);
	}
}
