package com.woody.core.util;

import java.io.File;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;

public class Locale {
	public static HashMap<String, FileConfiguration> list = new HashMap<>();
	
	public void Init() 
	{
		File[] files = FileManager.listFiles("locale/");
		for(File f : files) 
		{
			if(f.getName().endsWith(".loc"))
				list.put(f.getName().replace(".loc", ""), FileManager.getConfig("locale/" + f.getName()));
			else
				continue;
		}
	}
}
