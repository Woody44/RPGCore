package com.rpg.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rpg.core.CoreConfig;
import com.rpg.core.Main;
import com.rpg.core.framework.ChatManager;

public class CommandSystem implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
    	Player player = (Player) sender;
    	player.sendMessage(ChatManager.GetColorized(CoreConfig.infoColor + "####################"));
    	player.sendMessage(ChatManager.GetColorized(CoreConfig.infoColor + "######RPGcore#######"));
    	player.sendMessage(ChatManager.GetColorized(CoreConfig.infoColor + "###&dAuthors:_woody44_" + CoreConfig.infoColor + "," + "&ddamian5466" + CoreConfig.infoColor + "##"));
    	player.sendMessage(ChatManager.GetColorized(CoreConfig.infoColor + "#####&aVersion:" + Main.GetMe().getDescription().getVersion() + CoreConfig.infoColor + "####"));
    	player.sendMessage(ChatManager.GetColorized(CoreConfig.infoColor + "####################"));
        return true;
    }
}
