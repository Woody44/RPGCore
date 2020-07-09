package com.rpg.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rpg.core.CoreConfig;
import com.rpg.core.Main;
import com.rpg.core.framework.StringManager;

public class CommandSystem implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
    	Player player = (Player) sender;
    	player.sendMessage(StringManager.Colorize(CoreConfig.infoColor + "#########################################"));
    	player.sendMessage(StringManager.Colorize(CoreConfig.infoColor + "#&e&lRPGcore:"));
    	player.sendMessage(StringManager.Colorize(CoreConfig.infoColor + "##&b&lAuthors:"));
    	player.sendMessage(StringManager.Colorize(CoreConfig.infoColor + "###&d_woody44_"));
    	player.sendMessage(StringManager.Colorize(CoreConfig.infoColor + "###&ddamian5466"));
    	player.sendMessage(StringManager.Colorize(CoreConfig.infoColor + "##&aVersion:" + Main.GetMe().getDescription().getVersion()));
    	player.sendMessage(StringManager.Colorize(CoreConfig.infoColor + "#########################################"));
    	return true;
    }
}
