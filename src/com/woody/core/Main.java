package com.woody.core;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.woody.core.util.LocationManager;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.PluginManager;
import com.woody.core.events.*;
import com.woody.core.tabs.*;
import com.woody.core.commands.*;

public class Main extends JavaPlugin implements Listener{
	public static Main instance;
	@Override
	public void onLoad()
	{
		saveDefaultConfig();
		saveResource("badwords.yml", false);
		saveResource("levels.yml", false);
	}
	
	@Override
	public void onEnable() 
	{
		instance = this;
		Config.Reload();
		registerCommands();
		registerEvents();
		registerOther();
		onReload();
	}
	
	@Override
	public void onDisable() 
	{
		
	}
	
	public static Main getInstance() 
	{
		return instance;
	}
	
	private static void registerCommands() 
	{
		PluginManager.AddCommand(new CommandProfile());
		PluginManager.AddCommand(new CommandMoney());
		PluginManager.AddCommand(new CommandCooldown());
		PluginManager.AddCommand(new CommandLevel());
		PluginManager.AddCommand(new CommandLocation());
		PluginManager.AddCommand(new CommandPay());
		PluginManager.AddCommand(new CommandGUI());
		Main main = Main.getInstance();
		for(CommandExecutor ce: PluginManager.commands)
		{
			String cname = ce.getClass().getSimpleName();
			cname = ce.getClass().getSimpleName().replaceAll("Command", "").toLowerCase();
			main.getServer().getPluginCommand(cname).setExecutor(ce);
		}
	}
	
	private static void registerEvents() 
	{
		PluginManager.AddEvent(new OnJoin());
		PluginManager.AddEvent(new OnQuit());
		PluginManager.AddEvent(new EasterEggs());
		PluginManager.AddEvent(new Test());
		PluginManager.AddEvent(new Basics());
		PluginManager.AddEvent(new CommandGuiEvents());
		Main main = Main.getInstance();
		for(Listener event: PluginManager.events)
		{
			main.getServer().getPluginManager().registerEvents(event, Main.getInstance());
		}
	}
	
	private static void registerOther() 
	{
		Main.getInstance().getCommand("cooldown").setTabCompleter(new CommandCooldownTab());
		Main.getInstance().getCommand("level").setTabCompleter(new CommandLevelTab());
		Main.getInstance().getCommand("location").setTabCompleter(new CommandLocationTab());
		Main.getInstance().getCommand("profile").setTabCompleter(new CommandProfileTab());
		LocationManager.loadLocations();
	}
	
	private static void onReload() 
	{
		for(Player p : Bukkit.getOnlinePlayers())
			PlayerManager.registerOnlinePlayer(p);
	}
}
