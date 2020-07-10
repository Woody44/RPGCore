package com.rpg.core;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.rpg.core.commands.*;
import com.rpg.core.economy.*;
import com.rpg.core.events.*;
import com.rpg.core.framework.Logger;

public class Main extends JavaPlugin implements Listener{
	public static Main instance;
	
	@Override
	public void onEnable() 
	{
		instance = this;
		Logger.LogInfo("Starting Core.");
		
		saveDefaultConfig();
		@SuppressWarnings("unused")
		CoreConfig cfg = new CoreConfig();
		
		//DatabaseManager.Setup();
		RegisterCommands();
		RegisterEvents();
		RegisterOtherStuff();
		
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
		Manager.AddCommand(new CommandReloadConfig());
		Manager.AddCommand(new CommandLocation());
		Manager.AddCommand(new CommandSetExp());
		Manager.AddCommand(new CommandSetMoney());
		Manager.AddCommand(new CommandForce());
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
		
		Manager.AddEvent(new protect());
		for(Listener event: Manager.events)
		{
			getServer().getPluginManager().registerEvents(event, this);
		}
	}
	
	public void RegisterOtherStuff()
	{

	}
	
	public static Main GetMe() 
	{
		return instance;
	}
}
