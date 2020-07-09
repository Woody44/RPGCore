package com.rpg.core.economy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rpg.core.CoreConfig;
import com.rpg.core.framework.StringManager;
import com.rpg.core.framework.PlayerManager;

public class CommandMoney implements CommandExecutor
{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		String uuid = player.getUniqueId().toString();
		player.sendMessage(StringManager.Colorize(CoreConfig.infoColor + "Stan Konta: &a" + PlayerManager.getPlayer(uuid).money + "&2&l" + CoreConfig.currencySymbol));
		return true;
	}
}