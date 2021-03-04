package com.woody.core.commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.woody.core.util.FileManager;
import com.woody.core.util.StringManager;

public class CommandHelpOp implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		if(!(sender instanceof Player))
		{
			Bukkit.getLogger().warning("This command id for players only!");
			return true;
		}
		if(!sender.hasPermission("woody.helpop.admin"))
		{
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH_mm_ss_nn");
			LocalDateTime now = LocalDateTime.now();
			
			Player p = (Player)sender;
			ArrayList<Player> ops = new ArrayList<>();
			for(Player pl: Bukkit.getOnlinePlayers())
			{
				if(pl.hasPermission("woody.helpop.read"))
				{
					ops.add(pl);
				}
			}
			
			if(ops.size()==0)
			{
				String dateStg = dtf.format(now) + ".yml";
				HashMap<String, Object> hm = new HashMap<>();
				hm.put("player", p.getName());
				hm.put("message", String.join(" ", args));
				FileManager.createConfig("helpop/" + dateStg, hm);
			}
			else
			{
				for(Player pl : ops)
				{
					pl.sendMessage(StringManager.Colorize("&8[&cHelpOp&8] " + p.getDisplayName() + "&7: " + String.join(" ", args)));
				}
			}
		}
		else
		{
			switch(args[0])
			{
				
			}
		}
		return true;
    }
}