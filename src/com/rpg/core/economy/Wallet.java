package com.rpg.core.economy;

import com.rpg.core.DatabaseManager;

public class Wallet {
	public String uuid;
	public int Money;
	private DatabaseManager dbmg;
	
	public void Setup(DatabaseManager newdbmg) 
	{
		dbmg = newdbmg;
	}
	
	public void AddMoney(int value) 
	{
		Money += value;
		UpdateMoney();
	}
	
	public void UpdateMoney()
	{
		dbmg.SetPlayerWallet(this);
	}
}
