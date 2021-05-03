package com.woody.core.util;

import java.util.ArrayList;

import com.woody.core.Main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;

public class ItemManager {

	public static void AddPotion(ItemStack item, PotionEffect potion)
	{
		PotionMeta meta = (PotionMeta)item.getItemMeta();
		meta.addCustomEffect(potion, true);
		item.setItemMeta(meta);
	}
	
	public static ItemStack AddLore(ItemStack item, String[] value) 
	{
		ArrayList<String> lore;
		if(item.getItemMeta().getLore() != null)
			lore = (ArrayList<String>) item.getItemMeta().getLore();
		else
			lore = new ArrayList<String>();

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
	
	public static ItemStack AddLore(ItemStack item, String value) 
	{
		ArrayList<String> lore;
		if(item.getItemMeta().getLore() != null)
			lore = (ArrayList<String>) item.getItemMeta().getLore();
		else
			lore = new ArrayList<String>();

		lore.add(StringManager.Colorize(value));
		
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack SetLore(int index, ItemStack item, String value)
	{
		ArrayList<String> lore;
		if(item.getItemMeta().getLore() != null)
			lore = (ArrayList<String>) item.getItemMeta().getLore();
		else
			lore = new ArrayList<String>();
		
		if(lore == null || lore.size() < index)
			return item;
		
		lore.set(index, StringManager.Colorize(value));
		
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
		if(lore == null || lore.size() == 0)
			return item;
		
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
		if(lore == null || lore.size() == 0 || lore.size() < index)
			return item;
		
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
			if(line.contains(value)) 
			{
				line = line.replace(value, "");
				line = line.replace(":", "");
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
	
	public static ItemStack hideShit(ItemStack item)
	{
        final ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS);
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack createItemStack(final Material material, final String name, final String[] lore, final int amount)
    {
		return createItemStack(material, name, lore, amount, false);
	}

	public static ItemStack createItemStack(final Material material, final String name, final String[] lore, final int amount, boolean unbreakable)
    {
        ItemStack item = new ItemStack(material, amount);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(StringManager.Colorize(name));
        meta.setUnbreakable(unbreakable);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS);

        item.setItemMeta(meta);
        
        if(lore != null)
        	item = AddLore(item, lore);
        return item;
    }
	
	public static ItemStack createItemStack(final Material material, final String name, final String[] lore, final int amount, int customModelData)
	{
		ItemStack item = createItemStack(material, name, lore, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setCustomModelData(customModelData);
		item.setItemMeta(meta);
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
	
	public static boolean isSimilar(ItemStack first,ItemStack second, boolean exact)
    {
		if(exact)
			return isSimilar(first,second);
        boolean similar = false;
        if(first == null || second == null)
            return similar;
        
        boolean sameTypeId = (first.getType() == second.getType());
        boolean sameName = (first.getItemMeta().getDisplayName().contentEquals(second.getItemMeta().getDisplayName()));
        
        if(sameTypeId && sameName)
        	similar = true;
        return similar;
    }
	
	public static ItemStack damageItem(ItemStack item, String value) 
	{
		Damageable meta = (Damageable)item.getItemMeta();
		if(value.contains("%"))
		{
			int dmg = (int) (item.getType().getMaxDurability() * (Integer.parseInt(value.replace("%", "")) * 0.01));
	        meta.setDamage(dmg);
		}
		else
		{
			int dmg = Integer.parseInt(value);
	        meta.setDamage(dmg);
		}
		
		item.setItemMeta((ItemMeta)meta);
		return item;
	}

	public static boolean setOwner(ItemStack item, String uuid, boolean force)
	{
		ItemMeta meta = item.getItemMeta();
		if(!force){
			if(meta.getPersistentDataContainer().has(new NamespacedKey(Main.instance, "woodycore_owner"), PersistentDataType.STRING))
				return false;
			else
			{
				if(uuid == null)
					return false;

				meta.getPersistentDataContainer().set(new NamespacedKey(Main.instance, "woodycore_owner"), PersistentDataType.STRING, uuid);
				return true;
			}
		}
		else
		{
			if(uuid != null){
				meta.getPersistentDataContainer().remove(new NamespacedKey(Main.instance, "woodycore_owner"));
				meta.getPersistentDataContainer().set(new NamespacedKey(Main.instance, "woodycore_owner"), PersistentDataType.STRING, uuid);
				return true;
			}
			else{
				meta.getPersistentDataContainer().remove(new NamespacedKey(Main.instance, "woodycore_owner"));
				return true;
			}
		}
	}

	public static boolean setOwner(Item item, String uuid, boolean force)
	{
		if(!force){
			if(item.hasMetadata("woodycore_owner"))
				return false;
			else
			{
				if(uuid == null)
					return false;
				
					item.setMetadata("woodycore_owner", new FixedMetadataValue(Main.instance, uuid));
				return true;
			}
		}
		else
		{
			if(uuid != null){
				item.removeMetadata("woodycore_owner", Main.instance);
				item.setMetadata("woodycore_owner", new FixedMetadataValue(Main.instance, uuid));
				return true;
			}
			else{
				item.removeMetadata("woodycore_owner", Main.instance);
				return true;
			}
		}
	}

	public static String getOwner(Item item)
	{
		if(item.hasMetadata("woodycore_owner")){
			return item.getMetadata("woodycore_owner").get(0).asString();
		}
		else
			return null;
	}

	public static String getOwner(ItemStack item)
	{
		if(item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Main.instance,"woodycore_owner"), PersistentDataType.STRING)){
			return item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Main.instance,"woodycore_owner"), PersistentDataType.STRING);
		}
		else
			return null;
	}

	public static boolean hasOwner(ItemStack item){
		return item.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Main.instance,"woodycore_owner"), PersistentDataType.STRING);
	}

	public static boolean hasOwner(Item item){
		return item.hasMetadata("woodycore_owner");
	}

	public static boolean willBreak(ItemStack item)
	{
		Damageable meta = (Damageable)item.getItemMeta();
		int maxdmg = item.getType().getMaxDurability() - 1;
		int dmg = meta.getDamage();
		if(maxdmg > 0)
			return dmg >= maxdmg; 
		else
			return false;
	}
}
