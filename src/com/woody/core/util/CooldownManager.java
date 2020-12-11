package com.woody.core.util;

import java.util.Calendar;
import java.sql.Timestamp;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.woody.core.Main;

public class CooldownManager {
	static HashMap<String, HashMap<String, Long>> cooldowns = new HashMap<>();
	
	public static void cooldown(Player player, String thing, float seconds) 
	{
		if(!cooldowns.containsKey(player.getUniqueId().toString()))
		{
			cooldowns.put(player.getUniqueId().toString(), new HashMap<>());
		}
		
		if(cooldowns.get(player.getUniqueId().toString()).get(thing) == null)
		{
			long now = System.currentTimeMillis();

	        Timestamp original = new Timestamp(now);
	        Calendar cal = Calendar.getInstance();
	        cal.setTimeInMillis(original.getTime());
	        cal.add(Calendar.MILLISECOND, (int)(seconds * 1000));
	        Timestamp later = new Timestamp(cal.getTime().getTime());
			cooldowns.get(player.getUniqueId().toString()).put(thing, later.getTime());
			
			Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {

				@Override
				public void run() {
					resetCooldown(player, thing);
				}}, (int)(seconds * 20));
			return;
		}
		else
		{
			return;
		}
	}
	
	public static Long getCooldown(Player player, String thing, boolean formatted) 
	{
		if(!cooldowns.containsKey(player.getUniqueId().toString()))
			return 0L;
		
		if(cooldowns.get(player.getUniqueId().toString()).containsKey(thing))
		{
			if(formatted == false)
				return cooldowns.get(player.getUniqueId().toString()).get(thing);
			else
			{
				return ((cooldowns.get(player.getUniqueId().toString()).get(thing)) - System.currentTimeMillis())/1000;
			}
		}
		else
		{
			return 0L;
		}
	}
	
	public static void resetCooldown(Player player, String thing) 
	{
		if(!cooldowns.containsKey(player.getUniqueId().toString()))
			return;
		
		if(cooldowns.get(player.getUniqueId().toString()).get(thing) != null)
		{
			cooldowns.get(player.getUniqueId().toString()).remove(thing);
			return;
		}
	}
}
