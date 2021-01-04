package com.woody.core.util;

import java.text.DecimalFormat;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.woody.core.Config;

import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class StringManager {
	public static boolean HasBadWords(String string)
	{
		for(String s : Config.BadWords)
			if(string.contains(s))
				return true;
		
		return false;
	}
	
	public static String Colorize(String message) 
	{
		message = message.replace("(white)", "&f");
		message = message.replace("(black)", "&0");
		message = message.replace("(blue)", "&b");
		message = message.replace("(green)", "&a");
		message = message.replace("(pink)", "&d");
		message = message.replace("(purple)", "&d");
		message = message.replace("(red)", "&c");
		message = message.replace("(gold)", "&e");
		message = message.replace("(orange)", "&6");
		message = message.replace("(gray)", "&7");
		message = message.replace("(bold)", "&l");
		message = message.replace("(strike)", "&m");
		message = message.replace("(italic)", "&o");
		message = message.replace("(reset)", "&r");
		message = message.replace("(wtf)", "&k");
		message = message.replace("(uline)", "&n");
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public static String NoColors(String message) 
	{
		message = message.replace("&0", "");
		message = message.replace("&1", "");
		message = message.replace("&2", "");
		message = message.replace("&3", "");
		message = message.replace("&4", "");
		message = message.replace("&5", "");
		message = message.replace("&6", "");
		message = message.replace("&7", "");
		message = message.replace("&8", "");
		message = message.replace("&9", "");
		message = message.replace("&a", "");
		message = message.replace("&b", "");
		message = message.replace("&c", "");
		message = message.replace("&d", "");
		message = message.replace("&e", "");
		message = message.replace("&f", "");
		message = message.replace("&k", "");
		message = message.replace("&l", "");
		message = message.replace("&m", "");
		message = message.replace("&n", "");
		message = message.replace("&o", "");
		message = message.replace("&r", "");
		return ChatColor.stripColor(message);
	}
	
	public static String FillExp(String string, Player player) 
	{
		DecimalFormat decimalFormat = new DecimalFormat("#.00");
		string = string.replace("{EXP}", decimalFormat.format((float)(PlayerManager.onlinePlayers.get(player).getExp() / Config.levels.get(PlayerManager.onlinePlayers.get(player).getLevel()+1))) + "");
		string = string.replace("{LEVEL}", PlayerManager.onlinePlayers.get(player).getLevel() + "");
		string = string.replace("{LEVEL_MIN}", Config.chatLvlMin+"");
		return string;
	}
	
	public static String FillPlayer(String string, Player[] player) 
	{
		for(int i = 0; i < player.length; i++)
		{
			string = string.replace("{PLAYER_" + i + "}", player[i].getDisplayName());
			string = string.replace("{RPLAYER_" + i + "}", player[i].getName());
		}
		return string;
	}
	
	public static String FillPlayer(String string, Player player) 
	{
			string = string.replace("{PLAYER}", player.getDisplayName());
			string = string.replace("{RPLAYER}", player.getName());
		return string;
	}
	
	public static String FillMessage(String string, Player player, String message)
	{
		string = string.replace("{MESSAGE}", message);
		return string;
	}
	
	public static String FillChat(String format, Player player, String message, Player receiver) 
	{
		format = FillWorld(format, player.getLocation());
		format = FillExp(format, player);
		format = FillMessage(format, player, message);
		format = FillGroup(format, player);
		format = FillPings(format, receiver, true);
		format = FillPlayer(format, player);
		return format;
	}
	
	public static String FillWorld(String format, Location loc) 
	{
		format = format.replace("{WORLD_TYPE}", loc.getWorld().getEnvironment().toString());
		format = format.replace("{WORLD}", loc.getWorld().getName());
		format = format.replace("{X}", loc.getBlockX() + "");
		format = format.replace("{Y}", loc.getBlockY() + "");
		format = format.replace("{Z}", loc.getBlockZ() + "");
		format = format.replace("{BIOME}", loc.getWorld().getBiome(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()).name());
		return format;
	}
	
	public static String FillPings(String format, Player target, boolean centerColor) 
	{
		if(target ==null)
			return format;
		
		boolean hasPing = format.contains(NoColors(target.getDisplayName())) || format.contains(target.getName()) ||
				format.contains("@"+StringManager.FillGroup("{GROUP}", target)) || format.contains("@everyone");
		if(centerColor)
		{
			if(hasPing)
			{
				format = format.replace(NoColors(target.getDisplayName()), Colorize(Config.pingColor + NoColors(target.getDisplayName()) + "&r"));
				format = format.replace(target.getName(), Colorize(Config.pingColor + target.getName()) + "&r");
				format = format.replace("@"+StringManager.FillGroup("{GROUP}", target), Colorize(Config.pingColor + "@"+StringManager.FillGroup("{GROUP}", target)) + "&r");
				format = format.replace("@everyone", Colorize(Config.pingColor + "@everyone") + "&r");
			}
		}
		else
		{
			if(hasPing)
				format = Colorize(Config.pingColor + NoColors(format));
		}
		return format;
	}
	
	public static String FillGroup(String string, Player player) 
	{
		if(Bukkit.getPluginManager().isPluginEnabled("PermissionsEx"))
		{
			PermissionUser user = PermissionsEx.getUser(player);
			List<String> groups = user.getParentIdentifiers();
			string = string.replace("{GROUP}", groups.get(0));
			return string;
		}
		else 
		{
			if(player.isOp())
				string = string.replace("{GROUP}", "Operator");
			else
				string = string.replace("{GROUP}", "Player");
			return string;
		}
		
	}
}
