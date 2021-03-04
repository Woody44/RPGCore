package com.woody.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Player;

public class CommandSuicide implements CommandExecutor{

			@Override
			public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
		    {
				if(!(sender instanceof Player))
				{
					sender.sendMessage("This command is designed for players use only.");
					return true;
				}
				CraftLivingEntity cle = (CraftLivingEntity)sender;
				cle.getHandle().setLastDamager(cle.getHandle());
				cle.setHealth(0);
				return true;
		    }
		}
