package com.woody.core.types;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

import com.woody.core.Config;
import com.woody.core.util.FileManager;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;

public class CustomPlayer {
	public Profile profile;
	public Player player;
	
	public CustomPlayer(Player _player, int profileId) 
	{
		player = _player;
		loadProfile(profileId);
		levelUp();
	}
	
	public CustomPlayer(String _player, int profileId) 
	{
		player = Bukkit.getPlayer(UUID.fromString(_player));
		loadProfile(profileId);
		levelUp();
	}
	
	public void loadProfile(int profileId) 
	{
		Profile prof = null;
		if(profileId == -1)
			prof = PlayerManager.createProfile(player.getUniqueId().toString(), new Profile());
		else
			prof = PlayerManager.getProfile(player.getUniqueId().toString(), profileId);
		
		profile = prof;
		player.getInventory().setContents(new ItemStack[41]);
		player.getInventory().setContents(getInventory());
		updateExpBar();
	}
	
	public Profile getActualProfile() 
	{
		return profile;
	}
	public int getActualProfileId() 
	{
		return profile.id;
	}
	
	public boolean checkProfile(int id) 
	{
		FileConfiguration fc = FileManager.getConfig("players/" + player.getUniqueId().toString() + "/profiles/" + id + "/profile.yml");
		if(fc==null)
			return false;
		return true;
	}
	
	public void changeProfile(int profile) 
	{
		saveProfile();
		loadProfile(profile);
	}
	
	public void updateExpBar() 
	{
		player.setLevel(getLevel());
		
		if((float)(getExp())/Config.levels.get(getLevel()) < 1)
			player.setExp((float)(getExp())/Config.levels.get(getLevel()));
		else
			player.setExp(1);
	}
	
	private void levelUp() 
	{
		boolean leveled = false;
		long experience = getExp();
		int level = getLevel();
		while(experience >= Config.levels.get(level))
		{
			if(level == Config.levels.size())
			{
				break;
			}
			experience -= Config.levels.get(level);
			level ++;
			leveled = true;
		}
		if(!leveled)
			return;
		else
		{
			setLevel(level);
			setExp(experience);
			saveProfile();
		}
		
		Location loc = player.getLocation();
		player.getWorld().playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 1f, ((float)((float)level / (float)Config.levels.size()) * 2));
		if(level >= Config.levels.size() * 0.6) 
		{
			Bukkit.broadcastMessage(StringManager.Colorize("&b&lGracz &2&l" + player.getDisplayName() + "&b&l Uzyskal poziom &b&l&o&n " + level + " &b&l !!!" ));
			if(level == Config.levels.size()) 
			{
				player.getWorld().strikeLightning(player.getLocation().add(new Vector(0,7,0)));
				for(Player p : Bukkit.getOnlinePlayers()) 
				{
			        for(int i = 0; i< 10; i++)
			        {
			        	int j = (int)(Math.random() * 4);
			        	Firework fw = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
			        	FireworkMeta fwm = fw.getFireworkMeta();
			        	switch(j) 
			        	{
			        		case 0:
			    		        fwm.setPower(3);
			    		        fwm.addEffect(FireworkEffect.builder().withColor(Color.FUCHSIA).withFlicker().withTrail().with(Type.BALL_LARGE).withFade(Color.PURPLE).build());
			    		        fw.setFireworkMeta(fwm);
			    		        break;
			        		case 1:
			    		        fwm.setPower(3);
			    		        fwm.addEffect(FireworkEffect.builder().withColor(Color.GREEN).withFlicker().withTrail().with(Type.CREEPER).build());
			    		        fw.setFireworkMeta(fwm);
			    		        break;
			        		case 2:
			        			fwm.setPower(3);
				    		    fwm.addEffect(FireworkEffect.builder().withColor(Color.NAVY).withFlicker().withTrail().with(Type.STAR).withFade(Color.BLUE, Color.SILVER).build());
				    		    fw.setFireworkMeta(fwm);
				    		    break;
			        		case 3:
			        			fwm.setPower(3);
				    		    fwm.addEffect(FireworkEffect.builder().withColor(Color.ORANGE).withFlicker().withTrail().with(Type.BALL).build());
				    		    fw.setFireworkMeta(fwm);
				    		    break;
			        		case 4:
			        			fwm.setPower(3);
				    		    fwm.addEffect(FireworkEffect.builder().withColor(Color.WHITE).withFlicker().withTrail().with(Type.BURST).build());
				    		    fw.setFireworkMeta(fwm);
				    		    break;
			        		default:
			        			continue;
			        	}
			        }
			        p.playSound(loc, Sound.UI_TOAST_CHALLENGE_COMPLETE, 2f, 0.8f);
				}
			}
		}
	}
	
	public void saveGeneral() 
	{
		HashMap<String, Object> map = new HashMap<>();
		map.put("last-profile", profile.id);
		map.put("last-nick", player.getDisplayName());
		if(FileManager.getConfig("players/" + player.getUniqueId().toString() + "/player.yml") != null)
			FileManager.updateConfig("players/" + player.getUniqueId().toString()  + "/player.yml", map);
		else
			FileManager.createConfig("players/" + player.getUniqueId().toString()  + "/player.yml", map);
	}
	
	public void saveProfile() 
	{
		setInventory(player.getInventory().getContents());
		HashMap<String, Object> toSave = new HashMap<>();
		toSave.put("level", getLevel());
		toSave.put("experience", getExp());
		toSave.put("money", getMoney());
		toSave.put("inventory", getInventory());
		toSave.put("CustomProperties", profile.customProperties);
		FileManager.updateConfig("players/" + player.getUniqueId().toString() + "/profiles/" + profile.id + "/profile.yml", toSave);
	}
	
	public void ClearInventory()
	{	
		setInventory(new ItemStack[41]);
		FileManager.updateConfig("players/" + player.getUniqueId().toString() + "/profiles/" + profile.id + "/profile.yml", "inventory", profile.items);
	}
	
	public void saveAll() 
	{
		saveGeneral();
		saveProfile();
	}
	
	public int addMoney(int count) 
	{
		profile.money += count;
		saveProfile();
		return profile.money;
	}
	
	public void setMoney(int count) 
	{
		profile.money = count;
		saveProfile();
	}
	
	public int getMoney() 
	{
		return profile.money;
	}
	
	public void setExp(long count) 
	{
		profile.experience = count;
		levelUp();
		updateExpBar();
	}
	
	public void addExp(long count) 
	{
		profile.experience += count;
		levelUp();
		updateExpBar();
	}
	
	public long getExp() 
	{
		return profile.experience;
	}
	
	public int getLevel() 
	{
		return profile.level;
	}
	
	public void setLevel(int count) 
	{
		profile.level = count;
		updateExpBar();
	}
	
	public boolean setProperty(String key, Object value, boolean force) 
	{
		if(value == null)
		{
			if(profile.customProperties.containsKey(key))
				profile.customProperties.remove(key);
			saveProfile();
			return true;
		}
		else 
		{
			if(!profile.customProperties.containsKey(key))
			{
				profile.customProperties.put(key, value);
				saveProfile();
				return true;
			}
			else
			{
				if(force)
				{
					profile.customProperties.remove(key);
					profile.customProperties.put(key, value);
					saveProfile();
					return true;
				}
				return false;
			}
		}
	}
	
	public Object getProperty(String key) 
	{
		return profile.customProperties.get(key);
	}
	
	public ItemStack[] getInventory() 
	{
		return profile.items;
	}
	
	public void setInventory(ItemStack[] i) 
	{
		profile.items = i;
	}
	
	public FileConfiguration getPlayerGeneral() 
	{
		return FileManager.getConfig("players/" + player.getUniqueId().toString() + "/player.yml");
	}
}
