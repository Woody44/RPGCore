package com.woody.core.tabs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

public class CommandCooldownTab implements TabCompleter{
	ArrayList<String> actions = new ArrayList<>();
	
	public CommandCooldownTab() 
	{
		actions.add("do");
		actions.add("check");
		actions.add("checku");
		actions.add("reset");
	}
	
	@Override
	public List<String> onTabComplete( CommandSender sender, Command cmd,
			 String alias,  String[] args) {
		final List<String> completion = new ArrayList<String>();
		
		if(!sender.hasPermission("core.cooldown.admin"))
			return null;
		if(args.length == 1)
			StringUtil.copyPartialMatches(args[0], actions, completion);
		
		if(args.length == 2)
			StringUtil.copyPartialMatches(args[1], new ArrayList<String>(Arrays.asList("thing")), completion);
		
		if(args.length == 3 && args[0].contentEquals("do"))
			StringUtil.copyPartialMatches(args[2], new ArrayList<String>(Arrays.asList("seconds")), completion);
		
		Collections.sort(completion);
		return completion;
	}

}
