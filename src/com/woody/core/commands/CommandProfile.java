package com.woody.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.woody.core.Config;
import com.woody.core.types.CustomPlayer;
import com.woody.core.types.Profile;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;

public class CommandProfile implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		CustomPlayer cp = PlayerManager.onlinePlayers.get((Player)sender);
		if(cp == null)
		{
			((Player)sender).kickPlayer("Wystapil blad, relognij sie ... jesli problem sie powtorzy, skontaktuj sie z administratorem!");
			return true;
		}
		
		try {
			int profileid = Integer.parseInt(args[0]);
			
			if(cp.checkProfile(profileid))
			{
				cp.changeProfile(profileid);
				sender.sendMessage(StringManager.Colorize(Config.infoColor + "Zaladowano profil #"+args[0]));
				PlayerManager.onlinePlayers.replace((Player)sender, cp);
			}
			else
			{
				if(profileid > Config.maxProfiles)
				{
					sender.sendMessage(StringManager.Colorize(Config.errorColor + "Mozesz posiadac maksymalnie (bold)" + Config.maxProfiles + "&r" + Config.errorColor + " profil(i)!"));
					return true;
				}
				PlayerManager.createProfile(((Player)sender).getUniqueId().toString(), new Profile(profileid));
				sender.sendMessage(StringManager.Colorize(Config.infoColor + "Tworzenie profilu #"+args[0]));
			}
			return true;
		}catch (NumberFormatException e) {
			sender.sendMessage(StringManager.Colorize(Config.errorColor + "Profil wyraza sie poprzez liczbe!"));
			return true;
	  }
    }
}
