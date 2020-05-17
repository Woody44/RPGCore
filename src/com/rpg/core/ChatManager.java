package com.rpg.core;
import org.bukkit.ChatColor;

public class ChatManager {
	public static String GetColorized(String message) 
	{
		return ChatColor.translateAlternateColorCodes('&', message);
	}
}