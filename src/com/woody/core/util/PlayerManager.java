package com.woody.core.util;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.woody.core.Main;
import com.woody.core.types.CustomPlayer;

public class PlayerManager{
	
	private static HashMap <String, CustomPlayer> onlinePlayers = new HashMap<String, CustomPlayer>();
	
	public static CustomPlayer getOnlinePlayer(Player p)
	{
		return onlinePlayers.get(p.getUniqueId().toString());
	}

	public static HashMap<String, CustomPlayer> getOnlinePlayers()
	{
		return onlinePlayers;
	}
	
	public static void registerOnlinePlayer(Player player)
	{
		if(!hasPlayedBefore(player))
			onlinePlayers.put(player.getUniqueId().toString(), createNewPlayer(player));
		else
			onlinePlayers.put(player.getUniqueId().toString(), loadPlayer(player));
	}
	
	public static void unregisterOnlinePlayer(Player player) 
	{
		onlinePlayers.get(player.getUniqueId().toString()).getProfile().saveAll();
		onlinePlayers.remove(player.getUniqueId().toString());
		player.getInventory().clear();
	}

	public static CustomPlayer createNewPlayer(Player _player){
		return new CustomPlayer(_player);
	}

	public static CustomPlayer loadPlayer(Player p){
		return new CustomPlayer(p, getGeneral(p.getUniqueId().toString()).getInt("last-profile"));
	}
	
	public static void HideFromOthers(Player p) 
	 {
		for(Player op : Bukkit.getOnlinePlayers())
		{
			if(op != p)
				op.hidePlayer(Main.instance, p);
		}
	 }
	 
	 public static void ShowToOthers(Player p) 
	 {
		for(Player op : Bukkit.getOnlinePlayers())
		{
			if(op != p)
				op.showPlayer(Main.instance, p);
		}
	 }

	public static boolean isOnline(Player p)
	{
		return onlinePlayers.containsKey(p.getUniqueId().toString());
	}

	public static boolean hasPlayedBefore(Player p)
	{
		return FileManager.checkFileExistence("players/" + p.getUniqueId().toString() + "/");
	}

	public static FileConfiguration getGeneral(String uuid) 
	{
		return FileManager.getConfig("players/" + uuid + "/player.yml");
	}
}
