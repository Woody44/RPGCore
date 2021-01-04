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

import com.woody.core.Config;
import com.woody.core.util.PlayerManager;

public class CommandPowerToolTab implements TabCompleter{
	ArrayList<String> actions = new ArrayList<>();
	
	public CommandPowerToolTab() 
	{
		actions.add("+");
		actions.add("-");
		actions.add("/");
		actions.add("list");
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
			players = Bukkit.getOnlinePlayers().toArray(players);
			for(Player p: players)
				names.add(p.getName());
			StringUtil.copyPartialMatches(args[1], names, completion);
		}
		if(args.length == 3 && args[0].contentEquals("setexp"))
		{
			StringUtil.copyPartialMatches(args[2], new ArrayList<String>(Arrays.asList("0")), completion);
			StringUtil.copyPartialMatches(args[2], new ArrayList<String>(Arrays.asList(Config.levels.get((int)PlayerManager.onlinePlayers.get(Bukkit.getPlayer(args[1])).getLevel())*0.25f + "")), completion);
			StringUtil.copyPartialMatches(args[2], new ArrayList<String>(Arrays.asList(Config.levels.get((int)PlayerManager.onlinePlayers.get(Bukkit.getPlayer(args[1])).getLevel())*0.5f + "")), completion);
			StringUtil.copyPartialMatches(args[2], new ArrayList<String>(Arrays.asList(Config.levels.get((int)PlayerManager.onlinePlayers.get(Bukkit.getPlayer(args[1])).getLevel())*0.75f + "")), completion);
			StringUtil.copyPartialMatches(args[2], new ArrayList<String>(Arrays.asList(Config.levels.get((int)PlayerManager.onlinePlayers.get(Bukkit.getPlayer(args[1])).getLevel()) + "")), completion);
		}
		if(args.length == 3 && args[0].contentEquals("set"))
			StringUtil.copyPartialMatches(args[2], new ArrayList<String>(Arrays.asList(PlayerManager.onlinePlayers.get(Bukkit.getPlayer(args[1])).getLevel() + "")), completion);
		
		Collections.sort(completion);
		return completion;
	}

}
