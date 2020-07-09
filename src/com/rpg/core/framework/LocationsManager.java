package com.rpg.core.framework;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class LocationsManager 
{
	public static void Create(String name, Location location) 
	{
		if(name == null)
			return;
		
		FileConfiguration fc;
		File file = FileManager.CreateFile("location", name);
		fc = YamlConfiguration.loadConfiguration(file);
		fc.set("location", location);
		try
		{
			fc.save(file);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void DeleteLocation(String name) 
	{
		if(name ==null)
			return;
		
		try {
			FileManager.DeleteFile("location", name);
		}catch(IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static Location GetLocation(String name) 
	{
		FileConfiguration fc = FileManager.getFileConfig("location", name);
		if(fc != null)
			return fc.getLocation("location");
		return null;
	}
}
