package com.rpg.core;

import org.bukkit.event.Listener;

public abstract class Extension implements Listener{
	public Main main;
	public abstract Integer setup();
	public abstract Integer disable();
}
