package com.woody.core.events;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import com.woody.core.Config;
import com.woody.core.GLOBALVARIABLES;
import com.woody.core.types.CustomPlayer;
import com.woody.core.util.ItemManager;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;

public class Economy implements Listener{
	
	@EventHandler
    public void PlayerJoin(PlayerJoinEvent e)
    {
		if(!PlayerManager.hasPlayedBefore(e.getPlayer()))
		{
			PlayerManager.getOnlinePlayer(e.getPlayer()).getProfile().addMoney(Config.startBalance);
		}
    }

	public static int calculateOnDeath(Player _p, Player _k)
	{
		if(Config.moneyDrop) 
		{
			CustomPlayer vcp = PlayerManager.getOnlinePlayer(_p);
			if(vcp.getProfile().getMoney() <= 0)
				return 0;

			int money = (int)Config.moneyCount;
			if(Config.moneyPercent > 0.0)
				money += vcp.getProfile().getMoney() * Config.moneyPercent;

			if(_p == _k)
				money *= Config.suicideMultiplier;
			if(money > vcp.getProfile().getMoney())
				return 0;	
			return money;
		}
		return 0;
	}

	public static ItemStack moneyItem(int count) 
	{
		ItemStack item = ItemManager.createItemStack(Material.GOLD_NUGGET, StringManager.Colorize("&e&l" + count + " " + Config.currencySymbol), new String[] {StringManager.Colorize("&5&lDodaje pieniądze do rachunku gracza który to podniesie.")}, 1);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		return item;
	}

	@EventHandler
	public static void OnPlayerMoneyPickup(EntityPickupItemEvent e) 
	{
		if(e.getEntityType() != EntityType.PLAYER)
			return;
		
		ItemStack item = e.getItem().getItemStack();
		if(item.getType() == Material.GOLD_NUGGET && item.getEnchantmentLevel(Enchantment.DURABILITY) == 10 && item.getItemMeta().getDisplayName().contains(Config.currencySymbol))
		{
			e.setCancelled(true);
			PlayerManager.getOnlinePlayer((Player)e.getEntity()).getProfile().addMoney(Integer.parseInt(StringManager.NoColors(item.getItemMeta().getDisplayName()).replace(" "+Config.currencySymbol, "")));
			e.getItem().remove();
			e.getEntity().sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Picked up " + item.getItemMeta().getDisplayName()));
			Player p = ((Player)e.getEntity());
			p.playSound(p.getLocation(), Sound.BLOCK_VINE_STEP, 1, 2);
		}
	}
}
