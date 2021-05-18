package unibg.se4med;

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

public class FSTdatabase {
	
	public static Date getMilliseconds(int yyyy, int mm, int dd) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		java.sql.Date sqlDate = null;
		try {
			java.util.Date utilDate = null;
			try {
				utilDate = format.parse(yyyy + "/" + mm + "/" + dd);
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sqlDate = new java.sql.Date(utilDate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sqlDate;
	}
	
	public static String nomeUtente = null;
	public static String email = null;
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
	/**
	 * Connect to the database if it exists
	 * 
	 * @return Connection: a connection to the URL
	 */
	private static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			return DriverManager.getConnection(Constant.url, Constant.user, Constant.password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}