package com.rpg.items;
import org.bukkit.event.Listener;
import com.rpg.core.Main;
import com.rpg.items.events.*;

public class MainItems implements Listener 
{	
	Main main; 
	public void Setup(Main newMain) 
	{
		main = newMain;
		
		System.out.println("[RPGcore - Items] Loading..");
		//getServer().getPluginManager().registerEvents(new OnJoin(), this);
		main.getServer().getPluginManager().registerEvents(new PlayerAttack(), main);

		System.out.println("[RPGcore - Items] Is ready to use!");
	}
	
	public void Unload() 
	{
    	System.out.println("[RPGcore - Items] Unloading.");
	}
}
