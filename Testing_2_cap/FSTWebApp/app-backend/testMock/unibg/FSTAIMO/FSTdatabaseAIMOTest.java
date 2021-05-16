package unibg.FSTAIMO;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import unibg.se4med.Constant;


public class FSTdatabaseAIMOTest {
	
	FSTdatabaseAIMO fstAIMOMocked;
	
	@Before
	public void setUp() {
		fstAIMOMocked = Mockito.spy(FSTdatabaseAIMO.class);
		fstAIMOMocked=new FSTdatabaseAIMO();
	}

	@Test
	public void testIniziaConn() {
		fstAIMOMocked.IniziaConn();
		assertEquals(fstAIMOMocked.getConn(),null);
		
	}

	@Test
	public void testDatabaseOperator() {
		DSLContext dslDB=fstAIMOMocked.DatabaseOperator();
		assertEquals(dslDB.configuration().dialect(),SQLDialect.MYSQL);
	}

	@Test
	public void testGetDB() {
		DSLContext dslDB=fstAIMOMocked.getDB();
		assertNull(dslDB);
		fstAIMOMocked.DatabaseOperator();
		dslDB=fstAIMOMocked.getDB();
		assertNotNull(dslDB);
	}

	@Test
	public void testGetConn() throws SQLException {
		Connection c=fstAIMOMocked.getConn();
		assertNull(c);
		Mockito.when(fstAIMOMocked.getConn()).thenReturn(DriverManager.getConnection(Constant.url, Constant.user, Constant.password));
		fstAIMOMocked.IniziaConn();
		assertNotNull(fstAIMOMocked.getConn());
	}
	
	@Test
	public void testConnOpers() {
		//Mockito.when(fstAIMOMocked.connOpers()).thenReturn(true);
		fstAIMOMocked.IniziaConn();
		assertEquals(fstAIMOMocked.connOpers(),true);
	}

}
