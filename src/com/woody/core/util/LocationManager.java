package com.woody.core.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Location;

import com.woody.core.Main;

public class LocationManager {
	static private HashMap<String, Location> locations = new HashMap<>();
	
	public static Location getLocation(String name) 
	{
		return locations.get(name);
	}
	
	public static void registerLocation(String name, Location loc) 
	{
		locations.put(name, loc);
		ArrayList<Object> values = new ArrayList<Object>();
		values.add(loc);
		ArrayList<String> keys = new ArrayList<String>();
		keys.add("loc");
		FileManager.createConfig("locations/"+name+".yml", keys, values);
	}
	
	public static void loadLocations() 
	{
		File[] locs = FileManager.listFiles("locations/");
		for(File f: locs)
		{
			Main.instance.getLogger().info("##################LOADING LOCATION "+ f.getName());
			locations.put(f.getName().replace(".yml", ""), FileManager.getConfig("locations/"+f.getName()).getLocation("loc"));
		}
			
	}
	
	public static void deleteLocation(String name) 
	{
		FileManager.deleteFile("locations/"+name+".yml");
		locations.remove(name);
	}
	
	public static void updateLocation(String name, Location loc) 
	{
		ArrayList<Object> values = new ArrayList<Object>();
		values.add(loc);
		ArrayList<String> keys = new ArrayList<String>();
		keys.add("loc");
		FileManager.updateConfig("locations/"+name+".yml", keys, values);
	}
}
