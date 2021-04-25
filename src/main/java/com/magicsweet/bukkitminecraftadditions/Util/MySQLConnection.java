package com.magicsweet.bukkitminecraftadditions.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class MySQLConnection {
	String host, username, password;
	
	public MySQLConnection(String host, String username, String password) {
		this.host = host;
		this.username = username;
		this.password = password;
	}
	
	@Deprecated
	public Connection connect() throws SQLException {
		return connect("minecraft");
	}
	
	@Deprecated
	public Connection connect(String schema) throws SQLException {
		return DriverManager.getConnection(
			new String("jdbc:mysql://[ip]/[schema]?useSSL=false").replace("[ip]", this.host).replace("[schema]", schema),
			this.username,
			this.password
		);
	}
	
	@Deprecated
	public Statement getExecutableStatement() {
		try {
			return this.connect().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
