package com.rpg.core;

import java.util.ArrayList;

public final class CoreConfig {
	Main main;
	public static boolean announceFirstJoin, announceJoin, announceLeft;
	public static String 
	firstJoinMessage, joinMessage, leftMessage, 
	infoColor, warnColor, errorColor, otherColor,
	currencySymbol,
	chatLowLvlMessage, chatMessageFormat, chestLoggerFormat,
	dbhost,dbname,dbusr,dbpass;
	public static double fallDamageMultiplier, defPlayerSpeed;
	public static boolean restrictChat, logChests;
	public static int chatLvlMin, 
	dbport;
	public static int[] levels;
	
	public static boolean preventExplosions;
	public static ArrayList<String> preventExplosionsWorlds;
	
	
	CoreConfig(Main main)
	{
		this.main = main;
		LoadConfig();
	}
	
	private void LoadConfig() 
	{
		announceFirstJoin = main.getConfig().getBoolean("announce.first-join");
		announceJoin = main.getConfig().getBoolean("announce.join");
		announceLeft = main.getConfig().getBoolean("announce.left");
		firstJoinMessage = main.getConfig().getString("announce.first-join-message");
		joinMessage = main.getConfig().getString("announce.join-message");
		leftMessage = main.getConfig().getString("announce.left-message");
		
		currencySymbol = main.getConfig().getString("economy.currency-symbol");
		
		fallDamageMultiplier = main.getConfig().getDouble("fall-damage-multiplier");
		levels = new int[main.getConfig().getInt("levels.count") + 1];
		defPlayerSpeed = main.getConfig().getDouble("default-player-speed");
		
		restrictChat = main.getConfig().getBoolean("chat.restrict-chat");
		chatLvlMin = main.getConfig().getInt("chat.chat-lvl-min");
		chatLowLvlMessage = main.getConfig().getString("chat.chat-low-lvl-message");
		chatMessageFormat = main.getConfig().getString("chat.chat-message-format");
		infoColor = main.getConfig().getString("chat.info-color");
		warnColor = main.getConfig().getString("chat.warn-color");
		errorColor = main.getConfig().getString("chat.error-color");
		otherColor = main.getConfig().getString("chat.other-color");
		
		dbhost = main.getConfig().getString("database.host");
		dbname = main.getConfig().getString("database.name");
		dbpass = main.getConfig().getString("database.password");
		dbusr = main.getConfig().getString("database.username");
		dbport = main.getConfig().getInt("database.port");
		
		logChests = main.getConfig().getBoolean("other.chest-log.enabled");
		chestLoggerFormat = main.getConfig().getString("other.chest-log.format");
		
		preventExplosions = main.getConfig().getBoolean("protect.explosions.enabled");
		preventExplosionsWorlds = (ArrayList<String>) main.getConfig().getStringList("protect.explosions.worlds");
		for(int i = 0; i < levels.length; i++)
			if(i==0)
				continue;
			else
				levels[i] = main.getConfig().getInt("levels." + i);
	}
	
	public void Reload() 
	{
		LoadConfig();
	}
}
