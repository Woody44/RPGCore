package com.rpg.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.rpg.core.framework.LocationsManager;

public class CommandLocationDelete implements CommandExecutor{
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
    	if(args[0]==null)
    		return false;
    	
    	LocationsManager.DeleteLocation(args[0]);
    	sender.sendMessage("Lokacja Usunieta!");
        return true;
    }
}
