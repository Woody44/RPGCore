package com.rpg.core.framework;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.rpg.core.CoreConfig;

public class ChatManager {
		
	public static String GetColorized(String message) 
	{
		message = message.replace(" (red) ", "&c");
		message = message.replace(" (gold) ", "&e");
		message = message.replace(" (white) ", "&f");
		message = message.replace(" (green) ", "&2");
		message = message.replace(" (blue) ", "&b");
		message = message.replace(" (orange) ", "&6");
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	public static String FillVars(String format, Player player, String message) 
	{
		int lvl = DatabaseManager.GetPlayerLevel(player.getUniqueId().toString());
		format = format.replace(" {PLAYER} ", player.getDisplayName());
		format = format.replace(" {LEVEL} ", lvl + "");
		format = format.replace(" {LEVEL_MIN} ", CoreConfig.chatLvlMin+"");
		format = format.replace(" {MESSAGE} ", message);
		return format;
	}
	
	public static String FillVars(String format, Player player) 
	{
		int lvl = DatabaseManager.GetPlayerLevel(player.getUniqueId().toString());
		format = format.replace(" {PLAYER} ", player.getDisplayName());
		format = format.replace(" {LEVEL} ", lvl + "");
		format = format.replace(" {LEVEL_MIN} ", CoreConfig.chatLvlMin+"");
		return format;
	}
}