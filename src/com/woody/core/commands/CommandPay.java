package com.woody.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.woody.core.Config;
import com.woody.core.GLOBALVARIABLES;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;
public class CommandPay implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!(sender instanceof Player))
		{
			Bukkit.getLogger().warning("This command id for players use only.");
			return true;
		}
		
		int count = Integer.parseInt(args[1]);
		Player[] p = new Player[2];
		
		p[0] = (Player)sender;
		p[1] = Bukkit.getPlayer(args[0]);
		
		if(count <0) 
		{
			p[0].sendMessage(StringManager.Colorize(GLOBALVARIABLES.ERROR_PREFIX + "Nice Try."));
			return false;
		}
		if(PlayerManager.getOnlinePlayer(p[0]).getProfile().getMoney() < count)
		{
			p[0].sendMessage(StringManager.Colorize(GLOBALVARIABLES.BANK_PREFIX + "Nie posiadasz tyle pieniędzy!"));
			p[1].sendMessage(StringManager.Colorize(GLOBALVARIABLES.BANK_PREFIX + "Gracz &c" + p[0].getDisplayName() + "&b próbował przesłać na twoje konto &a" + count + "&2&l" + Config.currencySymbol + "&r&6, ale coś nie pykło..."));
		}
		else
		{
			PlayerManager.getOnlinePlayer(p[0]).getProfile().addMoney(count * -1);
			p[0].sendMessage(StringManager.Colorize(GLOBALVARIABLES.BANK_PREFIX + "Przelano graczowi &c" + p[1].getDisplayName() + " &a" + count + "&2&l" + Config.currencySymbol));
			p[1].sendMessage(StringManager.Colorize(GLOBALVARIABLES.BANK_PREFIX + "Gracz &c" + p[0].getDisplayName() + "&b przelał na twoje konto &a" + count + "&2&l" + Config.currencySymbol));
			PlayerManager.getOnlinePlayer(p[1]).getProfile().addMoney(count);
		}
		return true;
	}
}
