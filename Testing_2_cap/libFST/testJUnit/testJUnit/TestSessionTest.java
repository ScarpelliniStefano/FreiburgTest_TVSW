package testJUnit;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import session.TestSession;
import session.TestSession.*;
import test.DatiGenerazione;
import test.FSTest.Scelta;

public class TestSessionTest {

	private static test.DatiGenerazione dgen;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dgen=new DatiGenerazione();
		dgen.setNome("TestUser");
		dgen.setSesso("Maschio");
		dgen.setDataNasc(Calendar.getInstance());
		dgen.setC1(Color.RED);
		dgen.setC2(Color.BLUE);
		dgen.setMonitorSize(173);
		dgen.setDistSchermo(40);
		dgen.setDimensione(new Dimension(1920,1080));
		dgen.setWRect(200);
		dgen.setHRect(400);
		dgen.setHBar(300);
		dgen.setLivMax(12);
		dgen.setLivMin(1);
	}

	static TestSession session;
	
	@Before
	public void initialize() {
		session=null;
	}
	
	@Test
	public void testTest_session() {
		
		assertNull(session);
		session=new TestSession();
		assertNotNull(session);
		assertEquals(session.getStatoCorrente().currentDepth,12);
		assertEquals(session.getStatoCorrente().currentResult,Result.CONTINUA);
	}

	@Test
	public void testGetProfonditaCorrente() {
		session=new TestSession();
		session.iniziaTest(dgen);
		assertEquals(session.getProfonditaCorrente(),12);
		session.controlloRisposta("behind");
		assertEquals(session.getProfonditaCorrente(),6);
	}

	@Test
	public void testGetStatoCorrente() {
		session=new TestSession();
		session.iniziaTest(dgen);
		assertEquals(session.getStatoCorrente().currentDepth,12);
		assertEquals(session.getStatoCorrente().currentResult,Result.CONTINUA);
		session.controlloRisposta("behind");
		assertEquals(session.getStatoCorrente().currentDepth,6);
		assertEquals(session.getStatoCorrente().currentResult,Result.CONTINUA);
		session.controlloRisposta("stop");
		assertEquals(session.getStatoCorrente().currentResult,Result.FINE_NON_CERTIFICATA);
	}

	@Test
	public void testIniziaTest() {
		session=new TestSession();
		Scelta choice=session.iniziaTest(dgen);
		assertEquals(choice,Scelta.CORRETTO);
		choice=TestSession.iniziaTest(dgen);//controllo in caso di richiamata
		assertEquals(choice,Scelta.CORRETTO);
	}

	@Test
	public void testControlloRisposta() {
		session=new TestSession();
		session.iniziaTest(dgen);
		Scelta choice=session.controlloRisposta("behind");
		assertEquals(choice,Scelta.CORRETTO);
		choice=session.controlloRisposta("stop");
		assertEquals(choice,Scelta.FINISCI);
		
	}

	@Test
	public void testGetSessionResults() {
		session=new TestSession();
		session.iniziaTest(dgen);
		session.controlloRisposta("behind");
		session.controlloRisposta("stop");
		List<String> listRisp=session.getSessionResults();
		List<String> listCorrect=new ArrayList<String>();
		listCorrect.add("behind,behind,12");
		listCorrect.add("skip");
		for(int i=0;i<listRisp.size();i++) {
			assertEquals(listRisp.get(i).substring(0, listCorrect.get(i).length()),listCorrect.get(i));
		}
	}

	@Test
	public void testGetSessionAnswers() {
		session=new TestSession();
		session.iniziaTest(dgen);
		session.controlloRisposta("forward");
		session.controlloRisposta("behind");
		session.controlloRisposta("forward");
		session.controlloRisposta("stop");
		List<RispostaSingola> listRisp=session.getSessionAnswers();
		assertNotNull(listRisp);
	}

}
