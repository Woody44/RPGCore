package com.rpg.core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.PreparedStatement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public final class DatabaseManager{
	private Connection con;
	private String adr, db, usr, pass;
	private int port;
	public void Setup() 
	{
		adr = "mysql.titanaxe.com";
		port = 3306;
        db = "srv73958";
        usr = "srv73958";
        pass = "nVJfnvXZ";
        
        try 
        {
        	System.out.println("[Database Manager] Laczenie z Baza danych " + adr + "@" + db);
        	connect();
        } catch (ClassNotFoundException e) 
        {
        	e.printStackTrace();
        	System.out.println("[Database Manager] Blad polaczenia z baza!");
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
}
