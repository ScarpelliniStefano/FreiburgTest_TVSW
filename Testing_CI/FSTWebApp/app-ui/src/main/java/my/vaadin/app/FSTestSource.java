package my.vaadin.app;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import com.vaadin.server.StreamResource.StreamSource;

import libFSTest.draw.AngleCalculus;
import libFSTest.draw.GeneraImg;
import libFSTest.session.Test_session;
import libFSTest.session.PestBase.CertifierStatus;
import libFSTest.session.Test_session.Result;
import libFSTest.test.DatiGenerazione;
import libFSTest.test.FSTest.Scelta;

public class FSTestSource implements StreamSource {

	
	private static final long serialVersionUID = 1L;

	/** Il risultato */
	private static DatiGenerazione risultato=new DatiGenerazione();

	/** il tester della sessione corrente. */
	private static Test_session testSession=null;
	static boolean testInit=false;
	static ByteArrayOutputStream imagebuffer = null;
	
	/**
	 * costruttore
	 * @param result dati di generazione della sessione corrente
	 */
	public FSTestSource(DatiGenerazione result) {
		testInit=false;
		risultato=result;
		risultato.setSesso("Non disponibile");
		testSession = new Test_session();
	}
	
		
	   /**
		* funzione di generazione immagine del test di freiburg, dati i parametri di generazione
		*
		* @param result    dati di generazione strutturati come DatiGenerazione
		* 
		* @return          Stream immagine
		*/		
	public static InputStream IniziaTest(DatiGenerazione result) {
		if(risultato.getAngolo()==-1) {
			risultato=result;
		}
		else {
			risultato.setXBar(risultato.getLivMax());
		}
		
		//testSession = new Test_session(risultato);
		Scelta inizio=Test_session.IniziaTest(risultato);
		if(inizio==Scelta.CORRETTO) {
			BufferedImage image=null;
			image=GeneraImg.GeneraImmagine(risultato.getDimensione().width-80,risultato.getDimensione().height-80,risultato.getWRect(), risultato.getHRect(), risultato.getHBar(), risultato.getXBar(), risultato.getC1(), risultato.getC2());	
			
			//prova di inserimento immagine nello stream
				try {
					// Write the image to a buffer
							imagebuffer = new ByteArrayOutputStream();
							ImageIO.write(image, "png", imagebuffer);
							// Return a stream from the buffer
							testInit=true;
							return new ByteArrayInputStream(imagebuffer.toByteArray());
				}catch(IOException e) {
					return null;
				}
		}else		return null;
		
				
	}
	
	/**
	 * Ritorna la profondità corrente (come stabilita dal certificatore), attenzione perchè
	 * il certificatore potrebbe aver deciso di fermare il test.
	 *
	 * @return la profondità corrente
	 */
	public int getCurrentDepth() {
		return testSession.getProfonditaCorrente();
	}

	/**
	 * Prendi stato corrente
	 *
	 * @return stato corrente
	 */
	public static CertifierStatus getCurrentStatus() {
		return testSession.getStatoCorrente();
	}
	
	/**
	 * controllo della risposta con restituzione della scelta di continuare o fermarsi
	 *   
	 * @param rispostaData la risposta data dall'utente
	 * @return scelta la scelta fatta dal certificatore
	 */
	public static Scelta ControlloRisposta(String rispostaData) {
		Scelta scelta;
		scelta=testSession.ControlloRisposta(rispostaData); // Initialized before computeNextDepth()
		if(testSession.getStatoCorrente().currentResult!=Result.CONTINUA) {
			risultato.setLivello(1000* (AngleCalculus.MonitorWidthMM(risultato.getMonitorSize(),(int)risultato.getDimensione().getWidth(),(int)risultato.getDimensione().getHeight())*risultato.getXBar())/(int)risultato.getDimensione().getWidth());
		    scelta=Scelta.FINISCI;
			testInit=false;
		}
		return scelta;
		
	}
	
	/**
	 * setta la nuova immagine del test e la restituisce
	 * 
	 * @return Stream immagine
	 */
	public static InputStream settaNuovaImg() {
		
		assert testSession.getStatoCorrente().currentResult == Test_session.Result.CONTINUA;
		BufferedImage image = null;
		
		risultato.setPos(new Random().nextBoolean());
		image=GeneraImg.modificaMWeb(risultato.getXBar(), risultato);
		risultato.setAngolo(AngleCalculus.calcolaAngolo(risultato));
						
		if(image!=null)	{
		//prova di inserimento immagine nello stream
		try {
			// Write the image to a buffer
					imagebuffer = new ByteArrayOutputStream();
					ImageIO.write(image, "png", imagebuffer);
					// Return a stream from the buffer
					return new ByteArrayInputStream(imagebuffer.toByteArray());
		}catch(IOException e) {
			return null;
		}
			}else return null;
		
	}


	@Override
	public InputStream getStream() {
		if(testInit==false) {
		return FSTestSource.IniziaTest(risultato);
		}else {
			return FSTestSource.settaNuovaImg();
		}
	}
	
	
}