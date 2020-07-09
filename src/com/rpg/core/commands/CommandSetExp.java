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
    	String messageOne = CoreConfig.infoColor + "Ustawiono ilosc doswiadczenia gracza " + player.getDisplayName() + CoreConfig.infoColor + " na &l" + args[1]  + " ["+ Misc.ExpToLvl(Integer.parseInt(args[1])) +"]";
    	String messageTwo = CoreConfig.infoColor + "Zmieniono ilosc twojego doswiadczenia na " + CoreConfig.infoColor + "&l" + args[1] + " ["+ Misc.ExpToLvl(Integer.parseInt(args[1])) +"]";
    	((Player)sender).sendMessage(StringManager.Colorize(messageOne));
    	player.sendMessage(StringManager.Colorize(messageTwo));
    	PlayerManager.setExp(player.getUniqueId().toString(), Integer.parseInt(args[1]));
    	return true;
    }
}
