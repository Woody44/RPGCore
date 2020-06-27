package com.rpg.core.framework;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.rpg.core.CoreConfig;

public class ChatManager {
		
	public static String GetColorized(String message) 
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
	
	public static String FillVars(String format, Player player, String message) 
	{
		int lvl = DatabaseManager.GetPlayerExp(player.getUniqueId().toString());
		lvl = Misc.ExpToLvl(lvl);
		format = format.replace("{PLAYER}", player.getDisplayName());
		format = format.replace("{LEVEL}", lvl + "");
		format = format.replace("{LEVEL_MIN}", CoreConfig.chatLvlMin+"");
		format = format.replace("{MESSAGE}", message);
		return format;
	}
	
	public static String FillVars(String format, Player player) 
	{
		int lvl = DatabaseManager.GetPlayerExp(player.getUniqueId().toString());
		lvl = Misc.ExpToLvl(lvl);
		format = format.replace("{PLAYER}", player.getDisplayName());
		format = format.replace("{LEVEL}", lvl + "");
		format = format.replace("{LEVEL_MIN}", CoreConfig.chatLvlMin+"");
		return format;
	}
	
	public static String FillVars(String format, Location loc) 
	{
		format = format.replace("{WORLD}", loc.getWorld().getName());
		format = format.replace("{X}", loc.getX() + "");
		format = format.replace("{Y}", loc.getY() + "");
		format = format.replace("{Z}", loc.getZ() + "");
		return format;
	}
}