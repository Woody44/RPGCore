package com.woody.core.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.woody.core.Config;
import com.woody.core.util.LocationManager;
import com.woody.core.util.StringManager;

public class CommandLocation implements CommandExecutor{
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
    	if(args.length > 0)
	    	switch(args[0]) 
	    	{
	    		case "create":
	    			Location loc = LocationManager.getLocation(args[1]);
	    			if(loc == null)
	    			{
	    				LocationManager.registerLocation(args[1], ((Player)sender).getLocation());
	    				sender.sendMessage(StringManager.Colorize(Config.infoColor+ "Lokacja " + args[1] + " Utworzona!"));
	    				return true;
	    			}
	    			sender.sendMessage(StringManager.Colorize(Config.errorColor + "Lokacja " + args[1] + " juz istnieje!"));
	    			return true;
	    		case "delete":
	    			Location loc1 = LocationManager.getLocation(args[1]);
	    			if(loc1 == null)
	    				{
	    					sender.sendMessage(StringManager.Colorize(Config.errorColor + "Lokacja " + args[1] + " Nie istnieje!"));
	    					return true;
	    				}
	    			LocationManager.deleteLocation(args[1]);
	    			sender.sendMessage(StringManager.Colorize(Config.infoColor + "Lokacja " + args[1] + " Usunieta!"));
	    			return true;
	    		case "tp":
	    			Location loc2 = LocationManager.getLocation(args[1]);
	    			if(loc2 == null)
	    			{
	    				sender.sendMessage(StringManager.Colorize(Config.errorColor + "Lokacja " + args[1] + " Nie istnieje!"));
	    				return true;
	    			}
	    			((Player)sender).teleport(LocationManager.getLocation(args[1]));
	    			return true;
	    		default:
	    			((Player)sender).sendMessage(StringManager.Colorize(Config.warnColor + "Wybierz akcje do wykonania ... &l[create/delete/tp]"));
	    			break;
	    	}
    	else
    		((Player)sender).sendMessage(StringManager.Colorize(Config.warnColor+ "Wybierz akcje do wykonania ... &l[create/delete/tp]"));
        return true;
    }
}