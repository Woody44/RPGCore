package com.woody.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.woody.core.util.FileManager;

public class CommandSetSpawn implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		if(!FileManager.checkFileExistence("spawn.yml"))
			FileManager.createConfig("spawn.yml", "location", ((Player)sender).getLocation());
		else
			FileManager.updateConfig("spawn.yml", "location", ((Player)sender).getLocation());
		return true;
    }
}
