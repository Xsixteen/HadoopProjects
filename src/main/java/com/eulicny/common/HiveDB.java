package com.eulicny.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class HiveDB {
	
	 public static final String HIVE_JDBC_DRIVER = "org.apache.hive.jdbc.HiveDriver";
	 public static final String HIVE_JDBC_EMBEDDED_CONNECTION = "jdbc:hive2://ericdev01.aegisdoctrine.local:2181/;serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveserver2";

	 public Statement getConnection() throws ClassNotFoundException,
	            SQLException {
	        Class.forName(HIVE_JDBC_DRIVER);
	        Connection connection = DriverManager.getConnection(
	                HIVE_JDBC_EMBEDDED_CONNECTION, "", "");

	        Statement statement = connection.createStatement();
	        return statement;
	 }

}
