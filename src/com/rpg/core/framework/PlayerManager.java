package com.rpg.core.framework;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.rpg.core.CoreConfig;

public class PlayerManager {
	
	public static void setExp(String uuid, int exp) 
	{
		FileManager.updateFile("player", uuid, "experience", exp);
	}
	
	public static void addExp(String uuid, int exp) 
	{
		FileManager.updateFile("player", uuid, "experience", getPlayer(uuid).experience + exp);
	}
	
	public static void setNickname(String uuid, String nick) 
	{
		FileManager.updateFile("player", uuid, "nick", nick);
	}
	
	public static void setMoney(String uuid, int money) 
	{
		FileManager.updateFile("player", uuid, "money", money);
	}
	
	public static void addMoney(String uuid, int money) 
	{
		FileManager.updateFile("player", uuid, "money", getPlayer(uuid).money + money);
	}
	
	public static PlayerInfo getPlayer(String uuid)
	{
		PlayerInfo pi = new PlayerInfo();
		FileConfiguration fc = FileManager.getFileConfig("player", uuid);
		if(fc == null) 
			return null;
		pi.UUID = fc.getString("uuid");
		pi.name = fc.getString("name");
		pi.experience = fc.getInt("experience");
		pi.level = Misc.ExpToLvl(fc.getInt("experience"));
		pi.money = fc.getInt("money");
		return pi;
	}
	
	public static void UpdateExpBar(Player player, int exp) 
	{
		int level = Misc.ExpToLvl(exp);
		float exptonext = 0;
		if(level + 1 < CoreConfig.levels.length)
			exptonext = CoreConfig.levels[level+1] - CoreConfig.levels[level];
        float exptonextactual = exp - CoreConfig.levels[level];

		player.setLevel(level);
		if(exptonext != 0)
			player.setExp(exptonextactual / exptonext);
		else
			player.setExp(0);
	}
}
