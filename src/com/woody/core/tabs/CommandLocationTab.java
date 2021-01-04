package com.woody.core.tabs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import com.woody.core.util.LocationManager;

public class CommandLocationTab implements TabCompleter{
	ArrayList<String> actions = new ArrayList<>();
	public CommandLocationTab() 
	{
		actions.add("create");
		actions.add("delete");
		actions.add("tp");
	}
	
	@Override
	public List<String> onTabComplete( CommandSender sender, Command cmd,
			 String alias,  String[] args) {
		final List<String> completion = new ArrayList<String>();
		if(args.length == 1)
			StringUtil.copyPartialMatches(args[0], actions, completion);
		if(args.length == 2)
		{
			ArrayList<String> names = new ArrayList<>();
			for(String s : LocationManager.listLocations().keySet())
				names.add(s);
			StringUtil.copyPartialMatches(args[1], names, completion);
		}
		Collections.sort(completion);
		return completion;
	}

}
