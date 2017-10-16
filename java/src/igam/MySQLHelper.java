package igam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;



public class MySQLHelper {
	Connection conn=null;
	Statement stmt=null;
	PreparedStatement pStmt=null;
	ResultSet mRS = null;
	String dbName="";
	MySQLHelper(String dbName) throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		this.dbName = dbName;
	}

	void connect(String user, String pass) throws SQLException {
		String sConn = "jdbc:mysql://localhost/"+dbName+"?user="+user+"&password="+pass;
		conn = DriverManager.getConnection(sConn);		
	}
	
	ResultSet mQuery(String mSQL) throws SQLException {
		stmt = conn.createStatement();
		return stmt.executeQuery(mSQL);
	}

	
	int executeAutoIncrementSQL(String mSQL) throws SQLException {
		stmt = conn.createStatement();
		stmt.executeUpdate(mSQL, Statement.RETURN_GENERATED_KEYS);
		mRS = stmt.getGeneratedKeys();
	    if (!mRS.next()) {
	        // throw an exception from here
	    	throw new SQLException("No Key Generated");
	    }
		return mRS.getInt(1);
	}
	
	
	int executeUpdate(String mSQL, int mKey, ArrayList<String> mList) throws SQLException {
		pStmt = conn.prepareStatement(mSQL);
		pStmt.setInt(1, mKey);
		for (int i=0;i<mList.size();i++) {
			pStmt.setString(i+2, mList.get(i));
		}
		return pStmt.executeUpdate();
	}
	
	
}
