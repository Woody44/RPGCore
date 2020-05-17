package com.rpg.core;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ModuleMain extends JavaPlugin implements Listener{
	public abstract void RegisterCommands();
	public abstract void RegisterEvents();
	public abstract void Setup();
}
