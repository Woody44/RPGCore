package com.woody.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.woody.core.Config;
import com.woody.core.GLOBALVARIABLES;
import com.woody.core.types.CustomPlayer;
import com.woody.core.types.Profile;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;

public class CommandLevel implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		CustomPlayer cp;
		if(!sender.hasPermission("woody.level.admin") || args.length == 0)
		{
			Profile profile = PlayerManager.getOnlinePlayer((Player) sender).getProfile();
			sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Aktualny poziom: &2" + profile .getLevel() + "&6&l [ &2" + profile .getExp() + "&7/&a " + Config.levels.get(profile .getLevel()).get("xp") + " &6&l]"));
		}
		else
		{
			switch(args[0])
			{
				case "get":
					if(args.length == 1)
						cp = PlayerManager.getOnlinePlayer((Player)sender);
					else
						cp = PlayerManager.getOnlinePlayer(Bukkit.getPlayer(args[1]));
					
					if(cp != null)
						sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Aktualny Poziom Gracza &c" + args[1] + "&6&l: &2" + cp.getProfile().getLevel() + "&6&l [ &2" + cp.getProfile().getExp() + "&7/&a " + Config.levels.get(cp.getProfile().getLevel()).get("xp") + " &6&l]"));
					break;
				case "set":
					cp = PlayerManager.getOnlinePlayer(Bukkit.getPlayer(args[1]));
					if(Integer.parseInt(args[2]) <= Config.levels.size() && Integer.parseInt(args[2]) > 0 && cp != null)
					{
						cp.getProfile().setLevel(Integer.parseInt(args[2]));
						cp.getProfile().setExp(0);
					}
					else
						sender.sendMessage(StringManager.Colorize("&cWprowadzono zla wartosc!"));
					sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Aktualny Poziom Gracza &c" + args[1] + "&6&l: &2" + cp.getProfile().getLevel() + "&6&l [ &2" + cp.getProfile().getExp() + "&7/&a " + Config.levels.get(cp.getProfile().getLevel()).get("xp") + " &6&l]"));
					cp.getProfile().save();
					break;
				
				case "setexp":
					cp = PlayerManager.getOnlinePlayer(Bukkit.getPlayer(args[1]));
					if(Long.parseLong(args[2]) > 0)	
						cp.getProfile().setExp(Long.parseLong(args[2]));
					else
						cp.getProfile().setExp(0);
					sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Aktualny Poziom Gracza &c" + args[1] + "&6&l: &2" + cp.getProfile().getLevel() + "&6&l [ &2" + cp.getProfile().getExp() + "&7/&a " + Config.levels.get(cp.getProfile().getLevel()).get("xp") + " &6&l]"));
					cp.getProfile().save();
					break;
			}
		}
		
		return true;
    }
}
