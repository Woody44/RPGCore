package com.woody.core.types;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.woody.core.Config;
import com.woody.core.GLOBALVARIABLES;
import com.woody.core.events.custom.PlayerProfileCreationEvent;
import com.woody.core.events.custom.PlayerProfileLoadedEvent;
import com.woody.core.events.custom.PlayerProfileSwitchEvent;
import com.woody.core.events.custom.PlayerSendFriendRequest;
import com.woody.core.util.FileManager;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;

public class CustomPlayer {
	private Profile profile;
	public Player player;
	public FriendsList friends;
	public class FriendRelation
	{
		String p, last;
		FriendState state;
		public FriendRelation(String _p, FriendState _state, String _last)
		{
			p = _p;
			state = _state;
			last = _last;
		}
	}

	public class FriendsList
	{
		public HashMap<String, FriendRelation> friendsMap = new HashMap<>();
		
		public boolean Insert(FriendRelation fr, boolean force)
		{
			if(friendsMap.containsKey(fr.p))
			{
				if(!force)
					return false;
				else
				{
					friendsMap.replace(fr.p, fr);
					return true;
				}
			}
			else
			{
				friendsMap.put(fr.p, fr);
				return true;
			}
		}

		public void Invite(String _uuid)
		{
			if(friendsMap.containsKey(_uuid))
			{
				FriendRelation rell = friendsMap.get(_uuid);
				if(rell.state == FriendState.Blocked && !rell.last.contentEquals(player.getUniqueId().toString()))
					player.sendMessage(StringManager.Colorize(GLOBALVARIABLES.ERROR_PREFIX + "You can not invite this player."));
				else if (friendsMap.get(_uuid).state == FriendState.Pending && rell.last.contentEquals(player.getUniqueId().toString()))
					player.sendMessage(StringManager.Colorize(GLOBALVARIABLES.ERROR_PREFIX + "You have already invited this player."));
				else if (friendsMap.get(_uuid).state == FriendState.Pending && !rell.last.contentEquals(player.getUniqueId().toString()))
					Accept();
			}

			PlayerSendFriendRequest event = new PlayerSendFriendRequest(player.getUniqueId().toString(), _uuid);
			Bukkit.getPluginManager().callEvent(event);

			if(event.isCancelled())
				return;

			friendsMap.put(_uuid, new FriendRelation(_uuid, FriendState.Pending, player.getUniqueId().toString()));
			if(PlayerManager.isOnline(_uuid))
			{
				PlayerManager.getOnlinePlayer(_uuid).friends.Insert(new FriendRelation(_uuid, FriendState.Pending, player.getUniqueId().toString()), false);
				PlayerManager.getOnlinePlayer(_uuid).player.sendMessage(StringManager.Colorize(GLOBALVARIABLES.SYSTEM_PREFIX + "You've got friend request from &c" + player.getDisplayName() + "&6."));
			}
			else
			{
				FileConfiguration fc = FileManager.getConfig("players/" + _uuid + "/friends.yml");
				if(fc == null)
				{
					FileManager.createFile("players/" + _uuid+ "/friends.yml");
					return;
				}

				if(fc.contains(player.getUniqueId().toString()))
				{

				}
			}
		}
		public void Accept(){}
		public void Block(){}
		public void Get(){}
		public boolean IsFriend(){return false;} 
		public void Load()
		{
			FileConfiguration fc = FileManager.getConfig("players/" + player.getUniqueId().toString() + "/friends.yml");
			if(fc == null)
			{
				FileManager.createFile("players/" + player.getUniqueId().toString() + "/friends.yml");
				return;
			}

			for(String key : fc.getKeys(false))
			{
				String _last = fc.getString("last");
				FriendState _state = FriendState.valueOf(fc.getString("state"));
				friendsMap.put(key, new FriendRelation(key, _state, _last));
			}
		}
	}
	//
	
	public CustomPlayer(Player _player) 
	{
		player = _player;
	}
	
	public CustomPlayer(String _player, int profileId) 
	{
		player = Bukkit.getPlayer(UUID.fromString(_player));
		if(!isProfileValid(profileId))
			profileId = Integer.parseInt(getProfilesIdsList().get(0));
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
	
	public void loadProfile(int profileId) 
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

		profile.levelUp();
		player.getInventory().setContents(new ItemStack[41]);
		player.getInventory().setContents(profile.getSavedInventory());
		player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(profile.getMaxBaseHealth());
		player.setHealth(profile.getHealth());
		profile.updateFoodBar();
		profile.updateExpBar();

		PlayerProfileLoadedEvent event = new PlayerProfileLoadedEvent(player, profile.id);
		Bukkit.getPluginManager().callEvent(event);
	}
	
	public Profile getProfile() 
	{
		return profile;
	}

	public Profile getProfile(int profileId) 
	{
		Profile prof = new Profile(player, profileId);
		return prof;
	}
	
	public void switchProfile(int _profileId) 
	{
		PlayerProfileSwitchEvent event;
		if(profile == null)
			event = new PlayerProfileSwitchEvent(player, _profileId, -1);
		else
			event = new PlayerProfileSwitchEvent(player, _profileId, profile.id);
		
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
		PlayerProfileCreationEvent event = new PlayerProfileCreationEvent(player, profileId);
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
