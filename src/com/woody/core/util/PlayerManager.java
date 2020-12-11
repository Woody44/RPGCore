package com.woody.core.util;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.woody.core.Main;
import com.woody.core.types.CustomPlayer;
import com.woody.core.types.ProfileInfo;

public class PlayerManager {
	
	public static HashMap <Player, CustomPlayer> onlinePlayers = new HashMap<Player, CustomPlayer>();
	public static ProfileInfo loadProfile(String uuid, int profile) 
	{
		FileConfiguration fc = FileManager.getConfig("players/" + uuid + "/profiles/" + profile + "/profile.yml");
		if(fc==null && profile == 1)
			createProfile(new ProfileInfo(uuid));
		
		if(fc == null)
			return null;
		
		ItemStack[] itar = new ItemStack[41];
		@SuppressWarnings("unchecked")
		ArrayList<ItemStack> items = ((ArrayList<ItemStack>)fc.get("inventory"));
		if(items != null)
			return new ProfileInfo(uuid, profile, fc.getInt("level"), fc.getLong("experience"), fc.getInt("money"), items.toArray(itar));
		else
			return new ProfileInfo(uuid, profile, fc.getInt("level"), fc.getLong("experience"), fc.getInt("money"), new ItemStack[41]);
	}
	
	public static void createProfile(ProfileInfo pi) 
	{
		ArrayList<String> keys = new ArrayList<String>();
		ArrayList<Object> values = new ArrayList<Object>();
		keys.add("level");
		values.add(pi.level);
		keys.add("experience");
		values.add(pi.experience);
		keys.add("money");
		values.add(pi.money);
		keys.add("inventory");
		values.add(pi.inventory);
		FileManager.createConfig("players/" + pi.owner + "/profiles/" + pi.id + "/profile.yml", keys, values);
	}
	
	public static void updateProfile(ProfileInfo pi) 
	{
		ArrayList<String> keys = new ArrayList<String>();
		ArrayList<Object> values = new ArrayList<Object>();
		keys.add("level");
		values.add(pi.level);
		keys.add("experience");
		values.add(pi.experience);
		keys.add("money");
		values.add(pi.money);
		keys.add("inventory");
		values.add(pi.inventory);
		Main.instance.getLogger().info("players/" + pi.owner + "/profiles/" + pi.id + "/profile.yml");
		FileManager.updateConfig("players/" + pi.owner + "/profiles/" + pi.id + "/profile.yml", keys, values);
	}
	
	public static void registerOnlinePlayer(Player player)
	{
		int lastProf = 1;
		if(getPlayerGeneral(player.getUniqueId().toString()) != null)
			lastProf = getPlayerGeneral(player.getUniqueId().toString()).getInt("last-profile");
		onlinePlayers.put(player, new CustomPlayer(player.getUniqueId().toString(), lastProf));
	}
	
	public static void unregisterOnlinePlayer(Player player) 
	{
		onlinePlayers.get(player).saveProfile();
		onlinePlayers.get(player).saveGeneral();
		onlinePlayers.remove(player);
		player.getInventory().clear();
	}
	
	public static FileConfiguration getPlayerGeneral(String PlayerUUID) 
	{
		return FileManager.getConfig("players/"+PlayerUUID+"/player.yml");
	}
}
