package com.woody.core.gui;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.woody.core.util.FileManager;

public class MainMenuGUI{
	
	Inventory inv;
	ItemStack[] icons;
	
	public void Init() 
	{
		ArrayList<ItemStack> toReturn = new ArrayList<>();
		ConfigurationSection cs = FileManager.getConfig("menus/MainMenu.yml").getConfigurationSection("items");
		for(String node: cs.getKeys(false))
			toReturn.add(cs.getItemStack(node));
		
		int size = FileManager.getConfig("menus/MainMenu.yml").getInt("size");
		icons = new ItemStack[size];
		inv = Bukkit.createInventory(null, size, "Menu Glowne");
		icons = toReturn.toArray(icons);
	}
	
	//ItemStack[] icons = ; new ItemStack[27];
	/*public void addIcon(ItemStack icon)
	{
		for(int i = 0; i < icons.length; i++) {
			if(icons[i] == null)
				icons[i] = icon;
		}
	}
	
	public void setIcon(ItemStack icon, int index, boolean override)
	{
		if(override)
			icons[index] = icon;
		else
			if(icons[index] == null)
				icons[index] = icon;
			else
				return;
	}*/
	
	public void open(Player player) 
	{
		Init();
		inv.setContents(icons);
	}
}
