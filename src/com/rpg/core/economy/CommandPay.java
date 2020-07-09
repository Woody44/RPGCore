package com.rpg.core.economy;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rpg.core.CoreConfig;
import com.rpg.core.framework.StringManager;
import com.rpg.core.framework.PlayerManager;

public class CommandPay implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		int count = Integer.parseInt(args[1]);
		Player[] p = new Player[2];
		
		p[0] = (Player)sender;
		p[1] = Bukkit.getPlayer(args[0]);
		
		if(count <0) 
		{
			p[0].sendMessage(StringManager.Colorize(CoreConfig.errorColor + "Nice Try."));
			return false;
		}
		if(PlayerManager.getPlayer(p[0].getUniqueId().toString()).money < count)
		{
			p[0].sendMessage(StringManager.Colorize(CoreConfig.warnColor + "Nie posiadasz tyle pieniedzy!"));
			p[1].sendMessage(StringManager.Colorize(CoreConfig.warnColor + "Gracz " + p[0].getDisplayName() + "probowal przeslac na twoje konto " + count + ", ale cos nie pyklo..."));
		}
		else
		{
			PlayerManager.addMoney(p[0].getUniqueId().toString(), count * -1);
			p[0].sendMessage(StringManager.Colorize(CoreConfig.infoColor + "Przelano graczowi " + p[1].getDisplayName() + " &a" + count + "&2&l" + CoreConfig.currencySymbol));
			p[1].sendMessage(StringManager.Colorize(CoreConfig.infoColor + "Gracz " + p[0].getDisplayName() + CoreConfig.infoColor +" przelal na twoje konto &a" + count + "&2&l" + CoreConfig.currencySymbol));
			PlayerManager.addMoney(p[1].getUniqueId().toString(), count);
		}
		return true;
	}
}
