package com.rpg.core.framework;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class LocationsManager extends FileManager
{
	public static void Create(String name, Location location) 
	{
		if(name == null)
			return;
		
		FileConfiguration fc;
		File file = FileManager.CreateFile(GetPath("location", name, ""));
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
		
		DeleteFile(GetPath("location", name, ""));
	}
	
	public static Location GetLocation(String name) 
	{
		FileConfiguration fc = getFileConfig(GetPath("location", name, ""));
		if(fc != null)
			return fc.getLocation("location");
		return null;
	}
}
