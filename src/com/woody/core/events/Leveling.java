package com.woody.core.events;

import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.woody.core.Config;
import com.woody.core.Main;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;

public class Leveling implements Listener{
	
	@EventHandler
	 public static void OnPlayerDeath(PlayerDeathEvent e) 
	 {
		//xd
		if(!PlayerManager.onlinePlayers.containsKey(e.getEntity()))
			return;
	 }
	
	public static long CalculateOnDeath(Player p) 
	{
		if(Config.expLose > 0 && Config.expLose <= 1) {
			 long lost = (long)(PlayerManager.getPlayer(p).getExp() * Config.expLose);
			 PlayerManager.getPlayer(p).addExp(lost * -1);
			 p.sendMessage(StringManager.Colorize(Config.errorColor + "Utracono &l" + lost + "&r" + Config.errorColor + " doswiadczenia!"));
			 
			 if(Config.convertExp) 
			 {
				 for(int i = 10; i > 0; i--)
				 {
					 ExperienceOrb eo = (ExperienceOrb)p.getWorld().spawnEntity(p.getLocation(), EntityType.EXPERIENCE_ORB);
					 eo.setExperience((int)(lost * 0.1f));
					 eo.setGravity(false);
					 eo.setGlowing(true);
					 eo.setInvulnerable(true);
					 eo.setSilent(true);
					 eo.playEffect(EntityEffect.HURT_EXPLOSION);
				 }
			 }
			 return lost;
		 }
		return 0;
	}
	
	@EventHandler
	 public static void OnPlayerRespawn(PlayerRespawnEvent e) 
	 {
		Bukkit.getScheduler().runTaskLater(Main.instance, new Runnable(){

			@Override
			public void run() {
				if(PlayerManager.onlinePlayers.containsKey(e.getPlayer()))
					PlayerManager.onlinePlayers.get(e.getPlayer()).updateExpBar();
			}
		}, 1);
	 }
	
	@EventHandler
	public static void OnPlayerExpPickup(PlayerExpChangeEvent e) 
	{
		Player p = e.getPlayer();
		if(Config.convertExp)
			PlayerManager.onlinePlayers.get(p).addExp(e.getAmount());
		
		e.setAmount(0);
	}
	
	/*@EventHandler
	public static void OnPlayerAdvancement(PlayerAdvancementDoneEvent e) 
	{
		e.getAdvancement().
		Player p = e.getPlayer();
		if(Config.convertExp)
			PlayerManager.onlinePlayers.get(p).addExp(e.getAmount());
		else
			e.setAmount(0);
	}*/
	
}
