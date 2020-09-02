package com.rpg.core.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.rpg.core.framework.FileManager;

public class CommandModel implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
    	Player player = (Player) sender;
    	List<Entity> ents = player.getNearbyEntities(5, 5, 5);
    	switch(args[0]) 
    	{
    		case "create":
    		{
    			if(!FileManager.checkFileExistence("model", args[1]))
    			{
    				for(int i=0; i < ents.size(); i++) 
    		    	{
    		    		if(ents.get(i).getType() != EntityType.ARMOR_STAND)
    		    			continue;
    		    		else
    		    		{
    		    			FileManager.CreateConfigFile("model", args[1], "armorstand."+i+".pose",  ents.get(i).getPose());
    		    			FileManager.updateFile("model", args[1], "armorstand." + i + ".item0", ((ArmorStand)ents.get(i)).getEquipment().getItemInMainHand());
    		    		}
    		    	}
    			}
    			else
    				player.sendMessage("Taki model juz istnieje!");
    			
    			break;
    		}
    		
    		case "spawn":
    		{
    			if(!FileManager.checkFileExistence("model", args[1])) 
    			{
    				FileConfiguration fc = FileManager.getFileConfig("model", args[1]);
    				
    				int j =0;
    				while(fc.getConfigurationSection("armorstand." + j)!= null) 
    				{
    					//Pose pose = (Pose)fc.get("armorstand."+j+".pose");
    					ArmorStand e = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
    					e.setArms(true);
    					e.setAI(false);
    					e.setGravity(false);
    					e.setInvulnerable(true);
    					e.setVisible(false);
    					e.getEquipment().setItemInMainHand((ItemStack)fc.get("armorstand."+j+".item0"));
    					j++;
    				}
    			}
    		}
    	}
    	
    	return true;
    }

}
