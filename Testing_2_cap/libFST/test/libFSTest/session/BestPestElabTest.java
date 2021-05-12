package libFSTest.session;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import libFSTest.session.PestBase.CertifierStatus;
import libFSTest.session.PestBase.Soluzione;
import libFSTest.session.Test_session.Result;

public class BestPestElabTest {
	BestPestElaboratorNew bpElab;
	

	@Test
	public void testGetCurrentStatus() {
		bpElab=new BestPestElaboratorNew(12,1);
		CertifierStatus stat=bpElab.getCurrentStatus();
		assertEquals(stat.currentDepth,12);
		assertEquals(stat.currentResult,Result.CONTINUA);
	}

	@Test
	public void testGetNextDepth() {
		bpElab=new BestPestElaboratorNew(12,1);
		assertEquals(bpElab.getNextDepth(),12);
		bpElab.setNextDepth(11);
		assertEquals(bpElab.getNextDepth(),11);
	}

	@Test
	public void testSetNextDepth() throws IllegalArgumentException{
		bpElab=new BestPestElaboratorNew(12,1);
		bpElab.setNextDepth(12);
		assertEquals(bpElab.getNextDepth(),12);
		bpElab.setNextDepth(1);
		assertEquals(bpElab.getNextDepth(),1);
		try {
			bpElab.setNextDepth(13);
			fail("Eccezione non lanciata");
		}catch(IllegalArgumentException e) {
			//
		}
		try {
			bpElab.setNextDepth(0);
			fail("Eccezione non lanciata");
		}catch(IllegalArgumentException e) {
			//
		}
		
	}

	@Test
	public void testBestPestElaborator() {
		assertNull(bpElab);
		bpElab=new BestPestElaboratorNew(12,1);
		assertEquals(bpElab.getCurrentStatus().currentResult,Result.CONTINUA);
	
		int[][] maxMinErr= {{1,0},{1,2}};
		for(int[] err:maxMinErr) {
			bpElab=new BestPestElaboratorNew(err[0],err[1]);
			assertEquals(bpElab.rightLimit,1);
		}
	}
	
	@Test
	public void testBestPestElaboratorExcept() throws IllegalArgumentException {
		try {
			bpElab=new BestPestElaboratorNew(0,1);
			fail("Eccezione non lanciata");
		}catch(IllegalArgumentException e) {
			//eccezione ok
		}
		
	}

	@Test
	public void testComputeNextDepthSoluzione() {
		bpElab=new BestPestElaboratorNew(12,1);
				
		//percorso tutto corretto:
		bpElab=new BestPestElaboratorNew(12,1);
		for(int i=0;i<4;i++)
			bpElab.computeNextDepth(Soluzione.GIUSTA); //6,3,2,2
		assertEquals(bpElab.getCurrentStatus().currentDepth,2);
		assertEquals(bpElab.getCurrentStatus().currentResult,Result.FINE_CERTIFICATA);
		
		//percorso sempre errato (fine non certificata):
		bpElab=new BestPestElaboratorNew(12,1);
		for(int i=0;i<2;i++)
			bpElab.computeNextDepth(Soluzione.SBAGLIATA); //12,12
		assertEquals(bpElab.getCurrentStatus().currentDepth,12);
		assertEquals(bpElab.getCurrentStatus().currentResult,Result.FINE_NON_CERTIFICATA);
		
		//percorso per la prima volta corretto e poi errato:
		bpElab=new BestPestElaboratorNew(12,1);
		bpElab.computeNextDepth(Soluzione.GIUSTA); //6
		for(int i=0;i<3;i++)
			bpElab.computeNextDepth(Soluzione.SBAGLIATA); //9,11,12
		assertEquals(bpElab.getCurrentStatus().currentDepth,12);
		assertEquals(bpElab.getCurrentStatus().currentResult,Result.FINE_CERTIFICATA);
		
		//percorso per la prima volta corretto e poi errato:
		bpElab=new BestPestElaboratorNew(12,1);
		bpElab.computeNextDepth(Soluzione.GIUSTA); //6
		bpElab.computeNextDepth(Soluzione.SBAGLIATA); //9
		bpElab.computeNextDepth(Soluzione.STOP);
		assertEquals(bpElab.getCurrentStatus().currentDepth,9);
		assertEquals(bpElab.getCurrentStatus().currentResult,Result.FINE_NON_CERTIFICATA);
		
		//percorso per la prima volta errato, poi corretto e poi errato:
		bpElab=new BestPestElaboratorNew(12,1);
		bpElab.computeNextDepth(Soluzione.SBAGLIATA); //12
		bpElab.computeNextDepth(Soluzione.GIUSTA); //6
		bpElab.computeNextDepth(Soluzione.SBAGLIATA); //9
		assertEquals(bpElab.getCurrentStatus().currentDepth,9);
		assertEquals(bpElab.getCurrentStatus().currentResult,Result.CONTINUA);
		
	}

}
