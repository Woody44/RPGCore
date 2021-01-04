package com.woody.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSuicide implements CommandExecutor{

			@Override
			public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
		    {
				Player p = (Player)sender;
				p.damage(999999999, p);
				return true;
		    }
		}
