package com.rpg.core.framework;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
	public static ItemStack createItemStack(final Material material, final String name, final String... lore)
    {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }
}
