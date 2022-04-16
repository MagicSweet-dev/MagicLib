package com.magicsweet.lib.magiclib.util;

import lombok.SneakyThrows;

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
	
	@SneakyThrows
	public Connection connect(String schema, String... arguments) {
		return DriverManager.getConnection(
			("jdbc:mysql://[ip]/[schema]?useSSL=false" + String.join("&", arguments)).replace("[ip]", this.host).replace("[schema]", schema),
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
