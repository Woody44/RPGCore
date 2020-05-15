package com.rpg.items;

import com.rpg.core.Extension;
import com.rpg.core.Manager;
import com.rpg.items.events.*;

public class MainItems extends Extension
{	
	public MainItems(boolean enabled) 
	{
		if(!enabled)
		{
			disabled();
			return;
		}
		
		Manager.AddEvent(new PlayerAttack());
		
		System.out.println("[RPGcore] " + getClass().getSimpleName().replaceAll("Main", "").toLowerCase() + " Is ready to use!");
	}
	
	@Override
	public void disabled() 
	{
		
	}
	
	@Override
	public Integer disable() 
	{
    	return 0;
	}
}
