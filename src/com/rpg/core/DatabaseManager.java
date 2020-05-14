package com.rpg.core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.rpg.core.economy.Wallet;

public class DatabaseManager
{
	static private Connection con;
	static private String adr, db, usr, pass;
	static private int port;
	
	static public void Setup() 
	{
		System.out.println("[RPGcore - Db Manager] Loading...");
		adr = "mysql.titanaxe.com";
		port = 3306;
        db = "srv73958";
        usr = "srv73958";
        pass = "nVJfnvXZ";
        
        try 
        {
        	System.out.println("[RPGcore - Db Manager] Connecting to Database located at " + adr + "@" + db + "...");
        	connect();
        } catch (ClassNotFoundException e) 
        {
        	e.printStackTrace();
        	System.out.println("[RPGcore - Db Manager] Error occured while attempting to connect.");
        }catch(SQLException e) 
        {
        	e.printStackTrace();
        }
	}
	
	static public void connect() throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver");
		MysqlDataSource source = new MysqlDataSource();
		source.setServerName(adr);
		source.setPort(port);
		source.setDatabaseName(db);
		source.setUser(usr);
		source.setPassword(pass);
		
		con = source.getConnection();
		System.out.println("[RPGcore - Db Manager] Connected!");
	}
	
	static public int SetPlayerClass(String UUID, int klasa)
	{
		try 
		{
			PreparedStatement sql = con.prepareStatement("INSERT INTO Klasy VALUES (?, ?)");
	        sql.setString(1, UUID);
	        sql.setInt(2, klasa);
	        sql.execute();
	        return 0;
		} 
		catch (SQLException e) {
			return -1;
		}
	}
	
	static public int GetPlayerClass(String UUID) throws SQLException 
	{
		try {
			PreparedStatement sql = con.prepareStatement("SELECT Klasa FROM Klasy WHERE UUID = ?");
	        sql.setString(1, UUID);
	        ResultSet result = sql.executeQuery();
	        if(result.next()){
	        	return result.getInt(1);
	        }
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
        return -1;
	}
	
	static public void UpdatePlayerClass(String UUID, int klasa) 
	{
		UpdatePlayerClass(UUID, klasa, false);
	}
	
	static public void UpdatePlayerClass(String UUID, int klasa, boolean additive) 
	{
		try {
			PreparedStatement sql;
			if (klasa == 0 && additive == false)
			{
				sql = con.prepareStatement("DELETE FROM Klasy WHERE UUID = ?");
				sql.setString(1, UUID);
			}
			else
			{
				if(additive == true) 
				{
					sql = con.prepareStatement("UPDATE Klasy SET Klasa = Klasa + ? WHERE UUID = ?");
					
					sql.setInt(1, klasa);
					sql.setString(2, UUID);
					
				}
				else
				{
					sql = con.prepareStatement("UPDATE Klasy SET Klasa = ? WHERE UUID = ?");
					
					sql.setInt(1, klasa);
					sql.setString(2, UUID);
				}
						
			}
			
			sql.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	static public Wallet GetPlayerWallet(String UUID)
	{
		try {
			Wallet w = new Wallet();
			PreparedStatement sql = con.prepareStatement("SELECT * FROM Wallet WHERE UUID = ?");
	        sql.setString(1, UUID);
	        ResultSet result = sql.executeQuery();
	        if(result.next())
	        {
	        	w.uuid = result.getString(1);
	        	w.Money = result.getInt(2);
	        }
	        return w;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;		
		}
	}
	
	static public void AddPlayerWallet(Wallet w)
	{
		try {
			PreparedStatement sql = con.prepareStatement("INSERT INTO Wallet VALUES (?,?)");
	        sql.setString(1, w.uuid);
	        sql.setInt(2, w.Money);
	        sql.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();	
		}
	}
	
	static public void SetPlayerWallet(Wallet w)
	{
		Bukkit.broadcastMessage("Passes: " + adr + ":" + port + "@" + db + "#" + " " + pass);
		try {
			PreparedStatement sql = con.prepareStatement("UPDATE Wallet SET Money = ? WHERE UUID = ?");
	        sql.setInt(1, w.Money);
	        sql.setString(2, w.uuid);
	        sql.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();	
		}
	}
}
