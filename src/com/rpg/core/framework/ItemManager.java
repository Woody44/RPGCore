package com.rpg.core.framework;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemManager {

	public static ItemStack AddLore(ItemStack item, String[] value) 
	{
		ArrayList<String> lore;
		if(item.getItemMeta().getLore() != null)
			lore = (ArrayList<String>) item.getItemMeta().getLore();
		else
			lore = new ArrayList<String>();
		//// .I.

		for(String str : value)
		{
			str = str.replaceAll("_", " ");
			lore.add(ChatManager.GetColorized(str));
		}
		
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack ResetLore(ItemStack item) 
	{
		ItemMeta meta = item.getItemMeta();
		meta.setLore(new ArrayList<String>());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack DeleteLoreLine(ItemStack item, String value) 
	{
		ArrayList<String> lore = (ArrayList<String>) item.getItemMeta().getLore();
		for(String line : lore) 
		{
			if(line == value) 
			{
				lore.remove(line);
			}
		}
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
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
		if(item == null)
			return 0;
		
		ArrayList<String> lore = (ArrayList<String>) item.getItemMeta().getLore();
		if(lore == null || lore.size() <=0)
			return 0;
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
	public static ItemStack createItemStack(final Material material, final String name, final String[] lore)
    {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        item.setItemMeta(meta);
        
        if(lore != null)
        	AddLore(item, lore);
        return item;
    }
	
	public static boolean isSimilar(ItemStack first,ItemStack second)
    {
        boolean similar = false;
        if(first == null || second == null)
        {
            return similar;
        }
        boolean sameTypeId = (first.getType() == second.getType());
        boolean sameAmount = (first.getAmount() == second.getAmount());
        boolean sameHasItemMeta = (first.hasItemMeta() == second.hasItemMeta());
        boolean sameEnchantments = (first.getEnchantments().equals(second.getEnchantments()));
        boolean sameItemMeta = true;
        if(sameHasItemMeta)
            sameItemMeta = Bukkit.getItemFactory().equals(first.getItemMeta(), second.getItemMeta());
        if(sameTypeId && sameAmount && sameHasItemMeta && sameEnchantments && sameItemMeta)
            similar = true;
        return similar;
    }
}
