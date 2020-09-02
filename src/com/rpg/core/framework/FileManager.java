package com.rpg.core.framework;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.rpg.core.Main;

public class FileManager {
	
	public static void DeleteFile(String type, String name) throws IOException
	{
		if(checkFileExistence(type, name)) 
		{
			File file = getFile(type, name);
			file.delete();
		}
	}
	
	public static File CreateFile(String type, String name)
	{
		File file = new File(Main.GetMe().getDataFolder()+"/" + type + "s/", name + ".yml");
		
		return file;
	}
	
	public static FileConfiguration CreateConfigFile(String type, String name, String key, Object value) 
	{
		File f = CreateFile(type, name);
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		fc.set(key, value);
		try {
			fc.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fc;
	}
	
	public static void CreatePlayerFile(String uuid, String name) 
	{
		FileConfiguration fc;
		File file = CreateFile("player", uuid);
		fc = YamlConfiguration.loadConfiguration(file);
		fc.set("uuid", uuid);
		fc.set("name", name);
		fc.set("money", 0);
		fc.set("experience", 0);
		try
		{
			fc.save(file);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static File getFile(String path, String name)
	{
		if(checkFileExistence(path, name))
		{
			File returnFile = new File(Main.GetMe().getDataFolder() + "/" + path + "s/", name + ".yml");
			return returnFile;
		}
		else return null;
	}
	
	public static FileConfiguration getFileConfig(String path, String name)
	{
		if(checkFileExistence(path, name))
		{
			File returnFile = getFile(path , name);
			FileConfiguration returnConfig = YamlConfiguration.loadConfiguration(returnFile);
			return returnConfig;
		}
		else return null;
	}
	
	public static void updateFile(String path, String name, String key, Object value) 
	{
		FileConfiguration fc;
		File file = getFile(path, name);
		fc = YamlConfiguration.loadConfiguration(file);
		
		fc.set(key, value);
		try {
			fc.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean checkFileExistence(String path, String name)
	{
		File fileCheck = new File(Main.GetMe().getDataFolder() + "/" + path + "s/", name + ".yml");
		return fileCheck.exists();
	}
}
