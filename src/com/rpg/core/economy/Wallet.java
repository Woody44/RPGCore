package com.rpg.core.economy;

import com.rpg.core.DatabaseManager;

public class Wallet {
	public String uuid;
	public int Money;
	
	public void SetOwner(String newUUID) 
	{
		uuid = newUUID;
	}
	
	public void AddMoney(int value) 
	{
		Money += value;
		UpdateMoney();
	}
	
	public void UpdateMoney()
	{
		DatabaseManager.SetPlayerWallet(this);
	}
}
