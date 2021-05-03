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

	public static CustomPlayer getOnlinePlayer(String _uuid)
	{
		return onlinePlayers.get(_uuid);
	}

	public static HashMap<String, CustomPlayer> getOnlinePlayers()
	{
		return onlinePlayers;
	}
	
	public static CustomPlayer registerOnlinePlayer(Player player)
	{
		CustomPlayer _toReturn;
		if(!hasPlayedBefore(player))
			_toReturn = createNewPlayer(player);
		else
			_toReturn = loadPlayer(player);
			
		onlinePlayers.put(player.getUniqueId().toString(), _toReturn);
		return _toReturn;
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
		return new CustomPlayer(p);
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

	public static boolean isOnline(String _uuid)
	{
		return onlinePlayers.containsKey(_uuid);
	}

	public static boolean hasPlayedBefore(Player p)
	{
		return FileManager.checkFileExistence("players/" + p.getUniqueId().toString() + "/");
	}

	public static FileConfiguration getGeneral(String uuid) 
	{
		return FileManager.getConfig("players/" + uuid + "/player.yml");
	}

	/*public static HashMap<String, FriendState> GetFriends(String p){
		HashMap<String, FriendState> friends = new HashMap<>();
		FileConfiguration cfg = FileManager.getConfig("players/" + p + "/friends.yml");
		if(cfg == null)
			return null;
		ConfigurationSection sec = cfg.getConfigurationSection("friends");
		if(sec == null)
			return null;
		for (String k : sec.getKeys(false))
			friends.put(k, FriendState.valueOf(sec.getString(k)));
		
		return friends;
	}

	public static FriendState AreFriends(String p1, String p2)
	{
		FileConfiguration cfg = FileManager.getConfig("players/" + p1 + "/friends.yml");
		if(cfg == null)
			return null;
		
		ConfigurationSection sec = cfg.getConfigurationSection("friends");
		if(sec == null)
			return null;
		
		if(sec.contains(p2))
			return FriendState.valueOf(sec.getString(p2));
		else
			return FriendState.None;
		
		/*cfg = FileManager.getConfig("players/" + p2 + "/friends.yml");
		if(cfg == null)
		return FriendState.None;
		
		sec = cfg.getConfigurationSection("friends");
		if(sec == null)
			return FriendState.None;

		if(sec.contains(p1))
			return FriendState.valueOf(sec.getString(p1));
	}

	public static void SetFriendshipState(String p1, String p2, FriendState _state)
	{
		if(AreFriends(p1, p2) != null)
		{

		}
	}*/
}
