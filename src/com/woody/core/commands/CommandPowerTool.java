package com.woody.core.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.woody.core.GLOBALVARIABLES;
import com.woody.core.types.PowerToolInfo;
import com.woody.core.util.ItemManager;
import com.woody.core.util.StringManager;

public class CommandPowerTool implements CommandExecutor{
	public static HashMap<String, List<PowerToolInfo>> ptEntries = new HashMap<>();
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		if(!(sender instanceof Player))
		{
			Bukkit.getLogger().warning("This command id for players use only.");
			return true;
		}
		
		if(args.length < 1)
		{
			sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.ERROR_PREFIX + "Podano za mało argumentów!"));
			return true;
		}
		Player p = (Player) sender;
		ItemStack item;
		String uuid;
		String cmd;
		PowerToolInfo pti;
		
		item = p.getInventory().getItemInMainHand();
		if(item == null || item.getType() == Material.AIR){
			sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.ERROR_PREFIX + "Nie możesz przypisać komendy do powietrza!"));
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
			case "add":
				if(args.length < 2)
				{
					sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.ERROR_PREFIX + "Nie podano komendy do wykonania!"));
					return true;
				}
				ptEntries.get(uuid).add(pti);
				sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Dodano funkcjonalnosc [&c" + cmd + "&6] do przedmiotu &r" + pti.item.getItemMeta().getDisplayName() + "&6 [&c" + pti.item.getType() + "&6]"));
				break;
			case "remove":
				if(args.length < 2)
				{
					sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Nie podano id narzedzia."));
					return true;
				}
				List<PowerToolInfo> removeList = GetItemTools(uuid, item);
				
				ptEntries.get(uuid).remove(removeList.get(Integer.parseInt(args[1])));
				sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Usunięto funkcjonalność &c#" + args[1] + "&6 z przedmiotu &r" + pti.item.getItemMeta().getDisplayName() + "&6 [&c" + pti.item.getType() + "&6]"));
				break;
			case "clear":
				List<PowerToolInfo> vipeList = GetItemTools(uuid, item);
				for(PowerToolInfo ptis : vipeList) 
				{
					ptEntries.get(uuid).remove(ptis);
				}
				sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Usunięto wszelką funkcjonalność z przedmiotu &r" + pti.item.getItemMeta().getDisplayName() + "&6 [&c" + pti.item.getType() + "&6]"));
				break;
			case "list":
				List<PowerToolInfo> list = GetItemTools(uuid, item);
				
				if(list.size() > 0)
				{
					sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Lista funkcjonalności przypisanych do trzymanego przedmiotu:"));
					for(int i = 0; i < list.size(); i++)
						sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + i + " : &d"+ list.get(i).command));
				}
				else
					sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.CORE_PREFIX + "Ten przedmiot nie posiada funkcjonalności."));
				break;
			default:
				sender.sendMessage(StringManager.Colorize(GLOBALVARIABLES.ERROR_PREFIX + "Niewłaściwa akcja!"));
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
