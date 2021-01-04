package com.woody.core;

import java.sql.Timestamp;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.woody.core.util.FileManager;
import com.woody.core.util.LocationManager;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.PluginManager;
import com.woody.core.tabs.*;

public class Main extends JavaPlugin implements Listener{
	public static Main instance;
	@Override
	public void onLoad()
	{
		instance = this;
		saveDefaultConfig();
		if(!FileManager.checkFileExistence("badwords.yml"))
			saveResource("badwords.yml", false);
		if(!FileManager.checkFileExistence("levels.yml"))
			saveResource("levels.yml", false);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() 
	{
		if(Config.demo)
		{
			
			Date date = new Date(new Timestamp(System.currentTimeMillis()).getTime());
			boolean m = date.getYear() < 121;
			if(!m)
				m = (date.getMonth() < 2);
			if(!m)
				m = (date.getDay() < 11);
			
			if(!m)
			{
				BukkitRunnable run = new BukkitRunnable() {

					@Override
					public void run() {
						getLogger().warning("Wersja Demonstracyjna wygasla z dniem 10.12.2021r.! Skontaktuj sie z autorem pluginu.");
						Bukkit.getPluginManager().disablePlugin(instance);
					}
					
				};
				
				run.runTaskLater(this, 1L);
			}
			else
			{
				getLogger().warning("Wersja Demonstracyjna jest aktualna do 10.02.2021r.!");
			}
		}
		
		if(!instance.getConfig().getString("version").contentEquals(this.getDescription().getVersion()))
		{
			BukkitRunnable run = new BukkitRunnable() {

				@Override
				public void run() {
					getLogger().warning("Wersja pliku konfiguracyjnego nie zgadza sie z wersja pluginu!");
					Bukkit.getPluginManager().disablePlugin(instance);
				}
				
			};
			run.runTaskLater(this, 1L);
		}
		
		Config.Reload();
		PluginManager.registerCommands();
		PluginManager.registerEvents();
		registerOther();
		afterReload();
	}
	
	@Override
	public void onDisable() 
	{
		beforeReload();
	}
	
	public static Main getInstance() 
	{
		return instance;
	}
	
	private static void registerOther() 
	{
		Main.getInstance().getCommand("cooldown").setTabCompleter(new CommandCooldownTab());
		Main.getInstance().getCommand("level").setTabCompleter(new CommandLevelTab());
		Main.getInstance().getCommand("location").setTabCompleter(new CommandLocationTab());
		Main.getInstance().getCommand("profile").setTabCompleter(new CommandProfileTab());
		LocationManager.loadLocations();
	}
	
	private static void afterReload() 
	{
		instance.getLogger().info("Loading data of "  + Bukkit.getOnlinePlayers().size() + " player(s)");
		for(Player p : Bukkit.getOnlinePlayers())
			PlayerManager.registerOnlinePlayer(p);
	}
	
	private static void beforeReload() 
	{
		instance.getLogger().info("Saving data of "  + Bukkit.getOnlinePlayers().size() + " player(s)");
		for(Player p : Bukkit.getOnlinePlayers())
			PlayerManager.onlinePlayers.get(p).saveAll();
	}
}
