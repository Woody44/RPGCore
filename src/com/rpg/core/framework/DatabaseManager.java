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
			PreparedStatement sql = con.prepareStatement("UPDATE Gracze SET klasa = ?, KlasaLevel = ? WHERE UUID = ?;");
	        sql.setString(3, UUID);
	        sql.setInt(1, klasa);
	        sql.setInt(2, poziom);
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
			PreparedStatement sql = con.prepareStatement("SELECT Experience FROM Gracze WHERE UUID = ?");
	        sql.setString(1, UUID);
	        ResultSet result = sql.executeQuery();
	        if(result.next())
	        {
	        	int exp = result.getInt(1);
	        	for(int i=0; i < CoreConfig.levels.length; i++)
	        		if(i < CoreConfig.levels.length-1) {
		        		if(exp >= CoreConfig.levels[i] && exp < CoreConfig.levels[i+1])
		        			return i;
		        		else continue;
	        		}
	        		else
	        			return CoreConfig.levels.length -1;
	        }
		}
		catch(SQLException e) {
			e.printStackTrace();
			return 0;		
		}
		return 0;
	}
	
	static public void CreatePlayerInfo(PlayerInfo pi) 
	{
		String sqlq = "INSERT INTO Gracze VALUES (?, ?, ?, ?);";
		try {
			PreparedStatement sql = con.prepareStatement(sqlq);
	        sql.setString(1, pi.UUID);
	        sql.setInt(2, pi.Klasa);
	        sql.setInt(3, pi.KlasaLevel);
	        sql.setInt(4, pi.Experience);
	        sql.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();	
		}
	}
	
	static public PlayerInfo GetPlayerInfo(String UUID) 
	{
		try {
			PreparedStatement sql = con.prepareStatement("SELECT * FROM Inventory WHERE UUID = ?");
	        sql.setString(1, UUID);
	        ResultSet result = sql.executeQuery();
	        if(result.next())
	        {
	        	PlayerInfo pi = new PlayerInfo();
	        	pi.UUID = result.getString(1);
	        	pi.Klasa = result.getInt(2);
	        	pi.KlasaLevel = result.getInt(3);
	        	pi.Experience = result.getInt(4);
	        	return pi;
	        }
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	static public void CreatePlayerInventory(InventoryInfo ii) 
	{
		String sqlq = "INSERT INTO Inventory VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
		try {
			PreparedStatement sql = con.prepareStatement(sqlq);
	        sql.setString(1, ii.UUID);
	        sql.setInt(2, ii.earring_0);
	        sql.setInt(3, ii.earring_1);
	        sql.setInt(4, ii.necklake_0);
	        sql.setInt(5, ii.ring_0);
	        sql.setInt(6, ii.ring_1);
	        sql.setInt(7, ii.bracelet_0);
	        sql.setInt(8, ii.bracelet_1);
	        sql.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();	
		}
	}
	
	static public InventoryInfo GetInventoryInfo(String UUID) 
	{
		try {
			PreparedStatement sql = con.prepareStatement("SELECT * FROM WHERE UUID = ?");
	        sql.setString(1, UUID);
	        ResultSet result = sql.executeQuery();
	        if(result.next())
	        {
	        	InventoryInfo ii = new InventoryInfo();
	        	ii.UUID = result.getString(1);
	        	ii.earring_0= result.getInt(2);
	        	ii.earring_1 = result.getInt(3);
	        	ii.necklake_0 = result.getInt(4);
	        	
	        	ii.ring_0 = result.getInt(5);
	        	ii.ring_1 = result.getInt(6);
	        	ii.bracelet_0 = result.getInt(7);
	        	ii.bracelet_1 = result.getInt(8);
	        	return ii;
	        }
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	static public void RegisterNewPlayer(PlayerInfo pi, InventoryInfo ii, Wallet w) 
	{
		CreatePlayerInfo(pi);
		CreatePlayerInventory(ii);
		AddPlayerWallet(w);
	}
	
}
