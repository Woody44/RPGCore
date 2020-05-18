package com.rpg.core;

import com.rpg.core.framework.Logger;

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
		Logger.LogInfo("announceFirstJoin: " + announceFirstJoin);
		Logger.LogInfo("announceJoin: " + announceJoin);
		Logger.LogInfo("announceLeft: " + announceLeft);
		Logger.LogInfo("LeftMessage: " + leftMessage);
		Logger.LogInfo("JoinMessage: " + joinMessage);
		Logger.LogInfo("FirstJoinMessage: " + firstJoinMessage);
		Logger.LogInfo("CurrencySymbol: " + currencySymbol);
		Logger.LogInfo("FallDamageMultiplier: " + fallDamageMultiplier);
		Logger.LogInfo("restrictChat: " + restrictChat);
		Logger.LogInfo("ChatLvlMin: " + chatLvlMin);
		Logger.LogInfo("chatLowLvlMessage: " + chatLowLvlMessage);
		Logger.LogInfo("chatMessageFormat: " + chatMessageFormat);
		Logger.LogInfo("infoColor: " + infoColor);
		Logger.LogInfo("warnColor: " + warnColor);
		Logger.LogInfo("errorColor: " + errorColor);
		Logger.LogInfo("otherColor: " + otherColor);
	}
}
