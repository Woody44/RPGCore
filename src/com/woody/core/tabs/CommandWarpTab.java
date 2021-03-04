package com.woody.core.tabs;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import com.woody.core.util.FileManager;

public class CommandWarpTab implements TabCompleter{
	ArrayList<String> actions = new ArrayList<>();
	ArrayList<String> options = new ArrayList<>();
	public CommandWarpTab() 
	{
		actions.add("create");
		actions.add("delete");
		actions.add("set");
		
		options.add("separate-cooldown");
		options.add("cooldown");
	}
	
	@Override
	public List<String> onTabComplete( CommandSender sender, Command cmd, String alias,  String[] args) {
		final List<String> completion = new ArrayList<String>();
		
		switch(args.length)
		{
		
		case 1:
			StringUtil.copyPartialMatches(args[0], actions, completion);
			break;
		
		case 2:
			ArrayList<String> names = new ArrayList<>();
			if(args[1] != "create")
			{
				for(File f : FileManager.listFiles("warps/"))
					if(f.getName().contains(".yml"))
						names.add(f.getName().replace(".yml", ""));
				StringUtil.copyPartialMatches(args[1], names, completion);
			}
			break;
			
		case 3:
			if(args[0].contentEquals("set"))
				StringUtil.copyPartialMatches(args[2], options, completion);
			break;
		}

		Collections.sort(completion);
		return completion;
	}

}
