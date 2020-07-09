package com.rpg.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.rpg.core.CoreConfig;
import com.rpg.core.framework.StringManager;

public class CommandReloadConfig implements CommandExecutor{
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
    	CoreConfig.Reload();
    	sender.sendMessage(StringManager.Colorize(CoreConfig.infoColor + "RPG Core Config Reloaded!"));
        return true;
    }
}