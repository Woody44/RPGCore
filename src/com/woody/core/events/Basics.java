package com.woody.core.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.woody.core.Main;
import com.woody.core.types.CustomPlayer;
import com.woody.core.Config;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;

public class Basics implements Listener{

	@EventHandler
	public void OnHunger(FoodLevelChangeEvent e)
	{
		if(!Config.hunger)
			e.setCancelled(true);
	}
	
	@EventHandler
	public void OnFallDamage(EntityDamageEvent e ) 
	{
		if(e.getEntity().getType() != EntityType.PLAYER)
			return;
		
		if(e.getCause() == DamageCause.FALL)
			e.setDamage(e.getDamage() * Config.fallDamageMultiplier);
		
		/*if(deadPlayers.containsKey(e.getEntity().getUniqueId().toString()))
		{
			e.getEntity().setFireTicks(0);
			e.setCancelled(true);
		}*/
	}
	
	@EventHandler
	public void DamageByEntity(EntityDamageByEntityEvent e)
	{
		if(!(e.getEntity() instanceof LivingEntity))
			return;
		
		LivingEntity victimle = (LivingEntity) e.getEntity();
		//if(deadPlayers.containsKey(victimle.getUniqueId().toString()) || deadPlayers.containsKey(e.getDamager().getUniqueId().toString()))
		//	e.setCancelled(true);
		
		if(e.getDamager().getType() == EntityType.ARROW)
			if(Config.allowHeadshots)
			{
				Location eyes = victimle.getEyeLocation();
				Location projectile = e.getDamager().getLocation();
				double y, ey, dist;
				y = projectile.getY();
				ey = eyes.getY();
				dist = y - ey;
				if(dist < 0)
					dist *= -1;
				if(dist <= 0.4)
					e.setDamage(e.getDamage() + e.getDamage() * Config.headshotMultiplier);
			}
	}
	
	@EventHandler
	public void OnChat(AsyncPlayerChatEvent e) 
	{
		e.setCancelled(true);
		String originalMessage = e.getMessage();
		if(originalMessage.startsWith("/"))
			return;
		
		if(StringManager.HasBadWords(originalMessage))
		{
			e.getPlayer().sendMessage(StringManager.Colorize("Ty Hultaju! Nie uzywaj takich slowek!"));
			return;
		}
		
		int lvl = PlayerManager.onlinePlayers.get(e.getPlayer()).getLevel();
		if(lvl < 10)
			e.getPlayer().sendMessage(StringManager.Colorize(StringManager.FillExp(Config.chatLowLvlMessage, e.getPlayer())));
		else 
		{
			for(Player player : Bukkit.getOnlinePlayers()) 
			{
				if(originalMessage.contains(player.getName()))
				{
					player.playSound(player.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 2, 1.6f);
					player.sendMessage(StringManager.Colorize(StringManager.FillChat(Config.chatMessageFormat, e.getPlayer(), Config.pingColor + originalMessage)));
				}
				else if(originalMessage.contains("@"+StringManager.FillGroup("{GROUP}", player))) {
					player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 2, 1.7f);
					player.sendMessage(StringManager.Colorize(StringManager.FillChat(Config.chatMessageFormat, e.getPlayer(), Config.pingColor + originalMessage)));
				}
				else if(originalMessage.contains("@everyone"))
				{
					player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 1.8f);
					player.sendMessage(StringManager.Colorize(StringManager.FillChat(Config.chatMessageFormat, e.getPlayer(), Config.pingColor + originalMessage)));
				}
				else
					player.sendMessage(StringManager.Colorize(StringManager.FillChat(Config.chatMessageFormat, e.getPlayer(), originalMessage)));
			}
		}
	}
	
	@EventHandler
	public void OnMobDeath(EntityDeathEvent e)
	{
		e.getDrops().clear();
		e.setDroppedExp(0);
	}
	
	public static void HideFromOthers(Player p) 
	 {
		 for(Player op : Bukkit.getOnlinePlayers())
		 {
			 if(op != p)
				 op.hidePlayer(Main.getInstance(), p);
		 }
	 }
	 
	 public static void ShowToOthers(Player p) 
	 {
		 for(Player op : Bukkit.getOnlinePlayers())
		 {
			 if(op != p)
				 op.showPlayer(Main.getInstance(), p);
		 }
	 }
	 
	 @EventHandler
	 public static void OnPlayerDeath(PlayerDeathEvent e) 
	 {
		 CustomPlayer cp = PlayerManager.onlinePlayers.get(e.getEntity());
		 long lost = (long)(cp.getExp() * Config.expLose);
		 cp.addExp(lost * -1);
		 e.getEntity().sendMessage(StringManager.Colorize("&cUtracono &l" + lost + "&r&c doswiadczenia!"));
	 }
}
