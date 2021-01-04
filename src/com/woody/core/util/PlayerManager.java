package com.woody.core.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.woody.core.Main;
import com.woody.core.types.CustomPlayer;
import com.woody.core.types.Profile;

public class PlayerManager{
	
	public static HashMap <Player, CustomPlayer> onlinePlayers = new HashMap<Player, CustomPlayer>();
	
	public static CustomPlayer getPlayer(Player p) 
	{
		if(onlinePlayers.containsKey(p))
			return onlinePlayers.get(p);
		return getPlayer(p.getUniqueId().toString());
	}
	
	public static CustomPlayer getPlayer(String uuid) 
	{
		int lastProf = -1;
		if(getPlayerGeneral(uuid) != null)
			lastProf = getPlayerGeneral(uuid).getInt("last-profile");
		return new CustomPlayer(uuid, lastProf);
	}
	
	public static Profile getProfile(String uuid, int profileId) 
	{
		FileConfiguration fc = FileManager.getConfig("players/" + uuid + "/profiles/" + profileId + "/profile.yml");
		if(fc==null)
			return null;
		
		ItemStack[] itar = new ItemStack[41];
		@SuppressWarnings("unchecked")
		ArrayList<ItemStack> items = ((ArrayList<ItemStack>)fc.get("inventory"));
		HashMap<String, Object> customProperties = new  HashMap<>();
		
		ConfigurationSection cs = fc.getConfigurationSection("CustomProperties");
		for(String key : cs.getKeys(false))
			customProperties.put(key, cs.get(key));
		
		if(items != null)
			return new Profile(profileId, fc.getInt("level"), fc.getLong("experience"), fc.getInt("money"), items.toArray(itar), customProperties);
		else
			return new Profile(profileId, fc.getInt("level"), fc.getLong("experience"), fc.getInt("money"), itar, customProperties);
	}
	
	public static Profile createProfile(String uuid, Profile profile)
	{
		ArrayList<String> keys = new ArrayList<String>();
		ArrayList<Object> values = new ArrayList<Object>();
		keys.add("level");
		values.add(profile.level);
		keys.add("experience");
		values.add(profile.experience);
		keys.add("money");
		values.add(profile.money);
		keys.add("inventory");
		values.add(profile.items);
		FileManager.createConfig("players/" + uuid + "/profiles/" + profile.id + "/profile.yml", keys, values);
		return profile;
	}
	
	public static void registerOnlinePlayer(Player player)
	{
		onlinePlayers.put(player, getPlayer(player));
	}
	
	public static void unregisterOnlinePlayer(Player player) 
	{
		onlinePlayers.get(player).saveAll();
		onlinePlayers.remove(player);
		player.getInventory().clear();
	}
	
	public static void HideFromOthers(Player p) 
	 {
		 for(Player op : Bukkit.getOnlinePlayers())
		 {
			 if(op != p)
				 op.hidePlayer(Main.getInstance(), p);
		 }
	 }
	 
	 public static void ShowToOthers(Player p) 
	 {
		 for(Player op : Bukkit.getOnlinePlayers())
		 {
			 if(op != p)
				 op.showPlayer(Main.getInstance(), p);
		 }
	 }
	 
	 public static FileConfiguration getPlayerGeneral(String uuid) 
		{
			return FileManager.getConfig("players/" + uuid + "/player.yml");
		}
}
