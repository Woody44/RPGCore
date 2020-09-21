package com.rpg.core.framework;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import com.rpg.core.CoreConfig;

public class PlayerManager extends FileManager{
	public static HashMap<String, PlayerInfo> OnlinePlayers = new HashMap<>();
	
	public static PlayerInfo GetPlayerProfile(String uuid, int profile) 
	{
		PlayerInfo pi = new PlayerInfo();
		String path = GetPath("player","data", uuid+"/profiles/"+profile);
		FileConfiguration fc = getFileConfig(path);
		
		if(fc == null) 
			return null;
		pi.experience = fc.getLong("experience");
		pi.level = Misc.ExpToLvl(fc.getInt("experience"));
		pi.money = fc.getInt("money");
		
		
		path = GetPath("player","player", uuid);
		fc = getFileConfig(path);
		if(fc == null) 
			return null;
		pi.UUID = fc.getString("uuid");
		pi.name = fc.getString("name");
		pi.profile = fc.getInt("profile");
		return pi;
	}
	
	public static void setExp(String uuid, long exp) 
	{
		updateFile(GetPath("player", "data", uuid+"/profiles/"+OnlinePlayers.get(uuid).profile), "experience", exp);
		OnlinePlayers.get(uuid).experience = exp;
	}
	
	public static void addExp(String uuid, long exp) 
	{
		int lvl = Misc.ExpToLvl(OnlinePlayers.get(uuid).experience);
		int newlvl = Misc.ExpToLvl(OnlinePlayers.get(uuid).experience + exp);
		
		if(lvl < newlvl)
		{
			for (Player p : Bukkit.getOnlinePlayers()) 
			{
				if(p.getUniqueId().toString().equals(uuid))
				{
					LevelUp(p, newlvl);
					break;
				}
			}
		}
		if(newlvl < CoreConfig.levels.size() +1) {
			updateFile(GetPath("player", "data", uuid+"/profiles/"+OnlinePlayers.get(uuid).profile), "experience", OnlinePlayers.get(uuid).experience + exp);
			OnlinePlayers.get(uuid).experience += exp;
		}
		else {
			updateFile(GetPath("player", "data", uuid+"/profiles/"+OnlinePlayers.get(uuid).profile), "experience", CoreConfig.levels.get(CoreConfig.levels.size()-1));
			OnlinePlayers.get(uuid).experience += CoreConfig.levels.get(CoreConfig.levels.size()-1);
		}
	}
	
	public static void setNickname(String uuid, String nick) 
	{
		updateFile(GetPath("player", "data", uuid+"/profiles/"+OnlinePlayers.get(uuid).profile), "nick", nick);
	}
	
	public static void setMoney(String uuid, int money) 
	{
		updateFile(GetPath("player", "data", uuid+"/profiles/"+OnlinePlayers.get(uuid).profile), "money", money);
		OnlinePlayers.get(uuid).money = money;
	}
	
	public static void addMoney(String uuid, int money) 
	{
		updateFile(GetPath("player", "data", uuid+"/profiles/"+OnlinePlayers.get(uuid).profile), "money", GetPlayerProfile(uuid, 1).money + money);
		OnlinePlayers.get(uuid).money += money;
	}
	
	public static void UpdateExpBar(Player player, long exp) 
	{
		int lvl = Misc.ExpToLvl(exp);
		long toNext = 0;
		long toThis = 0;
		
		if(lvl < CoreConfig.levels.size()-1)
			toNext = CoreConfig.levels.get(lvl+1);
		if(lvl > 0)
			toThis = CoreConfig.levels.get(lvl);

		float progress = 0;
		if(toNext != 0)
			progress = (float)((double)(exp - toThis)/(double)(toNext - toThis));
		
		if(progress > 0 && progress < 1.01)
			player.setExp(progress);
		else
			player.setExp(0);
		player.setLevel(lvl);
	}
	
	public static void LevelUp(Player player, int lvl) 
	{
		Location loc = player.getLocation();
		if(lvl < 75)
			player.getWorld().playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 1f, 0.75f);
		if(lvl == 75)
			player.getWorld().playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 1f, 0.1f);
		if(lvl >= 75) 
		{
			player.getWorld().playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 1f, 0.8f);
			Bukkit.broadcastMessage(StringManager.Colorize("&b&lGracz &2&l" + player.getDisplayName() + "&b&l Uzyskal poziom &b&l&o&n " + lvl + " &b&l !!!" ));
			if(lvl == 150) 
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
}
