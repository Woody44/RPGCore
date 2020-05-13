package com.rpg.klasy.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rpg.klasy.gui.ChooseClassGUI;

public class CommandKlasy implements CommandExecutor
{
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player player = (Player) sender;
		ChooseClassGUI chooseclass = new ChooseClassGUI();
		chooseclass.openInventory(player);
		return true;
	}
}
