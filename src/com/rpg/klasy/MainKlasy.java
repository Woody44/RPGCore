package com.rpg.klasy;

import com.rpg.core.Extension;
import com.rpg.core.Manager;
import com.rpg.core.commands.NoCommand;
import com.rpg.klasy.abilities.PalladynAbilities;
import com.rpg.klasy.commands.CommandKlasy;
import com.rpg.klasy.commands.CommandUlepszklase;
import com.rpg.klasy.gui.ChooseClassGUI;
import com.rpg.klasy.gui.UpgradeClassGUI;

public class MainKlasy extends Extension
{
	public MainKlasy(boolean enabled) 
	{
		if(!enabled) 
		{
			disabled();
			return;
		}
		Manager.AddCommand(new CommandKlasy());
		Manager.AddCommand(new CommandUlepszklase());
		
		Manager.AddEvent(new ChooseClassGUI());
		Manager.AddEvent(new UpgradeClassGUI());
		Manager.AddEvent(new PalladynAbilities());
		System.out.println("[RPGcore] " + getClass().getSimpleName().replaceAll("Main", "").toLowerCase() + " Is ready to use!");
	}
	
	@Override
	public void disabled() 
	{
		Manager.AddCommand(new NoCommand("CommandKlasy"));
		Manager.AddCommand(new NoCommand("CommandUlepszklase"));
	}
	
	public Integer disable() 
	{
		return 0;
	}
}
