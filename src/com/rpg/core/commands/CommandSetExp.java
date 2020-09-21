package com.rpg.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rpg.core.CoreConfig;
import com.rpg.core.framework.Misc;
import com.rpg.core.framework.PlayerManager;
import com.rpg.core.framework.StringManager;

public class CommandSetExp implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
    	Player player = Bukkit.getPlayer(args[0]);
    	String[] messages = new String[2];
    	boolean level = false;
    	long amount = -1;
    	args[1] = args[1].toLowerCase();
    	
    	if(args[1].contains("l"))
    		level = true;
    	
    	amount = Long.parseLong(args[1].replace("l", ""));
    	
    	if(!level) 
    	{
    		messages[0] = CoreConfig.infoColor + "Ustawiono ilosc doswiadczenia gracza " + player.getDisplayName() + CoreConfig.infoColor + " na &l" + amount  + " ["+ Misc.ExpToLvl(amount) +"]";
    		messages[1] = CoreConfig.infoColor + "Zmieniono ilosc twojego doswiadczenia na " + CoreConfig.infoColor + "&l" + args[1] + " ["+ Misc.ExpToLvl(amount) +"]";
    		PlayerManager.setExp(player.getUniqueId().toString(), amount);
    		PlayerManager.UpdateExpBar(player, amount);
    	}
    	else
    	{
    		messages[0] = CoreConfig.infoColor + "Ustawiono poziom gracza " + player.getDisplayName() + CoreConfig.infoColor + " na &l" + amount;
    		messages[1] = CoreConfig.infoColor + "Twój poziom zosta³ ustawiony na " + CoreConfig.infoColor + " " + amount;
    		
    		amount = CoreConfig.levels.get((int) amount);
    		PlayerManager.setExp(player.getUniqueId().toString(), amount);
    		PlayerManager.UpdateExpBar(player, amount);
    	}
    	
    	((Player)sender).sendMessage(StringManager.Colorize(messages[0]));
    	player.sendMessage(StringManager.Colorize(messages[1]));
    	return true;
    }
}
