package com.rpg.core;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import com.rpg.core.economy.CommandPay;
import com.rpg.core.economy.CommandMoney;
import com.rpg.core.events.*;

public class Main extends ModuleMain implements Listener {
	
	CoreConfig cfg;
	
	@Override
	public void onEnable()
	{
		System.out.println("[RPGcore] Starting Core.");
		Setup();
		RegisterCommands();
		RegisterEvents();
		System.out.println("[RPGcore] Loading Done.");
	}
	
	@Override
	public void onDisable() 
	{
		System.out.println("[RPGcore] Disabling...");
		System.out.println("[RPGcore] Disabled!");
	}
	
	public void Setup() 
	{
		saveDefaultConfig();
		DatabaseManager.Setup();
		cfg = new CoreConfig(this);
	}
	
	public void RegisterCommands() 
	{
		Manager.AddCommand(new CommandMoney());
		Manager.AddCommand(new CommandPay());
		for(CommandExecutor ce: Manager.commands)
		{
			String cname = ce.getClass().getSimpleName();
			cname = ce.getClass().getSimpleName().replaceAll("Command", "").toLowerCase();
			getServer().getPluginCommand(cname).setExecutor(ce);
		}
	}
	
	public void RegisterEvents() 
	{
		Manager.AddEvent(new OnJoin());
		Manager.AddEvent(new OnLeft());
		Manager.AddEvent(new Basics());
		Manager.AddEvent(new OnCooldown(this));
		for(Listener event: Manager.events)
		{
			getServer().getPluginManager().registerEvents(event, this);
		}
	}
}
