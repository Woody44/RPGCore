package com.rpg.klasy.abilities;

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

import com.rpg.core.DatabaseManager;
import com.rpg.core.Main;

public class PalladynAbilities implements Listener
{
	public Main main;
	private ArrayList<Player> cooldown = new ArrayList<>();
	@EventHandler
	public void useFirstAbility(PlayerInteractEvent e) throws InterruptedException
	{
		Player player = (Player) e.getPlayer();
		PotionEffect effect = new PotionEffect(PotionEffectType.REGENERATION, 10, 3);
		if(e.getMaterial() == Material.BOOK)
		{
			e.setCancelled(true);
			if(!cooldown.contains(player))
			{
				if(DatabaseManager.GetPlayerClassLevel(player.getUniqueId().toString()) == 1)
				{
					effect.apply(player);
					cooldown.add(player);
					Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> 
					{
						cooldown.remove(player);
					}, 10);
				}
			}
			else
			{
				player.sendMessage(ChatColor.RED + "CZEKAJ 10 SEKUND");
			}
		}
	}
}
