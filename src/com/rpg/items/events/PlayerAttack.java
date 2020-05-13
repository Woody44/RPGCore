package com.rpg.items.events;

import java.util.ArrayList;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import com.rpg.items.playerstats.*;

public class PlayerAttack implements Listener{

	@EventHandler
	public void PlayerAtttack(EntityDamageByEntityEvent event) 
	{
		if(event.getDamager().getType() == EntityType.PLAYER)
		{
			Player attacker = (Player)event.getDamager();
			ArrayList<String> lore = (ArrayList<String>) attacker.getInventory().getItemInMainHand().getItemMeta().getLore();
			for(String line : lore) 
			{
				if(line.contains("Lifesteal")) 
				{
					line = line.replaceAll("Lifesteal:", "");
					float multiplier = Float.parseFloat(line);
					Lifesteal ls = new Lifesteal();
					
					ls.Use(attacker, (float)event.getDamage(), multiplier);
				}
			}
		}
	}
	
}
