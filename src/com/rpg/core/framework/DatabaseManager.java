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
	static private String adr, db, usr, pass;
	static private int port;
	
	public static void Setup() 
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
	
	public static void connect() throws ClassNotFoundException, SQLException
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
	
	public static int SetPlayerClass(String UUID, int klasa, int poziom)
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
	
	public static int GetPlayerClass(String UUID)
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
	
	public static void UpdatePlayerClass(String UUID, int klasa) 
	{
		UpdatePlayerClass(UUID, klasa, false);
	}
	
	public static void UpdatePlayerClass(String UUID, int klasa, boolean additive) 
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
	
	public static Wallet GetPlayerWallet(String UUID)
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
	
	public static void AddPlayerWallet(Wallet w)
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
	
	public static void UpdatePlayerWallet(Wallet w)
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
	
	public static int GetClassUpgradePrice(int actuallvl) 
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
	
	public static int GetPlayerClassLevel(String UUID) 
	{
		return Integer.parseInt(("" + GetPlayerClass(UUID)).substring(3));
	}
	
	public static int GetPlayerLevel(String UUID) 
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
	
	public static void CreatePlayerInfo(CustomPlayer pi) 
	{
		String sqlq = "INSERT INTO Gracze VALUES (?, ?, ?, ?);";
		try {
			PreparedStatement sql = con.prepareStatement(sqlq);
	        sql.setString(1, pi.player.getUniqueId().toString());
	        sql.setInt(2, pi.Klasa);
	        sql.setInt(3, pi.KlasaLevel);
	        sql.setInt(4, pi.Experience);
	        sql.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();	
		}
	}
	
	public static CustomPlayer GetPlayerInfo(String UUID) 
	{
		try {
			PreparedStatement sql = con.prepareStatement("SELECT * FROM Gracze WHERE UUID = ?");
	        sql.setString(1, UUID);
	        ResultSet result = sql.executeQuery();
	        if(result.next())
	        {
	        	CustomPlayer pi = new CustomPlayer();
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
	        	return pi;
	        }
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public static void CreatePlayerInventory(InventoryInfo ii) 
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
	
	public static void UpdateInventoryInfo(InventoryInfo ii) 
	{
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static InventoryInfo GetInventoryInfo(String UUID) 
	{
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
	        	return ii;
	        }
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public static void RegisterNewPlayer(CustomPlayer pi, InventoryInfo ii, Wallet w) 
	{
		CreatePlayerInfo(pi);
		CreatePlayerInventory(ii);
		AddPlayerWallet(w);
	}
	
	public static void UpdatePlayerExp(String UUID, int exp) 
	{
		try {
			PreparedStatement sql = con.prepareStatement("UPDATE Gracze SET Experience = ? WHERE UUID = ?");
			sql.setInt(1, exp);
			sql.setString(2, UUID);
			sql.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void UpdatePlayer(CustomPlayer cp) 
	{
		DatabaseManager.UpdateInventoryInfo(cp.inventoryInfo);
		DatabaseManager.UpdatePlayerClass(cp.UUID, cp.Klasa);
		DatabaseManager.UpdatePlayerWallet(cp.wallet);
		DatabaseManager.UpdatePlayerExp(cp.UUID, cp.Experience);
	}
	
	public static Location GetLocation(String name) 
	{
		try {
			PreparedStatement sql = con.prepareStatement("SELECT * FROM Lokacje WHERE Name = ?");
			sql.setString(1, name);
			ResultSet result = sql.executeQuery();
			
			if(result.next())
	        {
				
				return new Location(Bukkit.getWorld(result.getString(1)), result.getDouble(3),
						result.getDouble(4), result.getDouble(5), result.getFloat(6), result.getFloat(7));
				
	        }
		} catch(SQLException e){ e.printStackTrace(); return null;}
		 return null;
	}
	
	public static void CreateLocation(String name, Location location) 
	{
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
		} catch(SQLException e){ e.printStackTrace();}
	}
	
	public static void DeleteLocation(String name) 
	{
		try {
			PreparedStatement sql = con.prepareStatement("DELETE FROM Lokacje WHERE Nazwa = ?");
			sql.setString(1, name);
			sql.execute();
		} catch(SQLException e){ e.printStackTrace();}
	}
	
	public static ArrayList<CustomLocation> SyncLocations() 
	{
		try 
		{
			ArrayList<CustomLocation> locs = new ArrayList<CustomLocation>();
			PreparedStatement sql = con.prepareStatement("SELECT * FROM Lokacje");
			ResultSet result = sql.executeQuery();
			
			while(result.next())
			{
				locs.add(new CustomLocation(result.getString(3), new Location(Bukkit.getWorld(result.getString(2)), result.getDouble(4), result.getDouble(5), result.getDouble(6), result.getFloat(7), result.getFloat(8))));
			}
			return locs;
		} 
		catch(SQLException e)
		{ 
			e.printStackTrace(); return null;
		}
	}
}
