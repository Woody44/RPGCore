package com.woody.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;
import com.woody.core.GLOBALVARIABLES;

public class CommandBack implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		if(!(sender instanceof Player))
		{
			Bukkit.getLogger().warning("This command is for players use only.");
			return true;
		}
		
		Player p = (Player)sender;
		if(p != null)
		{
			
			Location loc = (Location) PlayerManager.getOnlinePlayer(p).getProfile().getProperty("LatestDeathPoint");
			if(loc != null)
			{
				p.sendMessage(StringManager.Colorize( GLOBALVARIABLES.CORE_PREFIX + "Teleportacja do miejsca ostatniej śmierci."));
				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 2, 0.7f);
				p.teleport(loc);
				loc.getWorld().playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 2, 0.7f);
				PlayerManager.getOnlinePlayer(p).getProfile().setProperty("LatestDeathPoint", null, true);
			}
			else
			{
				p.sendMessage(StringManager.Colorize( GLOBALVARIABLES.CORE_PREFIX + "Brak miejsca do którego można by wrócić."));
			}
		}
		return true;
    }
}
