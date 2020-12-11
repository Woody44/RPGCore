package com.woody.core.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.woody.core.Main;

public class FileManager {
	
	public static File createFolder(String path) 
	{
		File file = new File(Main.getInstance().getDataFolder() + "/" + path);
		file.mkdir();
		return file;
	}
	
	public static File createFile(String path) 
	{
		File file = new File(Main.getInstance().getDataFolder() + "/" + path);
		return file;
	}
	
	public static void deleteFile(String path)
	{
		if(checkFileExistence(path)) 
		{
			File file = getFile(path);
			file.delete();
		}
	}
	
	public static boolean checkFileExistence(String path)
	{
		File fileCheck = new File(Main.getInstance().getDataFolder() + "/" + path);
		return fileCheck.exists();
	}
	
	public static File getFile(String path)
	{
		if(checkFileExistence(path))
		{
			File returnFile = new File(Main.getInstance().getDataFolder() + "/" + path);
			return returnFile;
		}
		else return null;
	}
	
	public static FileConfiguration createConfig(String path, ArrayList<String> keys, ArrayList<Object> values) 
	{
		File f = createFile(path);
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		for(int i=0; i < keys.size(); i++)
			fc.set(keys.get(i), values.get(i));
		
		try {
			fc.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fc;
	}
	
	public static FileConfiguration createConfig(String path, String key, Object value) 
	{
		File f = createFile(path);
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
	
	public static FileConfiguration createConfig(String path, HashMap<String, Object> stuff) 
	{
		File f = createFile(path);
		FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
		
		for( String key : stuff.keySet())
			fc.set(key, stuff.get(key));
		
		try {
			fc.save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fc;
	}
	
	public static FileConfiguration getConfig(String path)
	{
		if(checkFileExistence(path))
		{
			File returnFile = getFile(path);
			FileConfiguration returnConfig = YamlConfiguration.loadConfiguration(returnFile);
			return returnConfig;
		}
		else return null;
	}
	
	public static void updateConfig(String path, ArrayList<String> keys, ArrayList<Object> values) 
	{
		FileConfiguration fc;
		File file = getFile(path);
		if(!checkFileExistence(path))
		{
			createConfig(path, keys, values);
			return;
		}
		
		fc = YamlConfiguration.loadConfiguration(file);
		for(int i=0; i < keys.size(); i++)
			fc.set(keys.get(i), values.get(i));
		
		try {
			fc.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateConfig(String path, String key, Object value) 
	{
		FileConfiguration fc;
		File file = getFile(path);
		
		if(!checkFileExistence(path))
		{
			createConfig(path, key, value);
			return;
		}
		
		fc = YamlConfiguration.loadConfiguration(file);
		
		fc.set(key, value);
		
		try {
			fc.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateConfig(String path, HashMap<String, Object> stuff) 
	{
		FileConfiguration fc;
		File file = getFile(path);
		if(!checkFileExistence(path))
		{
			createConfig(path, stuff);
			return;
		}
		
		fc = YamlConfiguration.loadConfiguration(file);
		
		for( String key : stuff.keySet())
			fc.set(key, stuff.get(key));
		
		try {
			fc.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static File[] listFiles(String path) 
	{
		File[] f = new File (Main.getInstance().getDataFolder() + "/" + path).listFiles();
		if(f != null)
			return f;
		else
			return createFolder(path).listFiles();
	}
}
