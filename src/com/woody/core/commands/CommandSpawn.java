package com.woody.core.commands;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.woody.core.Config;
import com.woody.core.util.CooldownManager;
import com.woody.core.util.FileManager;
import com.woody.core.util.StringManager;

public class CommandSpawn implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		Player p = (Player) sender;
		Location loc = FileManager.getConfig("spawn.yml").getLocation("location");
		if(!FileManager.checkFileExistence("spawn.yml"))
			sender.sendMessage(StringManager.Colorize(Config.errorColor + "Nie ustawiono spawnu."));
		else
		{
			long cd = CooldownManager.getCooldown(p, "TELEPORT", true);
			if(cd > 0)
			{
				p.sendMessage(StringManager.Colorize(Config.infoColor + "Teleportacja mozliwa za &l" + cd + Config.infoColor + "sekund."));
				return true;
			}
			else
				CooldownManager.cooldown(p, "TELEPORT", Config.tpCd);
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 2, 1.2f);
			sender.sendMessage(StringManager.Colorize(Config.otherColor + "Teleportacja na spawn."));
			((Player)sender).teleport(loc);
			loc.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 2, 1.2f);
		}
		return true;
    }
}