package com.woody.core.tabs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.woody.core.util.PlayerManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class CommandProfileTab implements TabCompleter{
	ArrayList<String> actions = new ArrayList<>();
	
	public CommandProfileTab() 
	{
		actions.add("id");
	}
	
	@Override
	public List<String> onTabComplete( CommandSender sender, Command cmd,
			 String alias,  String[] args) {
		final List<String> completion = new ArrayList<String>();
		if(args.length == 1)
			StringUtil.copyPartialMatches(args[0], PlayerManager.getOnlinePlayer((Player)sender).getProfilesIdsList(), completion);
		Collections.sort(completion);
		return completion;
	}

}
