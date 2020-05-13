package com.rpg.items.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class CommandSystem implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
    	ItemStack diamond = new ItemStack(Material.DIAMOND, 70);
    	diamond.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 69);
    	ItemMeta meta = diamond.getItemMeta();
    	meta.setDisplayName("Kurwiszon");
    	
    	ArrayList<String> lore = new ArrayList<String>();
    	lore.add("X");
    	lore.add("D");
    	meta.setLore(lore);
    	
    	diamond.setItemMeta(meta);
    	
    	Player player = (Player) sender;
    	player.teleport(new Location(Bukkit.getWorld(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3])));
        sender.sendMessage("Uzyles " + command.getName());
        
        Bukkit.broadcastMessage(player.getType().toString());
        player.getInventory().addItem(diamond);
        return true;
    }
}
