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
		
		if(args.length == 1)
			StringUtil.copyPartialMatches(args[0], actions, completion);
		
		if(args.length == 2 )
		{
			List<String> names = new ArrayList<String>();
			Player[] players = new Player[Bukkit.getOnlinePlayers().size()];
			players = Bukkit.getOnlinePlayers().toArray(players);
			for(Player p: players)
				names.add(p.getName());
			StringUtil.copyPartialMatches(args[1], names, completion);
		}
			
		
		if(args.length == 3)
			StringUtil.copyPartialMatches(args[2], new ArrayList<String>(Arrays.asList("thing")), completion);
		
		if(args.length == 4 && args[0].contentEquals("do"))
		{
			StringUtil.copyPartialMatches(args[3], new ArrayList<String>(Arrays.asList("5")), completion);
			StringUtil.copyPartialMatches(args[3], new ArrayList<String>(Arrays.asList("10")), completion);
			StringUtil.copyPartialMatches(args[3], new ArrayList<String>(Arrays.asList("15")), completion);
		}
		
		Collections.sort(completion);
		return completion;
	}

}
