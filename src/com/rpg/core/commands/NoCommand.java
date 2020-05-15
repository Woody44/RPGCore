package com.rpg.core.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rpg.core.CoreConfig;


public class NoCommand implements CommandExecutor{ 
	public String originalName;
	public NoCommand(String originalName)
	{
		this.originalName = originalName;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', CoreConfig.warnColor + CoreConfig.addonDisabled));
		return true;
	}
}
