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
import com.woody.core.events.custom.CoreReloadedEvent;
import com.woody.core.tabs.*;
import com.woody.core.types.CustomPlayer;

public class Main extends JavaPlugin implements Listener{
	public static Main instance;
	@Override
	public void onLoad()
	{
		System.out.println("PLUGIN Woody_core IS TAGGED AS ALPHA VERSION, IT CAN BE UNSTABLE!\nSEND ANY FEEDBACK TO OWNER PLEASE");
		LocalDate ld = LocalDate.now();
		int year = ld.getYear();
		Month month = ld.getMonth();
		int day = ld.getDayOfMonth();
		String actualDate = day + "." + month.getValue() + "."+year;

		instance = this;
		saveDefaultConfig();
		if(!FileManager.checkFileExistence("badwords.yml"))
			saveResource("badwords.yml", false);
		if(!FileManager.checkFileExistence("levels.yml"))
			saveResource("levels.yml", false);

		if(instance.getConfig() != null && instance.getConfig().getString("version") != null && !instance.getConfig().getString("version").contentEquals(this.getDescription().getVersion()))
		{
			if(instance.getConfig().getString("version") == "0.0.0.0")
			{
				getConfig().set("version", Main.instance.getDescription().getVersion());
				saveConfig();
			}
			else
			{
				getLogger().warning("Config File Version is incompatible with Plugin version!");
				getLogger().warning("Dumping old Config!");
				
				new File(getDataFolder() + "/config.yml").renameTo(new File(getDataFolder() + "/config.yml.old_" + actualDate));
				saveDefaultConfig();
				getConfig().set("version", Main.instance.getDescription().getVersion());
				saveConfig();
			}
		}

		Config.Reload();

		if(Bukkit.getOnlinePlayers().size() > 0)
		{
			CoreReloadedEvent event = new CoreReloadedEvent();
			Bukkit.getPluginManager().callEvent(event);
			afterReload();
		}
	}
	
	@Override
	public void onEnable()
	{
		PluginManager.registerCommands();
		PluginManager.registerEvents();
		registerOther();
		if(Config.hungerAsMana)
			Bukkit.getScheduler().runTaskTimer(instance, Basic.manaRegenTask, 60, 60);
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
		Bukkit.getLogger().warning("I see you are reloading server ... u r lucky bcs my plugin handles it correctly! but do not do this pls, other plugins may die.");
		for(Player p : Bukkit.getOnlinePlayers())
			if(p!=null)
			{
				CustomPlayer _cp = PlayerManager.registerOnlinePlayer(p);
				int _lastProf = PlayerManager.getGeneral(_cp.player.getUniqueId().toString()).getInt("last-profile");
				_cp.loadProfile(_lastProf);
			}
	}
	
	private static void beforeReload() 
	{
		for(Player p : Bukkit.getOnlinePlayers())
			if(p!=null)
				PlayerManager.getOnlinePlayer(p).getProfile().saveAll();
	}
	//TODO: Quest z SkyJumpem, Secret

	//TODO: Przywitanie gracza
	//TODO: Kolorowe Tabliczki
	//TODO: Nawigacja po punktach
	//TODO: Kanały na chacie
	//TODO: Lista znajomych
	//TODO: Party
	//TODO: Menu graficzne Profili
	//TODO: Menu Graficzne Party
	//TODO: Menu Graficzne Listy Znajomych
	//TODO: Tab Completery
	//TODO: TPA
	//TODO: Trades
}
