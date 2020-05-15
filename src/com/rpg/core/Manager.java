package com.rpg.core;

import java.util.ArrayList;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

public class Manager {
	public static ArrayList<CommandExecutor> commands = new ArrayList<CommandExecutor>();
	public static ArrayList<Listener> events = new ArrayList<Listener>();

	public static void AddCommand(CommandExecutor cmd) 
	{
		commands.add(cmd);
	}
	
	public static void AddEvent(Listener event) 
	{
		events.add(event);
	}
}
