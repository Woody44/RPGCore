package com.woody.core.types;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Sound;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import com.woody.core.Config;
import com.woody.core.Main;
import com.woody.core.util.FileManager;
import com.woody.core.util.PlayerManager;
import com.woody.core.util.StringManager;

public class CustomPlayer {
	private int profile;
	private long experience;
	private int level;
	private int money;
	public String uuid;
	public HashMap<String, Object> customProperties = new HashMap<>();
	
	public CustomPlayer(String uuid, int profile) 
	{
		this.uuid = uuid;
		loadProfile(profile);
	}
	
	public int addMoney(int count) 
	{
		money += count;
		return money;
	}
	
	public void setMoney(int count) 
	{
		money = count;
	}
	
	public int getMoney() 
	{
		return money;
	}
	
	public void setExp(long count) 
	{
		experience = count;
		levelUp();
		updateExpBar();
	}
	
	public void addExp(long count) 
	{
		experience += count;
		levelUp();
		updateExpBar();
	}
	
	public long getExp() 
	{
		return experience;
	}
	
	public int getLevel() 
	{
		return level;
	}
	
	public void setLevel(int count) 
	{
		level = count;
		updateExpBar();
	}
	
	public int getActualProfile() 
	{
		return this.profile;
	}
	
	public boolean checkProfile(int id) 
	{
		if(PlayerManager.loadProfile(uuid, id) != null)
			return true;
		else
			return false;
	}
	
	public void changeProfile(int profile) 
	{
		saveProfile();
		loadProfile(profile);
	}
	
	public void saveProfile() 
	{
		PlayerManager.updateProfile(new ProfileInfo(uuid, profile, level, experience, money, Bukkit.getPlayer(UUID.fromString(uuid)).getInventory().getContents()));
	}
	
	public void loadProfile(int profile) 
	{
		this.profile = profile;
		ProfileInfo pi = PlayerManager.loadProfile(uuid, profile);
		if(pi == null)
			return;
		
		this.experience = pi.experience;
		this.level = pi.level;
		this.money = pi.money;
		this.profile = profile;
		Bukkit.getPlayer(UUID.fromString(uuid)).getInventory().setContents(new ItemStack[41]);
		Bukkit.getPlayer(UUID.fromString(uuid)).getInventory().setContents(pi.inventory);
	}
	
	public void updateExpBar() 
	{
		Player player = Bukkit.getPlayer(UUID.fromString(uuid));
		player.setLevel(level);
		
		if((float)(experience)/Config.levels.get(level) < 1)
			player.setExp((float)(experience)/Config.levels.get(level));
		else
			player.setExp(1);
	}
	
	private void levelUp() 
	{
		boolean leveled = false;
		while(experience > Config.levels.get(level))
		{
			if(level == Config.levels.size())
			{break;}
			experience -= Config.levels.get(level);
			level += 1;
			leveled = true;
		}
		if(!leveled)
			return;
		Player player = Bukkit.getPlayer(UUID.fromString(uuid));
		Location loc = player.getLocation();
		if(level < 30)
			player.getWorld().playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 1f, 0.75f);
		if(level == 30)
			player.getWorld().playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 1f, 0.1f);
		if(level >= 31) 
		{
			player.getWorld().playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 1f, 0.8f);
			Bukkit.broadcastMessage(StringManager.Colorize("&b&lGracz &2&l" + player.getDisplayName() + "&b&l Uzyskal poziom &b&l&o&n " + level + " &b&l !!!" ));
			if(level == 50) 
			{
				player.getWorld().strikeLightning(player.getLocation());
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
			        p.playSound(loc, Sound.UI_TOAST_CHALLENGE_COMPLETE, 2f, 1.4f);
				}
			}
		}
	}
	
	public void saveGeneral() 
	{
		HashMap<String, Object> map = new HashMap<>();
		map.put("last-profile", profile);
		map.put("last-nick", Bukkit.getPlayer(UUID.fromString(uuid)));
		if(FileManager.getConfig("players/"+uuid+"/player.yml") != null)
			FileManager.updateConfig("players/"+uuid+"/player.yml", map);
		else
			FileManager.createConfig("players/"+uuid+"/player.yml", map);
	}
	
	public boolean setProperty(String key, Object value) 
	{
		if(!customProperties.containsKey(key))
		{
			customProperties.put(key, value);
			return true;
		}
		else
			return false;
	}
	
	public Object getProperty(String key) 
	{
		return customProperties.get(key);
	}
}
