package com.woody.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.woody.core.util.FileManager;

public class Config {
	static FileConfiguration config;
	public static boolean announceFirstJoin, announceJoin, announceLeft;
	public static String 
	firstJoinMessage, joinMessage, leftMessage, 
	infoColor, warnColor, errorColor, otherColor, pingColor,
	currencySymbol,
	chatLowLvlMessage, chatMessageFormat;
	public static boolean restrictChat;
	public static int chatLvlMin;
	public static double fallDamageMultiplier, defPlayerSpeed;
	public static float expLose;
	
	public static boolean preventExplosions, dropExplosions;
	public static float explosionsDropRate;
	public static ArrayList<String> preventExplosionsWorlds;
	public static float renewTime;
	
	public static boolean floorCheck;
	public static ArrayList<String> floorCheckBlocks;
	public static boolean allowHeadshots;
	public static double headshotMultiplier;
	public static ArrayList<String> BadWords;
	public static HashMap<Integer, Long> levels;
	
	public static boolean hunger;
	public Config() 
	{
		Reload();
	}
	
	public static void Reload() 
	{
		Main.getInstance().reloadConfig();
		config = Main.getInstance().getConfig();
		announceFirstJoin = config.getBoolean("announce.first-join");
		announceJoin = config.getBoolean("announce.join");
		announceLeft = config.getBoolean("announce.left");
		firstJoinMessage = config.getString("announce.first-join-message");
		joinMessage = config.getString("announce.join-message");
		leftMessage = config.getString("announce.left-message");
		
		currencySymbol = config.getString("economy.currency-symbol");
		
		fallDamageMultiplier = config.getDouble("world.fall-damage-multiplier");
		defPlayerSpeed = config.getDouble("world.default-player-speed");
		hunger = config.getBoolean("world.hunger");
		
		restrictChat = config.getBoolean("chat.restrict");
		chatLvlMin = config.getInt("chat.lvl-min");
		chatLowLvlMessage = config.getString("chat.low-lvl-message");
		chatMessageFormat = config.getString("chat.message-format");
		infoColor = config.getString("chat.info-color");
		warnColor = config.getString("chat.warn-color");
		errorColor = config.getString("chat.error-color");
		otherColor = config.getString("chat.other-color");
		
		preventExplosions = config.getBoolean("protect.explosions.enabled");
		preventExplosionsWorlds = (ArrayList<String>) config.getStringList("protect.explosions.worlds");
		dropExplosions = config.getBoolean("protect.explosions.drop");
		explosionsDropRate = (float)config.getDouble("protect.explosions.drop-rate");
		renewTime = (float)config.getDouble("protect.explosions.time");
		
		floorCheck = (boolean)config.getBoolean("protect.floor-check.enabled");
		floorCheckBlocks = (ArrayList<String>) config.getStringList("protect.floor-check.blocks");
		pingColor = config.getString("chat.ping-color");
		expLose = (float) config.getDouble("world.exp-lose");
		
		
		allowHeadshots = config.getBoolean("combat.allow-headshots");
		headshotMultiplier = config.getDouble("combat.headshot-multiplier");
		
		FileConfiguration badWordsCfg = FileManager.getConfig("badwords.yml");
		BadWords = new ArrayList<String>();
		BadWords.addAll((List<String>) badWordsCfg.getStringList("bad-words"));
		
		levels = new HashMap<>();
		FileConfiguration fc = FileManager.getConfig("levels.yml");
		ConfigurationSection sec = fc.getConfigurationSection("levels");
		for(String key : sec.getKeys(false)){
			Long xp = sec.getLong(key + ".xp");
			levels.put(Integer.parseInt(key), xp);
		}
	}
}
