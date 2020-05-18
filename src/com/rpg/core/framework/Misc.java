package com.rpg.core.framework;

import java.util.ArrayList;

public class Misc {
	public static ArrayList<String> cooldowns = new ArrayList<>();
	
	public static void Cooldown(String playerNick, String thing)
	{
		if(cooldowns.contains(playerNick + "-" + thing))
			return;
		else
			cooldowns.add(playerNick + "-" + thing);
	}
	
	public static boolean getCooldown(String playerNick, String thing)
	{
		if(cooldowns.contains(playerNick + "-" + thing))
			return true;
		else
			return false;
	}
	
	public static void resetCooldown(String playerNick, String thing)
	{
		if(cooldowns.contains(playerNick + "-" + thing))
			cooldowns.remove(playerNick + "-" + thing);
		else
			return;
	}
}
