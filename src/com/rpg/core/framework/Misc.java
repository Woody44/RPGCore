package com.rpg.core.framework;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import com.rpg.core.Main;

public class Misc {
	public static Main main;
	public Misc(Main main)
	{
		Misc.main = main;
	}
	
	public static ArrayList<String> cooldowns = new ArrayList<>();
	
	public static boolean Cooldown(String playerNick, String thing, float timeInSeconds)
	{
		if(cooldowns.contains(playerNick + "-" + thing))
			return false;
		else
		{
			cooldowns.add(playerNick + "-" + thing);
			Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> { Misc.resetCooldown(playerNick, thing); }, (int)timeInSeconds * 20);
			return true;
		}
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
	
	public static boolean Chance(float chanceInPercentFromZeroToHundred_DoNotEvenTryOtherValues) 
	{
		if(chanceInPercentFromZeroToHundred_DoNotEvenTryOtherValues < 0)
			chanceInPercentFromZeroToHundred_DoNotEvenTryOtherValues = 0;
		else if (chanceInPercentFromZeroToHundred_DoNotEvenTryOtherValues > 100)
			chanceInPercentFromZeroToHundred_DoNotEvenTryOtherValues = 100;
		
		float rv = (float)Math.random();
		float value = rv * chanceInPercentFromZeroToHundred_DoNotEvenTryOtherValues;
		
		if(value <= chanceInPercentFromZeroToHundred_DoNotEvenTryOtherValues)
			 return true;
		else return false;
	}
}
