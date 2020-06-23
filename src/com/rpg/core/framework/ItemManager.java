package com.rpg.core.framework;

import java.util.ArrayList;

import org.bukkit.Bukkit;
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
	
	public static ItemStack SetLore(ItemStack item, String value) 
	{
		ArrayList<String> lore = new ArrayList<String>();
		String[] newLore = value.split("||");
		for(String str : newLore)
			lore.add(ChatManager.GetColorized(str));
		item.getItemMeta().setLore(lore);
		return item;
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
	public static ItemStack createItemStack(final Material material, final String name, final String lore)
    {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);

        item.setItemMeta(meta);
        
        SetLore(item, lore);
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
