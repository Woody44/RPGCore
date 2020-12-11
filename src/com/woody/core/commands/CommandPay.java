package com.woody.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.woody.core.Config;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;
public class CommandPay implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		int count = Integer.parseInt(args[1]);
		Player[] p = new Player[2];
		
		p[0] = (Player)sender;
		p[1] = Bukkit.getPlayer(args[0]);
		
		if(count <0) 
		{
			p[0].sendMessage(StringManager.Colorize(Config.errorColor + "Nice Try."));
			return false;
		}
		if(PlayerManager.onlinePlayers.get(p[0]).getMoney() < count)
		{
			p[0].sendMessage(StringManager.Colorize(Config.warnColor + "Nie posiadasz tyle pieniedzy!"));
			p[1].sendMessage(StringManager.Colorize(Config.warnColor + "Gracz " + p[0].getDisplayName() + "probowal przeslac na twoje konto " + count + ", ale cos nie pyklo..."));
		}
		else
		{
			PlayerManager.onlinePlayers.get(p[0]).addMoney(count * -1);
			p[0].sendMessage(StringManager.Colorize(Config.infoColor + "Przelano graczowi " + p[1].getDisplayName() + " &a" + count + "&2&l" + Config.currencySymbol));
			p[1].sendMessage(StringManager.Colorize(Config.infoColor + "Gracz " + p[0].getDisplayName() + Config.infoColor +" przelal na twoje konto &a" + count + "&2&l" + Config.currencySymbol));
			PlayerManager.onlinePlayers.get(p[1]).addMoney(count);
		}
		return true;
	}
}
