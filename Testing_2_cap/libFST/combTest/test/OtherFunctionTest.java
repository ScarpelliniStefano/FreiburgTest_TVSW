package test;

import static org.junit.Assert.*;
import org.junit.Test;

import session.AbstractPestBase.Soluzione;
import session.TestSession.Result;

public class OtherFunctionTest {

	@Test
	public void testPossibleCert() {
		try {
		assertEquals(OtherFunction.PossibleCert(3,2,false,2,Soluzione.STOP),Result.FINE_NON_CERTIFICATA); //riga 3
		assertEquals(OtherFunction.PossibleCert(10,9,true,2,Soluzione.GIUSTA),Result.FINE_CERTIFICATA); //riga 15
		assertEquals(OtherFunction.PossibleCert(2,1,false,1,Soluzione.SBAGLIATA),Result.FINE_CERTIFICATA); //riga 41
		assertEquals(OtherFunction.PossibleCert(9,2,false,0,Soluzione.SBAGLIATA),Result.CONTINUA); //riga 8
		assertEquals(OtherFunction.PossibleCert(20,9,false,0,Soluzione.GIUSTA),Result.CONTINUA); //riga 33
		assertEquals(OtherFunction.PossibleCert(10,2,true,1,Soluzione.GIUSTA),Result.CONTINUA); //riga 12
		assertEquals(OtherFunction.PossibleCert(18,1,false,2,Soluzione.GIUSTA),Result.CONTINUA); //riga 22
		}catch(Exception e) {
			fail("è stata lanciata una eccezione");
		}
	}
	
	@Test
	public void testPossibleCertWithNumberEsternal(){
		
		//rimuovendo i vincoli di ingresso
		try {
			assertEquals(OtherFunction.PossibleCert(-5,1,true,2,Soluzione.STOP),Result.FINE_NON_CERTIFICATA); //riga 3
			fail("eccezione non lanciata");
		} catch (Exception e) {
			//lanciata eccezione
		}
		try {
			assertEquals(OtherFunction.PossibleCert(0,6,false,2,Soluzione.valueOf("pigiama")),Result.CONTINUA); //riga 19
			fail("eccezione non lanciata");
		} catch (Exception e) {
			//lanciata eccezione
		}
		try {
			assertEquals(OtherFunction.PossibleCert(21,12,true,-1,Soluzione.GIUSTA),Result.CONTINUA); //riga 152
			fail("eccezione non lanciata");
		} catch (Exception e) {
			//lanciata eccezione
		}
		try {
			assertEquals(OtherFunction.PossibleCert(3,1,false,2,null),Result.CONTINUA); //riga 55
			fail("eccezione non lanciata");
		} catch (Exception e) {
			//lanciata eccezione
		}
		
	}
	
	
}
