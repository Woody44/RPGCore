package com.rpg.klasy;

import com.rpg.core.DatabaseManager;
import com.rpg.core.Extension;
import com.rpg.core.Main;
import com.rpg.core.Manager;
import com.rpg.klasy.commands.CommandKlasy;
import com.rpg.klasy.commands.CommandUlepszklase;
import com.rpg.klasy.gui.ChooseClassGUI;
import com.rpg.klasy.gui.UpgradeClassGUI;

public class MainKlasy extends Extension
{
	Main main;
	public DatabaseManager dbmg;
	public Integer setup()
	{
		Manager.AddCommand(new CommandKlasy());
		Manager.AddCommand(new CommandUlepszklase());
		
		Manager.AddEvent(new ChooseClassGUI());
		Manager.AddEvent(new UpgradeClassGUI());
		return 0;
	}
	
	public Integer disable() 
	{
		return 0;
	}
}
