package com.woody.core.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.woody.core.Config;
import com.woody.core.Main;
import com.woody.core.events.custom.LevelUpEvent;
import com.woody.core.types.CustomPlayer;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;

public class Leveling implements Listener{
	
	@EventHandler
	public static void OnLevelUP(LevelUpEvent e) 
	{
		e.getPlayer().sendMessage(StringManager.Colorize("&6&lYou have gained level " + e.getLevel() + " !"));
	}
	
	public static long CalcExpLose(CustomPlayer p) 
	{
		if(Config.expLose > 0) 
		{
			if(Config.expLose > 1)
				Config.expLose = 1;
					
			return (long)(p.getProfile().getExp() * Config.expLose);
		}
		return 0;
	}
	
	@EventHandler
	 public static void OnPlayerRespawn(PlayerRespawnEvent e) 
	 {
		Bukkit.getScheduler().runTaskLater(Main.instance, new Runnable(){

			@Override
			public void run() {
				CustomPlayer cp = PlayerManager.getOnlinePlayer(e.getPlayer());
				if(cp!=null)
					cp.getProfile().updateExpBar();
			}
		}, 1);
	 }
	
	@EventHandler
	public static void OnPlayerExpPickup(PlayerExpChangeEvent e) 
	{
		Player p = e.getPlayer();
		if(Config.convertExp)
			PlayerManager.getOnlinePlayer(p).getProfile().addExp(e.getAmount());
		
		e.setAmount(0);
	}
}
