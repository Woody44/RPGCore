package com.rpg.klasy;

import com.rpg.core.DatabaseManager;
import com.rpg.core.Main;
import com.rpg.klasy.commands.CommandKlasy;
import com.rpg.klasy.commands.UpgradeClass;
import com.rpg.klasy.gui.ChooseClass;
import com.rpg.klasy.gui.UpgradeClassGUI;

import org.bukkit.event.Listener;

public class MainKlasy implements Listener
{
	Main main;
	public DatabaseManager dbmg;
	public void Setup(Main newMain)
	{
		main = newMain;
		dbmg = main.dbmg;
		System.out.println("[RPGcore - Klasy] Loading..");
		main.getCommand("klasy").setExecutor(new CommandKlasy());
		main.getCommand("ulepszklase").setExecutor(new UpgradeClass());
		ChooseClass cc = new ChooseClass();
		
		cc.main = this;
		main.getServer().getPluginManager().registerEvents(cc, main);
		
		UpgradeClassGUI ucg = new UpgradeClassGUI();
		ucg.main = this;
		main.getServer().getPluginManager().registerEvents(ucg, main);
		
		System.out.println("[RPGcore - Klasy] Is ready to use!");
	}
	
	public void Unload() 
	{
		System.out.println("[RPGcore - Klasy] Unloading.");
	}
}
