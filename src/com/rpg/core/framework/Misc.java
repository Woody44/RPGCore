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
			Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> { Misc.resetCooldown(playerNick, thing); }, (int)(timeInSeconds * 20));
			return true;
		}
	}
	public static boolean getCooldown(String playerNick, String thing)
	{
		return cooldowns.contains(playerNick + "-" + thing);
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
		
		return value <= chance;
	}
	
	public static int ExpToLvl(long exp) 
	{
		for(int i = 0; i < CoreConfig.levels.size(); i++)
		{
			if(i + 1 < CoreConfig.levels.size())
			{
				if(exp >= CoreConfig.levels.get(i) && exp < CoreConfig.levels.get(i+1)) 
				{
					return i;
				}
				else if(exp < CoreConfig.levels.get(0))
					return 0;
			}
			else 
			{
				return CoreConfig.levels.size() - 1;
			}
		}
		return 0;
	}
}