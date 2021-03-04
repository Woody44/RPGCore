package com.woody.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.woody.core.util.FileManager;

public class Config {
	static FileConfiguration config;
	
	public static boolean demo = true;
	
	//Modules Info
	public static boolean 
		announcementsModule, 
		chatModule, 
		combatModule, 
		economyModule, 
		levelingModule, 
		protectionModule, 
		worldModule;
	
	//Announce Module
	public static boolean 
		announceFirstJoin,
		announceJoin,
		announceLeft;
	public static String 
		firstJoinMessage,
		joinMessage, 
		leftMessage;
	
	//Chat Module
	public static int 
		chatLvlMin;
	public static boolean
		restrictChat,
		allowPings,
		censor,
		chatSound,
		colors;
	public static String
		chatLowLvlMessage,
		chatMessageFormat;
	public static ArrayList<String> 
		BadWords;
	
	//Combat Module
	public static boolean 
		allowHeadshots,
		combatLog,
		combatLogVoidItems,
		moneyDrop, moneyItem, removeMoney;
	public static double 
		headshotMultiplier,
		combatLogTime,
		moneyCount,
		moneyPercent,
		suicideMultiplier;
	
	//Commands Module
	public static int
		tpCd,
		teleportationInvulnerability;
			//xd
	
	//Economy Module
	public static String 
		currencySymbol;
	public static int startBalance;
	
	//Leveling Module
	public static HashMap<Integer, HashMap<String, Object>>
		levels;
	public static float 
		expLose;
	public static boolean 
		convertExp;
	
	//Player Module
	public static HashMap<String, Integer> maxProfiles;
	public static double
		hungerMultiplier,
		feedMultiplier,
		defPlayerSpeed,
		startingHealth;
	public static boolean 
		hunger, bedRespawn,
		keepInventory, hungerDamage,
		hungerAsMana;
	
	//Protection Module
	public static boolean 
		preventExplosions,
		dropExplosions,
		itemDropPrevention,
		itemPickupPrevention,
		itemDropTagging;
	public static float 
		explosionsDropRate;
	public static ArrayList<String>
		preventExplosionsWorlds;
	public static float 
		renewTime;
	public static boolean 
		floorCheck;
	public static ArrayList<String> 
		floorCheckBlocks;
	
	//World Module
	public static double 
		fallDamageMultiplier;
	public static boolean 
		mobDrop, 
		mobVanillaExp;
	public static double 
		mobVanillaExpMultiplier;
	public static List<String> respawnWorlds;

	//EasterEggs
	public static boolean easterEggs;
	
	public Config() 
	{
		Reload();
	}
	
	public static void Reload() 
	{
		Main.instance.reloadConfig();
		config = Main.instance.getConfig();
		
	//Modules Info
		worldModule = config.getBoolean("world.enabled");
		levelingModule = config.getBoolean("leveling.enabled");
		combatModule = config.getBoolean("combat.enabled");
		announcementsModule = config.getBoolean("announce.enabled");
		chatModule = config.getBoolean("chat.enabled");
		economyModule = config.getBoolean("economy.enabled");
		protectionModule = config.getBoolean("protect.enabled");
		
	//Announce Module
		if(announcementsModule)
		{
			announceFirstJoin = config.getBoolean("announce.first-join");
			announceJoin = config.getBoolean("announce.join");
			announceLeft = config.getBoolean("announce.left");
			firstJoinMessage = config.getString("announce.first-join-message");
			joinMessage = config.getString("announce.join-message");
			leftMessage = config.getString("announce.left-message");
		}
		
	//Chat Module
		if(chatModule)
		{
			if(levelingModule)
			{
				restrictChat = config.getBoolean("chat.restrict");
				chatLvlMin = config.getInt("chat.lvl-min");
				chatLowLvlMessage = config.getString("chat.low-lvl-message");
			}
			else
				restrictChat = false;
			
			chatMessageFormat = config.getString("chat.message-format");
			
			allowPings = config.getBoolean("chat.allow-pings");
			
			censor = config.getBoolean("chat.censor");
			if(censor) {
				FileConfiguration badWordsCfg = FileManager.getConfig("badwords.yml");
				BadWords = new ArrayList<String>();
				BadWords.addAll((List<String>) badWordsCfg.getStringList("bad-words"));
			}
			
			chatSound = config.getBoolean("chat.sound");
			colors = config.getBoolean("chat.colors");
		}
		
	//Combat Module
		if(combatModule)
		{
			allowHeadshots = config.getBoolean("combat.allow-headshots");
			headshotMultiplier = config.getDouble("combat.headshot-multiplier");
			combatLog = config.getBoolean("combat.combat-log");
			combatLogTime = config.getDouble("combat.combat-log-time");
			if(combatLogTime <= 0.0)
				combatLog = false;
			
			combatLogVoidItems = config.getBoolean("combat.combat-log-items-void");
			moneyDrop = config.getBoolean("combat.money-drop.enabled");
			moneyItem = config.getBoolean("combat.money-drop.item");
			removeMoney = config.getBoolean("combat.money-drop.remove");
			moneyCount = config.getDouble("combat.money-drop.count");
			if(moneyCount < 0.0)
				moneyCount = 0.0;
			
			moneyPercent = config.getDouble("combat.money-drop.percent");
			if(moneyPercent > 1.0)
				moneyPercent = 1.0;
			else
				if(moneyPercent < 0.0)
					moneyPercent = 0.0;
			
			suicideMultiplier = config.getDouble("combat.money-drop.suicide-multiplier");
			if(suicideMultiplier < 0.0)
				suicideMultiplier = 1.0;
		}
		
	//Commands
		tpCd = config.getInt("commands.teleportation-cooldown");
		
	//Economy Module
		if(economyModule)
		{
			currencySymbol = config.getString("economy.currency-symbol");
			startBalance = config.getInt("economy.start-balance");
		}
		
	//Leveling Module
		if(levelingModule)
		{
			levels = new HashMap<>();
			FileConfiguration fc = FileManager.getConfig("levels.yml");
			ConfigurationSection sec = fc.getConfigurationSection("levels");
			for(String key : sec.getKeys(false)){
				ConfigurationSection actualSection = fc.getConfigurationSection("levels." + key);
				levels.put(Integer.parseInt(key), new HashMap<>());
				for(String actualKey : actualSection.getKeys(false)){
					levels.get(Integer.parseInt(key)).put(actualKey, actualSection.get(actualKey));
				}
			}
			
			expLose = (float) config.getDouble("leveling.exp-lose");
			convertExp = config.getBoolean("leveling.convert-exp");
		}
		
	//Player Module
		maxProfiles = new HashMap<>();
		ConfigurationSection maxprofilescs = config.getConfigurationSection("players.max-profiles");
		for(String key : maxprofilescs.getKeys(false))
			maxProfiles.put(key, config.getInt("players.max-profiles." + key));
		
		hunger = config.getBoolean("players.hunger");
		hungerMultiplier = config.getDouble("players.hunger-multiplier");
		feedMultiplier = config.getDouble("players.feed-multiplier");
		defPlayerSpeed = config.getDouble("players.default-player-speed");
		keepInventory = config.getBoolean("players.keep-inventory");
		
		bedRespawn = config.getBoolean("players.bed-respawn");

		hungerDamage = config.getBoolean("players.hunger-damage");
		hungerAsMana = config.getBoolean("players.hunger-as-mana");

		startingHealth = config.getDouble("players.start-health");
	//Protection Module
		if(protectionModule)
		{
			preventExplosions = config.getBoolean("protect.explosions.enabled");
			preventExplosionsWorlds = (ArrayList<String>) config.getStringList("protect.explosions.worlds");
			dropExplosions = config.getBoolean("protect.explosions.drop");
			explosionsDropRate = (float)config.getDouble("protect.explosions.drop-rate");
			renewTime = (float)config.getDouble("protect.explosions.time");
			
			floorCheck = config.getBoolean("protect.floor-check.enabled");
			floorCheckBlocks = (ArrayList<String>) config.getStringList("protect.floor-check.blocks");


			itemDropPrevention = config.getBoolean("protect.item-drop-prevention.enabled");
			if(itemDropPrevention)
				itemDropTagging = config.getBoolean("protect.item-drop-prevention.tag");
			else
				itemDropTagging = false;

			itemPickupPrevention = config.getBoolean("protect.item-pickup-prevention.enabled");
		}
		
	//World Module
		if(worldModule)
		{
			fallDamageMultiplier = config.getDouble("world.fall-damage-multiplier");
			
			mobDrop = config.getBoolean("world.mob-drop");
			mobVanillaExp = config.getBoolean("world.mob-vanilla-exp");
			mobVanillaExpMultiplier = config.getDouble("world.mob-vanilla-exp-multiplier");
			
			respawnWorlds = new ArrayList<>();
			for(World w : Bukkit.getWorlds()) 
				if(config.getStringList("world.respawn-worlds").contains(w.getName()))
					respawnWorlds.add(w.getName());
		}
		
	//EasterEggs
		easterEggs = config.getBoolean("other.easter-eggs");
	}
}