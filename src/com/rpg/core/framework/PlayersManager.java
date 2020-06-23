package com.rpg.core.framework;

import java.util.ArrayList;

public class PlayersManager {
	public static ArrayList<CustomPlayer> Players = new ArrayList<CustomPlayer>();
	public static void RegisterPlayer(CustomPlayer cp) 
	{
		Players.add(cp);
	}
	
	public static void UnregisterPlayer(String UUID) 
	{
		for(CustomPlayer cp : Players)
			if(cp.player.getUniqueId().toString() == UUID)
				Players.remove(cp);
	}
	
	public static CustomPlayer GetOnlinePlayer(String UUID) 
	{
		for(CustomPlayer cp : Players) 
		{
			if(cp.UUID == UUID)
			{
				return cp;
			}
		}
		
		return null;
	}
	
	public static void GetPlayerUpdate(CustomPlayer cp) 
	{
		cp = DatabaseManager.GetPlayerInfo(cp.UUID);
	}
	
	public static void SendPlayerUpdate(CustomPlayer cp) 
	{
		DatabaseManager.UpdatePlayer(cp);
	}
}
