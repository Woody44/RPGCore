package com.rpg.core;

import java.util.ArrayList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import com.rpg.klasy.MainKlasy;
import com.rpg.items.MainItems;
import com.rpg.core.commands.CommandTest;
import com.rpg.core.economy.CommandPay;
import com.rpg.core.economy.CommandMoney;
import com.rpg.core.events.OnJoin;

public class Main extends JavaPlugin implements Listener {
	
	public DatabaseManager dbmg;
	public ArrayList<Extension> extensions;
	
	@Override
	public void onEnable()
	{
		System.out.println("[RPGcore] Starting Core.");
		Setup();
		LoadAddons();
		RegisterCommands();
		LoadEvents();
		System.out.println("[RPGcore] Loading Done.");
	}
	
	
	
	@Override
	public void onDisable() 
	{
		System.out.println("[RPGcore] Disabling...");
		for(Extension ex : extensions) 
			ex.Unload();
		
		System.out.println("[RPGcore] Disabled!");
	}
	
	public void Setup() 
	{
		DatabaseManager.Setup();
	}
	
	public void LoadAddons() 
	{
		System.out.println("[RPGcore] Loading Addons...");
		extensions = new ArrayList<Extension>();
		extensions.add(new MainItems());
		extensions.add(new MainKlasy());
		
		for(Extension ex : extensions) 
			ex.Setup(this);
	}
	
	public void RegisterCommands() 
	{
		getServer().getPluginCommand("test").setExecutor(new CommandTest());
		getServer().getPluginCommand("money").setExecutor(new CommandMoney());
		getServer().getPluginCommand("pay").setExecutor(new CommandPay());
	}
	
	public void LoadEvents() 
	{
		getServer().getPluginManager().registerEvents(new OnJoin(), this);
	}
}
