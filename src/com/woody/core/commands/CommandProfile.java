package com.woody.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.woody.core.Config;
import com.woody.core.GLOBALVARIABLES;
import com.woody.core.types.CustomPlayer;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;

public class CommandProfile implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		if(!(sender instanceof Player))
		{
			Bukkit.getLogger().warning("This command id for players use only.");
			return true;
		}
		
		CustomPlayer cp = PlayerManager.getOnlinePlayer((Player)sender);
		if(cp == null)
		{
			((Player)sender).kickPlayer("Wystąpił błąd, relognij sie ... jeśli problem się powtórzy - skontaktuj się z administratorem!");
			return true;
		}
		
		try {
			int profileid = Integer.parseInt(args[0]);
			
			if(cp.isProfileValid(profileid))
			{
				if(cp.getProfile().id != profileid){
					cp.switchProfile(profileid);
					sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Załadowano profil &c#"+args[0]));
				}
				else
				{
					sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Korzystasz już z tego profilu!"));
				}
			}
			else
			{
				int maxprofs = 0;
				boolean permissed = false;
				for(String key : Config.maxProfiles.keySet())
				{
					if(cp.player.hasPermission("woody.maxprofiles." + key))
					{
						if(Config.maxProfiles.get(key) < 0)
						{
							permissed = true;
							maxprofs = -1;
							break;
						}
							if(maxprofs < Config.maxProfiles.get(key))
							continue;
						maxprofs = Config.maxProfiles.get(key);
						permissed = true;
					}
				}
				if(!permissed)
					maxprofs = Config.maxProfiles.get("default");

				if(profileid > maxprofs && maxprofs >= 0)
				{
					sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Możesz posiadać maksymalnie &c" + maxprofs + "&6 profil(e/i)!"));
					return true;
				}

				cp.createProfile(profileid);
				sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Tworzenie profilu &c#"+args[0]));
			}
			return true;
		}catch (NumberFormatException e) {
			sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.ERROR_PREFIX + "Profil wyraża się poprzez liczbę!"));
			return true;
	  }
    }
}
