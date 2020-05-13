package com.rpg.core;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import com.rpg.klasy.MainKlasy;
import com.rpg.items.MainItems;

import com.rpg.core.commands.CommandTest;

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
		
		CommandTest CT = new CommandTest();
		CT.main = this;
		getServer().getPluginCommand("test").setExecutor(CT);;
		
		
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
