package com.rpg.items;

import com.rpg.core.Extension;
import com.rpg.core.Manager;
import com.rpg.items.events.*;

public class MainItems extends Extension
{	
	@Override
	public Integer setup() 
	{
		//getServer().getPluginManager().registerEvents(new OnJoin(), this);
		Manager.AddEvent(new PlayerAttack());
		return 0;
	}
	
	@Override
	public Integer disable() 
	{
    	return 0;
	}
}
