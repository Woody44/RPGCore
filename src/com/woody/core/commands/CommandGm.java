package com.woody.core.commands;

import com.woody.core.GLOBALVARIABLES;
import com.woody.core.util.StringManager;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGm  implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		if(args.length < 1)
		{
			sender.sendMessage(command.getUsage());
			return true;
		}
		if(!(sender instanceof Player))
		{
			Bukkit.getLogger().info("This Command is for players only!");
		}
		
		Player p = null;
		
		if(args.length >= 2)
			p = Bukkit.getPlayer(args[1]);
		
		if(p == null)
			p = (Player)sender;
		
		boolean b = p.hasPermission("woody.gm.*");

		switch(args[0])
		{
			case "0":
			if(b || p.hasPermission("woody.gm.0"))
			{
				p.setGameMode(GameMode.SURVIVAL);
				p.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Zmieniono twój tryb gry na &c&lSurvival&6."));
			}
			//DamianChuj dc = new DamianChuj("Viego Granie", ((Player)sender).getInventory().getItemInMainHand());
			//PlayerManager.getOnlinePlayer(((Player)sender)).getProfile().setProperty("DamianChuj", dc, true);
				break;
				
			case "1":
			if(b || p.hasPermission("woody.gm.1"))
			{
				p.setGameMode(GameMode.CREATIVE);
				p.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Zmieniono twój tryb gry na &c&lCreative&6."));
			}
			//DamianChuj dcc = (DamianChuj)PlayerManager.getOnlinePlayer(((Player)sender)).getProfile().getProperty("DamianChuj");
			//Bukkit.broadcastMessage(dcc.dupa);
				break;
			case "2":
			if(b || p.hasPermission("woody.gm.2"))
			{
				p.setGameMode(GameMode.ADVENTURE);
				p.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Zmieniono twój tryb gry na &c&lAdventure&6."));
			}
				break;
			case "3":
			if(b || p.hasPermission("woody.gm.3"))
			{
				p.setGameMode(GameMode.SPECTATOR);
				p.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Zmieniono twój tryb gry na &c&lSpectator&6."));
			}
				break;
		}
		return false;	
    }
}
