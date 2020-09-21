package com.rpg.core.framework;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.rpg.core.Main;

public class FileManager {
	
	public static String GetPath(String type, String Name, String extend) 
	{
		return Main.GetMe().getDataFolder() + "/" + type + "s/" + extend + "/" + Name + ".yml";
	}
	
	public static File CreateFile(String path)
	{
		File file = new File(path);
		return file;
	}
	
	public static void DeleteFile(String path)
	{
		if(checkFileExistence(path)) 
		{
			File file = getFile(path);
			file.delete();
		}
	}
	
	public static FileConfiguration CreateConfigFile(String path, String name, String key, Object value) 
	{
		File f = CreateFile(path);
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
	
	public static void CreatePlayerProfile(String uuid, String name, String profile) 
	{
		FileConfiguration fc;
		File file = CreateFile(GetPath("player", "data", uuid+"/profiles/"+profile));
		fc = YamlConfiguration.loadConfiguration(file);
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
		
		file = CreateFile(GetPath("player", "player", uuid));
		fc = YamlConfiguration.loadConfiguration(file);
		fc.set("uuid", uuid);
		fc.set("name", name);
		fc.set("profile", 1);
		try
		{
			fc.save(file);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static File getFile(String path)
	{
		if(checkFileExistence(path))
		{
			File returnFile = new File(path);
			return returnFile;
		}
		else return null;
	}
	
	public static FileConfiguration getFileConfig(String path)
	{
		if(checkFileExistence(path))
		{
			File returnFile = getFile(path);
			FileConfiguration returnConfig = YamlConfiguration.loadConfiguration(returnFile);
			return returnConfig;
		}
		else return null;
	}
	
	public static void updateFile(String path, String key, Object value) 
	{
		FileConfiguration fc;
		File file = getFile(path);
		fc = YamlConfiguration.loadConfiguration(file);
		
		fc.set(key, value);
		try {
			fc.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean checkFileExistence(String path)
	{
		File fileCheck = new File(path);
		return fileCheck.exists();
	}
}
