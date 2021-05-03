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
import com.woody.core.events.custom.ManaDrainEvent;
import com.woody.core.events.custom.ManaRegenEvent;
import com.woody.core.events.custom.MoneyGainEvent;
import com.woody.core.events.custom.PlayerProfileSaveEvent;
import com.woody.core.events.custom.SkillPointGainEvent;
import com.woody.core.util.FileManager;
import com.woody.core.util.StringManager;

public class Profile {
	private Player player = null;
	public int id;
	private int level;
	private long experience;
	private int skillPoints;
	private int money;
	public ItemStack[] items;
	private HashMap<String, Object> customProperties = new HashMap<>();
	private HashMap<String, Object> tempProperties = new HashMap<>();

	private double baseMaxHealth;
	private double baseMaxMana;
	private double baseDamage;
	public double baseSpeed;

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
		baseSpeed = fc.getDouble("baseSpeed");
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

	public void setBaseDamage(double _value)
	{
		baseDamage = _value;
	}

	public double getBaseDamage()
	{
		return baseDamage;
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
		if(mana == 0)
			return;
		if(mana < 0)
		{
			ManaDrainEvent event = new ManaDrainEvent(player, _value * -1);
			Bukkit.getPluginManager().callEvent(event);
			if(event.isCancelled() || event.getMana() < 0)
				return;
			
			_value = event.getMana() * -1;
		}
		else if(mana > 0)
		{
			ManaRegenEvent event = new ManaRegenEvent(player, _value * manaRegenStrength);
			Bukkit.getPluginManager().callEvent(event);
			if(event.isCancelled() || event.getMana() < 0)
				return;
			_value = event.getMana();
		}
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
		if(experience >= (int)Config.levels.get(level).get("xp") && level < Config.getMaxLevel())
			return true;
		return false;
	}

	public void setSkillPoints(int count)
	{
		skillPoints = count;
	}

	public void addSkillPoints(int count)
	{
		SkillPointGainEvent event = new SkillPointGainEvent(player, count);
		Bukkit.getPluginManager().callEvent(event);
		if(event.isCancelled())
			return;
		skillPoints += event.getSkillPoints();
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
		MoneyGainEvent event = new MoneyGainEvent(player, count);
		Bukkit.getPluginManager().callEvent(event);
		if(event.isCancelled())
			return money;

		money += event.getMoney();
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
		toSave.put("speed", baseSpeed);
		toSave.put("manaRegenStrength", manaRegenStrength);

		toSave.put("inventory", getSavedInventory());
		toSave.put("CustomProperties", customProperties);

		lastLoc = player.getLocation();
		toSave.put("LastLocation", lastLoc);
		
		PlayerProfileSaveEvent event = new PlayerProfileSaveEvent(player, toSave);
		Bukkit.getPluginManager().callEvent(event);
		if(event.isCancelled())
			return;
		
		toSave = event.getData();
		if(toSave != null)
			FileManager.updateConfig("players/" + player.getUniqueId().toString() + "/profiles/" + id + "/profile.yml", toSave);
	}
	
	public void saveAll() 
	{
		saveGeneral();
		save();
	}

	public boolean setProperty(String key, Object value, boolean force) 
	{
		return setProperty(key, value, force, false);
	}
	
	public boolean setProperty(String key, Object value, boolean force, boolean temporary) 
	{
		if(value == null)
		{
			if(temporary){
				if(tempProperties.containsKey(key))
					tempProperties.remove(key);
			}
			else{
				if(customProperties.containsKey(key))
					customProperties.remove(key);
			}
			return true;
		}
		else 
		{
			if(temporary)
			{
				if(!tempProperties.containsKey(key))
				{
					tempProperties.put(key, value);
					return true;
				}
				else
				{
					if(force)
					{
						tempProperties.remove(key);
						tempProperties.put(key, value);
						return true;
					}
					return false;
				}
			}
			else
			{
				if(!customProperties.containsKey(key))
				{
					customProperties.put(key, value);
					return true;
				}
				else
				{
					if(force)
					{
						customProperties.remove(key);
						customProperties.put(key, value);
						return true;
					}
					return false;
				}
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
		return getProperty(key, false);
	}

	public Object getProperty(String key, boolean temporary) 
	{
		return customProperties.get(key);
	}

	public void levelUp() 
	{
		int _level = getLevel();
		long _experience = getExp();
		boolean leveled = false;
		
		LevelUpEvent event = null;
		
		while(canLevelUp())
		{
			if(_level == Config.levels.size())
				break;
			
			_experience -= (int)Config.levels.get(_level).get("xp");
			_level ++;
			
			setLevel(_level);
			experience = _experience;
			skillPoints += (int)Config.levels.get(_level).get("sp");
			
			leveled = true;
			event = new LevelUpEvent(player, _level, _experience);
			Bukkit.getPluginManager().callEvent(event);
		}
		
		if(event == null || event.isCancelled())
			return;
		
		if(leveled)
		{
			player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, ((float)((float)_level / (float)Config.levels.size()) * 2));
		}
		
		if(_level == Config.getMaxLevel())
		{
			Bukkit.broadcastMessage(StringManager.Colorize("&b&lGracz " + player.getDisplayName() + "&r&b osiągnął ostateczny poziom!"));
			for(Player p: Bukkit.getOnlinePlayers()) 
				p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 2f, 0.75f);
		}
	}

	public void updateExpBar() 
	{
		player.setLevel(getLevel());
		
		if(level >= Config.getMaxLevel())
		{
			player.setExp(0);	
			return;
		}

		if((float)(getExp())/(int)Config.levels.get(getLevel()).get("xp") < 1)
			player.setExp((float)(getExp())/(int)Config.levels.get(getLevel()).get("xp"));
		else
			player.setExp(1);
	}

	public void updateFoodBar()
	{
		player.setFoodLevel((int)((mana/baseMaxMana) * 20));
	}
}
