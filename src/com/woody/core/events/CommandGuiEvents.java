package com.woody.core.events;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import com.woody.core.util.FileManager;

public class CommandGuiEvents implements Listener{
	
	@EventHandler
	public void DamianToChuj( InventoryCloseEvent e ) 
	{
		if(e.getView().getTitle().contains("--edition--"))
		{
			String guiName = e.getView().getTitle().replace("--edition--", "");
			ArrayList<String> keys = new ArrayList<>();
			ArrayList<Object> values = new ArrayList<>(Arrays.asList(e.getInventory().getContents()));
			FileManager.updateConfig("menus/" + guiName + ".yml", keys, values);
		}
	}
}
