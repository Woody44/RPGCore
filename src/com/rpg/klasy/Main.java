package com.rpg.klasy;

import org.bukkit.plugin.java.JavaPlugin;

import com.rpg.core.DatabaseManager;

import org.bukkit.event.Listener;

public class Main extends JavaPlugin implements Listener
{
	static DatabaseManager dbmg = new DatabaseManager();
	public void onEnable()
	{
		dbmg.Setup();
		this.getCommand("klasy").setExecutor(new CommandKlasy());
		this.getCommand("ulepszklase").setExecutor(new UpgradeClass());
		getServer().getPluginManager().registerEvents(new ChooseClass(), this);
		getServer().getPluginManager().registerEvents(new UpgradeClassGUI(), this);
	}
}
