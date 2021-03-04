package com.woody.core;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.woody.core.util.FileManager;
import com.woody.core.util.LocationManager;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.PluginManager;

import java.io.File;
import java.time.LocalDate;
import java.time.Month;

import com.woody.core.events.Basic;
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
	
	@Override
	public void onEnable() 
	{
		LocalDate ld = LocalDate.now();
		int year = ld.getYear();
		Month month = ld.getMonth();
		int day = ld.getDayOfMonth();
		String actualDate = day + "." + month.getValue() + "."+year;
		/*if(Config.demo)
		{
			if(year >= 2021 && month.getValue() >= 4 && day >=30)
			{
				BukkitRunnable run = new BukkitRunnable() {

					@Override
					public void run() {
						getLogger().warning("Wersja Demonstracyjna wygasla z dniem " + "30.04.2021" + ".! Skontaktuj sie z autorem pluginu.");
						Bukkit.getPluginManager().disablePlugin(instance);
					}
					
				};
				
				run.runTaskLater(this, 1L);
			}
			else
			{
				getLogger().warning("Wersja Demonstracyjna jest aktualna do " + "30.04.2021" + ".!");
			}
		}*/
		
		if(!instance.getConfig().getString("version").contentEquals(this.getDescription().getVersion()))
		{
			getLogger().warning("Wersja pliku konfiguracyjnego nie zgadza sie z wersja pluginu!");
			getLogger().warning("Dumping old Config!");
			
			new File(getDataFolder() + "/config.yml").renameTo(new File(getDataFolder() + "/config.yml.old_" + actualDate));
			saveDefaultConfig();
			getConfig().set("version", Main.instance.getDescription().getVersion());
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
	
	private static void registerOther() 
	{
		Main.instance.getCommand("cooldown").setTabCompleter(new CommandCooldownTab());
		Main.instance.getCommand("level").setTabCompleter(new CommandLevelTab());
		Main.instance.getCommand("location").setTabCompleter(new CommandLocationTab());
		Main.instance.getCommand("profile").setTabCompleter(new CommandProfileTab());
		Main.instance.getCommand("warp").setTabCompleter(new CommandWarpTab());
		LocationManager.loadLocations();
	}
	
	private static void afterReload() 
	{
		for(Player p : Bukkit.getOnlinePlayers())
			if(p!=null)
				PlayerManager.registerOnlinePlayer(p);

		if(Config.hungerAsMana)
			Bukkit.getScheduler().runTaskTimer(instance, Basic.manaRegenTask, 60, 60);
	}
	
	private static void beforeReload() 
	{
		for(Player p : Bukkit.getOnlinePlayers())
			if(p!=null)
				PlayerManager.getOnlinePlayer(p).getProfile().saveAll();
	}
}
