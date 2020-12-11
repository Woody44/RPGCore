package com.woody.core.tabs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

public class CommandLocationTab implements TabCompleter{
	ArrayList<String> actions = new ArrayList<>();
	ArrayList<String> name = new ArrayList<>();
	public CommandLocationTab() 
	{
		actions.add("create");
		actions.add("delete");
		actions.add("tp");
		
		name.add("name");
	}
	
	@Override
	public List<String> onTabComplete( CommandSender sender, Command cmd,
			 String alias,  String[] args) {
		final List<String> completion = new ArrayList<String>();
		if(args.length == 1)
			StringUtil.copyPartialMatches(args[0], actions, completion);
		if(args.length == 2)
			StringUtil.copyPartialMatches(args[1], name, completion);
		Collections.sort(completion);
		return completion;
	}

}
