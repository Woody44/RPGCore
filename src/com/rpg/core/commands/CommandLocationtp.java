package com.rpg.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rpg.core.framework.LocationsManager;

public class CommandLocationtp implements CommandExecutor{
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
    	if(args[0]==null)
    		return false;
    	
    	((Player)sender).teleport(LocationsManager.GetLocation(args[0]).location);
        return true;
    }
}
