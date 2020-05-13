package com.rpg.klasy.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rpg.klasy.gui.UpgradeClassGUI;

public class CommandUpgradeClass implements CommandExecutor
{
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player player = (Player) sender;
		UpgradeClassGUI ulepszklase = new UpgradeClassGUI();
		ulepszklase.openInventory(player);
		return true;
	}
}
