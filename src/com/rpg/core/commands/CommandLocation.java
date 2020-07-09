package com.rpg.core.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rpg.core.CoreConfig;
import com.rpg.core.framework.LocationsManager;
import com.rpg.core.framework.StringManager;

public class CommandLocation implements CommandExecutor{
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
    	if(args.length > 0)
	    	switch(args[0]) 
	    	{
	    		case "create":
	    			Location loc = LocationsManager.GetLocation(args[1]);
	    			if(loc == null)
	    			{
	    				LocationsManager.Create(args[1], ((Player)sender).getLocation());
	    				sender.sendMessage(StringManager.Colorize(CoreConfig.infoColor+ "Lokacja " + args[1] + " Utworzona!"));
	    				return true;
	    			}
	    			sender.sendMessage(StringManager.Colorize(CoreConfig.errorColor + "Lokacja " + args[1] + " juz istnieje!"));
	    			return true;
	    		case "delete":
	    			Location loc1 = LocationsManager.GetLocation(args[1]);
	    			if(loc1 == null)
	    				{
	    					sender.sendMessage(StringManager.Colorize(CoreConfig.errorColor + "Lokacja " + args[1] + " Nie istnieje!"));
	    					return true;
	    				}
	    			LocationsManager.DeleteLocation(args[1]);
	    			sender.sendMessage(StringManager.Colorize(CoreConfig.infoColor + "Lokacja " + args[1] + " Usunieta!"));
	    			return true;
	    		case "tp":
	    			Location loc2 = LocationsManager.GetLocation(args[1]);
	    			if(loc2 == null)
	    			{
	    				sender.sendMessage(StringManager.Colorize(CoreConfig.errorColor + "Lokacja " + args[1] + " Nie istnieje!"));
	    				return true;
	    			}
	    			((Player)sender).teleport(LocationsManager.GetLocation(args[1]));
	    			return true;
	    		default:
	    			((Player)sender).sendMessage(StringManager.Colorize(CoreConfig.warnColor + "Wybierz akcje do wykonania ... &l[create/delete/tp]"));
	    			break;
	    	}
    	else
    		((Player)sender).sendMessage(StringManager.Colorize(CoreConfig.warnColor+ "Wybierz akcje do wykonania ... &l[create/delete/tp]"));
        return true;
    }
}