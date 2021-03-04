package com.woody.core.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.woody.core.Config;
import com.woody.core.GLOBALVARIABLES;
import com.woody.core.events.custom.PlayerWarpingEvent;
import com.woody.core.util.CooldownManager;
import com.woody.core.util.FileManager;
import com.woody.core.util.StringManager;

public class CommandWarp implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		if(!(sender instanceof LivingEntity))
		{
			sender.sendMessage("This command is designed for players use only.");
			return true;
		}
		
		if(args.length < 1)
		{
			sender.sendMessage(StringManager.Colorize(command.getUsage()));
			return true;
		}
		if(sender.hasPermission("woody.warp.admin"))
			switch(args[0])
			{
				case "teleport":
					teleportToWarp(sender, args[1]);
					break;
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
							if(args[3].contentEquals("true") || args[3].contentEquals("t"))
							{
								if(!FileManager.checkFileExistence("warps/" + args[1] + ".yml"))
								{
									sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.ERROR_PREFIX + "Taki warp nie istnieje."));
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
								sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Ustawienie Zapisane."));
								FileManager.updateConfig("warps/"+args[1] + ".yml", "cooldown", amount);
							}
							else
							{
								if(amount == 0)
								{
									sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Cooldown zresetowany."));
									FileManager.updateConfig("warps/"+args[1] + ".yml", "cooldown", 0);
								}
								else
								{
									sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Cooldown zresetowany."));
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
		if(!sender.hasPermission("woody.warp.create"))
		{
			sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.ERROR_PREFIX + "Nie posiadasz do tego uprawnień!"));
			return;
		}
		
		if(warpName == null)
		{
			sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.ERROR_PREFIX + "Musisz wprowadzić nazwę warpa."));
			return;
		}
		
		if(FileManager.checkFileExistence("warps/" + warpName + ".yml"))
			sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.ERROR_PREFIX + "Taki warp już istnieje."));
		else
		{
			sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Warp &c" + warpName +"&6 utworzony"));
			HashMap<String, Object> hm = new HashMap<>();
			hm.put("location", ((Player)sender).getLocation());
			hm.put("separate-cooldown", false);
			hm.put("cooldown", Config.tpCd);
			FileManager.createConfig("warps/" + warpName + ".yml", hm);
		}
	}
	
	public static void deleteWarp(CommandSender sender, String warpName) 
	{
		if(!sender.hasPermission("woody.warp.delete"))
		{
			sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.ERROR_PREFIX + "Nie posiadasz do tego uprawnień!"));
			return;
		}
		
		if(warpName == null)
		{
			sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.ERROR_PREFIX + "Musisz wprowadzić nazwę warpa."));
			return;
		}
		
		if(!FileManager.checkFileExistence("warps/" + warpName + ".yml"))
			sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.ERROR_PREFIX + "Taki warp nie istnieje."));
		else
		{
			sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Warp &c" + warpName +"&6 usuniety."));
			FileManager.deleteFile("warps/" + warpName + ".yml");
		}
		return;
	}
	
	public static void teleportToWarp(CommandSender sender, String warpName) 
	{
		if(!FileManager.checkFileExistence("warps/"+warpName+".yml"))
		{
			sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Taki warp nie istnieje!"));
			return;
		}
		
		FileConfiguration fc = FileManager.getConfig("warps/" + warpName + ".yml");
		boolean isPermitted = sender.hasPermission("woody.warps.*");
		if(!isPermitted)
			isPermitted = sender.hasPermission("woody.warps." + warpName);
		
		if(isPermitted)
		{
			Player player = (Player)sender;
			long cd = 0;

			if(fc.getBoolean("separate-cooldown"))
				cd = CooldownManager.getCooldown((LivingEntity)player, "TELEPORT-WARP."+warpName, true);
			else
				cd = CooldownManager.getCooldown((LivingEntity)player, "TELEPORT", true);
			
			if(cd > 0)
			{
				player.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Teleportacja do tego warpa będzie możliwa za &c" + cd + "&6 sekund."));
				return;
			}
			
			
			
			if(fc.getBoolean("separate-cooldown"))
				cd = FileManager.getConfig("warps/" + warpName + ".yml").getInt("cooldown");
			else
				cd = Config.tpCd;
				
			PlayerWarpingEvent event = new PlayerWarpingEvent(player, player.getLocation(), fc.getLocation("location"), cd);
			Bukkit.getPluginManager().callEvent(event);
			if(event.isCancelled())
			{
				player.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Teleportacja przerwana!"));
				return;	
			}
			
			if(player.hasPermission("woody.teleport.nocooldown"))
				cd = 0;
			else
				cd = event.getCooldown();

			if(fc.getBoolean("separate-cooldown"))
				CooldownManager.cooldown((LivingEntity)player, "TELEPORT-WARP."+warpName, cd);
			else
				CooldownManager.cooldown((LivingEntity)player, "TELEPORT", cd);
			
			player.teleport(FileManager.getConfig("warps/"+warpName+".yml").getLocation("location"));
			player.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Teleportacja do &c" + warpName + "&6."));
			
		}
		else
		{
			sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Nie posiadasz uprawnien do tego warpa."));
		}
	}
}