package com.woody.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.woody.core.GLOBALVARIABLES;
import com.woody.core.util.FileManager;
import com.woody.core.util.StringManager;

public class CommandSetSpawn implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		if(args.length == 0 || args[0] != "NONE")
		{
			if(!FileManager.checkFileExistence("spawn.yml"))
				FileManager.createConfig("spawn.yml", "location", ((Player)sender).getLocation());
			else
				FileManager.updateConfig("spawn.yml", "location", ((Player)sender).getLocation());
			sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Lokalizacja spawnu ustawiona."));
		}
		else 
		{
			if(args[0] == "NONE")
			{
				sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Lokalizacja spawnu usunięta."));
				FileManager.deleteFile("spawn.yml");
				return true;
			}
		}
		return true;
    }
}
