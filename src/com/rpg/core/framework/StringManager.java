package com.rpg.core.framework;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.rpg.core.CoreConfig;

public class StringManager {
		
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
		return ChatColor.stripColor(message);
	}
	
	public static String FillExp(String string, Player player) 
	{
		String uuid = player.getUniqueId().toString();
		int exp = PlayerManager.getPlayer(uuid).experience;
		int lvl = Misc.ExpToLvl(exp);
		string = string.replace("{EXP}", exp+"");
		string = string.replace("{LEVEL}", lvl+"");
		string = string.replace("{LEVEL_MIN}", CoreConfig.chatLvlMin+"");
		return string;
	}
	
	public static String FillPlayer(String string, Player player) 
	{
		string = string.replace("{PLAYER}", player.getDisplayName());
		return string;
	}
	
	public static String FillMessage(String string, Player player, String message)
	{
		string = string.replace("{MESSAGE}", message);
		return string;
	}
	
	public static String FillChat(String format, Player player, String message) 
	{
		format = FillWorld(format, player.getLocation());
		format = FillExp(format, player);
		format = FillPlayer(format, player);
		format = FillMessage(format, player, message);
		return format;
	}
	
	public static String FillWorld(String format, Location loc) 
	{
		format = format.replace("{WORLD}", loc.getWorld().getName());
		format = format.replace("{X}", loc.getX() + "");
		format = format.replace("{Y}", loc.getY() + "");
		format = format.replace("{Z}", loc.getZ() + "");
		return format;
	}
}