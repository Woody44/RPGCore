package com.rpg.core;

public final class CoreConfig {
	public static boolean announceFirstJoin, announceJoin, announceLeft;
	public static String 
	firstJoinMessage, joinMessage, leftMessage, 
	infoColor, warnColor, errorColor, otherColor,
	currencySymbol,
	chatLowLvlMessage, chatMessageFormat;
	public static Double fallDamageMultiplier;
	public static boolean restrictChat;
	public static int chatLvlMin;
	CoreConfig(Main main)
	{
		announceFirstJoin = main.getConfig().getBoolean("announce.first-join");
		announceJoin = main.getConfig().getBoolean("announce.join");
		announceLeft = main.getConfig().getBoolean("announce.left");
		firstJoinMessage = main.getConfig().getString("announce.first-join-message");
		joinMessage = main.getConfig().getString("announce.join-message");
		leftMessage = main.getConfig().getString("announce.left-message");
		
		currencySymbol = main.getConfig().getString("economy.currency-symbol");
		
		fallDamageMultiplier = main.getConfig().getDouble("fall-damage-multiplier");
		
		restrictChat = main.getConfig().getBoolean("chat.restrict-chat");
		chatLvlMin = main.getConfig().getInt("chat.chat-lvl-min");
		chatLowLvlMessage = main.getConfig().getString("chat.chat-low-lvl-message");
		chatMessageFormat = main.getConfig().getString("chat.chat-message-format");
		infoColor = main.getConfig().getString("chat.info-color");
		warnColor = main.getConfig().getString("chat.warn-color");
		errorColor = main.getConfig().getString("chat.error-color");
		otherColor = main.getConfig().getString("chat.other-color");
	}
	
	public static void logger() 
	{
		Main.LogInfo("announceFirstJoin: " + announceFirstJoin);
		Main.LogInfo("announceJoin: " + announceJoin);
		Main.LogInfo("announceLeft: " + announceLeft);
		Main.LogInfo("LeftMessage: " + leftMessage);
		Main.LogInfo("JoinMessage: " + joinMessage);
		Main.LogInfo("FirstJoinMessage: " + firstJoinMessage);
		Main.LogInfo("CurrencySymbol: " + currencySymbol);
		Main.LogInfo("FallDamageMultiplier: " + fallDamageMultiplier);
		Main.LogInfo("restrictChat: " + restrictChat);
		Main.LogInfo("ChatLvlMin: " + chatLvlMin);
		Main.LogInfo("chatLowLvlMessage: " + chatLowLvlMessage);
		Main.LogInfo("chatMessageFormat: " + chatMessageFormat);
		Main.LogInfo("infoColor: " + infoColor);
		Main.LogInfo("warnColor: " + warnColor);
		Main.LogInfo("errorColor: " + errorColor);
		Main.LogInfo("otherColor: " + otherColor);
	}
}
