package test_jml;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Dimension;

import org.junit.Before;
import org.junit.Test;

import fstTest.AbstractAngleCalculus;
import fstTest.DatiGenerazione;

public class DatiGenerazioneTest {
	
	private static DatiGenerazione dgen;
	
	@Before
	public void setupData() {
		dgen=new DatiGenerazione();
	}
	
	@Test
	public void testCostructor() {
		dgen=new DatiGenerazione();
		assertEquals(dgen.getC1(),Color.RED);
		assertEquals(dgen.getC2(),Color.BLUE);
		assertEquals(dgen.getNome(),"Unnamed");
		assertEquals(dgen.getSesso(),"null");
		assertEquals(dgen.getMonitorSize(),0);
		assertEquals(dgen.getDistSchermo(),0);
		assertEquals(dgen.getDimensione(),new Dimension(1,1));
		assertEquals(dgen.getWRect(),-1);
		assertEquals(dgen.getHRect(),-1);
		assertEquals(dgen.getHBar(),-1);
		assertNotNull(dgen.getXBar());
		assertNotNull(dgen.getLivMax());
		assertNotNull(dgen.getLivMin());
		assertFalse(dgen.isPos());
		assertNotNull(dgen.getLivello());
		assertNotNull(dgen.getAngolo());
		System.out.println("Test costruttore DatiGenerazione");
		dgen=new DatiGenerazione("Unnamed","null",100,1);
		assertEquals(dgen.getNome(),"Unnamed");
		assertEquals(dgen.getSesso(),"null");
		assertEquals(dgen.getMonitorSize(),100);
		assertEquals(dgen.getWRect(),1);
		dgen=new DatiGenerazione("Unnamed","null",1, 1920,1080,0,0,0,0,0,1);
		assertEquals(dgen.getNome(),"Unnamed");
		assertEquals(dgen.getSesso(),"null");
		assertEquals(dgen.getMonitorSize(),1);
		assertEquals(dgen.getDistSchermo(),0);
		assertEquals(dgen.getDimensione(),new Dimension(1920,1080));
		assertEquals(dgen.getWRect(),1900);
		assertEquals(dgen.getHRect(),0);
		assertEquals(dgen.getHBar(),0);
		assertNotNull(dgen.getLivMax());
		assertNotNull(dgen.getLivMin());
	}
	
	@Test
	public void testDataInsert() {
		dgen.setNome("TestUser");
		dgen.setSesso("Maschio");
		dgen.setC1(Color.RED);
		dgen.setC2(Color.BLUE);
		dgen.setMonitorSize(173);
		dgen.setDistSchermo(40);
		dgen.setDimensione(new Dimension(1920,1080));
		dgen.setDimensione(1920.0d,1080.0d);
		dgen.setWRect(200);
		dgen.setHRect(400);
		dgen.setHBar(300);
		dgen.setLivMax(12);
		dgen.setLivMin(1);
		System.out.println("Dati inseriti. Test su inserimento parametri corretti.");
		assertEquals(dgen.getC1(),Color.red);
		assertEquals(dgen.getC2(),Color.BLUE);
		assertEquals(dgen.getNome(),"TestUser");
		assertEquals(dgen.getSesso(),"Maschio");
		assertEquals(dgen.getMonitorSize(),173);
		assertEquals(dgen.getDistSchermo(),400);
		assertEquals(dgen.getDimensione(),new Dimension(1920,1080));
		assertEquals(dgen.getWRect(),200);
		assertEquals(dgen.getHRect(),400);
		assertEquals(dgen.getHBar(),300);
		assertEquals(dgen.getXBar(),12);
		assertEquals(dgen.getLivMax(),12);
		assertEquals(dgen.getLivMin(),1);
	}
	
	@Test
	public void testWRect() {
		System.out.println("Test su inserimento wrect corretto.");
		dgen.setDimensione(1920,1080);
		dgen.setWRect(200);
		assertEquals(dgen.getWRect(),200);
		
		System.out.println("Test su inserimento wrect scorretto.");
		int[] err= {1910,-3};
		for(int e:err) {
			dgen.setWRect(e);
			assertEquals(dgen.getWRect(),1900);
		}
	}
	
	@Test
	public void testHRect() {
		System.out.println("Test su inserimento hrect corretto.");
		dgen.setDimensione(1920,1080);
		dgen.setHRect(300);
		assertEquals(dgen.getHRect(),300);
		
		System.out.println("Test su inserimento hrect scorretto.");
		int[] err= {1070,-5};
		for(int e:err) {
			dgen.setHRect(e);
			assertEquals(dgen.getHRect(),1060);
		}
	}
	
	@Test
	public void testHBar() {
		System.out.println("Test su inserimento hbar corretto.");
		dgen.setDimensione(1920,1080);
		dgen.setHRect(300);
		dgen.setHBar(200);
		assertEquals(dgen.getHBar(),200);
		
		System.out.println("Test su inserimento hbar scorretto.");
		int[] err= {295,-5};
		for(int e:err) {
			dgen.setHBar(e);
			assertEquals(dgen.getHBar(),290);
		}
	
	}
	
	
	@Test
	public void testXBar() {
		System.out.println("Test su inserimento xbar corretto.");
		dgen.setDimensione(1920,1080);
		dgen.setWRect(200);
		dgen.setLivMax(12);		
		dgen.setXBar(12);
		assertEquals(dgen.getXBar(),12);
		dgen.setLivMin(1);
		dgen.setXBar(5);
		assertEquals(dgen.getXBar(),5);
		
		System.out.println("Test su inserimento xbar scorretto.");
		dgen.setLivMax(3);
		dgen.setLivMin(10);
		dgen.setXBar(5);
		assertEquals(dgen.getXBar(),3);
		
		dgen.setLivMax(12);
		dgen.setLivMin(4);
		int[] err= {13,3,-5};
		for(int e:err) {
			dgen.setXBar(e);
			assertEquals(dgen.getXBar(),12);
		}
	
	}
	
	@Test
	public void testLivMax(){
		System.out.println("Test su inserimento livello massimo corretto.");
		dgen.setDimensione(1920,1080);
		dgen.setWRect(200);
		dgen.setLivMax(12);
		assertEquals(dgen.getLivMax(),12);
		
		System.out.println("Test su inserimento livello massimo scorretto.");
		int[] err= {220,195,-5};
		for(int e:err) {
			dgen.setLivMax(e);
			assertEquals(dgen.getLivMax(),190);
		}

	
	}
	
	@Test
	public void testLivMin() {
		System.out.println("Test su inserimento livello minimo corretto.");
		dgen=new DatiGenerazione();
		dgen.setDimensione(1920, 1080);
		dgen.setWRect(200);
		dgen.setLivMax(12);
		dgen.setLivMin(1);
		assertEquals(dgen.getLivMin(),1);
		
		System.out.println("Test su inserimento livello minimo scorretto.");
		
		int[] err= {13,198,-5};
		for(int e:err) {
			dgen.setLivMin(e);
			assertEquals(dgen.getLivMax(),12);
		}
	
	}
	
	
	
	@Test
	public void testMonSize() {
		dgen.setMonitorSize(156);
		assertEquals(dgen.getMonitorSize(),156);
		dgen.setMonitorSize(0);
		assertNotEquals(dgen.getMonitorSize(),156);
	}
	
	@Test
	public void testAngleLevel() {
		dgen.setAngolo(AbstractAngleCalculus.calcolaAngolo(173, new Dimension(1920,1080), 12, 40));
		dgen.setLivello(12);
		assertEquals(dgen.getAngolo(),617,0);
		assertEquals(dgen.getLivello(),12,1);
	}
	
	
	
	
}
