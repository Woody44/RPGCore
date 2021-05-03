package com.woody.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.woody.core.GLOBALVARIABLES;
import com.woody.core.types.Profile;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;

public class CommandHeal implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		Player p;
		if(!(sender instanceof Player) && args.length < 0)
		{
			Bukkit.getLogger().warning("You must specify player!");
			return true;
		}
		
		if(args.length > 0)
			p = Bukkit.getPlayer(args[0]);
		else
			p = (Player)sender;

		if(label.contains("heal"))
		{
			p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
			p.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Twoje rany zostały zagojone."));
		}
		else if(label.contains("treat")){
				p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5, 20, true));
				p.setSaturation(20);
				p.setExhaustion(20);
				p.setFoodLevel(20);
				RemoveBadPotions(p);
				p.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX+ "Pomoc od Doktorka przybywa."));
		}
		else if(label.contains("cure")){
				p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
				p.setSaturation(20);
				p.setExhaustion(20);
				p.setFoodLevel(20);
				p.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Uzdrowiono Cię."));
				for(PotionEffectType pet : PotionEffectType.values())
					p.removePotionEffect(pet);
		}
		else if(label.contains("mana")){
				Profile prof = PlayerManager.getOnlinePlayer(p).getProfile();
				prof.setMana(prof.getMaxBaseMana());
				p.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "&9Twoja mana została magicznie zregenerowana."));
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
}
