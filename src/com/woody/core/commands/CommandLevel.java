package com.woody.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.woody.core.Config;
import com.woody.core.types.CustomPlayer;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;

public class CommandLevel implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		CustomPlayer cp; 
		if(args.length < 1)
		{
			cp = PlayerManager.onlinePlayers.get((Player)sender);
			sender.sendMessage(StringManager.Colorize("&6&lAktualny poziom: &2" + cp.getLevel() + "&6&l [ &2" + cp.getExp() + "&7/&a " + Config.levels.get(cp.getLevel()) + " &6&l]"));
		}
		else
		{
			if(sender.hasPermission("core.level.set"))
			switch(args[0])
			{
				case "get":
					cp = PlayerManager.onlinePlayers.get(Bukkit.getPlayer(args[1]));
					if(cp != null)
						sender.sendMessage(StringManager.Colorize("&6&lAktualny Poziom Gracza &r&6" + args[1] + "&6&l: &2" + cp.getLevel() + "&6&l [ &2" + cp.getExp() + "&7/&a " + Config.levels.get(cp.getLevel()) + " &6&l]"));
				break;
				case "set":
					cp = PlayerManager.onlinePlayers.get(Bukkit.getPlayer(args[1]));
					if(Integer.parseInt(args[2]) <= Config.levels.size())
						cp.setLevel(Integer.parseInt(args[2]));
					else
						sender.sendMessage("&cWprowadzono zbyt wysoka wartosc!");
					sender.sendMessage(StringManager.Colorize("&6&lAktualny Poziom Gracza &r&6" + args[1] + "&6&l: &2" + cp.getLevel() + "&6&l [ &2" + cp.getExp() + "&7/&a " + Config.levels.get(cp.getLevel()) + " &6&l]"));
					cp.saveProfile();
					break;
				
				case "setexp":
					cp = PlayerManager.onlinePlayers.get(Bukkit.getPlayer(args[1]));
					//if(Long.parseLong(args[2]) >= 0 && Long.parseLong(args[2]) < Config.levels.get(cp.getLevel()))
						cp.setExp(Long.parseLong(args[2]));
					//else
					//	sender.sendMessage("&cWprowadzono zbyt wysoka wartosc!");
					sender.sendMessage(StringManager.Colorize("&6&lAktualny Poziom Gracza &r&6" + args[1] + "&6&l: &2" + cp.getLevel() + "&6&l [ &2" + cp.getExp() + "&7/&a " + Config.levels.get(cp.getLevel()) + " &6&l]"));
					cp.saveProfile();
					break;
			}
		}
		
		return true;
    }

}
