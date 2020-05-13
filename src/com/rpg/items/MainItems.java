package com.rpg.items;

import com.rpg.core.Extension;
import com.rpg.core.Main;
import com.rpg.items.events.*;

public class MainItems extends Extension
{	
	@Override
	public void Setup(Main newMain) 
	{
		main = newMain;
		
		System.out.println("[RPGcore - Items] Loading..");
		//getServer().getPluginManager().registerEvents(new OnJoin(), this);
		main.getServer().getPluginManager().registerEvents(new PlayerAttack(), main);

		System.out.println("[RPGcore - Items] Is ready to use!");
	}
	
	@Override
	public void Unload() 
	{
    	System.out.println("[RPGcore - Items] Unloading.");
	}
}
