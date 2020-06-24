package com.rpg.core.economy;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rpg.core.CoreConfig;
import com.rpg.core.framework.ChatManager;
import com.rpg.core.framework.CustomPlayer;
import com.rpg.core.framework.PlayersManager;
import com.rpg.core.framework.Wallet;

public class CommandPay implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		int count = Integer.parseInt(args[1]);
		Wallet[] w = new Wallet[2];
		CustomPlayer[] p = new CustomPlayer[2];
		p[0] = PlayersManager.GetOnlinePlayer(((Player)sender).getUniqueId().toString());
		p[1] = PlayersManager.GetOnlinePlayer(Bukkit.getPlayer(args[0]).getUniqueId().toString());
		w[0] = p[0].wallet;
		w[1] = p[1].wallet;
		
		if(count < 0)
		{
			p[0].player.sendMessage("Nice Try");
			return false;
		}
		
		if(w[0] != null && w[1] != null && w[0].Money >= count) 
		{
			p[0].player.sendMessage(ChatManager.GetColorized(CoreConfig.infoColor + "Przes³ano graczowi " + p[1].player.getName() + " &a" + count + CoreConfig.currencySymbol));
			w[0].AddMoney(count * -1);
			p[1].player.sendMessage(ChatManager.GetColorized(CoreConfig.infoColor + "Otrzymano " + "&a" + count + "&2" + CoreConfig.currencySymbol + CoreConfig.infoColor + " od gracza " + p[0].player.getName()));
			w[1].AddMoney(count);
		}
		else
		{
			p[0].player.sendMessage(ChatManager.GetColorized(CoreConfig.warnColor + "Nie posiadasz tyle pieniedzy!"));
		}
		return true;
	}
}
