package com.rpg.core.economy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rpg.core.ChatManager;
import com.rpg.core.CoreConfig;
import com.rpg.core.DatabaseManager;

public class CommandMoney implements CommandExecutor
{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		String uuid = player.getUniqueId().toString();
		Wallet w = DatabaseManager.GetPlayerWallet(uuid);
		
		if(w == null) {
			w = new Wallet();
			w.uuid = uuid;
			w.Money = 0;
			DatabaseManager.AddPlayerWallet(w);
		}
		
		player.sendMessage(ChatManager.GetColorized(CoreConfig.infoColor + "Aktualny stan konta: " + "&a" + w.Money + CoreConfig.currencySymbol));
		return true;
	}
}
