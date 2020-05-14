package com.rpg.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.rpg.core.Main;

public class CommandTest implements CommandExecutor{
	public Main main;
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!Bukkit.getPlayer(args[0]).hasPermission("CHUJCIWDUPE"))
		{
			Bukkit.getPlayer(args[0]).sendMessage("NIE");
		}
		String UUID = Bukkit.getPlayer(args[0]).getUniqueId().toString();
		main.dbmg.UpdatePlayerClass(UUID, Integer.parseInt(args[1]), false);
		return true;
	}
}
