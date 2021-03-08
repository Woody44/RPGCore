package com.woody.core.util;

import java.util.ArrayList;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

import com.woody.core.Config;
import com.woody.core.Main;
import com.woody.core.commands.*;
import com.woody.core.events.*;

public class PluginManager {
	
	public static void registerCommands() 
	{
		ArrayList<CommandExecutor> commands = new ArrayList<CommandExecutor>();
		
		commands.add(new CommandBack());
		commands.add(new CommandCooldown());
		commands.add(new CommandLevel());
		commands.add(new CommandLocation());
		commands.add(new CommandMoney());
		commands.add(new CommandPay());
		commands.add(new CommandPowerTool());
		commands.add(new CommandProfile());
		commands.add(new CommandWarp());
		commands.add(new CommandSuicide());
		commands.add(new CommandFeed());
		commands.add(new CommandHeal());
		commands.add(new CommandSetSpawn());
		commands.add(new CommandSpawn());
		commands.add(new CommandReloadConfig());
		commands.add(new CommandHelpOp());
		commands.add(new CommandGm());
		commands.add(new CommandNick());
		Main main = Main.instance;
		for(CommandExecutor ce: commands)
		{
			String cname = ce.getClass().getSimpleName();
			cname = ce.getClass().getSimpleName().replaceAll("Command", "").toLowerCase();
			main.getServer().getPluginCommand(cname).setExecutor(ce);
		}
		
		commands = null;
	}
	
	public static void registerEvents()
	{
		
		ArrayList<Listener> events = new ArrayList<Listener>();
			events.add(new Basic());
		if(Config.announcementsModule)
			events.add(new Announce());
		if(Config.chatModule)
			events.add(new Chat());
		if(Config.combatModule)
			events.add(new Combat());
		if(Config.economyModule)
			events.add(new Economy());
		if(Config.levelingModule)
			events.add(new Leveling());
		if(Config.protectionModule)
			events.add(new Protect());
		if(Config.worldModule)
			events.add(new World());
		
		if(Config.easterEggs)
			events.add(new EasterEggs());
		
		//events.add(new CommandGuiEvents());
		
		Main main = Main.instance;
		for(Listener event: events)
		{
			main.getServer().getPluginManager().registerEvents(event, Main.instance);
		}
		
		events = null;
	}
}
