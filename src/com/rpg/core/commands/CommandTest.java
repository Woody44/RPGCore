package com.rpg.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.rpg.core.Main;
import com.rpg.core.economy.Wallet;

public class CommandTest implements CommandExecutor{
	public Main main;
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Wallet w = new Wallet();
		w.uuid = Bukkit.getPlayer(args[0]).getUniqueId().toString();
		w.Money = Integer.parseInt(args[1]);
		main.dbmg.SetPlayerWallet(w);
		return true;
	}
}
