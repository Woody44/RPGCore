package com.rpg.core.events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import com.rpg.core.CoreConfig;
import com.rpg.core.Main;
import com.rpg.core.framework.StringManager;

import net.minecraft.server.v1_16_R1.EntityCat;
import net.minecraft.server.v1_16_R1.EntityLiving;
import net.minecraft.server.v1_16_R1.PacketPlayOutEntityDestroy;

import com.rpg.core.framework.PlayerManager;

public class Basics implements Listener
{
	
	public static ArrayList<Player> sneakedPlayers = new ArrayList<Player>();
	@EventHandler
	public void OnHunger(FoodLevelChangeEvent e)
	{
		e.setCancelled(true);
	}
	
	@EventHandler
	public void OnFallDamage(EntityDamageEvent e ) 
	{
		if(e.getCause() == DamageCause.FALL)
		{
			e.setDamage(e.getDamage() * CoreConfig.fallDamageMultiplier);
		}
	}
	
	@EventHandler
	public void OnChat(AsyncPlayerChatEvent e) 
	{
		String originalMessage = e.getMessage();
		int lvl = PlayerManager.getPlayer(e.getPlayer().getUniqueId().toString()).level;
		if(lvl < 10) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(StringManager.Colorize(StringManager.FillExp(CoreConfig.chatLowLvlMessage, e.getPlayer())));
		}
		else
			e.setFormat(StringManager.Colorize(StringManager.FillChat(CoreConfig.chatMessageFormat, e.getPlayer(), originalMessage)));
	}
	
	@EventHandler
	public void OnMobDeath(EntityDeathEvent e)
	{
		e.getDrops().clear();
		e.setDroppedExp(0);
	}
	
	@EventHandler
	public void OnPlayerSneak(PlayerToggleSneakEvent e)
	{
		Player player = e.getPlayer();
		if(!sneakedPlayers.contains(player)) {
			PacketPlayOutEntityDestroy destroyPacket = new PacketPlayOutEntityDestroy(player.getEntityId());
			World world = player.getWorld();
			EntityLiving ent = null;
			ent = new EntityCat(null, ((CraftPlayer) player).getHandle().world);
			for(Player p : Bukkit.getOnlinePlayers())
				p.hidePlayer(Main.GetMe(), player);
			sneakedPlayers.add(player);
			
			Cat cat = (Cat)player.getWorld().spawnEntity(player.getLocation(), EntityType.CAT);
		}
		else
		{
			for(Player p : Bukkit.getOnlinePlayers())
				p.showPlayer(Main.GetMe(), player);
			sneakedPlayers.remove(player);
		}
	}
}
