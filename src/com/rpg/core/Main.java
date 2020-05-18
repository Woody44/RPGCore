package com.rpg.core;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import com.rpg.core.economy.CommandPay;
import com.rpg.core.economy.CommandMoney;
import com.rpg.core.events.*;

public class Main extends ModuleMain implements Listener {
	
	@Override
	public void OnSetup() 
	{
		LogInfo("Starting Core.");
		saveDefaultConfig();
		DatabaseManager.Setup();
		
		if (getServer().getPluginManager().getPlugin("AuthMe") == null) 
		{
			LogError("SERVER DOES NOT MEET REQUIREMENTS:");
			LogError("I can not find AuthMe!");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		LogInfo("Loading Done.");
	}
	
	@Override
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
	
	@Override
	public void RegisterEvents() 
	{
		Manager.AddEvent(new Announcing());
		Manager.AddEvent(new Basics());
		Manager.AddEvent(new OnCooldown(this));
		for(Listener event: Manager.events)
		{
			getServer().getPluginManager().registerEvents(event, this);
		}
	}

	@Override
	public void ShutDown() {
		LogWarn("Disabling...");
		LogWarn("Disabled!");
	}
}
