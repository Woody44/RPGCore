package com.rpg.core;

import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;

public final class CoreConfig {
	static FileConfiguration config;
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
	
	public static boolean preventExplosions, dropExplosions;
	public static float explosionsDropRate;
	public static ArrayList<String> preventExplosionsWorlds;
	
	
	CoreConfig()
	{
		config = Main.GetMe().getConfig();
		LoadConfig();
	}
	
	private static void LoadConfig() 
	{
		announceFirstJoin = config.getBoolean("announce.first-join");
		announceJoin = config.getBoolean("announce.join");
		announceLeft = config.getBoolean("announce.left");
		firstJoinMessage = config.getString("announce.first-join-message");
		joinMessage = config.getString("announce.join-message");
		leftMessage = config.getString("announce.left-message");
		
		currencySymbol = config.getString("economy.currency-symbol");
		
		fallDamageMultiplier = config.getDouble("fall-damage-multiplier");
		levels = new int[config.getInt("levels.count") + 1];
		defPlayerSpeed = config.getDouble("default-player-speed");
		
		restrictChat = config.getBoolean("chat.restrict-chat");
		chatLvlMin = config.getInt("chat.chat-lvl-min");
		chatLowLvlMessage = config.getString("chat.chat-low-lvl-message");
		chatMessageFormat = config.getString("chat.chat-message-format");
		infoColor = config.getString("chat.info-color");
		warnColor = config.getString("chat.warn-color");
		errorColor = config.getString("chat.error-color");
		otherColor = config.getString("chat.other-color");
		
		dbhost = config.getString("database.host");
		dbname = config.getString("database.name");
		dbpass = config.getString("database.password");
		dbusr = config.getString("database.username");
		dbport = config.getInt("database.port");
		
		logChests = config.getBoolean("other.chest-log.enabled");
		chestLoggerFormat = config.getString("other.chest-log.format");
		
		preventExplosions = config.getBoolean("protect.explosions.enabled");
		preventExplosionsWorlds = (ArrayList<String>) config.getStringList("protect.explosions.worlds");
		dropExplosions = config.getBoolean("protect.explosions.drop");
		explosionsDropRate = (float)config.getDouble("protect.explosions.drop-rate");
		for(int i = 0; i < levels.length; i++)
			if(i==0)
				continue;
			else
				levels[i] = config.getInt("levels." + i);
	}
	
	public static void Reload() 
	{
		Main.GetMe().reloadConfig();
		config = Main.GetMe().getConfig();
		LoadConfig();
	}
}
