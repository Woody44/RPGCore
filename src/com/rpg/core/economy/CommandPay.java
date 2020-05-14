package com.rpg.core.economy;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rpg.core.DatabaseManager;

public class CommandPay implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		int count = Integer.parseInt(args[1]);
		Wallet[] w = new Wallet[2];
		Player[] p = new Player[2];
		p[0] = Bukkit.getPlayer(sender.getName());
		p[1] = Bukkit.getPlayer(args[0]);
		w[0] = DatabaseManager.GetPlayerWallet(p[0].getUniqueId().toString());
		w[1] = DatabaseManager.GetPlayerWallet(p[1].getUniqueId().toString());
		
		if(count < 0)
		{
			Bukkit.getPlayer(sender.getName()).sendMessage("");
			return false;
		}
		if(w[0] != null && w[1] != null) 
		{
			w[0].AddMoney(count * -1);
			
			w[1].AddMoney(count);
		}
		return true;
	}
}
