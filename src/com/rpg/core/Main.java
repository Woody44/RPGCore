package com.rpg.core;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import com.rpg.klasy.MainKlasy;
import com.rpg.items.MainItems;

import com.rpg.core.commands.CommandTest;
import com.rpg.core.economy.CommandWallet;

public class Main extends JavaPlugin implements Listener {
	
	public DatabaseManager dbmg;
	MainKlasy mk;
	MainItems mi;
	
	public void XD() {}
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
		getServer().getPluginCommand("test").setExecutor(CT);
		CommandWallet CW = new CommandWallet();
		CW.main = this;
		getServer().getPluginCommand("wallet").setExecutor(CW);
		
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
