package com.woody.core.util;

import java.util.Calendar;
import java.sql.Timestamp;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.woody.core.Main;

public class CooldownManager {
	static HashMap<String, HashMap<String, Long>> cooldowns = new HashMap<>();
	
	public static void cooldown(Player player, String thing, long seconds) 
	{
		if(!cooldowns.containsKey(player.getUniqueId().toString()))
			cooldowns.put(player.getUniqueId().toString(), new HashMap<>());
		
		if(cooldowns.get(player.getUniqueId().toString()).get(thing) == null)
		{
	        Calendar cal = Calendar.getInstance();
	        cal.add(Calendar.MILLISECOND, (int)(seconds * 1000));
	        Timestamp later = new Timestamp(cal.getTime().getTime());
			cooldowns.get(player.getUniqueId().toString()).put(thing, later.getTime());
			
			Bukkit.getScheduler().runTaskLater(Main.instance, new Runnable() {

				@Override
				public void run() {
					resetCooldown(player, thing);
				}}, (int)(seconds * 20 - 20));
			return;
		}
		else
		{
			return;
		}
	}
	
	public static void cooldown(LivingEntity ent, String thing, long seconds) 
	{
		if(seconds == 0 || thing == null || thing == "")
			return;

		if(!cooldowns.containsKey(ent.getUniqueId().toString()))
			cooldowns.put(ent.getUniqueId().toString(), new HashMap<>());
		
		if(cooldowns.get(ent.getUniqueId().toString()).get(thing) == null)
		{
	        Calendar cal = Calendar.getInstance();
	        cal.add(Calendar.MILLISECOND, (int)(seconds * 1000));
	        Timestamp later = new Timestamp(cal.getTime().getTime());
			cooldowns.get(ent.getUniqueId().toString()).put(thing, later.getTime());
			
			Bukkit.getScheduler().runTaskLater(Main.instance, new Runnable() {

				@Override
				public void run() {
					resetCooldown(ent, thing);
				}}, (int)(seconds * 20 - 20));
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
				return ((cooldowns.get(player.getUniqueId().toString()).get(thing)) - System.currentTimeMillis())/1000;
		}
		else
			return 0L;
	}
	
	public static Long getCooldown(LivingEntity ent, String thing, boolean formatted) 
	{
		if(!cooldowns.containsKey(ent.getUniqueId().toString()))
			return 0L;
		
		if(cooldowns.get(ent.getUniqueId().toString()).containsKey(thing))
		{
			if(formatted == false)
				return cooldowns.get(ent.getUniqueId().toString()).get(thing);
			else
				return ((cooldowns.get(ent.getUniqueId().toString()).get(thing)) - System.currentTimeMillis())/1000;
		}
		else
			return 0L;
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
	
	public static void resetCooldown(LivingEntity ent, String thing) 
	{
		if(!cooldowns.containsKey(ent.getUniqueId().toString()))
			return;
		
		if(cooldowns.get(ent.getUniqueId().toString()).get(thing) != null)
		{
			cooldowns.get(ent.getUniqueId().toString()).remove(thing);
			return;
		}
	}
}
