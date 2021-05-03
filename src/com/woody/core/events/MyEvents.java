package com.woody.core.events;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.woody.core.Config;
import com.woody.core.GLOBALVARIABLES;
import com.woody.core.events.custom.PlayerCustomDeathEvent;
import com.woody.core.types.CustomPlayer;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;
public class MyEvents implements Listener{

    @EventHandler
	public static void OnPlayerDeath(PlayerDeathEvent e) 
	{
		PlayerCustomDeathEvent event;
		e.getDrops().clear();
        e.setDroppedExp(0);

		CustomPlayer _cp = PlayerManager.getOnlinePlayer(e.getEntity());

        long lostExp = 0;
		int lostMoney = 0;
        boolean suicide = e.getEntity() == e.getEntity().getKiller() || e.getEntity().getKiller() == null;
        boolean keepInventory = Config.keepInventory;
        if(Config.levelingModule)
			lostExp = Leveling.CalcExpLose(_cp);
        if(Config.economyModule)
            lostMoney = Economy.calculateOnDeath(e.getEntity(), e.getEntity().getKiller());
        
        event = new PlayerCustomDeathEvent(e.getEntity(), lostExp, lostMoney, suicide, keepInventory, true);
        Bukkit.getPluginManager().callEvent(event);
        
        if(event.getLocationSave())
        {
            _cp.getProfile().setProperty("LatestDeathPoint", e.getEntity().getLocation(), true);
            _cp.getProfile().setProperty("LatestDeathTime", System.currentTimeMillis(), true);
        }

        if(event.getLostExp() > 0)
        {
            _cp.getProfile().addExp(- lostExp);
			e.getEntity().sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "You Have Lost &c" + lostExp + "&6 experience!"));
			
			if(Config.convertExp) 
			{
                ExperienceOrb eo = (ExperienceOrb)e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(), EntityType.EXPERIENCE_ORB);
                eo.setVelocity(new Vector(0,0,0));
                eo.setExperience((int)(lostExp));
                eo.setGravity(true);
                eo.setCustomName(StringManager.Colorize(e.getEntity().getDisplayName() + " &2&lXP"));
                eo.setCustomNameVisible(true);
                eo.setGlowing(true);
                eo.setInvulnerable(true);
                eo.setSilent(true);
                eo.getLocation().getWorld().spawnParticle(Particle.CLOUD, eo.getLocation(), 150 , 0.15, 0.15, 0.15);
                eo.getWorld().spawnParticle(Particle.SPIT, eo.getLocation().getX(), eo.getLocation().getY(), eo.getLocation().getZ(), 0, 0, 0, 0.2, 100, null);
			}
        }

        if(event.getLostMoney() > 0)
        {
            _cp.getProfile().addMoney(event.getLostMoney() * -1);

            if(Config.moneyItem)
                e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), Economy.moneyItem(event.getLostMoney()));
        }
        if(!event.getKeepInventory())
            dropPlayerItems(e.getEntity());

		
		
        _cp.getProfile().setMana(_cp.getProfile().getMaxBaseMana() / 2);
        _cp.getProfile().save();
		
		if(e.getEntity().hasPermission("woody.back"));
		{
			e.getEntity().sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "You can use &c/back &6to get back to your latest death point!"));
		}
	}
	
	private static void dropPlayerItems(Player p) 
	{
		ItemStack[] items = p.getInventory().getContents();
		p.getInventory().setContents(new ItemStack[41]);
		for(ItemStack i : items)
		{
			if(i == null)
				continue;
			Item ie = p.getWorld().dropItem(p.getLocation(), i);
			ie.setPickupDelay(120);
			ie.setInvulnerable(true);
			ie.setGlowing(true);
		}
		PlayerManager.getOnlinePlayer(p).ClearInventory();
	}
}
