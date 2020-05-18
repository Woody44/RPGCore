package com.rpg.core;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.rpg.core.commands.*;
import com.rpg.core.economy.*;
import com.rpg.core.events.*;
import com.rpg.core.framework.DatabaseManager;
import com.rpg.core.framework.Logger;
import com.rpg.core.framework.Misc;

public class Main extends JavaPlugin implements Listener{
	
	@Override
	public void onEnable() 
	{
		@SuppressWarnings("unused")
		CoreConfig cfg = new CoreConfig(this);
		@SuppressWarnings("unused")
		Misc misc = new Misc(this);
		
		Logger.LogInfo("Starting Core.");
		
		RegisterCommands();
		RegisterEvents();
		
		saveDefaultConfig();
		DatabaseManager.Setup();
		
		if (getServer().getPluginManager().getPlugin("AuthMe") == null) 
		{
			Logger.LogError("SERVER DOES NOT MEET REQUIREMENTS:");
			Logger.LogError("I can not find AuthMe!");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		Logger.LogInfo("Loading Done.");
	}
	
	@Override
	public void onDisable() 
	{
		Logger.LogWarn("Disabling...");
		Logger.LogWarn("Disabled!");
	}
	
	public void RegisterCommands() 
	{
		Manager.AddCommand(new CommandMoney());
		Manager.AddCommand(new CommandPay());
		Manager.AddCommand(new CommandSystem());
		for(CommandExecutor ce: Manager.commands)
		{
			String cname = ce.getClass().getSimpleName();
			cname = ce.getClass().getSimpleName().replaceAll("Command", "").toLowerCase();
			getServer().getPluginCommand(cname).setExecutor(ce);
		}
	}
	
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
}
