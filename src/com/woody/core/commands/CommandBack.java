package com.woody.core.commands;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.woody.core.Config;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;

public class CommandBack implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		Player p = (Player)sender;
		if(p != null)
		{
			
			Location loc = (Location) PlayerManager.onlinePlayers.get(p).getProperty("LatestDeathPoint");
			if(loc != null)
			{
				p.sendMessage(StringManager.Colorize(Config.infoColor + "Teleportacja do miejsca ostatniej smierci."));
				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 2, 0.7f);
				p.teleport(loc);
				loc.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 2, 0.7f);
				PlayerManager.onlinePlayers.get(p).setProperty("LatestDeathPoint", null, true);
			}
			else
			{
				p.sendMessage(StringManager.Colorize(Config.infoColor + "Brak miejsca do ktorego mozna by wrocic."));
			}
		}
		return true;
    }
}
