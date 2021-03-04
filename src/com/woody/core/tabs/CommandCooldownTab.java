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
		actions.add("set");
		actions.add("get");
		actions.add("reset");
	}
	
	@Override
	public List<String> onTabComplete( CommandSender sender, Command cmd,
			 String alias,  String[] args) {
		final List<String> completion = new ArrayList<String>();
		
		switch(args.length)
		{
			case 1:
				StringUtil.copyPartialMatches(args[0], actions, completion);
				break;
			case 2:
				return null;
			case 3:
				if(args[0].contentEquals("set") || args[0].contentEquals("get"))
					StringUtil.copyPartialMatches(args[0], Arrays.asList(new String[]{"thing"}), completion);
				else
					StringUtil.copyPartialMatches(args[0], Arrays.asList(new String[]{""}), completion);
			case 4:
				if(args[0].contentEquals("set"))
					StringUtil.copyPartialMatches(args[3], new ArrayList<String>(Arrays.asList("5", "15", "25", "30", "45", "60", "90", "120", "150", "180", "300", "600", "900", "1200" )), completion);
				break;
		}
		Collections.sort(completion);
		return completion;
	}

}
