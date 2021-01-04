package com.woody.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.woody.core.util.CooldownManager;

public class CommandCooldown implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		if(args.length < 2)
			return true;
		
		String thing = args[2];
		switch(args[0])
		{
			case "do":
				int seconds = Integer.parseInt(args[3]);
				CooldownManager.cooldown(Bukkit.getPlayer(args[1]), thing, seconds);
				break;
			case "check":
				sender.sendMessage("cooldown: " + CooldownManager.getCooldown( Bukkit.getPlayer(args[1]), thing, true));
				break;
				
			case "reset":
				CooldownManager.resetCooldown(Bukkit.getPlayer(args[1]), thing);
				break;
		}
		
		
		return true;
    }

}
