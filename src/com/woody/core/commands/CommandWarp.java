package com.woody.core.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.woody.core.Config;
import com.woody.core.util.CooldownManager;
import com.woody.core.util.FileManager;
import com.woody.core.util.StringManager;

public class CommandWarp implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		if(args.length < 1)
		{
			sender.sendMessage(StringManager.Colorize(command.getUsage()));
			sender.sendMessage(StringManager.Colorize(Config.infoColor + "&lLista warpow: " + Config.otherColor + String.join(", ", getWarps(sender))));
			return true;
		}
		if(sender.hasPermission("core.warp.create") || sender.hasPermission("core.warp.delete"))
			switch(args[0])
			{
				case "create":
					createWarp(sender, args[1]);
					break;
				case "delete":
					deleteWarp(sender, args[1]);
					break;
				case "set":
					switch(args[2])
					{
						case "separate-cooldown":
							if(args[3] == "true" || args[3] == "t")
							{
								if(!FileManager.checkFileExistence("warps/" + args[1] + ".yml"))
								{
									sender.sendMessage(StringManager.Colorize(Config.errorColor+"Taki warp nie istnieje."));
									return true;
								}
								
								FileManager.updateConfig("warps/"+args[1] + ".yml", "separate-cooldown", true);
							}
							else
								if(args[3] == "false" || args[3] == "f")
								{
									
									FileManager.updateConfig("warps/"+args[1] + ".yml", "separate-cooldown", false);
								}
						break;
						case "cooldown":
							int amount = Integer.parseInt(args[3]);
							if(amount > 0)
							{
								sender.sendMessage(StringManager.Colorize(Config.infoColor+"Ustawienie Zapisane."));
								FileManager.updateConfig("warps/"+args[1] + ".yml", "cooldown", amount);
							}
							else
							{
								if(amount == 0)
								{
									sender.sendMessage(StringManager.Colorize(Config.infoColor+"Cooldown zresetowany."));
									FileManager.updateConfig("warps/"+args[1] + ".yml", "cooldown", 0);
								}
								else
								{
									sender.sendMessage(StringManager.Colorize(Config.infoColor+"Cooldown zresetowany."));
									FileManager.updateConfig("warps/"+args[1] + ".yml", "cooldown", Config.tpCd);
								}
							}
							break;
					}
					break;
				default:
					teleportToWarp(sender, args[0]);
					break;
			}
		else
		{
			teleportToWarp(sender, args[0]);
		}
			return true;
    }
	public static List<String>getWarps(CommandSender sender) 
	{
		ArrayList<String> list = new ArrayList<>();
		for ( File F : FileManager.listFiles("warps/")) 
		{
			if(F.getName().contains(".yml"))
			{
				if(sender!= null)
				{
					if(sender.hasPermission("warps."+F.getName().replace(".yml", "")))
					{
						list.add(F.getName().replace(".yml", ""));
					}
				}
				else
					list.add(F.getName().replace(".yml", ""));
			}
		}
		
		return list;
	}
	
	public static void createWarp(CommandSender sender, String warpName) 
	{
		if(!sender.hasPermission("core.warp.create"))
		{
			sender.sendMessage(StringManager.Colorize(Config.errorColor+"Nie masz do tego uprawnien!"));
			return;
		}
		
		if(warpName == null)
		{
			sender.sendMessage(StringManager.Colorize(Config.errorColor+"Musisz podac nazwe warp'a."));
			return;
		}
		
		if(FileManager.checkFileExistence("warps/" + warpName + ".yml"))
			sender.sendMessage(StringManager.Colorize(Config.infoColor + "Taki warp juz istnieje."));
		else
		{
			sender.sendMessage(StringManager.Colorize(Config.infoColor + "Warp" + warpName +" utworzony"));
			HashMap<String, Object> hm = new HashMap<>();
			hm.put("location", ((Player)sender).getLocation());
			hm.put("separate-cooldown", false);
			hm.put("cooldown", Config.tpCd);
			FileManager.createConfig("warps/" + warpName + ".yml", hm);
		}
	}
	
	public static void deleteWarp(CommandSender sender, String warpName) 
	{
		if(!sender.hasPermission("core.warp.delete"))
		{
			sender.sendMessage(StringManager.Colorize(Config.errorColor + "Nie masz do tego uprawnien!"));
			return;
		}
		
		if(warpName == null)
		{
			sender.sendMessage(StringManager.Colorize(Config.errorColor+"Musisz podac nazwe warp'a."));
			return;
		}
		
		if(!FileManager.checkFileExistence("warps/" + warpName + ".yml"))
			sender.sendMessage(StringManager.Colorize(Config.infoColor + "Taki warp nie istnieje."));
		else
		{
			sender.sendMessage(StringManager.Colorize(Config.infoColor + "Warp" + warpName +" usuniety."));
			FileManager.deleteFile("warps/" + warpName + ".yml");
		}
		return;
	}
	
	public static void teleportToWarp(CommandSender sender, String warpName) 
	{
		if(!FileManager.checkFileExistence("warps/"+warpName+".yml"))
		{
			sender.sendMessage(StringManager.Colorize(Config.infoColor+"Taki warp nie istnieje!"));
			return;
		}
		
		FileConfiguration fc = FileManager.getConfig("warps/" + warpName + ".yml");
		boolean isPermitted = sender.hasPermission("core.warps.*");
		if(!isPermitted)
			isPermitted = sender.hasPermission("core.warps." + warpName);
		
		if(isPermitted)
		{
			Player player = (Player)sender;
			long cd = 0;
			if(fc.getBoolean("separate-cooldown"))
			{
				cd = CooldownManager.getCooldown(player, "TELEPORT-WARP."+warpName, true);
			}
			else
			{
				cd = CooldownManager.getCooldown(player, "TELEPORT", true);
			}
			
			if(cd > 0)
			{
				player.sendMessage(StringManager.Colorize(Config.infoColor + "Teleportacja do tego warpa bedzie mozliwa za &l" + cd + Config.infoColor + " sekund."));
				return;
			}
			
			if(fc.getBoolean("separate-cooldown"))
				CooldownManager.cooldown(player, "TELEPORT-WARP."+warpName, FileManager.getConfig("warps/" + warpName + ".yml").getInt("cooldown"));
			else
				CooldownManager.cooldown(player, "TELEPORT", Config.tpCd);
			
			player.teleport(FileManager.getConfig("warps/"+warpName+".yml").getLocation("location"));
			player.sendMessage(StringManager.Colorize(Config.infoColor + "Teleportacja do &l" + warpName));
			
		}
		else
		{
			sender.sendMessage(StringManager.Colorize(Config.errorColor + "Nie posiadasz uprawnien do tego warpa."));
		}
	}
}