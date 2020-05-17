package com.rpg.core;

import java.util.ArrayList;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import com.rpg.core.economy.CommandPay;
import com.rpg.core.commands.NoCommand;
import com.rpg.core.economy.CommandMoney;
import com.rpg.core.events.*;

public class Main extends JavaPlugin implements Listener {
	
	public ArrayList<Extension> extensions;
	CoreConfig cfg;
	
	@Override
	public void onEnable()
	{
		System.out.println("[RPGcore] Starting Core.");
		Setup();
		LoadAddons();
		RegisterCommands();
		RegisterEvents();
		System.out.println("[RPGcore] Loading Done.");
	}
	
	
	
	@Override
	public void onDisable() 
	{
		System.out.println("[RPGcore] Disabling...");
		for(Extension ex : extensions)
		{
			System.out.println("[RPGcore] Disabling Addon " + ex.getClass().getSimpleName() + " ...");
			if(ex.disable()!= 0)
				System.out.println("[RPGcore] Forcing Addon " + ex.getClass().getSimpleName() + " to disable ...");
			else
				System.out.println("[RPGcore] Addon " + ex.getClass().getSimpleName() + " disabled.");
		}
		System.out.println("[RPGcore] Disabled!");
	}
	
	public void Setup() 
	{
		saveDefaultConfig();
		DatabaseManager.Setup();
		cfg = new CoreConfig(this);
	}
	
	public void LoadAddons() 
	{
		System.out.println("[RPGcore] Loading Addons...");
	}
	
	public void RegisterCommands() 
	{
		Manager.AddCommand(new CommandMoney());
		Manager.AddCommand(new CommandPay());
		for(CommandExecutor ce: Manager.commands)
		{
			String cname = ce.getClass().getSimpleName();
			if(cname.contains("NoCommand"))
			{
				cname = ((NoCommand)ce).originalName.replaceAll("Command", "").toLowerCase();
				getServer().getPluginCommand(cname).setExecutor(ce);
			}
			else 
			{
				cname = ce.getClass().getSimpleName().replaceAll("Command", "").toLowerCase();
				getServer().getPluginCommand(cname).setExecutor(ce);
				
			}
			
		}
	}
	
	public void RegisterEvents() 
	{
		Manager.AddEvent(new OnJoin());
		Manager.AddEvent(new OnLeft());
		Manager.AddEvent(new DefaultStuff());
		Manager.AddEvent(new OnCooldown(this));
		Manager.AddEvent(new OnFallDamage());
		for(Listener event: Manager.events)
		{
			getServer().getPluginManager().registerEvents(event, this);
		}
		
	}
}
