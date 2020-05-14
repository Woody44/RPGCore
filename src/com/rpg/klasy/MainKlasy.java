package com.rpg.klasy;

import com.rpg.core.DatabaseManager;
import com.rpg.core.Extension;
import com.rpg.core.Main;
import com.rpg.klasy.commands.CommandKlasy;
import com.rpg.klasy.commands.CommandUpgradeClass;
import com.rpg.klasy.gui.ChooseClassGUI;
import com.rpg.klasy.gui.UpgradeClassGUI;

public class MainKlasy extends Extension
{
	Main main;
	public DatabaseManager dbmg;
	public void Setup(Main newMain)
	{
		main = newMain;
		dbmg = main.dbmg;
		System.out.println("[RPGcore - Klasy] Loading..");
		main.getCommand("klasy").setExecutor(new CommandKlasy());
		main.getCommand("ulepszklase").setExecutor(new CommandUpgradeClass());
		ChooseClassGUI cc = new ChooseClassGUI();
		
		main.getServer().getPluginManager().registerEvents(cc, main);
		
		UpgradeClassGUI ucg = new UpgradeClassGUI();
		main.getServer().getPluginManager().registerEvents(ucg, main);
		
		System.out.println("[RPGcore - Klasy] Is ready to use!");
	}
	
	public void Unload() 
	{
		System.out.println("[RPGcore - Klasy] Unloading.");
	}
}
