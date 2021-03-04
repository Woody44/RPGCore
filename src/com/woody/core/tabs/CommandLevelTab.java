package com.woody.core.tabs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import com.woody.core.Config;
import com.woody.core.util.PlayerManager;

public class CommandLevelTab implements TabCompleter{
	ArrayList<String> actions = new ArrayList<>();
	
	public CommandLevelTab() {
		actions.add("get");
		actions.add("set");
		actions.add("setexp");
	}
	
	@Override
	public List<String> onTabComplete( CommandSender sender, Command cmd,
			 String alias,  String[] args) {
		if(!sender.hasPermission("woody.level.set"))
			return null;
		
		final ArrayList<String> completion = new ArrayList<String>();
		
		if(args.length == 1){
			StringUtil.copyPartialMatches(args[0], actions, completion);
		}
		
		if(args.length == 2) {
			return null;
		}
		if(args.length == 3 && args[0].contentEquals("setexp")){
			ArrayList<String> x = new ArrayList<>();
			x.add(Config.levels.get(PlayerManager.getOnlinePlayer(Bukkit.getPlayer(args[1])).getProfile().getLevel()).get("xp") + "");
			x.add((int)((int)Config.levels.get(PlayerManager.getOnlinePlayer(Bukkit.getPlayer(args[1])).getProfile().getLevel()).get("xp") *0.75f) + "");
			x.add((int)((int)Config.levels.get(PlayerManager.getOnlinePlayer(Bukkit.getPlayer(args[1])).getProfile().getLevel()).get("xp") *0.5f) + "");
			x.add((int)((int)Config.levels.get(PlayerManager.getOnlinePlayer(Bukkit.getPlayer(args[1])).getProfile().getLevel()).get("xp") *0.25f) + "");
			x.add("0");
			StringUtil.copyPartialMatches(args[2], x, completion);
		}
		if(args.length == 3 && args[0].contentEquals("set"))
		{
			ArrayList<String> x = new ArrayList<String>();
			for(int st : Config.levels.keySet())
				x.add(st + "");

			StringUtil.copyPartialMatches(args[2], x, completion);
		}
		Collections.sort(completion);
		return completion;
	}

}
