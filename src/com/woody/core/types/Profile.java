package com.woody.core.types;

import java.util.HashMap;
import org.bukkit.inventory.ItemStack;

public class Profile {
	public int id;
	public long experience;
	public int level;
	public int money;
	public ItemStack[] items;
	public HashMap<String, Object> customProperties = new HashMap<>();
	
	public Profile(int _id, int _level, long exp, int _money, ItemStack[] _items, HashMap<String, Object> _cp) 
	{
		id = _id;
		level = _level;
		experience = exp;
		money = _money;
		items = _items;
		customProperties = _cp;
	}
	
	public Profile() 
	{
		id = 1;
		level = 1;
		experience = 0;
		money = 0;
		items = new ItemStack[41];
		customProperties = new HashMap<String, Object>();
	}
	
	public Profile(int _id) 
	{
		id = _id;
		level = 1;
		experience = 0;
		money = 0;
		items = new ItemStack[41];
		customProperties = new HashMap<String, Object>();
	}
}
