package testJUnit;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

import session.AbstractPestBase.CertifierStatus;
import session.TestSession.Result;
import test.DatiGenerazione;
import test.FSTest;
import test.FSTest.Scelta;


public class FSTestTest {

	private static DatiGenerazione dgen;
	static FSTest fst;
	@Before
	public void initialize() {
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
		fst=new FSTest();
	}
	
	/*@Test(expected = IllegalArgumentException.class)
	public void testSceltaScorretta() {
		 try {
			 FSTest.Scelta s=FSTest.Scelta.valueOf("ciao");
			 fail("Expected exception of type java.lang.IllegalArgumentException");
		 }catch(java.lang.IllegalArgumentException e) {
			 //exception
		 }
	}*/
	
	
	@Test
	public void testFSTest() {
		System.out.println("Costruttore (vuoto) sotto test");
		fst=new FSTest();
		assertNotNull(fst);
	}

	@Test
	public void testIniziaTest() throws IOException {
		ByteArrayInputStream img=(ByteArrayInputStream) FSTest.iniziaTest(dgen);
		img.available();
		assertNotNull(img.available());
		
		img=(ByteArrayInputStream) FSTest.iniziaTest(dgen);
		img.available();
		assertNotNull(img.available());
	}

	@Test
	public void testGetCurrentDepthCorrect() {
		fst=new FSTest();
		try {
			FSTest.iniziaTest(dgen);
		} catch (IOException e) {
			fail("lanciata eccezione");
		}
		int curr=fst.getCurrentDepth();
		assertEquals(curr,12);
		fst.controlloRisposta("behind");
		curr=fst.getCurrentDepth();
		Result r=FSTest.getCurrentStatus().currentResult;
		//non possiamo proseguire poi con la verifica perchè è randomica
		assertEquals(fst.getCurrentDepth(),6);
		
	}
	
	@Test
	public void testGetCurrentDepthIncorrect() {
		try {
			FSTest.iniziaTest(dgen);
		} catch (IOException e) {
			fail("lanciata eccezione");
		}
		int curr=fst.getCurrentDepth();
		assertEquals(curr,12);
		fst.controlloRisposta("forward");
		curr=fst.getCurrentDepth();
		assertEquals(fst.getCurrentDepth(),12);
		Result stat=FSTest.getCurrentStatus().currentResult;
		assertEquals(stat,Result.CONTINUA);
		FSTest.settaNuovaImg();
		fst.controlloRisposta("forward");
		stat=FSTest.getCurrentStatus().currentResult;
		curr=fst.getCurrentDepth();

		
	}


}
