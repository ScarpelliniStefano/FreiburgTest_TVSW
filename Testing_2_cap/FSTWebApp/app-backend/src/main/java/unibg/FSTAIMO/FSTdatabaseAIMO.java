package unibg.FSTAIMO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

import com.vaadin.sass.internal.parser.ParseException;

import unibg.se4med.Constant;

public class FSTdatabaseAIMO {
	
	static Connection conn;
	static DSLContext database;

	public static void IniziaConn() {
		conn = getConnection();

	}

	public static DSLContext DatabaseOperator() {
		database = DSL.using(conn, SQLDialect.MYSQL, new Settings().withExecuteLogging(true));
		return database;
	}

	public static DSLContext getDB() {
		return database;
	}

	public static Connection getConn() {
		return conn;
	}
	
	public static Boolean connOpers() {
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
}