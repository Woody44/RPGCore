package com.woody.core.types;

import org.bukkit.inventory.ItemStack;

public class ProfileInfo {
	public String owner;
	public int id;
	public long experience;
	public int level;
	public int money;
	public ItemStack[] inventory;
	
	public ProfileInfo(String owner, int id, int level, long experience, int money, ItemStack[] items) 
	{
		this.owner = owner;
		this.id = id;
		this.level = level;
		this.experience = experience;
		this.money = money;
		this.inventory = items;
	}
	public ProfileInfo(String owner) 
	{
		this.owner = owner;
		id = 1;
		experience = 0;
		level = 1;
		money = 0;
		inventory = null;
	}
	
	public ProfileInfo(String owner, int id) 
	{
		this.owner = owner;
		this.id = id;
		experience = 0;
		level = 0;
		money = 0;
		inventory = null;
	}
}
