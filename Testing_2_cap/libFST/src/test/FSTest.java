package test;

import java.awt.image.BufferedImage;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

import db.Doctor;
import db.FSTdatabaseAIMO;
import db.Patient;
import draw.AbstractAngleCalculus;
import draw.GeneraImg;
import session.TestSession;
import session.AbstractPestBase.CertifierStatus;
import session.TestSession.Result;


/**
 * @author stefa
 * classe test freiburg
 */
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
	private static DatiGenerazione risultato=new DatiGenerazione();

	/** the tester. */
	public static TestSession testSession;
	
	/** The stream of image */
	private static ByteArrayOutputStream imagebuffer;
	
	
	private FSTdatabaseAIMO db;
	
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
	public static InputStream iniziaTest(final DatiGenerazione result) throws IOException {
		if(risultato.getAngolo()==-1) {
			risultato=result;
		}
		else {
			risultato.setXBar(risultato.getLivMax());
			risultato.setPos(false);
		}
		
		testSession = new TestSession();
		final Scelta inizio=TestSession.iniziaTest(risultato);
		ByteArrayInputStream imgByteArray = null;
		if(inizio==Scelta.CORRETTO) {
			BufferedImage image;
			if(Double.compare(risultato.getDimensione().getWidth(),1)==0) { //problema con la call chain
		        image=GeneraImg.generaImmagine(risultato.getWRect(), risultato.getHRect(), risultato.getHBar(), risultato.getXBar(), risultato.getC1(), risultato.getC2());		
			}else {
				image=GeneraImg.generaImmagine(risultato.getDimensione().width,risultato.getDimensione().height,risultato.getWRect(), risultato.getHRect(), risultato.getHBar(), risultato.getXBar(), risultato.getC1(), risultato.getC2());	
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
		return imgByteArray;
		
				
	}
	
	/**
	 * Return the current depth (as established by the certifier), be careful the
	 * certifier may have decided to stop
	 *
	 * @return the current depth
	 */
	public int getCurrentDepth() {
		return testSession.getProfonditaCorrente();
	}

	/**
	 * Gets the current status
	 *
	 * @return the current status
	 */
	public static CertifierStatus getCurrentStatus() {
		return testSession.getStatoCorrente();
	}
	
	
	
	/**
	 * @param rispostaData
	 * @return
	 */
	public static Scelta controlloRisposta(final String rispostaData) {
		Scelta scelta;
		scelta=testSession.controlloRisposta(rispostaData); // Initialized before computeNextDepth()
		if(!testSession.getStatoCorrente().currentResult.equals(Result.CONTINUA)) { //call chain
			if(testSession.getStatoCorrente().currentResult==Result.FINE_NON_CERTIFICATA) { //call chain
				risultato.setAngolo(0);
				risultato.setLivello(0);
			}else {
				risultato.setAngolo(AbstractAngleCalculus.calcolaAngolo(risultato));
			    /*call chain*/
				risultato.setLivello(1000* (AbstractAngleCalculus.monitorWidthMM(risultato.getMonitorSize(),(int)risultato.getDimensione().getWidth(),(int)risultato.getDimensione().getHeight())*risultato.getXBar())/(int)risultato.getDimensione().getWidth());
		    }
		}
		return scelta;
		
	}
	
	
	
	/**
	 * metodo per cambio posizione
	 */
	private static void changePos() {
		//Bug: Random object created and used only 
		//once in test.FSTest.changePos()
		risultato.setPos(ThreadLocalRandom.current().nextBoolean()); 
		//prima era new Random().nextBoolean()
		//ora si è creato un generatore di randomici
	}
	
	/**
	 * @return nuova immagine
	 */
	public static InputStream settaNuovaImg() {
		
		assert testSession.getStatoCorrente().currentResult == TestSession.Result.CONTINUA; //call chain
		BufferedImage image;
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

	
	/**
	 * @param database
	 */
	public void setDB(final FSTdatabaseAIMO database) {
		this.db=database;
	}
	
	/**
	 * controllo autorizzazione
	 * @param user
	 * @param psw
	 * @return
	 */
	public Boolean checkAuthorization(final String user,final String psw) {
		return db.checkAuthorization(user, psw);
	}
	
	/**
	 * aggiungi dottore
	 * @param d
	 * @return
	 */
	public Boolean addDoctor(final Doctor doc) {
		return db.insertDoc(doc);
	
	}
	
	/**
	 * assegna paziente
	 * @param p
	 * @return
	 */
	public Boolean assignP(final Patient pat) {
		return db.assignPatDoc(pat, db.getDoc());
	}
	

}
