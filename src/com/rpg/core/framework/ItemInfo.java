package com.rpg.core.framework;

import java.util.ArrayList;

public class ItemInfo {
	public String material, Name, enchantments;
	public ArrayList<String> Lore;
	public boolean unbreakable;
	public int ID, count, cooldown;
	
	public ItemInfo(int ID, String material, String Name, ArrayList<String> Lore, boolean unbreakable, int count, String enchantments, int cooldown) 
	{
		this.ID = ID;
		this.material = material;
		this.Name = Name;
		this.Lore = Lore;
		this.unbreakable = unbreakable;
		this.count = count;
		this.enchantments = enchantments;
		this.cooldown = cooldown;
	}
	
	public ItemInfo() 
	{
		this.ID = -1;
		this.material = "Material.WOODEN_SWORD";
		this.Name = "Example Item";
		this.Lore = new ArrayList<String>();
		this.unbreakable = true;
		this.count = 1;
		this.enchantments = "Enchantment.DURABILITY:10,Enchantment.DAMAGE_ALL:1";
		this.cooldown = 0;
	}
}
