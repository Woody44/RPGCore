package com.woody.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import com.woody.core.Config;
import com.woody.core.util.StringManager;

public class CommandHeal implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		Player p;
		if(!(sender instanceof LivingEntity))
			return true;
		
		if(args.length > 0)
			p = Bukkit.getPlayer(args[0]);
		else
			p = (Player)sender;
		
		switch(label)
		{
			case "heal":
				p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
				p.sendMessage(StringManager.Colorize(Config.infoColor + "Twoje rany zostaly zagojone."));
				break;
			case "babysit":
				p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
				FullFeed(p);
				RemoveAllPotions(p);
				p.sendMessage(StringManager.Colorize(Config.infoColor + "Ktos sie toba zaopiekowal."));
				break;
			case "bb":
				p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
				FullFeed(p);
				RemoveAllPotions(p);
				p.sendMessage(StringManager.Colorize(Config.infoColor + "Ktos sie toba zaopiekowal."));
				break;
			case "cure":
				FullFeed(p);
				RemoveBadPotions(p);
				p.sendMessage(StringManager.Colorize(Config.infoColor + "Uzdrowiono cie."));
				break;
		}
		return true;
    }
	
	public void RemoveBadPotions(Player p) 
	{
		p.removePotionEffect(PotionEffectType.BLINDNESS);
		p.removePotionEffect(PotionEffectType.CONFUSION);
		p.removePotionEffect(PotionEffectType.HARM);
		p.removePotionEffect(PotionEffectType.WEAKNESS);
		p.removePotionEffect(PotionEffectType.SLOW_DIGGING);
		p.removePotionEffect(PotionEffectType.SLOW);
		p.removePotionEffect(PotionEffectType.POISON);
		p.removePotionEffect(PotionEffectType.HUNGER);
	}
	
	public void RemoveAllPotions(Player p) 
	{
		for(PotionEffectType pet : PotionEffectType.values())
			p.removePotionEffect(pet);
	}
	
	public void FullFeed(Player p) 
	{
		p.setSaturation(20);
		p.setExhaustion(20);
		p.setFoodLevel(20);
	}
}
