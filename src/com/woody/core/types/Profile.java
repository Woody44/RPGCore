package com.woody.core.types;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.woody.core.Config;
import com.woody.core.events.custom.ExperienceGainEvent;
import com.woody.core.events.custom.LevelUpEvent;
import com.woody.core.util.FileManager;
import com.woody.core.util.StringManager;

public class Profile {
	public Player player = null;
	public int id;
	private int level;
	private long experience;
	private int skillPoints;
	private int money;
	public ItemStack[] items;
	private HashMap<String, Object> customProperties = new HashMap<>();

	private double baseMaxHealth;
	private double baseMaxMana;
	public double baseDamage;
	public double speed;

	private double health;
	private double mana;

	public double manaRegenStrength;

	public Location lastLoc;

	public Profile(Player _player, int _id) 
	{
		player = _player;
		FileConfiguration fc = FileManager.getConfig("players/" + player.getUniqueId().toString() + "/profiles/" + _id+ "/profile.yml");

		id = _id;
		level = fc.getInt("level");
		experience = fc.getLong("experience");
		skillPoints = fc.getInt("skillPoints");
		money = fc.getInt("money");
		ItemStack[] itar = new ItemStack[41];

		baseMaxHealth = fc.getDouble("baseMaxHealth");
		baseMaxMana = fc.getDouble("baseMaxMana");
		baseDamage = fc.getDouble("baseDamage");
		speed = fc.getDouble("baseSpeed");
		mana = fc.getDouble("mana");
		health = fc.getDouble("health");
		manaRegenStrength = fc.getDouble("manaRegenStrength");

		lastLoc = fc.getLocation("LastLocation");
		@SuppressWarnings("unchecked")
		ArrayList<ItemStack> _items = ((ArrayList<ItemStack>)fc.get("inventory"));
		items = _items.toArray(itar);

		ConfigurationSection cs = fc.getConfigurationSection("CustomProperties");
		if(cs !=null)
			for(String key : cs.getKeys(false))
				customProperties.put(key, cs.get(key));
	}

	public void setMaxBaseHealth(double _value)
	{
		if(_value <= 0)
			return;

		baseMaxHealth = _value;
		player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(baseMaxHealth);
	}

	public double addMaxBaseHealth(double _value)
	{
		if(_value <= 0)
			return baseMaxHealth;

		baseMaxHealth += _value;
		player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(baseMaxHealth);
		return baseMaxHealth;
	}

	public double getMaxBaseHealth()
	{
		return baseMaxHealth;
	}

	public double getHealth()
	{
		return health;
	}

	public void setMaxBaseMana(double _value)
	{
		if(_value < 0)
			return;
		baseMaxMana = _value;
		updateFoodBar();
	}

	public void addMaxBaseMana(double _value)
	{
		if(_value <= 0)
			return;
		baseMaxMana += _value;
		updateFoodBar();
	}

	public double getMaxBaseMana()
	{
		return baseMaxMana;
	}

	public void setMana(double _value)
	{
		mana = _value;
	}
	
	public void addMana(double _value)
	{
		mana += _value;
		if(mana < 0)
			mana = 0;
		if(mana > baseMaxMana)
			mana = baseMaxMana;

		updateFoodBar();
	}

	public double getMana()
	{
		return mana;
	}

	public void setLevel(int count) 
	{
		level = count;
		updateExpBar();
	}

	public int getLevel() 
	{
		return level;
	}

	public void setExp(long count) 
	{
		experience = count;
		levelUp();
		updateExpBar();
	}
	
	public void addExp(long count) 
	{
		ExperienceGainEvent event = new ExperienceGainEvent(player, count);
		Bukkit.getPluginManager().callEvent(event);
		if(event.isCancelled())
			return;
		
		experience += event.getExp();
		levelUp();
		updateExpBar();
	}
	
	public long getExp() 
	{
		return experience;
	}
	
	private boolean canLevelUp() 
	{
		int level = getLevel();
		long experience = getExp();
		if(experience >= (int)Config.levels.get(level).get("xp"))
			return true;
		return false;
	}

	public void setSkillPoints(int count)
	{
		skillPoints = count;
	}

	public void addSkillPoints(int count)
	{
		skillPoints += count;
	}

	public int getSkillPoints()
	{
		return skillPoints;
	}

	public void setMoney(int count) 
	{
		money = count;
	}

	public int addMoney(int count) 
	{
		money += count;
		return money;
	}

	public int getMoney() 
	{
		return money;
	}
	
	public void saveGeneral() 
	{
		HashMap<String, Object> map = new HashMap<>();
		map.put("last-profile", id);
		map.put("last-nick", player.getDisplayName());
		map.put("last-ipv4", player.getAddress().getHostString());
		if(FileManager.getConfig("players/" + player.getUniqueId().toString() + "/player.yml") != null)
			FileManager.updateConfig("players/" + player.getUniqueId().toString()  + "/player.yml", map);
		else
			FileManager.createConfig("players/" + player.getUniqueId().toString()  + "/player.yml", map);
	}
	
	public void save() 
	{
		setInventory(player.getInventory().getContents());
		HashMap<String, Object> toSave = new HashMap<>();
		toSave.put("level", getLevel());
		toSave.put("experience", getExp());
		toSave.put("skillPoints", skillPoints);
		toSave.put("money", getMoney());

		toSave.put("baseMaxHealth", baseMaxHealth);

		health = player.getHealth();
		toSave.put("health", health);
		toSave.put("baseMaxMana", baseMaxMana);
		toSave.put("mana", mana);
		toSave.put("baseDamage", baseDamage);
		toSave.put("speed", speed);
		toSave.put("manaRegenStrength", manaRegenStrength);

		toSave.put("inventory", getSavedInventory());
		toSave.put("CustomProperties", customProperties);

		lastLoc = player.getLocation();
		toSave.put("LastLocation", lastLoc);
		
		FileManager.updateConfig("players/" + player.getUniqueId().toString() + "/profiles/" + id + "/profile.yml", toSave);
	}
	
	public void saveAll() 
	{
		saveGeneral();
		save();
	}
	
	public boolean setProperty(String key, Object value, boolean force) 
	{
		if(value == null)
		{
			if(customProperties.containsKey(key))
				customProperties.remove(key);
			save();
			return true;
		}
		else 
		{
			if(!customProperties.containsKey(key))
			{
				customProperties.put(key, value);
				save();
				return true;
			}
			else
			{
				if(force)
				{
					customProperties.remove(key);
					customProperties.put(key, value);
					save();
					return true;
				}
				return false;
			}
		}
	}

	public ItemStack[] getSavedInventory() 
	{
		return items;
	}
	
	public void setInventory(ItemStack[] i) 
	{
		items = i;
	}
	
	public Object getProperty(String key) 
	{
		return customProperties.get(key);
	}

	public void levelUp() 
	{
		int level = getLevel();
		long _experience = getExp();
		boolean leveled = false;
		
		LevelUpEvent event = null;
		
		while(canLevelUp())
		{
			if(level == Config.levels.size())
				break;
			
			_experience -= (int)Config.levels.get(level).get("xp");
			level ++;
			
			setLevel(level);
			experience = _experience;
			skillPoints += (int)Config.levels.get(level).get("sp");
			save();
			
			leveled = true;
			
			event = new LevelUpEvent(player, level, _experience);
			Bukkit.getPluginManager().callEvent(event);
		}
		
		if(event == null || event.isCancelled())
			return;
		
		if(leveled)
		{
			player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, ((float)((float)level / (float)Config.levels.size()) * 2));
		}
		
		if(level == Config.levels.size())
		{
			Bukkit.broadcastMessage(StringManager.Colorize("&b&lGracz " + player.getDisplayName() + "&r&b osiągnął ostateczny poziom!"));
			for(Player p: Bukkit.getOnlinePlayers()) 
				p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 2f, 0.75f);
		}
	}

	public void updateExpBar() 
	{
		player.setLevel(getLevel());
		
		if((float)(getExp())/(int)Config.levels.get(getLevel()).get("xp") < 1)
			player.setExp((float)(getExp())/(int)Config.levels.get(getLevel()).get("xp"));
		else
			player.setExp(1);
	}

	public void updateFoodBar()
	{
		player.setFoodLevel((int)((mana/baseMaxMana) * 20));
	}

	public void RegenMana(double _value)
	{
		addMana(_value * manaRegenStrength);
	}
}
