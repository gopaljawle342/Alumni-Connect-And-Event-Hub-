package config;
import java.sql.*;
public class DBHelper {
	/* its encapsulation we can not access connection class directly require to inherit DBHelper class then 
	 we can use connection reference.*/
	/*this method can access connection references from DBConfig class by calling public methods*/
	
	protected DBConfig db=DBConfig.getDBInstance();
	protected Connection conn=DBConfig.getConnection();
	protected PreparedStatement stmt=DBConfig.getStatement();
	protected ResultSet rs=DBConfig.getResultSet();
	protected CallableStatement cs=DBConfig.getCallable();
	
}
