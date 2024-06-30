package config;

import java.sql.*;
import java.util.*;

public class DBConfig {

	private static Connection conn;
	private static ResultSet rs ;
	private static PreparedStatement stmt;
	private static CallableStatement cs;
	
	private static DBConfig db=null;
	
	private DBConfig() {
		
		try {
			
			Properties p=new Properties();
			p.load(PathHelper.fis);  //its access path from pathhepler class
			String driverclass=p.getProperty("drivername");
			String username=p.getProperty("username");
			String password=p.getProperty("password");
			String url=p.getProperty("url");
			
		   Class.forName(driverclass);
		   conn=DriverManager.getConnection(url,username,password);
		   if(conn!=null) {
			System.out.println("DataBase is connected....");   
		   }
		   else {
			   System.out.println("Data base not connected.....");
		   }
		   
		}
		catch(Exception ex) {
			System.out.println("Error is"+ex);
			
		}
	}
	
	public static DBConfig getDBInstance() {
		if(db==null) {
			db=new DBConfig();
		}
		return db;
	}
	
	public static Connection getConnection() {
		return conn;
	}
	public static PreparedStatement getStatement() {
		return stmt;
	}
	
	public static ResultSet getResultSet() {
		return rs;
	}
	public static CallableStatement getCallable() {
		return cs;
	}
}
