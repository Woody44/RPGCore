package com.woody.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.woody.core.Config;
import com.woody.core.util.StringManager;

public class CommandFeed  implements CommandExecutor{

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
		
		if(p!=null)
		{
			p.setSaturation(20);
			p.setExhaustion(20);
			p.setFoodLevel(20);
			p.sendMessage(StringManager.Colorize(Config.infoColor + "Twoj glod zostal zaspokojony."));
		}
		return true;
    }
}
