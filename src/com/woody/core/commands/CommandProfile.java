package com.woody.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.woody.core.types.CustomPlayer;
import com.woody.core.types.ProfileInfo;
import com.woody.core.util.PlayerManager;

public class CommandProfile implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		CustomPlayer cp = PlayerManager.onlinePlayers.get((Player)sender);
		if(cp == null)
		{
			((Player)sender).kickPlayer("Wystapil blad, relogowanie ... jesli problem sie powtorzy, skontaktuj sie z administratorem!");
			return true;
		}
		
		try {
			int profileid = Integer.parseInt(args[0]);
			if(cp.checkProfile(profileid))
			{
				cp.changeProfile(profileid);
				sender.sendMessage("Zaladowano profil #"+args[0]);
				PlayerManager.onlinePlayers.replace((Player)sender, cp);
			}
			else
			{
				PlayerManager.createProfile(new ProfileInfo(((Player)sender).getUniqueId().toString(), profileid));
				sender.sendMessage("Tworzenie profilu #"+args[0]);
			}
			return true;
		}catch (NumberFormatException e) {
			sender.sendMessage("Profil wyraza sie poprzez liczbe!");
			return true;
	  }
    }
}
