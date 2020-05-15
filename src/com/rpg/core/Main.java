package com.rpg.core;

import java.util.ArrayList;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import com.rpg.klasy.MainKlasy;
import com.rpg.items.MainItems;
import com.rpg.core.economy.CommandPay;
import com.rpg.core.economy.CommandMoney;
import com.rpg.core.events.*;

public class Main extends JavaPlugin implements Listener {
	
	public ArrayList<Extension> extensions;
	
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
	}
	
	public void LoadAddons() 
	{
		System.out.println("[RPGcore] Loading Addons...");
		extensions = new ArrayList<Extension>();
		extensions.add(new MainItems());
		extensions.add(new MainKlasy());
		
		for(Extension ex : extensions) 
		{
			System.out.println("[RPGcore] Loading " + ex.getClass().getSimpleName().replaceAll("Main", "").toLowerCase() + " ...");
			if(ex.setup()!=0) 
			{
				System.out.println("[RPGcore] Error While Loading" + ex.getClass().getSimpleName().replaceAll("Main", "").toLowerCase() + " ... Disabling!");
				ex.disable();
			}
			else
				System.out.println("[RPGcore] " + ex.getClass().getSimpleName().replaceAll("Main", "").toLowerCase() + " Is ready to use!");
		}
	}
	
	public void RegisterCommands() 
	{
		Manager.AddCommand(new CommandMoney());
		Manager.AddCommand(new CommandPay());
		for(CommandExecutor ce: Manager.commands)
		{
			String cname = ce.getClass().getSimpleName().replaceAll("Command", "").toLowerCase();
			getServer().getPluginCommand(cname).setExecutor(ce);
		}
	}
	
	public void RegisterEvents() 
	{
		Manager.AddEvent(new OnJoin());
		Manager.AddEvent(new DefaultStuff());
		for(Listener event: Manager.events)
		{
			getServer().getPluginManager().registerEvents(event, this);
		}
		
	}
}
