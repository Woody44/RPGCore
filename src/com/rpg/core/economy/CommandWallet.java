package com.rpg.core.economy;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.rpg.core.Main;

public class CommandWallet implements CommandExecutor{
	
	public Main main;
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String uuid = Bukkit.getPlayer(args[0]).getUniqueId().toString();
		Wallet w = main.dbmg.GetPlayerWallet(uuid);
		
		if(w == null) {
			w = new Wallet();
			w.uuid = uuid;
			w.Money = Integer.parseInt(args[1]);
			w.Setup(main.dbmg);
			main.dbmg.AddPlayerWallet(w);
		}
		else 
		{
			w.Setup(main.dbmg);
			w.AddMoney(Integer.parseInt(args[1]));
		}
		return true;
	}
}
