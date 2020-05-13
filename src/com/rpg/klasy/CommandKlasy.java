package com.rpg.klasy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandKlasy implements CommandExecutor
{
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player player = (Player) sender;
		ChooseClass chooseclass = new ChooseClass();
		chooseclass.openInventory(player);
		return true;
	}
}
