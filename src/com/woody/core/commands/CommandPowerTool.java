package com.woody.core.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.woody.core.Config;
import com.woody.core.types.PowerToolInfo;
import com.woody.core.util.ItemManager;
import com.woody.core.util.StringManager;

public class CommandPowerTool implements CommandExecutor{
	public static HashMap<String, List<PowerToolInfo>> ptEntries = new HashMap<>();
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		if(args.length < 1)
		{
			sender.sendMessage(StringManager.Colorize(Config.errorColor + "Za malo argumentow!"));
			return true;
		}
		Player p = (Player) sender;
		ItemStack item;
		String uuid;
		String cmd;
		PowerToolInfo pti;
		if(p == null){
			sender.sendMessage(StringManager.Colorize(Config.errorColor + "BLAD!"));
			return true;
		}

		
		item = p.getInventory().getItemInMainHand();
		if(item == null || item.getType() == Material.AIR){
			sender.sendMessage(StringManager.Colorize(Config.errorColor + "Nie mozesz przypisac komendy do powietrza!"));
			return true;
		}
		
		uuid = p.getUniqueId().toString();
		if(!ptEntries.containsKey(uuid))
			ptEntries.put(uuid, new ArrayList<PowerToolInfo>());
		
		cmd = "";
		for(int i = 1; i < args.length; i++)
			if(i <= args.length - 2)
				cmd += args[i] + " ";
			else
				cmd += args[i];
		
		pti = new PowerToolInfo(p.getInventory().getItemInMainHand(), cmd);
		switch(args[0]) 
		{
			case "+":
				if(args.length < 2)
				{
					sender.sendMessage(StringManager.Colorize(Config.errorColor + "Nie podano komendy do wykonania!"));
					return true;
				}
				ptEntries.get(uuid).add(pti);
				sender.sendMessage(StringManager.Colorize(Config.infoColor + "Dodano funkcjonalnosc [" + cmd + "] do przedmiotu " + pti.item.getItemMeta().getDisplayName() + " [" + pti.item.getType() + "]"));
				break;
			case "-":
				if(args.length < 2)
				{
					sender.sendMessage(StringManager.Colorize(Config.errorColor + "Nie podano id narzedzia."));
					return true;
				}
				List<PowerToolInfo> removeList = GetItemTools(uuid, item);
				
				ptEntries.get(uuid).remove(removeList.get(Integer.parseInt(args[1])));
				sender.sendMessage(StringManager.Colorize(Config.infoColor + "Usunieto funkcjonalnosc #" + args[1] + " z przedmiotu " + pti.item.getItemMeta().getDisplayName() + " [" + pti.item.getType() + "]"));
				break;
			case "/":
				List<PowerToolInfo> vipeList = GetItemTools(uuid, item);
				for(PowerToolInfo ptis : vipeList) 
				{
					ptEntries.get(uuid).remove(ptis);
				}
				sender.sendMessage(StringManager.Colorize(Config.infoColor + "Usunieto wszelka funkcjonalnosc z przedmiotu " + pti.item.getItemMeta().getDisplayName() + " [" + pti.item.getType() + "]"));
				break;
			case "list":
				List<PowerToolInfo> list = GetItemTools(uuid, item);
				
				if(list.size() > 0)
				{
					sender.sendMessage(StringManager.Colorize(Config.infoColor + "Lista funkcjonalnosci przypisanych do trzymanego przedmiotu:"));
					for(int i = 0; i < list.size(); i++)
						sender.sendMessage(StringManager.Colorize(Config.infoColor + i + " : " + Config.otherColor + list.get(i).command));
				}
				else
					sender.sendMessage(StringManager.Colorize(Config.infoColor + "Ten przedmiot nie posiada funkcjonalnosci."));
				break;
			default:
				sender.sendMessage(StringManager.Colorize(Config.errorColor + "Niewlasciwa akcja!"));
				break;
		}
		return true;
    }
	
	public static List<PowerToolInfo> GetItemTools(String uuid, ItemStack item) 
	{
		List<PowerToolInfo> list = new ArrayList<>();
		for(PowerToolInfo ptis : ptEntries.get(uuid))
		{
			if(ItemManager.isSimilar(item, ptis.item, false))
			{
				list.add(ptis);
			}
		}
		return list;
	}
}
