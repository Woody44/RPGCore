package com.rpg.core.commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.rpg.core.ChatManager;
import com.rpg.core.CoreConfig;


public class CommandSystem implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
    	Player player = (Player) sender;
    	player.sendMessage(ChatManager.GetColorized(CoreConfig.infoColor + "####################"));
    	player.sendMessage(ChatManager.GetColorized(CoreConfig.infoColor + "######RPGcore#######"));
    	player.sendMessage(ChatManager.GetColorized(CoreConfig.infoColor + "##&dAuthor:_woody44_" + CoreConfig.infoColor +"##"));
    	player.sendMessage(ChatManager.GetColorized(CoreConfig.infoColor + "###&aVersion: 1.0.0" + CoreConfig.infoColor + "###"));
    	player.sendMessage(ChatManager.GetColorized(CoreConfig.infoColor + "####################"));
        return true;
    }
}
