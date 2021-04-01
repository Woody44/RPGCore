package com.woody.core.events;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.util.Vector;

import com.woody.core.Config;
import com.woody.core.GLOBALVARIABLES;
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
	
	public static long CalculateOnDeath(Player p) 
	{
		if(Config.expLose > 0) 
		{
			if(Config.expLose > 1)
				Config.expLose = 1;
					
			long lost = (long)(PlayerManager.getOnlinePlayer(p).getProfile().getExp() * Config.expLose);
			PlayerManager.getOnlinePlayer(p).getProfile().addExp(lost * -1);
			p.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Lost &c" + lost + "&6 experience!"));
			
			if(Config.convertExp) 
			{
				for(int i = 5; i > 0; i--)
				{
					ExperienceOrb eo = (ExperienceOrb)p.getWorld().spawnEntity(p.getLocation(), EntityType.EXPERIENCE_ORB);
					eo.setVelocity(new Vector(0,0,0));
					eo.setExperience((int)(lost * 0.2f));
					eo.setGravity(true);
					eo.setCustomName(StringManager.Colorize(p.getDisplayName() + " &2&lXP"));
					eo.setGlowing(true);
					eo.setInvulnerable(true);
					eo.setSilent(true);
					eo.getLocation().getWorld().spawnParticle(Particle.CLOUD, eo.getLocation(), 150 , 0.15, 0.15, 0.15);
					eo.getWorld().spawnParticle(Particle.SPIT, eo.getLocation().getX(), eo.getLocation().getY(), eo.getLocation().getZ(), 0, 0, 0, 0.2, 100, null);
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
