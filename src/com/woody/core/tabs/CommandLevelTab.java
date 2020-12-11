package com.woody.core.tabs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class CommandLevelTab implements TabCompleter{
	ArrayList<String> actions = new ArrayList<>();
	
	public CommandLevelTab() 
	{
		actions.add("get");
		actions.add("set");
		actions.add("setexp");
	}
	
	@Override
	public List<String> onTabComplete( CommandSender sender, Command cmd,
			 String alias,  String[] args) {
		if(!sender.hasPermission("core.level.set"))
			return null;
		
		final List<String> completion = new ArrayList<String>();
		
		if(args.length == 1)
			StringUtil.copyPartialMatches(args[0], actions, completion);
		
		if(args.length == 2) {
			List<String> names = new ArrayList<String>();
			Player[] players = new Player[Bukkit.getOnlinePlayers().size()];
			Bukkit.getOnlinePlayers().toArray();
			for(Player p: players)
			names.add(p.getName());
			StringUtil.copyPartialMatches(args[1], names, completion);
		}
		if(args.length == 3 && args[0].contains("set"))
			StringUtil.copyPartialMatches(args[2], new ArrayList<String>(Arrays.asList("amount")), completion);
		
		Collections.sort(completion);
		return completion;
	}

}
