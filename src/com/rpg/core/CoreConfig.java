package com.rpg.core;

public final class CoreConfig {
	public static boolean announceFirstJoin, announceJoin, announceLeft;
	public static String firstJoinMessage, joinMessage, leftMessage, infoColor, warnColor, errorColor, otherColor;
	public Main main;
	public final void CoreConfig() 
	{
		announceFirstJoin = main.getConfig().getBoolean("core.announce.first-join");
		announceJoin = main.getConfig().getBoolean("core.announce.join");
		announceLeft = main.getConfig().getBoolean("core.announce.left");
		firstJoinMessage = main.getConfig().getString("core.announce.first-join-message");
		joinMessage = main.getConfig().getString("core.announce.join-message");
		leftMessage = main.getConfig().getString("core.announce.left-message");
		infoColor = main.getConfig().getString("core.info-color");
		warnColor = main.getConfig().getString("core.warn-color");
		errorColor = main.getConfig().getString("core.error-color");
		otherColor = main.getConfig().getString("core.other-color");
	}
}
