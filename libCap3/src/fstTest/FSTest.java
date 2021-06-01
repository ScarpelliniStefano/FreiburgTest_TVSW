package fstTest;

import java.awt.image.BufferedImage;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;
import java.util.Random;

import javax.imageio.ImageIO;

import fstTest.PestBase.CertifierStatus;
import fstTest.TestSession.Result;


@SuppressWarnings("deprecation")
public class FSTest extends Observable {
	///PMD1: conversione di tutti i nomi di metodi 
	// 		con le lettere maiuscole iniziali in minuscole	
	//PMD2: aggiunta dei commenti a ogni attributo e metodo della classe
	//PMD3: aggiunta parentesi graffe negli if-else
	//PMD4: aggiunto final a alcune variabili che potevano essere non inizializzate
	// (MethodArgumentCouldBeFinal: Parameter is not assigned and could be declared
	//  final)
	//PMD5: confronti tra valori cambiati in compareTo o metodi di confronto
	
	/** The result. */
	/*@ spec_public @*/
	/*@ non_null @*/
	private static DatiGenerazione risultato=new DatiGenerazione();

	/** the tester. */
	/*@ spec_public @*/
	public static TestSession testSession;
	
	/** The stream of image */
	/*@ spec_public @*/
	private static ByteArrayOutputStream imagebuffer;
	
	/** generatore di randomici */
	/*@ spec_public @*/
	/*@ non_null @*/
	static Random rnGen=new Random();
	
	
	/**
	 * The Enum Scelta
	 */
	public enum Scelta {
		/** Passa */
		PASSA,
		/** Corretto */
		CORRETTO,
		/** Sbagliato */
		SBAGLIATO,
		/** Finisci */
		FINISCI,;

	}
	
	
	
		
	   /**
		* funzione di generazione immagine del test di freiburg dati 
		* i parametri di generazione
		* @param result    dati di generazione strutturati come 
		* 					DatiGenerazione
		* @return      Stream immagine
		* @throws IOException 
		*/	
	//@requires result.livMax>0;
	//@ensures \result!=null;
	public static InputStream iniziaTest(final DatiGenerazione result) throws IOException {
		if(risultato.getAngolo()==-1) {
			risultato=result;
		}
		else {
			risultato.setXBar(risultato.getLivMax());
			risultato.setPos(false);
		}
		//@assert risultato.getPos()==false;
		testSession = new TestSession();
		final Scelta inizio=TestSession.iniziaTest(risultato);
		ByteArrayInputStream imgByteArray = null;
		//@ assert inizio==Scelta.CORRETTO;
		if(inizio==Scelta.CORRETTO) {
			BufferedImage image;
			if(Double.compare(risultato.getDimensione().getWidth(),1)==0) { //problema con la call chain
		        image=GeneraImg.GeneraImmagine(risultato.getWRect(), risultato.getHRect(), risultato.getHBar(), risultato.getXBar(), risultato.getC1(), risultato.getC2());		
			}else {
				image=GeneraImg.GeneraImmagine(risultato.getDimensione().width,risultato.getDimensione().height,risultato.getWRect(), risultato.getHRect(), risultato.getHBar(), risultato.getXBar(), risultato.getC1(), risultato.getC2());	
				//problema con la call chain
			}
			//prova di inserimento immagine nello stream
				try {
					// Write the image to a buffer
							imagebuffer = new ByteArrayOutputStream();
							ImageIO.write(image, "png", imagebuffer);
							// Return a stream from the buffer
							imgByteArray=new ByteArrayInputStream(imagebuffer.toByteArray());
				}catch(IOException e) {
					e.printStackTrace(); //change to logger
					
				}
		}
		//@ assert imgByteArray!=null;
		return imgByteArray;
		
				
	}

	/**
	 * Return the current depth (as established by the certifier), be careful the
	 * certifier may have decided to stop
	 *
	 * @return the current depth
	 */
	//@ ensures \result==testSession.getPofonditaCorrente();
	public int getCurrentDepth() {
		return testSession.getProfonditaCorrente();
	}

	/**
	 * Gets the current status
	 *
	 * @return the current status
	 */
	//@ ensures \result==testSession.getStatoCorrente();
	public static CertifierStatus getCurrentStatus() {
		return testSession.getStatoCorrente();
	}
	
	
	
	/**
	 * @param rispostaData
	 * @return
	 */
	//@ requires rispostaData!=null
	//@ ensures \result!=null;
	public static Scelta controlloRisposta(final /*@ non_null@*/ String rispostaData) {
		Scelta scelta;
		scelta=testSession.controlloRisposta(rispostaData); // Initialized before computeNextDepth()
		if(!testSession.getStatoCorrente().currentResult.equals(Result.CONTINUA)) { //call chain
			if(testSession.getStatoCorrente().currentResult==Result.FINE_NON_CERTIFICATA) { //call chain
				risultato.setAngolo(0);
				risultato.setLivello(0);
			}else {
				risultato.setAngolo(AbstractAngleCalculus.calcolaAngolo(risultato));
			    /*call chain*/risultato.setLivello(1000* (AbstractAngleCalculus.monitorWidthMM(risultato.getMonitorSize(),(int)risultato.getDimensione().getWidth(),(int)risultato.getDimensione().getHeight())*risultato.getXBar())/(int)risultato.getDimensione().getWidth());
		   }
		}
		return scelta;
		
	}
	
	
	
	/**
	 * metodo per cambio posizione
	 */
	private static void changePos() {
		risultato.setPos(rnGen.nextBoolean()); 
		//prima era new Random().nextBoolean()
		//ora si � creato un generatore di randomici
	}
	
	/**
	 * @return nuova immagine
	 */
	//@ensures \result!=null;
	public static InputStream settaNuovaImg() {
		
		assert testSession.getStatoCorrente().currentResult == TestSession.Result.CONTINUA; //call chain
		BufferedImage image = null;
		ByteArrayInputStream imgByteArray = null;
		changePos();
		image=GeneraImg.modificaM(risultato.getXBar(), risultato);
						
		if(image!=null)	{
		//prova di inserimento immagine nello stream
			try {
			// Write the image to a buffer
					imagebuffer = new ByteArrayOutputStream();
					ImageIO.write(image, "png", imagebuffer);
					// Return a stream from the buffer
					imgByteArray=new ByteArrayInputStream(imagebuffer.toByteArray());
			}catch(IOException e) {
				e.printStackTrace(); //sarebbe da usare un logger
			}
		
		}
		return imgByteArray;
	}
	

}
