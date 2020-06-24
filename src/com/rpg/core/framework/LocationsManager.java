package com.rpg.core.framework;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LocationsManager 
{
	public static ArrayList<CustomLocation> Locations = new ArrayList<CustomLocation>();
	
	public static void RegisterLocation(String name, Location location) 
	{
		if(name == null)
			return;
		
		Locations.add(new CustomLocation(name, location));
	}
	
	public static void RegisterLocation(CustomLocation cl) 
	{
		if(cl == null)
			return;
		
		Locations.add(cl);
	}
	
	public static void DeleteLocation(String name) 
	{
		if(name ==null)
			return;
		
		DatabaseManager.DeleteLocation(name);
		Locations.remove(GetLocation(name));
	}
	
	public static void CreateLocation(Player player, String name) 
	{
		if(name == null)
			return;
		
		DatabaseManager.CreateLocation(name, player.getLocation());
		RegisterLocation(name, player.getLocation());
	}
	
	public static CustomLocation GetLocation(String name) 
	{
		for(CustomLocation cl : Locations)
			if(cl.Name.equals(name))
				return cl;
		return null;
	}
}
