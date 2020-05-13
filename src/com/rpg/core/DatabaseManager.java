package com.rpg.core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.PreparedStatement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DatabaseManager
{
	private Connection con;
	private String adr, db, usr, pass;
	private int port;
	
	public void Setup() 
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
	
	public void connect() throws ClassNotFoundException, SQLException
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
	
	public void SetPlayerClass(String UUID, int klasa) throws SQLException 
	{
		PreparedStatement sql = con.prepareStatement("INSERT INTO Klasy VALUES (?, ?)");
        sql.setString(1, UUID);
        sql.setInt(2, klasa);
        sql.execute();
	}
	
	public int GetPlayerClass(String UUID) throws SQLException 
	{
		PreparedStatement sql = con.prepareStatement("SELECT Klasa FROM Klasy WHERE UUID = ?");
        sql.setString(1, UUID);
        ResultSet result = sql.executeQuery();
        if(result.next()){
        	return result.getInt(1);
        }
        return -1;
	}
	
	public void UpdatePlayerClass(String UUID, int klasa) 
	{
		try {
			
			PreparedStatement sql;
			if(klasa > 0)
			{
				sql = con.prepareStatement("UPDATE Klasy SET Klasa = ? WHERE UUID = ?");
				
				sql.setInt(1, klasa);
				sql.setString(2, UUID);
			}
			else
			{
				sql = con.prepareStatement("DELETE FROM Klasy WHERE UUID = ?");
				sql.setString(1, UUID);
			}
			
			sql.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
