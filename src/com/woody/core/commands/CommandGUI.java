package com.woody.core.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.woody.core.Config;
import com.woody.core.types.CommandGuiAction;
import com.woody.core.util.FileManager;
import com.woody.core.util.ItemManager;
import com.woody.core.util.StringManager;

public class CommandGUI implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
		String guiName = args[1];
		Inventory inv = GetInv(9, guiName);
		if(args.length > 2)
			inv = GetInv(Integer.parseInt(args[2]), guiName);
		CommandGuiAction action = CommandGuiAction.valueOf(args[0]);
		switch(action) 
		{
			case create:
				ArrayList<String> keys = new ArrayList<>(Arrays.asList("items"));
				ArrayList<Object> values = new ArrayList<>(Arrays.asList(ItemManager.createItemStack(Material.BEDROCK, "Andrzej", new String[] {"Placeholder"}, 1)));
				if(FileManager.getConfig("menus/" + guiName + ".yml") == null)
				{
					FileManager.createConfig("menus/" + guiName + ".yml", keys, values);
					((Player)sender).openInventory(inv);
				}
				else 
				{
					sender.sendMessage(StringManager.Colorize(Config.otherColor + "Takie menu juz istnieje!"));
				}
				break;
			case clone:
				
				break;
			case delete:
				if(FileManager.getConfig("menus/" + guiName + ".yml") == null)
				{
					sender.sendMessage(StringManager.Colorize(Config.warnColor + "Takie Inventory nie istnieje!"));
				}
				else
				{
					FileManager.deleteFile("menus/" + guiName + ".yml");
					sender.sendMessage(StringManager.Colorize(Config.warnColor + "Plik Usuniêty."));
				}
				break;
			case edit:
				ConfigurationSection cs = FileManager.getConfig("menus/" + guiName + ".yml").getConfigurationSection("items");
				Set<String> keys1 = cs.getKeys(false);
				inv = GetInv(keys1.size(), guiName);
				for(String s : keys1)
					inv.addItem(cs.getItemStack(s));
				((Player)sender).openInventory(inv);
				break;
			default:
				sender.sendMessage(StringManager.Colorize("&cZla akcja!"));
				break;
		}
		//FileManager.updateConfig("/menus/MainMenu.yml", keys, values);
		
		return true;
    }
	
	public Inventory GetInv(int size, String name)
	{
		return Bukkit.createInventory(null, size, name.replace("_", " ")+"--edition--");
	}
}
