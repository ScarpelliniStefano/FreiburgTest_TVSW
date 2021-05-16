package libFSTest.test;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Dimension;

import java.util.Calendar;



import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import libFSTest.test.FSTest.Scelta;

public class FSTestTestMocked {

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

		fst.IniziaTest(dgen);
		Mockito.when(dgen.getPos()).thenReturn(false);
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
	public void testGetCurrentDepthCorrect() {
		int curr=fst.getCurrentDepth();
		assertEquals(curr,12);
		
		Scelta s=fst.ControlloRisposta("behind");
		assertEquals(s,Scelta.CORRETTO);
		Mockito.when(fst.getCurrentDepth()).thenReturn(curr/2);
		curr=fst.getCurrentDepth();
		fst.settaNuovaImg();
		assertEquals(curr,6);
		s=fst.ControlloRisposta("forward");
		assertEquals(s,Scelta.CORRETTO);
		fst.settaNuovaImg();
		curr=fst.getCurrentDepth();
		assertEquals(curr,3);
		
	}
	
	@Test
	public void testGetCurrentDepthIncorrect() {
		fst.IniziaTest(dgen);
		int curr=fst.getCurrentDepth();
		assertEquals(curr,12);
		fst.ControlloRisposta("forward");
		curr=fst.getCurrentDepth();
		assertEquals(fst.getCurrentDepth(),12);
		Result stat=fst.getCurrentStatus().currentResult;
		assertEquals(stat,Result.CONTINUA);
		Mockito.when(fst.ControlloRisposta(Mockito.anyString())).thenReturn(Scelta.SBAGLIATO);
		fst.settaNuovaImg();
		fst.ControlloRisposta("forward");
		stat=fst.getCurrentStatus().currentResult;
		curr=fst.getCurrentDepth();

		
	}


}
