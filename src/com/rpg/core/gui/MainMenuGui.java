package com.rpg.core.gui;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MainMenuGui {
	public static HashMap<ItemStack, Inventory> menus = new HashMap<>();
	public Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST, "Menu Glowne");
	
	public MainMenuGui(Player p) 
	{
		init();
		p.openInventory(inv);
	}
	
	public void init() 
	{
		for(ItemStack k : menus.keySet()) 
		{
			inv.addItem(k);
		}
	}
}
