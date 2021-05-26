package test_jml;

import java.awt.Color;
import java.awt.Dimension;

import org.junit.Test;

import fstTest.DatiGenerazione;

public class TesterDatiGen {

	@Test
	public void testDgenClear() {
		DatiGenerazione dgen=new DatiGenerazione();
	}
	
	@Test
	public void testDgenHalf() {
		DatiGenerazione dgen=new DatiGenerazione("pippo","man",164,1);
	}
	
	@Test
	public void testDgenFull() {
		DatiGenerazione dgen=new DatiGenerazione("pippo","man",173, 1920,1080,400,300,200,40,1,12);
	}
	
	@Test
	public void testSesso() {
		DatiGenerazione dgen=new DatiGenerazione();
		dgen.setSesso("f");
	}
	
	@Test
	public void testMonSize() {
		DatiGenerazione dgen=new DatiGenerazione();
		dgen.setMonitorSize(5);
		dgen.setMonitorSize(0);
	}
	
	@Test
	public void testWRect() {
		DatiGenerazione dgen=new DatiGenerazione();
		dgen.setDimensione(new Dimension(1920,1080));
		dgen.setWRect(400);
		dgen.setWRect(1910);
	}
	
	@Test
	public void testXBar() {
		DatiGenerazione dgen=new DatiGenerazione();
		dgen.setDimensione(new Dimension(1920,1080));
		dgen.setWRect(400);
		dgen.setLivMax(3);
		dgen.setXBar(11);
	}
	
	@Test
	public void testPos() {
		DatiGenerazione dgen=new DatiGenerazione();
		dgen.setPos(true);
	}
	

}
