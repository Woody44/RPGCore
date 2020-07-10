package com.rpg.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandForce  implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		String arguments = null;
		if(args.length > 2)
			for(int i = 2; i < args.length; i++) 
			{
				if(i == 2)
					arguments += args[i];
				else
					arguments += " " + args[i];
			}
		String cmd = args[1];
		if(arguments != null)
			cmd += " " + arguments;
		
    	Player player = Bukkit.getPlayer(args[0]);
    	player.performCommand(cmd);
    	return true;
    }
}
