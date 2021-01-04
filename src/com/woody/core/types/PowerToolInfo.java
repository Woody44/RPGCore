package com.woody.core.types;

import org.bukkit.inventory.ItemStack;

public class PowerToolInfo {
	public ItemStack item;
	public String command;
	
	public PowerToolInfo(ItemStack _item, String _command) 
	{
		item = _item;
		command = _command;
	}
}
