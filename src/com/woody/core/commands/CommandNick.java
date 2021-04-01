package com.woody.core.commands;

import com.woody.core.util.StringManager;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandNick implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		if(!(sender instanceof Player))
		{
			Bukkit.getLogger().warning("This command is for players use only.");
			return true;
		}
		
		Player p = (Player)sender;
		
		if(args.length < 1)
		{
			p.setDisplayName(p.getName());
			return true;
		}
		p.setDisplayName(StringManager.Colorize(String.join("_", args)));
		p.setCustomName(StringManager.Colorize(String.join("_", args)));
		p.setCustomNameVisible(true);
		return true;
    }
}