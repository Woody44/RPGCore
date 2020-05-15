package com.rpg.items.events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.rpg.core.ItemManager;
import com.rpg.items.playerstats.*;

public class PlayerAttack implements Listener{

	@EventHandler
	public void PlayerAtttack(EntityDamageByEntityEvent event) 
	{
		if(event.getDamager().getType() == EntityType.PLAYER)
		{
			Player attacker = (Player)event.getDamager();
			if(attacker.getInventory().getItemInMainHand() != null) {
				Float value = ItemManager.CheckLore(attacker.getInventory().getItemInMainHand(), "Lifesteal");
				if(value != 0)
				{
					Lifesteal ls = new Lifesteal();
					ls.Use(attacker, (float)event.getDamage(), value);
				}
			}
		}
	}
	
}
