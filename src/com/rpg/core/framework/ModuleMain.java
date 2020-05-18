package com.rpg.core.framework;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ModuleMain extends JavaPlugin implements Listener{
	
	@Override
	public void onEnable() 
	{
		Setup();
		
	}
	
	@Override
	public void onDisable() 
	{
		ShutDown();
	}
	
	public void Setup() 
	{
		RegisterCommands();
		RegisterEvents();
		OnSetup();
	}
	public abstract void OnSetup();
	public abstract void RegisterCommands();
	public abstract void RegisterEvents();
	public abstract void ShutDown();
	
	public static void LogError(String message)
	{
		System.out.println("\u001B[31m" + "[ERROR] [RPGcore]" + message + "\u001B[37m" );
	}
	
	public static void LogWarn(String message) 
	{
		System.out.println("\u001B[33m" + "[WARN] [RPGcore]" + message + "\u001B[37m" );
	}
	
	public static void LogInfo(String message) 
	{
		System.out.println("\u001B[36m" + "[INFO] [RPGcore]" + message + "\u001B[37m" );
	}
	
	public static void Log(String message) 
	{
		System.out.println("\u001B[37m" + "[RPGcore]" + message + "\u001B[37m" );
	}
	
	public static void LogError(String prefix, String message)
	{
		System.out.println("\u001B[31m" + "[ERROR] [RPG - " + prefix + "]" + message + "\u001B[37m" );
	}
	
	public static void LogWarn(String prefix, String message) 
	{
		System.out.println("\u001B[33m" + "[WARN] [RPG - " + prefix + "]" + message + "\u001B[37m" );
	}
	
	public static void LogInfo(String prefix, String message) 
	{
		System.out.println("\u001B[36m" + "[INFO] [RPG - " + prefix + "]" + message + "\u001B[37m" );
	}
	
	public static void Log(String prefix, String message) 
	{
		System.out.println("\u001B[37m" + "[RPG - " + prefix + "]" + message + "\u001B[37m" );
	}
}
