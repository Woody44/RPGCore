package com.rpg.core.framework;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import com.rpg.core.CoreConfig;
import com.rpg.core.Main;

public class Misc {
	public static Main main = Main.GetMe();
	
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
	
		float chance = chanceInPercentFromZeroToHundred_DoNotEvenTryOtherValues / 100;
		float value = (float)Math.random();
		
		if(value <= chance)
			 return true;
		else return false;
	}
	
	public static int ExpToLvl(int exp) 
	{
		for(int i=0; i < CoreConfig.levels.length; i++)
    		if(i < CoreConfig.levels.length-1) {
        		if(exp >= CoreConfig.levels[i] && exp < CoreConfig.levels[i+1])
        			return i;
        		else continue;
    		}
    		else
    			return CoreConfig.levels.length -1;
		
		return 0;
	}
}
