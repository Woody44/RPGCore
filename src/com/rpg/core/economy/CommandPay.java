package com.rpg.core.economy;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rpg.core.CoreConfig;
import com.rpg.core.framework.ChatManager;
import com.rpg.core.framework.DatabaseManager;
import com.rpg.core.framework.Wallet;

public class CommandPay implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		int count = Integer.parseInt(args[1]);
		Wallet[] w = new Wallet[2];
		Player[] p = new Player[2];
		p[0] = (Player)sender;
		p[1] = Bukkit.getPlayer(args[0]);
		w[0] = DatabaseManager.GetPlayerWallet(p[0].getUniqueId().toString());
		w[1] = DatabaseManager.GetPlayerWallet(p[1].getUniqueId().toString());
		
		if(count < 0)
		{
			p[0].sendMessage("Nice Try");
			return false;
		}
		
		if(w[0] != null && w[1] != null && w[0].Money >= count && p[0] != p[1]) 
		{
			p[0].sendMessage(ChatManager.GetColorized(CoreConfig.infoColor + "Przes³ano graczowi " + p[1].getName() + " &a" + count + CoreConfig.currencySymbol));
			w[0].AddMoney(count * -1);
			p[1].sendMessage(ChatManager.GetColorized(CoreConfig.infoColor + "Otrzymano " + "&a" + count + "&2" + CoreConfig.currencySymbol + CoreConfig.infoColor + " od gracza " + p[0].getName()));
			w[1].AddMoney(count);
		}
		else
		{
			p[0].sendMessage(ChatManager.GetColorized(CoreConfig.warnColor + "Nie posiadasz tyle pieniedzy!"));
		}
		return true;
	}
}
