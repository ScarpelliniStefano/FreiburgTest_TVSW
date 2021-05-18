package libFST.db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class FSTdatabaseAIMO {
	
	static Connection conn;

	public void IniziaConn() {
		conn = getConnection();

	}

	public Connection getConn() {
		return conn;
	}
	
	public Boolean connOpers() {
		if (conn!=null) 
			return true;
		else
			return false;
	}
	/**
	 * Connect to the database if it exists
	 * 
	 * @return Connection: a connection to the URL
	 */
	private static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			return DriverManager.getConnection(Constant.urlMed, Constant.userMed, Constant.passwordMed);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return null;
	}
	private static String toSha256(String password) throws NoSuchAlgorithmException
	{
		 MessageDigest md = MessageDigest.getInstance("SHA-256");
	     md.update(password.getBytes());
	     StringBuffer result = new StringBuffer();
	     for (byte byt : md.digest()) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
	     return result.toString();
	}
	public Boolean checkAuthorization(String user,String psw) {
		this.IniziaConn();
		try {
			psw = toSha256(psw);
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			
		}
		try {
			Statement s= conn.createStatement();
			ResultSet r=s.executeQuery("SELECT * FROM dataAIMO where (mail="+user+" AND psw="+psw+")");
			if(r.next())
				return true;
		} catch (SQLException e) {
			return false;
		}
		
		return null;
		
	}
}