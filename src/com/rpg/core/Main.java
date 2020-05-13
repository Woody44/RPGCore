package com.rpg.core;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import com.rpg.klasy.MainKlasy;
import com.rpg.items.MainItems;

public class Main extends JavaPlugin implements Listener {
	
	public DatabaseManager dbmg;
	MainKlasy mk;
	MainItems mi;
	
	@Override
	public void onEnable()
	{
		System.out.println("[RPGcore] Starting Core.");
		System.out.println("[RPGcore] Loading Addons...");
		dbmg = new DatabaseManager();
		dbmg.Setup();
		mk = new MainKlasy();
		mi = new MainItems();
		
		
		mk.Setup(this);
		mi.Setup(this);
		System.out.println("[RPGcore] Loading Done.");
	}
	
	@Override
	public void onDisable() 
	{
		System.out.println("[RPGcore] Disabling...");
		mk.Unload();
		mi.Unload();
		System.out.println("[RPGcore] Disabled!");
	}
}
