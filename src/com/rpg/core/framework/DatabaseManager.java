package com.rpg.core.framework;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.PreparedStatement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.rpg.core.CoreConfig;

public class DatabaseManager
{
	public static Connection con;
	public static MysqlDataSource source = new MysqlDataSource();
	static private String adr, db, usr, pass;
	static private int port;
	
	public static void Setup() 
	{
		try 
		{
			if (con != null && con.isClosed() == false)
				disconnect();
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
		
		Logger.LogInfo("Db Manager", "Loading...");
		adr = CoreConfig.dbhost;
		port = CoreConfig.dbport;
        db = CoreConfig.dbname;
        usr = CoreConfig.dbusr;
        pass = CoreConfig.dbpass;
        
        Logger.LogInfo("Db Manager", "Connecting to Database located at " + adr + "@" + db + "...");
	}
	
	public static void connect()
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException ex) 
		{
			ex.printStackTrace();
		}
		source.setServerName(adr);
		source.setPort(port);
		source.setDatabaseName(db);
		source.setUser(usr);
		source.setPassword(pass);
		source.setUseSSL(false);
		try {
			con = source.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Logger.LogInfo("Db Manager", "Connected!");
	}
	
	public static void disconnect() 
	{
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static int SetPlayerClass(String UUID, int klasa, int poziom)
	{
		connect();
		try 
		{
			PreparedStatement sql = con.prepareStatement("UPDATE Gracze SET klasa = ?, KlasaLevel = ? WHERE UUID = ?;");
	        sql.setString(3, UUID);
	        sql.setInt(1, klasa);
	        sql.setInt(2, poziom);
	        sql.execute();
	        disconnect();
	        return 0;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			disconnect();
			return -1;
		}
	}
	
	public static int GetPlayerClass(String UUID)
	{
		connect();
		try {
			PreparedStatement sql = con.prepareStatement("SELECT Klasa FROM Gracze WHERE UUID = ?");
	        sql.setString(1, UUID);
	        ResultSet result = sql.executeQuery();
	        if(result.next()){
	        	int value = result.getInt(1);
	        	disconnect();
	        	return value;
	        }
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		disconnect();
        return -1;
	}
	
	public static void UpdatePlayerClass(String UUID, int klasa) 
	{
		UpdatePlayerClass(UUID, klasa, false);
	}
	
	public static void UpdatePlayerClass(String UUID, int klasa, boolean additive) 
	{
		connect();
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
			disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
			disconnect();
		}
		
	}
	
	public static Wallet GetPlayerWallet(String UUID)
	{
		connect();
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
	        disconnect();
	        return w;
		}
		catch(SQLException e) {
			e.printStackTrace();
			disconnect();
			return null;		
		}
	}
	
	public static void AddPlayerWallet(Wallet w)
	{
		connect();
		try {
			PreparedStatement sql = con.prepareStatement("INSERT INTO Wallet VALUES (?,?)");
	        sql.setString(1, w.uuid);
	        sql.setInt(2, w.Money);
	        sql.execute();
	        disconnect();
		}
		catch(SQLException e) {
			e.printStackTrace();
			disconnect();
		}
	}
	
	public static void UpdatePlayerWallet(Wallet w)
	{
		connect();
		try {
			PreparedStatement sql = con.prepareStatement("UPDATE Wallet SET Money = ? WHERE UUID = ?");
	        sql.setInt(1, w.Money);
	        sql.setString(2, w.uuid);
	        sql.executeUpdate();
	        disconnect();
		}
		catch(SQLException e) {
			e.printStackTrace();
			disconnect();
		}
	}
	
	public static void UpdatePlayerWallet(String UUID, int money) 
	{
		connect();
		try {
			PreparedStatement sql = con.prepareStatement("UPDATE Wallet SET Money = Money + ? WHERE UUID = ?");
	        sql.setInt(1, money);
	        sql.setString(2, UUID);
	        sql.executeUpdate();
	        disconnect();
		}
		catch(SQLException e) {
			e.printStackTrace();
			disconnect();
		}
	}
	
	public static int GetClassUpgradePrice(int actuallvl) 
	{
		connect();
		if(actuallvl >= 7)
			return 0;
		
		try {
			PreparedStatement sql = con.prepareStatement("SELECT Cena FROM Cennik WHERE Poziom = ?");
	        sql.setInt(1, actuallvl);
	        ResultSet result = sql.executeQuery();
	        if(result.next())
	        {
	        	int value = result.getInt(1);
	        	disconnect();
	        	return value;
	        }
		}
		catch(SQLException e) {
			e.printStackTrace();
			disconnect();
			return 0;		
		}
		
		disconnect();
		return 0;
	}
	
	public static int GetPlayerClassLevel(String UUID) 
	{
		return Integer.parseInt(("" + GetPlayerClass(UUID)).substring(3));
	}
	
	public static int GetPlayerExp(String UUID) 
	{
		connect();
		try {
			PreparedStatement sql = con.prepareStatement("SELECT Experience FROM Gracze WHERE UUID = ?");
	        sql.setString(1, UUID);
	        ResultSet result = sql.executeQuery();
	        if(result.next())
	        {
	        	int exp = result.getInt(1);
	        	disconnect();
	        	return exp;
	        }
		}
		catch(SQLException e) {
			e.printStackTrace();
			disconnect();
			return 0;		
		}
		disconnect();
		return 0;
	}
	
	public static void CreatePlayerInfo(PlayerInfo pi) 
	{
		connect();
		String sqlq = "INSERT INTO Gracze VALUES (?, ?, ?, ?);";
		try {
			PreparedStatement sql = con.prepareStatement(sqlq);
	        sql.setString(1, pi.UUID);
	        sql.setInt(2, pi.Klasa);
	        sql.setInt(3, pi.KlasaLevel);
	        sql.setInt(4, pi.Experience);
	        sql.execute();
	        disconnect();
		}
		catch(SQLException e) {
			e.printStackTrace();
			disconnect();
		}
	}
	
	public static PlayerInfo GetPlayerInfo(String UUID) 
	{
		connect();
		try {
			PreparedStatement sql = con.prepareStatement("SELECT * FROM Gracze WHERE UUID = ?");
	        sql.setString(1, UUID);
	        ResultSet result = sql.executeQuery();
	        if(result.next())
	        {
	        	PlayerInfo pi = new PlayerInfo();
	        	pi.UUID = result.getString(1);
	        	pi.Klasa = result.getInt(2);
	        	pi.KlasaLevel = result.getInt(3);
	        	pi.Experience = result.getInt(4);
	        	pi.wallet = new Wallet();
	        	pi.wallet.Money = 0;
	        	pi.wallet.SetOwner(result.getString(1));
	        	
	        	InventoryInfo ii = GetInventoryInfo(UUID);
	        	if(ii != null)
	        		pi.inventoryInfo = ii;
	        	else
	        	{
	        		pi.inventoryInfo = new InventoryInfo();
	        		pi.inventoryInfo.UUID = UUID;
	        	}
	        	
	        	Wallet w = GetPlayerWallet(UUID);
	        	if(w != null)
	        		pi.wallet = w;
	        	else
	        	{
	        		pi.wallet = new Wallet();
	        		pi.wallet.SetOwner(UUID);
	        		pi.wallet.Money = 0;
	        	}
	        	disconnect();
	        	return pi;
	        }
		}
		catch(SQLException e) {
			e.printStackTrace();
			disconnect();
			return null;
		}
		disconnect();
		return null;
	}
	
	public static void CreatePlayerInventory(InventoryInfo ii) 
	{
		connect();
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
	        disconnect();
		}
		catch(SQLException e) {
			e.printStackTrace();
			disconnect();
		}
	}
	
	public static void UpdateInventoryInfo(InventoryInfo ii) 
	{
		connect();
		try {
			PreparedStatement sql = con.prepareStatement("UPDATE Inventory SET earring_0 = ?, earring_1 = ?, necklake_0 = ?, ring_0 = ?, ring_1 = ?, bracelet_0 = ?, bracelet_1 = ? WHERE UUID = ?");
			sql.setInt(1, ii.earring_0);
			sql.setInt(2, ii.earring_1);
			sql.setInt(3, ii.necklake_0);
			sql.setInt(4, ii.ring_0);
			sql.setInt(5, ii.ring_1);
			sql.setInt(6, ii.bracelet_0);
			sql.setInt(7, ii.bracelet_1);
			sql.setString(8, ii.UUID);
			sql.executeUpdate();
			disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}
	}
	
	public static InventoryInfo GetInventoryInfo(String UUID) 
	{
		connect();
		try {
			PreparedStatement sql = con.prepareStatement("SELECT * FROM Inventory WHERE UUID = ?");
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
	        	disconnect();
	        	return ii;
	        }
		}
		catch(SQLException e) {
			e.printStackTrace();
			disconnect();
			return null;
		}
		disconnect();
		return null;
	}
	
	public static void RegisterNewPlayer(PlayerInfo pi, InventoryInfo ii, Wallet w) 
	{
		CreatePlayerInfo(pi);
		CreatePlayerInventory(ii);
		AddPlayerWallet(w);
	}
	
	public static void UpdatePlayerExp(String UUID, int exp) 
	{
		connect();
		try {
			PreparedStatement sql = con.prepareStatement("UPDATE Gracze SET Experience = ? WHERE UUID = ?");
			sql.setInt(1, exp);
			sql.setString(2, UUID);
			sql.executeUpdate();
			disconnect();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
		}
	}
	
	public static void UpdatePlayer(PlayerInfo cp) 
	{
		DatabaseManager.UpdateInventoryInfo(cp.inventoryInfo);
		DatabaseManager.UpdatePlayerClass(cp.UUID, cp.Klasa);
		DatabaseManager.UpdatePlayerWallet(cp.wallet);
		DatabaseManager.UpdatePlayerExp(cp.UUID, cp.Experience);
	}
	
	public static Location GetLocation(String name) 
	{
		connect();
		try {
			PreparedStatement sql = con.prepareStatement("SELECT * FROM Lokacje WHERE Name = ?");
			sql.setString(1, name);
			ResultSet result = sql.executeQuery();
			
			if(result.next())
	        {
				Location loc = new Location(Bukkit.getWorld(result.getString(1)), result.getDouble(3),
						result.getDouble(4), result.getDouble(5), result.getFloat(6), result.getFloat(7));
				disconnect();
				return loc;
				
	        }
		} catch(SQLException e){ e.printStackTrace(); disconnect(); return null;}
		disconnect();
		 return null;
	}
	
	public static void CreateLocation(String name, Location location) 
	{
		connect();
		try {
			PreparedStatement sql = con.prepareStatement("INSERT INTO Lokacje(Swiat, Nazwa, X, Y, Z, yaw, pitch) VALUES(?, ?, ?, ?, ?, ?, ?)");
			sql.setString(1, location.getWorld().getName());
			sql.setString(2, name);
			sql.setDouble(3, location.getX());
			sql.setDouble(4, location.getY());
			sql.setDouble(5, location.getZ());
			sql.setFloat(6, location.getYaw());
			sql.setFloat(7, location.getPitch());
			sql.execute();
			disconnect();
		} catch(SQLException e){ e.printStackTrace(); disconnect();}
	}
	
	public static void DeleteLocation(String name) 
	{
		connect();
		try {
			PreparedStatement sql = con.prepareStatement("DELETE FROM Lokacje WHERE Nazwa = ?");
			sql.setString(1, name);
			sql.execute();
			disconnect();
		} catch(SQLException e){ e.printStackTrace(); disconnect();}
	}
	
	public static ArrayList<CustomLocation> SyncLocations() 
	{
		connect();
		try 
		{
			ArrayList<CustomLocation> locs = new ArrayList<CustomLocation>();
			PreparedStatement sql = con.prepareStatement("SELECT * FROM Lokacje");
			ResultSet result = sql.executeQuery();
			
			while(result.next())
			{
				locs.add(new CustomLocation(result.getString(3), new Location(Bukkit.getWorld(result.getString(2)), result.getDouble(4), result.getDouble(5), result.getDouble(6), result.getFloat(7), result.getFloat(8))));
			}
			disconnect();
			return locs;
		} 
		catch(SQLException e)
		{ 
			e.printStackTrace(); disconnect(); return null;
		}
	}
}
