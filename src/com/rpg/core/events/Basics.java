package com.rpg.core.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import com.rpg.core.CoreConfig;
import com.rpg.core.Main;
import com.rpg.core.framework.StringManager;
import com.rpg.mobs.MobData;
import com.rpg.mobs.MobFile;
import com.rpg.core.framework.ItemManager;
import com.rpg.core.framework.LocationsManager;
import com.rpg.core.framework.PlayerManager;

public class Basics implements Listener
{
	
	public static HashMap<String, HashMap<String, Object>> deadPlayers = new HashMap<>();
	@EventHandler
	public void OnHunger(FoodLevelChangeEvent e)
	{
		e.setCancelled(true);
	}
	
	@EventHandler
	public void OnFallDamage(EntityDamageEvent e ) 
	{
		if(e.getEntity().getType() != EntityType.PLAYER)
			return;
		
		if(e.getCause() == DamageCause.FALL)
			e.setDamage(e.getDamage() * CoreConfig.fallDamageMultiplier);
		
		if(deadPlayers.containsKey(e.getEntity().getUniqueId().toString()))
		{
			e.getEntity().setFireTicks(0);
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void DamageByEntity(EntityDamageByEntityEvent e)
	{
		LivingEntity victimle = (LivingEntity) e.getEntity();
		if(deadPlayers.containsKey(victimle.getUniqueId().toString()) || deadPlayers.containsKey(e.getDamager().getUniqueId().toString()))
			e.setCancelled(true);
		
		if(e.getDamager().getType() == EntityType.ARROW)
			if(CoreConfig.allowHeadshots)
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
					e.setDamage(e.getDamage() + e.getDamage() * CoreConfig.headshotMultiplier);
			}
	}
	
	@EventHandler
	public void OnChat(AsyncPlayerChatEvent e) 
	{
		e.setCancelled(true);
		String originalMessage = e.getMessage();
		if(originalMessage.startsWith("/"))
			return;
		int lvl = PlayerManager.getPlayer(e.getPlayer().getUniqueId().toString()).level;
		if(lvl < 10)
			e.getPlayer().sendMessage(StringManager.Colorize(StringManager.FillExp(CoreConfig.chatLowLvlMessage, e.getPlayer())));
		else 
		{
			for(Player player : Bukkit.getOnlinePlayers()) 
			{
				if(originalMessage.contains(player.getName()))
				{
					player.playSound(player.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 2, 1.6f);
					player.sendMessage(StringManager.Colorize(StringManager.FillChat(CoreConfig.chatMessageFormat, e.getPlayer(), CoreConfig.pingColor + originalMessage)));
				}
				else if(originalMessage.contains("@"+StringManager.FillGroup("{GROUP}", player))) {
					player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 2, 1.7f);
					player.sendMessage(StringManager.Colorize(StringManager.FillChat(CoreConfig.chatMessageFormat, e.getPlayer(), CoreConfig.pingColor + originalMessage)));
				}
				else if(originalMessage.contains("@everyone"))
				{
					player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 1.8f);
					player.sendMessage(StringManager.Colorize(StringManager.FillChat(CoreConfig.chatMessageFormat, e.getPlayer(), CoreConfig.pingColor + originalMessage)));
				}
				else
					player.sendMessage(StringManager.Colorize(StringManager.FillChat(CoreConfig.chatMessageFormat, e.getPlayer(), originalMessage)));
			}
		}
	}
	
	@EventHandler
	public void OnMobDeath(EntityDeathEvent e)
	{
		e.getDrops().clear();
		e.setDroppedExp(0);
	}
	
	@EventHandler
	 public void onDeath(PlayerDeathEvent e) {
			Player p = (Player)e.getEntity();
			p.setBedSpawnLocation(p.getLocation(), true);
			deadPlayers.put(p.getUniqueId().toString(), new HashMap<String, Object>());
			deadPlayers.get(p.getUniqueId().toString()).put("location", p.getLocation());
			HideFromOthers(p);
			
			if(Main.GetMe().getServer().getPluginManager().getPlugin("RPGMobs") != null) {
				ArrayList<ArmorStand> stands = new ArrayList<>();
				for(MobData md : MobFile.getMobsData("model----dummy")) 
				{
					ArmorStand as = (ArmorStand)p.getWorld().spawnEntity(p.getLocation().add(md.spawnLocation).setDirection(md.spawnLocation.getDirection()), EntityType.valueOf(md.type));
					as.setBodyPose(md.bodyPose);
					as.setHeadPose(md.headPose);
					as.setLeftArmPose(md.leftArmPose);
					as.setLeftLegPose(md.leftLegPose);
					as.setRightArmPose(md.rightArmPose);
					as.setRightLegPose(md.rightLegPose);
					as.setGravity(false);
					as.setAI(false);
					as.setArms(true);
					as.setInvulnerable(true);
					as.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1, 100000, false, false, false));
					as.setVisible(false);
					stands.add(as);
				}
				
				ItemStack head = ItemManager.createItemStack(Material.PLAYER_HEAD, p.getName(), new String[] {}, 1);
				SkullMeta meta = (SkullMeta)head.getItemMeta();
				meta.setOwningPlayer(Bukkit.getOfflinePlayer(p.getUniqueId()));
				head.setItemMeta(meta);
				stands.get(0).setVisible(true);
				stands.get(0).getEquipment().setChestplate(p.getInventory().getChestplate());
				stands.get(0).getEquipment().setHelmet(head);
				stands.get(0).getEquipment().setItemInMainHand(p.getInventory().getItemInMainHand());
				stands.get(0).getEquipment().setItemInOffHand(p.getInventory().getItemInOffHand());
				stands.get(1).getEquipment().setLeggings(p.getInventory().getLeggings());
				stands.get(1).getEquipment().setBoots(p.getInventory().getBoots());
				
				if(stands.get(0).getEquipment().getChestplate().getType() == Material.AIR)
					stands.get(0).getEquipment().setChestplate(ItemManager.createItemStack(Material.LEATHER_CHESTPLATE, "dummy", new String[] {}, 1));
				if(stands.get(0).getEquipment().getLeggings().getType() == Material.AIR)
					stands.get(1).getEquipment().setLeggings(ItemManager.createItemStack(Material.LEATHER_LEGGINGS, "dummy", new String[] {}, 1));
				if(stands.get(1).getEquipment().getBoots().getType() == Material.AIR)
					stands.get(0).getEquipment().setBoots(ItemManager.createItemStack(Material.LEATHER_BOOTS, "dummy", new String[] {}, 1));
				deadPlayers.get(p.getUniqueId().toString()).put("dummies", stands);
				
				p.sendTitle((StringManager.Colorize("&4Umarles")), "Musisz odczekac " + CoreConfig.respawnTime + " sekund", 20, 60, 20);
				BukkitTask task = Bukkit.getScheduler().runTaskLater(Main.GetMe(), new Runnable() {
					@Override
					public void run() {
						if(deadPlayers.containsKey(p.getUniqueId().toString()))
							RespawnPlayer(p);
					}}, 20 * CoreConfig.respawnTime);
				deadPlayers.get(p.getUniqueId().toString()).put("task", task.getTaskId());
			}
		}
	 
	 @EventHandler
	 public void OnPlayerMove(PlayerMoveEvent e) 
	 {
		 if(deadPlayers.containsKey(e.getPlayer().getUniqueId().toString()))
		 {
			 Player p = e.getPlayer();
			 String uuid = p.getUniqueId().toString();
			 if(p.getLocation().distance((Location)deadPlayers.get(uuid).get("location")) > 1)
				 p.teleport(((Location)deadPlayers.get(uuid).get("location")).setDirection(p.getLocation().getDirection()));
		 }
	 }
	 
	 
	 @EventHandler
	 public void OnJoin(PlayerJoinEvent e)
	 {
		 for(String s: deadPlayers.keySet())
		 {
			 if(s != e.getPlayer().getUniqueId().toString()) {
				 Player p = Bukkit.getPlayer(UUID.fromString(s));
				 if(p != null)
					 e.getPlayer().hidePlayer(Main.GetMe(),p);
			 }
		 }
	 }
	 
	 @SuppressWarnings("unchecked")
	public static void RespawnPlayer(Player p) 
	 {
		 if(deadPlayers.containsKey(p.getUniqueId().toString()))
		 {
			 p.sendTitle(StringManager.Colorize("&2Odrodzono"), "Uwazaj bardziej na siebie, ok?...", 20, 40, 20);
			 Bukkit.getScheduler().cancelTask((int)deadPlayers.get(p.getUniqueId().toString()).get("task"));
			 for(ArmorStand as : (ArrayList<ArmorStand>)deadPlayers.get(p.getUniqueId().toString()).get("dummies"))
				 as.remove();
			 deadPlayers.remove(p.getUniqueId().toString());
			 p.teleport(LocationsManager.GetLocation("spawn"));
			 p.setVelocity(new Vector(0,0,0));
			 p.setBedSpawnLocation(LocationsManager.GetLocation("spawn"), true);
			 ShowToOthers(p);
		 }
	 }
	 
	 @SuppressWarnings("unchecked")
	public static void ResurrectPlayer(Player p) 
	 {
		if(deadPlayers.containsKey(p.getUniqueId().toString()))
		{
			p.sendTitle(StringManager.Colorize("&aZostales Ozywiony"), "Powodzenia :p", 20, 60, 20);
			 Bukkit.getScheduler().cancelTask((int)deadPlayers.get(p.getUniqueId().toString()).get("task"));
			 for(ArmorStand as : (ArrayList<ArmorStand>)deadPlayers.get(p.getUniqueId().toString()).get("dummies"))
				 as.remove();
			 deadPlayers.remove(p.getUniqueId().toString());
			 p.setVelocity(new Vector(0,0,0));
			 p.setBedSpawnLocation(LocationsManager.GetLocation("spawn"), true);
				
			 ShowToOthers(p);
		} 
	 }
	 
	 public static void HideFromOthers(Player p) 
	 {
		 for(Player op : Bukkit.getOnlinePlayers())
		 {
			 if(op != p)
				 op.hidePlayer(Main.GetMe(), p);
		 }
	 }
	 
	 public static void ShowToOthers(Player p) 
	 {
		 for(Player op : Bukkit.getOnlinePlayers())
		 {
			 if(op != p)
				 op.showPlayer(Main.GetMe(), p);
		 }
	 }
	/*@EventHandler
	public void OnPlayerSneak(PlayerToggleSneakEvent e)
	{
		Player player = e.getPlayer();
		if(!sneakedPlayers.contains(player)) {
			for(Player p : Bukkit.getOnlinePlayers())
				p.hidePlayer(Main.GetMe(), player);
			sneakedPlayers.add(player);
		}
		else
		{
			for(Player p : Bukkit.getOnlinePlayers())
				p.showPlayer(Main.GetMe(), player);
			sneakedPlayers.remove(player);
		}
	}*/
}
