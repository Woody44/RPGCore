package com.woody.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.woody.core.Config;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;

public class CommandMoney implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		if(!Config.economyModule)
		{
			sender.sendMessage(StringManager.Colorize(Config.errorColor + "Modul Ekonomii zostal wylaczony."));
		}
		if(args.length == 0)
			sender.sendMessage(StringManager.Colorize("&a&lStan Konta: &2" + PlayerManager.onlinePlayers.get(((Player)sender)).getMoney() + "&a&l."));
		else
			switch(args[0]) 
			{
				case "set":
					if(sender.hasPermission("core.money.set")) 
					{
						Player player = Bukkit.getPlayer(args[1]);
						PlayerManager.onlinePlayers.get(player).setMoney(Integer.parseInt(args[2]));
						break;
					}
			}
		return true;
    }
}
