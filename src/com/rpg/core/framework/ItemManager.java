package com.rpg.core.framework;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemManager {

	public static void AddLore(ItemStack item, String value) 
	{
		ArrayList<String> lore = (ArrayList<String>) item.getItemMeta().getLore();
		String[] newLore = value.split("||");
		for(String str : newLore)
			lore.add(ChatManager.GetColorized(str));
		item.getItemMeta().setLore(lore);
	}
	
	public static void SetLore(ItemStack item, String value) 
	{
		ArrayList<String> lore = new ArrayList<String>();
		String[] newLore = value.split("||");
		for(String str : newLore)
			lore.add(ChatManager.GetColorized(str));
		item.getItemMeta().setLore(lore);
	}
	
	public static void ResetLore(ItemStack item) 
	{
		item.getItemMeta().setLore(new ArrayList<String>());
	}
	
	public static void DeleteLoreLine(ItemStack item, String value) 
	{
		ArrayList<String> lore = (ArrayList<String>) item.getItemMeta().getLore();
		for(String line : lore) 
		{
			if(line == value) 
			{
				lore.remove(line);
			}
		}
		item.getItemMeta().setLore(lore);
	}
	
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
				if(line.contains("%"))
				{
					line = line.replaceAll("%", "");
					return Float.parseFloat(line) / 100;
				}
				else
					
				return Float.parseFloat(line);
			}
		}
		return 0;
	}
	public static ItemStack createItemStack(final Material material, final String name, final String lore)
    {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        item.setItemMeta(meta);
        
        SetLore(item, lore);
        return item;
    }
}
