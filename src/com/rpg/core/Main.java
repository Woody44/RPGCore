package com.rpg.core;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.rpg.core.commands.*;
import com.rpg.core.economy.*;
import com.rpg.core.events.*;
import com.rpg.core.framework.Logger;

public class Main extends JavaPlugin implements Listener{
	public static Main instance;
	
	@Override
	public void onEnable() 
	{
		instance = this;
		Logger.LogInfo("Starting Core.");
		
		saveDefaultConfig();
		@SuppressWarnings("unused")
		CoreConfig cfg = new CoreConfig();
		
		//DatabaseManager.Setup();
		RegisterCommands();
		RegisterEvents();
		RegisterOtherStuff();
		
		Logger.LogInfo("Loading Done.");
	}
	
	@Override
	public void onDisable() 
	{
		Logger.LogWarn("Disabling...");
		Logger.LogWarn("Disabled!");
	}
	
	public void RegisterCommands() 
	{
		Manager.AddCommand(new CommandMoney());
		Manager.AddCommand(new CommandPay());
		Manager.AddCommand(new CommandSystem());
		Manager.AddCommand(new CommandReloadConfig());
		Manager.AddCommand(new CommandLocation());
		Manager.AddCommand(new CommandSetExp());
		Manager.AddCommand(new CommandSetMoney());
		Manager.AddCommand(new CommandForce());
		for(CommandExecutor ce: Manager.commands)
		{
			String cname = ce.getClass().getSimpleName();
			cname = ce.getClass().getSimpleName().replaceAll("Command", "").toLowerCase();
			getServer().getPluginCommand(cname).setExecutor(ce);
		}
	}
	
	public void RegisterEvents() 
	{
		Manager.AddEvent(new Announcing());
		Manager.AddEvent(new Basics());
		Manager.AddEvent(new Eastereggs());
		
		Manager.AddEvent(new protect());
		for(Listener event: Manager.events)
		{
			getServer().getPluginManager().registerEvents(event, this);
		}
	}
	
	public void RegisterOtherStuff()
	{
		getServer().getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
			@Override
			public void run() {
				if(!CoreConfig.floorCheck)
					return;
				
				ArrayList<String> mats = CoreConfig.floorCheckBlocks;
				for(Player player : getServer().getOnlinePlayers())
				{
					if(player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR)
						continue;
					
					Block B1, B2;
					B1 = player.getLocation().add(0, -0.7, 0).getBlock();
					B2 = player.getLocation().add(0, -1.5, 0).getBlock();
					for(String mat : mats) {
						Material matt = Material.getMaterial(mat.toUpperCase());
						if( matt!= null) {
							if(B1.getType() == matt || B2.getType() == matt) {
								player.setFireTicks(20 * 2);
							}
						}
					}
				}
			}}, 0, 20*1);
		
		
		getServer().getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
			@Override
			public void run() {
				for(Player player : getServer().getOnlinePlayers())
				{
					if(player.getHealth() <= player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * 0.1)
						player.playSound(player.getLocation(), Sound.BLOCK_CONDUIT_AMBIENT, 0.7f, 1.7f);
				}
			}}, 0, 20*2);
	}
	
	public static Main GetMe() 
	{
		return instance;
	}
}
