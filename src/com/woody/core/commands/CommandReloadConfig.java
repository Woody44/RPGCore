package com.woody.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.woody.core.Config;
import com.woody.core.GLOBALVARIABLES;
import com.woody.core.util.StringManager;

public class CommandReloadConfig implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		Config.Reload();
		sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Config przeladowany.▪▫"));
		sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Jesli uruchomiono lub wyłączono jakiś moduł - potrzebujesz &crestartu&8&l(zalecany)&6/&creloadu&8&l(Niezalecany ze względu na inne pluginy)&6 całego serwera"));
		return true;
    }
}
