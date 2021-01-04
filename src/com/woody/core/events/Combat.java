package com.woody.core.events;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.woody.core.Config;
import com.woody.core.Main;
import com.woody.core.types.CustomPlayer;
import com.woody.core.util.ItemManager;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;

class InCombat
{
	Player p;
	
	BukkitRunnable task = new BukkitRunnable() 
	{
		@Override
		public void run() {
			Expire();
		}
	};
	
	InCombat(Player _p)
	{
		p = _p;
		task.runTaskLater(Main.instance, (long)(Config.combatLogTime * 20));
	}
	
	public void Expire() 
	{
		task.cancel();
		Combat.ic.remove(p.getUniqueId().toString());
	}
	
	public void Renew()
	{
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
		p.setHealth(0);
		
		Expire();
		CustomPlayer cp = PlayerManager.getPlayer(p);
		if(Config.levelingModule)
			cp.addExp(- Leveling.CalculateOnDeath(p));
		Basic.saveOnDeath(p);
		cp.saveAll();
	}
}

public class Combat implements Listener{
	
	public static HashMap<String, InCombat> ic = new HashMap<>();
	public static ItemStack moneyItem(int count) 
	{
		ItemStack item = ItemManager.createItemStack(Material.GOLD_NUGGET, StringManager.Colorize("&e&l" + count + " " + Config.currencySymbol), new String[] {StringManager.Colorize("&5&lDodaje pieniadze do rachunku.")}, 1);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		return item;
	}
	
	@EventHandler
	public void OnPlayerQuit(PlayerQuitEvent e) 
	{
		Player p = e.getPlayer();
		if(ic.containsKey(p.getUniqueId().toString())) 
		{
			e.setQuitMessage(StringManager.Colorize(e.getQuitMessage() + Config.errorColor + "(bold) [*]"));
			ic.get(p.getUniqueId().toString()).Penalize();
		}
	}
	
	@EventHandler
	public static void OnPlayerMoneyPickup(EntityPickupItemEvent e) 
	{
		if(e.getEntityType() != EntityType.PLAYER)
			return;
		
		ItemStack item = e.getItem().getItemStack();
		if(item.getType() == Material.GOLD_NUGGET)
			if(item.getEnchantmentLevel(Enchantment.DURABILITY) == 10)
				if(item.getItemMeta().getDisplayName().contains(Config.currencySymbol))
				{
					e.setCancelled(true);
					PlayerManager.getPlayer((Player)e.getEntity()).addMoney(Integer.parseInt(StringManager.NoColors(item.getItemMeta().getDisplayName()).replace(" "+Config.currencySymbol, "")));
					e.getItem().remove();
					e.getEntity().sendMessage(StringManager.Colorize(Config.otherColor + "Podniesiono " + item.getItemMeta().getDisplayName()));
					Player p = ((Player)e.getEntity());
					p.playSound(p.getLocation(), Sound.BLOCK_VINE_STEP, 1, 2);
				}
	}
	
	@EventHandler
	public static void OnPlayerDeath(PlayerDeathEvent e) 
	{
		if(Config.moneyDrop) 
		{
			
			int money = (int)Config.moneyCount;
			CustomPlayer kcp = null, vcp = PlayerManager.getPlayer(e.getEntity());
			if((Player)e.getEntity().getKiller() != null)
				kcp = PlayerManager.getPlayer((Player)e.getEntity().getKiller());
			
			if(kcp == null)
				return;
			
			if(Config.moneyPercent > 0.0)
				money += vcp.getMoney() * Config.moneyPercent;
			
			if(money <= 0)
				return;
			
			if(e.getEntity() == e.getEntity().getKiller())
				money *= Config.suicideMultiplier;
			
			if(Config.removeMoney)
				vcp.addMoney(-1 * money);
			
			if(!Config.moneyItem)
				kcp.addMoney(money);
			else
				vcp.player.getWorld().dropItem(vcp.player.getLocation(), moneyItem(money));
			
			e.getEntity().sendMessage(StringManager.Colorize(Config.errorColor + "Utracono &l" + money + " " + Config.currencySymbol));
				
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
		Bukkit.getLogger().info(attacker.getDisplayName() + " walczy z " + victim.getDisplayName());
		String attackerId = attacker.getUniqueId().toString(), victimId = victim.getUniqueId().toString();
		if(!ic.containsKey(attackerId))
			ic.put(attackerId, new InCombat(attacker));
		else
			ic.get(attackerId).Renew();
		
		if(!ic.containsKey(victimId))
			ic.put(victimId, new InCombat(victim));
		else
			ic.get(victimId).Renew();
	}
}
