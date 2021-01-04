package com.woody.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.woody.core.Config;
import com.woody.core.util.StringManager;

public class CommandReloadConfig implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		Config.Reload();
		sender.sendMessage(StringManager.Colorize(Config.infoColor + "Config przeladowany.▪▫"));
		sender.sendMessage(StringManager.Colorize(Config.infoColor + "Jesli uruchomiono lub wylaczono jakis modul - potrzebujesz restartu calego pluginu lub serwera."));
		return true;
    }
}
