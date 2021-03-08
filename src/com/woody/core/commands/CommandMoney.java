package com.woody.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.woody.core.Config;
import com.woody.core.GLOBALVARIABLES;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;

public class CommandMoney implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		if(!Config.economyModule)
		{
			sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.ERROR_PREFIX + "Moduł Ekonomii został wyłączony."));
		}
		
		if(!(sender instanceof Player))
		{
			if(args.length == 0)
				Bukkit.getLogger().warning("You must specify player!");
			else
				switch(args[0]) 
				{
					case "set":
						Player player = Bukkit.getPlayer(args[1]);
						if(player!=null)
							PlayerManager.getOnlinePlayer(player).getProfile().setMoney(Integer.parseInt(args[2]));
						else
							Bukkit.getLogger().warning("You must specify player!");
						break;
					default:
						Player p = Bukkit.getPlayer(args[0]);
						if(p != null)
							sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.BANK_PREFIX + "Stan Konta: &a" + PlayerManager.getOnlinePlayer(p).getProfile().getMoney() + " &2&l" + Config.currencySymbol));
						else
							Bukkit.getLogger().warning("You must specify player!");
						break;
				}
			
			return true;
		}

		if(args.length == 0)
			sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.BANK_PREFIX + "Stan Konta: &a" + PlayerManager.getOnlinePlayer(((Player)sender)).getProfile().getMoney() + " &2&l" + Config.currencySymbol));
		else
			switch(args[0]) 
			{
				case "set":
					if(sender.hasPermission("woody.money.set")) 
					{
						Player player = Bukkit.getPlayer(args[1]);
						PlayerManager.getOnlinePlayer(player).getProfile().setMoney(Integer.parseInt(args[2]));
						break;
					}
				default:
					Player p = Bukkit.getPlayer(args[0]);
					if(p != null)
						sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.BANK_PREFIX + "Stan Konta: &a" + PlayerManager.getOnlinePlayer(p).getProfile().getMoney() + " &2&l" + Config.currencySymbol));
					break;
			}
		return true;
    }
}
