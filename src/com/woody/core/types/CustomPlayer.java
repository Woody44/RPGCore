package com.woody.core.types;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.woody.core.Config;
import com.woody.core.events.custom.PlayerProfileCreated;
import com.woody.core.events.custom.PlayerProfileSwitch;
import com.woody.core.util.FileManager;

public class CustomPlayer {
	private Profile profile;
	public Player player;
	
	public CustomPlayer(Player _player)
	{
		player = _player;
		createProfile(1);
		loadProfile(1);
		profile.saveAll();
	}
	
	public CustomPlayer(Player _player, int profileId) 
	{
		player = _player;
		if(!isProfileValid(profileId))
			profileId = Integer.parseInt(getProfilesIdsList().get(0));

		loadProfile(profileId);
		profile.levelUp();
	}
	
	public CustomPlayer(String _player, int profileId) 
	{
		player = Bukkit.getPlayer(UUID.fromString(_player));
		if(!isProfileValid(profileId))
			profileId = Integer.parseInt(getProfilesIdsList().get(0));

		loadProfile(profileId);
		profile.levelUp();
	}
	
	public boolean isProfileValid(int id) 
	{
		FileConfiguration fc = FileManager.getConfig("players/" + player.getUniqueId().toString() + "/profiles/" + id + "/profile.yml");
		if(fc==null)
			return false;
		return true;
	}

	public List<String> getProfilesIdsList()
	{
		List<String> toreturn = new ArrayList<>();
		for(File f : FileManager.listFiles("players/" + player.getUniqueId().toString() + "/profiles/"))
		{
			if(f.getName().endsWith(".yml"))
				toreturn.add(f.getName().replace(".yml", ""));
		}

		return toreturn;
	}

	public List<Profile> getProfilesList()
	{
		ArrayList<Profile> toreturn = new ArrayList<>();
		for(File f : FileManager.listFiles("players/" + player.getUniqueId().toString() + "/profiles"))
			toreturn.add(getProfile(Integer.parseInt(f.getName())));

		return toreturn;
	}
	
	private void loadProfile(int profileId) 
	{
		if(!isProfileValid(profileId))
			return;

		profile = getProfile(profileId);
		if(profile.lastLoc != null)
		{
			player.teleport(profile.lastLoc);
		}
		else
		{
			FileConfiguration fc = FileManager.getConfig("spawn.yml");
			Location spawnLoc = null;
			if(fc != null)
				spawnLoc = FileManager.getConfig("spawn.yml").getLocation("location");

			if(spawnLoc != null)
			{
				player.teleport(spawnLoc);
			}
		}
	}
	
	public Profile getProfile() 
	{
		return profile;
	}

	public Profile getProfile(int profileId) 
	{
		Profile prof = new Profile(player, profileId);
		if(prof.player == null)
			return null; 
		else
			return prof;
	}
	
	public void switchProfile(int _profileId) 
	{
		PlayerProfileSwitch event = new PlayerProfileSwitch(player, _profileId);
		Bukkit.getPluginManager().callEvent(event);
		if(event.isCancelled())
			return;
			
		if(profile != null)
			profile.save();
		loadProfile(_profileId);
	}
	
	public void ClearInventory()
	{	
		profile.setInventory(new ItemStack[41]);
		FileManager.updateConfig("players/" + player.getUniqueId().toString() + "/profiles/" + profile.id + "/profile.yml", "inventory", profile.items);
		
		player.getInventory().clear();
	}
	
	public Profile createProfile(int profileId)
	{
		PlayerProfileCreated event = new PlayerProfileCreated(player, profileId);
		Bukkit.getPluginManager().callEvent(event);

		if(event.isCancelled())
			return null;
		
		if(isProfileValid(profileId))
			return null;

		HashMap<String, Object> toSave = new HashMap<>();
		toSave.put("level", 1);
		toSave.put("experience", 0);
		toSave.put("skillPoints", 1);
		toSave.put("money", 0);

		toSave.put("baseMaxHealth", Config.startingHealth);
		toSave.put("health", Config.startingHealth);
		toSave.put("baseMaxMana", 10.0);
		toSave.put("baseDamage", 1.0);
		toSave.put("speed", Config.defPlayerSpeed);
		toSave.put("mana", 10.0);
		toSave.put("manaRegenStrength", 1.0);
		toSave.put("inventory", new ItemStack[41]);

		toSave.put("CustomProperties", new HashMap<String, Object>());
		FileManager.createConfig("players/" + player.getUniqueId().toString() + "/profiles/" + profileId + "/profile.yml", toSave);
		return new Profile(player, profileId);
	}
	
	public FileConfiguration getGeneral()
	{
		return FileManager.getConfig("players/" + player.getUniqueId().toString() + "/player.yml");
	}
}
