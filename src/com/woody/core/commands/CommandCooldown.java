package com.woody.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.woody.core.util.CooldownManager;

public class CommandCooldown implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		if(args.length < 2)
			return true;
		
		String thing = args[1];
		
		if(sender.hasPermission("core.cooldown.admin"))
		switch(args[0])
		{
			case "do":
				int seconds = Integer.parseInt(args[2]);
				CooldownManager.cooldown((Player)sender, thing, seconds);
				break;
			case "check":
				sender.sendMessage("cooldown: " + CooldownManager.getCooldown((Player)sender, thing, true));
				break;
				
			case "checku":
				sender.sendMessage("cooldown: " + CooldownManager.getCooldown((Player)sender, thing, false));
				break;
				
			case "reset":
				CooldownManager.resetCooldown((Player)sender, thing);
				break;
		}
		
		
		return true;
    }

}
