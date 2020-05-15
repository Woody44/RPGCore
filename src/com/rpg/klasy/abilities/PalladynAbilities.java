package com.rpg.klasy.abilities;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.rpg.core.ItemManager;

public class PalladynAbilities implements Listener
{
	@EventHandler
	private void useFirstAbility(PlayerInteractEvent e)
	{
		ItemStack firstAbility = ItemManager.createItemStack(Material.BOOK, "§6Regeneracja");
		if(e.getItem() == firstAbility)
		{
			e.setCancelled(true);
		}
	}
}
