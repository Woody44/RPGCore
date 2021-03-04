package com.woody.core.util;

import java.io.File;
import java.util.HashMap;
import org.bukkit.Location;

public class LocationManager {
	static private HashMap<String, Location> locations = new HashMap<>();
	
	public static Location getLocation(String name) 
	{
		return locations.get(name);
	}
	
	public static void registerLocation(String name, Location loc) 
	{
		if(locations.containsKey(name))
			return;

		locations.put(name, loc);
		FileManager.createConfig("locations/"+name+".yml", "loc", loc);
	}
	
	public static void loadLocations() 
	{
		File[] locs = FileManager.listFiles("locations/");
		for(File f: locs)
			if(f.getName().endsWith(".yml"))
				locations.put(f.getName().replace(".yml", ""), FileManager.getConfig("locations/"+f.getName()).getLocation("loc"));
	}
	
	public static HashMap<String, Location> listLocations() 
	{
		return locations;
	}
	
	public static void deleteLocation(String name) 
	{
		FileManager.deleteFile("locations/"+name+".yml");
		locations.remove(name);
	}
	
	public static void updateLocation(String name, Location loc) 
	{
		locations.replace(name, loc);
		FileManager.updateConfig("locations/"+name+".yml", "loc", loc);
	}
}
