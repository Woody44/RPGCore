package com.woody.core.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.woody.core.GLOBALVARIABLES;
import com.woody.core.util.LocationManager;
import com.woody.core.util.StringManager;

public class CommandLocation implements CommandExecutor{
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
    	if(args.length >= 2)
    	{
    		Location loc = LocationManager.getLocation(args[1]);
	    	switch(args[0]) 
	    	{
	    		case "create":
	    			
	    			if(loc == null)
	    			{
	    				LocationManager.registerLocation(args[1], ((Player)sender).getLocation());
	    				sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Lokacja &c" + args[1] + "&6 Utworzona!"));
	    				return true;
	    			}
	    			sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.ERROR_PREFIX + "Lokacja &c" + args[1] + "&4 już istnieje!"));
	    			return true;
	    		case "delete":
	    			if(loc == null)
	    				{
	    					sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.ERROR_PREFIX + "Lokacja &c" + args[1] + "&4 Nie istnieje!"));
	    					return true;
	    				}
	    			LocationManager.deleteLocation(args[1]);
	    			sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Lokacja &c" + args[1] + "&6 Usunięta!"));
	    			return true;
	    		case "tp":
	    			if(loc == null)
	    			{
	    				sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Lokacja &c" + args[1] + "&6 Nie istnieje!"));
	    				return true;
	    			}
	    			((Player)sender).teleport(LocationManager.getLocation(args[1]));
	    			return true;
	    		default:
	    			((Player)sender).sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Wybierz akcję do wykonania ... &l[create/delete/tp]"));
	    			break;
	    	}
    	}
    	else
    		((Player)sender).sendMessage(StringManager.Colorize(GLOBALVARIABLES.ERROR_PREFIX + "Podano Za mało argumentów"));
        return true;
    }
}