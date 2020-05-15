package com.rpg.core.events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.rpg.core.Main;

import org.bukkit.event.block.Action;

public class OnCooldown implements Listener{
	public Main main;
	public OnCooldown(Main main)
	{
		this.main = main;
	}
	private ArrayList<Player> cooldown = new ArrayList<>();

	@EventHandler
	public void OnCooldown(PlayerInteractEvent event) 
	{
		Player player = event.getPlayer();
		Action action = event.getAction();
		if (!cooldown.contains(player)) 
		{
			if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) 
			{
				if (player.getItemInHand().getType() == Material.END_ROD) 
				{
					player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 50, 5));
					cooldown.add(player);
					Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> 
					{
						cooldown.remove(player);
					}, 200);
				}
			}
		} 
		else 
		{
			player.sendMessage(ChatColor.RED + "CZEKAJ 10 SEKUND");
		}
	}
}
