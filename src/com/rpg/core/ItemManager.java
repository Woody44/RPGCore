package com.rpg.core;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

public class ItemManager {

	public static String GetLore(ItemStack item, String value) 
	{
		ArrayList<String> lore = (ArrayList<String>) item.getItemMeta().getLore();
		for(String line : lore) 
		{
			if(line.contains(value)) 
			{
				return line;
			}
		}
		return null;
	}
	
	public static float CheckLore(ItemStack item, String value) 
	{
		ArrayList<String> lore = (ArrayList<String>) item.getItemMeta().getLore();
		for(String line : lore) 
		{
			if(line.contains(value)) 
			{
				line = line.replaceAll(value + ":", "");
				float return_= Float.parseFloat(line);
				return return_;
			}
		}
		return 0;
	}
}
