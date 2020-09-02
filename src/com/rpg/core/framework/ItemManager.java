package com.rpg.core.framework;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
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
			lore.add(StringManager.Colorize(str));
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
			if(line.contains(value)) 
			{
				lore.remove(line);
				break;
			}
		}
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack DeleteLoreLine(ItemStack item, int index) 
	{
		ArrayList<String> lore = (ArrayList<String>) item.getItemMeta().getLore();
		if(lore.get(index) != null)
			lore.remove(index);
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static String GetLore(ItemStack item, String value) 
	{
		ArrayList<String> lore = (ArrayList<String>) item.getItemMeta().getLore();
		if(lore == null)
			return null;
		for(String line : lore) 
		{
			if(line.contains(value)) 
			{
				return line;
			}
		}
		return null;
	}
	
	public static ItemStack Rename(ItemStack item, String[] value) 
	{
		ItemMeta meta = item.getItemMeta();
		String itemName = "";
		for(int i = 0; i < value.length; i++)
			itemName += value[i];

		meta.setDisplayName(StringManager.Colorize(itemName));
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack Rename(ItemStack item, String value) 
	{
		ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(StringManager.Colorize(value));
		item.setItemMeta(meta);
		return item;
	}
	
	public static float CheckLore(ItemStack item, String value) 
	{
		if(item == null || item.getType() == Material.AIR)
			return 0;
		
		ArrayList<String> lore = (ArrayList<String>) item.getItemMeta().getLore();
		if(lore == null || lore.size() <=0)
			return 0;
		for(String line : lore) 
		{
			if(line.contains(value+":")) 
			{
				line = line.replace(value + ":", "");
				line = StringManager.NoColors(line);
				line = line.replace("*", "");
				if(line.contains("%"))
				{
					line = line.replace("%", "");
					return Float.parseFloat(line) / 100;
				}
				else
					
				return Float.parseFloat(line);
			}
		}
		return 0;
	}
	public static ItemStack createItemStack(final Material material, final String name, final String[] lore, final int amount)
    {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(StringManager.Colorize(name));
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS);

        item.setItemMeta(meta);
        item.setAmount(amount);
        
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
	
	static public String GetType(String string) 
	{
		if(string.contains("Naszyjnik"))
			return "necklake";
		else if(string.toLowerCase().contains("pierscien"))
			return "ring";
		else if(string.toLowerCase().contains("kolczyk"))
			return "earring";
		else if(string.toLowerCase().contains("bransoleta"))
			return "bracelet";
		else
			return null;
	}
	
	static public String GetSlotType(int i) 
	{
		switch(i) 
		{
			case 0:
				return "earring0";
			case 1:
				return "earring1";
			case 2:
				return "necklake0";
			case 3:
				return "ring0";
			case 4:
				return "ring1";
			case 5:
				return "bracelet0";
			case 6:
				return "bracelet1";
			default:
				return null;
		}
	}
	
	static public ItemStack xd()
	{
		ItemStack item = createItemStack(Material.DIAMOND_SWORD, "dagger", new String[]{}, 1);
		item.getItemMeta().setCustomModelData(3);
		return item;
	}
}
