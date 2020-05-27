package com.rpg.core.framework;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.PreparedStatement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.rpg.core.CoreConfig;

public class DatabaseManager
{
	public static Connection con;
	static private String adr, db, usr, pass;
	static private int port;
	
	static public void Setup() 
	{
		try {
		if (con != null && con.isClosed() == false)
				con.close();
		
		}catch(SQLException e) {
			e.printStackTrace();
		}
		Logger.LogInfo("Db Manager", "Loading...");
		adr = CoreConfig.dbhost;
		port = CoreConfig.dbport;
        db = CoreConfig.dbname;
        usr = CoreConfig.dbusr;
        pass = CoreConfig.dbpass;
        
        try 
        {
        	Logger.LogInfo("Db Manager", "Connecting to Database located at " + adr + "@" + db + "...");
        	connect();
        } catch (ClassNotFoundException e) 
        {
        	e.printStackTrace();
        	Logger.LogError("Db Manager", "Error occured while attempting to connect.");
        	Logger.LogError("Core", "PLUGIN MAY BE BROKEN BECAUSE CAN'T CONNECT TO SPECIFIED DATABASE");
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
		Logger.LogInfo("Db Manager", "Connected!");
	}
	
	static public int SetPlayerClass(String UUID, int klasa, int poziom)
	{
		try 
		{
			PreparedStatement sql = con.prepareStatement("INSERT INTO Gracze VALUES (?, ?, ?)");
	        sql.setString(1, UUID);
	        sql.setInt(2, klasa);
	        sql.setInt(3, poziom);
	        sql.execute();
	        return 0;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	static public int GetPlayerClass(String UUID)
	{
		try {
			PreparedStatement sql = con.prepareStatement("SELECT Klasa FROM Gracze WHERE UUID = ?");
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
				sql = con.prepareStatement("DELETE FROM Gracze WHERE UUID = ?");
				sql.setString(1, UUID);
			}
			else
			{
				if(additive == true) 
				{
					sql = con.prepareStatement("UPDATE Gracze SET Klasa = Klasa + ? WHERE UUID = ?");
					
					sql.setInt(1, klasa);
					sql.setString(2, UUID);
					
				}
				else
				{
					sql = con.prepareStatement("UPDATE Gracze SET Klasa = ? WHERE UUID = ?");
					
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
	
	static public int GetClassUpgradePrice(int actuallvl) 
	{
		if(actuallvl >= 7)
			return 0;
		
		try {
			PreparedStatement sql = con.prepareStatement("SELECT Cena FROM Cennik WHERE Poziom = ?");
	        sql.setInt(1, actuallvl);
	        ResultSet result = sql.executeQuery();
	        if(result.next())
	        {
	        	return result.getInt(1);
	        }
		}
		catch(SQLException e) {
			e.printStackTrace();
			return 0;		
		}
		return 0;
	}
	
	static public int GetPlayerClassLevel(String UUID) 
	{
		return Integer.parseInt(("" + GetPlayerClass(UUID)).substring(3));
	}
	
	static public int GetPlayerLevel(String UUID) 
	{
		try {
			PreparedStatement sql = con.prepareStatement("SELECT Level FROM Gracze WHERE UUID = ?");
	        sql.setString(1, UUID);
	        ResultSet result = sql.executeQuery();
	        if(result.next())
	        {
	        	return result.getInt(1);
	        }
		}
		catch(SQLException e) {
			e.printStackTrace();
			return 0;		
		}
		return 0;
	}
}
