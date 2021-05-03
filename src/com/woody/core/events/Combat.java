package com.woody.core.events;

/*import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.woody.core.Config;
import com.woody.core.Main;
import com.woody.core.types.CustomPlayer;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;

class InCombat
{
	CustomPlayer cp;
	
	BukkitRunnable task = new BukkitRunnable() 
	{
		@Override
		public void run() {
			Expire();
		}
	};
	
	InCombat(CustomPlayer _cp)
	{
		Bukkit.broadcastMessage( _cp.player.getName() + " is in combat!");
		cp = _cp;
		task.runTaskLater(Main.instance, (long)(Config.combatLogTime * 20));
	}
	
	public void Expire() 
	{
		Bukkit.broadcastMessage( cp.player.getName() + " is in no longer combat!");
		task.cancel();
		Combat.ic.remove(cp.player.getUniqueId().toString());
	}
	
	public void Renew()
	{
		Bukkit.broadcastMessage( cp.player.getName() + " renewed combat time.");
		task.cancel();
		task = new BukkitRunnable() 
		{
			@Override
			public void run() {
				Expire();
			}
		};
		task.runTaskLater(Main.instance, (long)(Config.combatLogTime * 20));
	}

	public void Penalize()
	{
		Expire();
		Bukkit.broadcastMessage( cp.player.getName() + " left the game during combat.");
		if(Config.levelingModule)
			cp.getProfile().addExp(- Leveling.CalcExpLose(cp));
		cp.getProfile().saveAll();
		cp.player.setHealth(0);
	}
}

public class Combat implements Listener{
	
	public static HashMap<String, InCombat> ic = new HashMap<>();
	
	@EventHandler
	public void OnPlayerQuit(PlayerQuitEvent e) 
	{
		Player p = e.getPlayer();
		if(ic.containsKey(p.getUniqueId().toString())) 
		{
			e.setQuitMessage(StringManager.Colorize(e.getQuitMessage() + " &4&l [*]"));
			ic.get(p.getUniqueId().toString()).Penalize();
		}
	}
	
	@EventHandler
	public void DamageByEntity(EntityDamageByEntityEvent e)
	{
		if(!Config.combatModule)
			return;
		
		Entity damager = e.getDamager(), victim = e.getEntity();
		if(!(victim instanceof LivingEntity))
			return;
		
		if(e.getDamager() instanceof Projectile)
		{
			Projectile proj = (Projectile)e.getDamager();
			if(proj.getShooter() instanceof Player && victim instanceof Player)
			{
				damager = (Player)proj.getShooter();
			}
			
			if(Config.allowHeadshots)
			{
				Location eyes = ((LivingEntity)victim).getEyeLocation();
				Location projLoc = proj.getLocation();
				double dist = eyes.distance(projLoc);
				if(dist <= 0.4)
					e.setDamage(e.getDamage() * Config.headshotMultiplier);
			}
		}
		
		if(damager instanceof Player && victim instanceof Player)
		{
			setInCombat(((Player)damager), ((Player)victim));
		}
	}
	
	public static void setInCombat(Player attacker, Player victim) 
	{
		String attackerId = attacker.getUniqueId().toString(), victimId = victim.getUniqueId().toString();
		if(!ic.containsKey(attackerId))
			ic.put(attackerId, new InCombat(PlayerManager.getOnlinePlayer(attacker)));
		else
			ic.get(attackerId).Renew();
		
		if(!ic.containsKey(victimId))
			ic.put(victimId, new InCombat(PlayerManager.getOnlinePlayer(victim)));
		else
			ic.get(victimId).Renew();
	}
}
*/