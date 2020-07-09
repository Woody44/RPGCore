package com.rpg.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rpg.core.CoreConfig;
import com.rpg.core.framework.PlayerManager;
import com.rpg.core.framework.StringManager;

public class CommandSetMoney implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
    	Player player = Bukkit.getPlayer(args[0]);
    	PlayerManager.setMoney(player.getUniqueId().toString(), Integer.parseInt(args[1]));
    	String messageOne = CoreConfig.infoColor + "Ustawiono stan konta gracza &l" + player.getDisplayName() + CoreConfig.infoColor + " na &a" + args[1]  + "&2&l" + CoreConfig.currencySymbol;
    	String messageTwo = CoreConfig.infoColor + "Ustawiono wysokosc twojego rachunku bankowego na &a&l" + args[1] + "&2&l" + CoreConfig.currencySymbol;
    	((Player)sender).sendMessage(StringManager.Colorize(messageOne));
    	player.sendMessage(StringManager.Colorize(messageTwo));
    	PlayerManager.setMoney(player.getUniqueId().toString(), Integer.parseInt(args[1]));
    	return true;
    }
}
