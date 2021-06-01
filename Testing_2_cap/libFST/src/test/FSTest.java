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
import session.AbstractPestBase;
import session.AbstractPestBase.CertifierStatus;
import session.TestSession.Result;


/**
 * @author stefa
 * classe test freiburg
 */
public class FSTest extends Observable {
	//CODE REFACTOR:
	//LONG METHOD: i metodi che sono troppo lunghi sono stati 
	//suddivisi in sottometodi privati
	
	///PMD1: conversione di tutti i nomi di metodi 
	// 		con le lettere maiuscole iniziali in minuscole	
	//PMD2: aggiunta dei commenti a ogni attributo e metodo della classe
	//PMD3: aggiunta parentesi graffe negli if-else
	//PMD4: aggiunto final a alcune variabili che potevano essere non inizializzate
	// (MethodArgumentCouldBeFinal: Parameter is not assigned and could be declared
	//  final)
	//PMD5: confronti tra valori cambiati in compareTo o metodi di confronto
	
	/** The result. */
	private static DatiGenerazione datagen=new DatiGenerazione();

	/** the tester. */
	public static TestSession testSession;
	
	/** The stream of image */
	private static ByteArrayOutputStream imagebuffer;
	
	
	/**
	 * database
	 */
	private transient FSTdatabaseAIMO database;
	
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
		risultatoSplitted();
		if(datagen.getAngolo()==-1) {
			datagen=result;
		}
		
		testSession = new TestSession();
		final Scelta inizio=TestSession.iniziaTest(datagen);
		ByteArrayInputStream imgByteArray = null;
		final BufferedImage image = image();
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
		
		return imgByteArray;
		
				
	}

	private static void risultatoSplitted() {
		risultatoAngle();
		if (datagen.getAngolo() == -1) {
		} else {
			datagen.setPos(false);
		}
	}

	private static void risultatoAngle() {
		if (datagen.getAngolo() == -1) {
		} else {
			datagen.setXBar(datagen.getLivMax());
		}
	}

	private static BufferedImage image() {
		BufferedImage image;
			image = GeneraImg.generaImmagine(datagen.getWRect(), datagen.getHRect(), datagen.getHBar(),
					datagen.getXBar(), datagen.getC1(), datagen.getC2());
		return image;
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
			risultato();
		}
		return scelta;
		
	}

	private static void risultato() {
		if (testSession.getStatoCorrente().currentResult == Result.FINE_NON_CERTIFICATA) {
			datagen.setAngolo(0);
			datagen.setLivello(0);
		} else {
			datagen.setAngolo(AbstractAngleCalculus.calcolaAngolo(datagen));
			//varie chain call
			datagen
					.setLivello(1000
							* (AbstractAngleCalculus.monitorWidthMM(datagen.getMonitorSize(),
									(int) datagen.getDimensione().getWidth(),
									(int) datagen.getDimensione().getHeight()) * datagen.getXBar())
							/ (int) datagen.getDimensione().getWidth());
		}
	}
	
	
	
	/**
	 * metodo per cambio posizione
	 */
	private static void changePos() {
		//Bug: Random object created and used only 
		//once in test.FSTest.changePos()
		datagen.setPos(ThreadLocalRandom.current().nextBoolean()); //chain call
		//prima era new Random().nextBoolean()
	}
	
	/**
	 * @return nuova immagine
	 */
	public static InputStream settaNuovaImg() {
		
		assert testSession.getStatoCorrente().currentResult == TestSession.Result.CONTINUA; //call chain
		final BufferedImage image = imageSplit();
		ByteArrayInputStream imgByteArray = null;
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
		return imgByteArray;
	}

	private static BufferedImage imageSplit() {
		BufferedImage image;
		changePos();
		image = GeneraImg.modificaM(datagen.getXBar(), datagen);
		return image;
	}
	
	/**
	 * @param database
	 */
	public void setDB(final FSTdatabaseAIMO database) {
		this.database=database;
	}
	
	/**
	 * controllo autorizzazione
	 * @param user
	 * @param psw
	 * @return
	 */
	public Boolean checkAuthorization(final String user,final String psw) {
		return database.checkAuthorization(user, psw);
	}
	
	/**
	 * aggiungi dottore
	 * @param d
	 * @return
	 */
	public Boolean addDoctor(final Doctor doc) {
		return database.insertDoc(doc);
	
	}
	
	/**
	 * assegna paziente
	 * @param p
	 * @return
	 */
	public Boolean assignP(final Patient pat) {
		return database.assignPatDoc(pat, database.getDoc());
	}

}
