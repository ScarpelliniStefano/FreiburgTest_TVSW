package session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import draw.AbstractAngleCalculus;
import session.AbstractPestBase.CertifierStatus;
import test.DatiGenerazione;
import test.FSTest.Scelta;

/**
 * @author stefa
 * classe sessione di test
 */
public class TestSession{
	///PMD1: conversione di tutti i nomi di metodi 
	// 		con le lettere maiuscole iniziali in minuscole	
	//      e i nomi delle classi con l'iniziale maiuscola e senza simboli (_)
	//PMD2: aggiunta dei commenti a ogni attributo e metodo della classe
	//PMD3: aggiunta parentesi graffe negli if-else
	//PMD4: aggiunto final a alcune variabili che potevano essere non inizializzate
	// (MethodArgumentCouldBeFinal: Parameter is not assigned and could be declared
	//  final)
	//PMD5: confronti tra valori cambiati in compareTo o metodi di confronto
	//PMD6: accorciamento di alcuni nomi di parametro
	
    /** risultato */
    private static DatiGenerazione risultato=new DatiGenerazione();
	/** the tester. */
	private static BestPestElaboratorNew testElab;
	/** the list of session story */
	private static List<RispostaSingola> sessionStory = new ArrayList</*RispostaSingola*/>();	
		/**
		 * @author stefa
		 *Single answer given by the user
		 */
		public static class RispostaSingola {
			/**
			 * risposta
			 */
			private final transient String risposta; //aggiunto "transient"
			//membro che se viene serializzata la classe, non viene trasferito ma perso
			/**
			 * Costruzione della singola risposta della sessione
			 * 
			 * @param timeTaken istante nel quale è stata data la risposta risposta 
			 */
			public RispostaSingola(final String sceltaFatta,final String sceltaCorrente,final int profCorrente,final double angolo,final Date timeTaken) {
				//String sceltaFattaS = (sceltaFatta == "stop") ? "skip" : sceltaFatta.toString();
				final String sceltaFattaS = "stop".equals(sceltaFatta) ? "skip" : sceltaFatta/*.toString()*/;
				risposta = sceltaFattaS + "," + sceltaCorrente/*.toString()*/ + "," + profCorrente + "," + angolo + "," + timeTaken;
			}

			@Override
			public String toString() {
				return risposta;
			}
		}
	
	/**
	 * The Enum Result della sessione alla fine
	 */
	public enum Result {
		/** Continua test */
		CONTINUA,
		/** Fine certificata */
		FINE_CERTIFICATA,
		/** Fine non certificata */
		FINE_NON_CERTIFICATA // non può essere registrata profondità
	}
	
	
	
	/**
	 * costruttore
	 */
	public TestSession() {
		sessionStory.clear();
	}
	
		
	
	
	
	/**
	 * Ritorna la profondità corrente (come stabilito dal certificatore)
	 *
	 * @return profondità corrente
	 */
	public int getProfonditaCorrente() {
		return testElab.getCurrentDepth();
	}

	/**
	 * Ritorna lo stato corrente
	 *
	 * @return stato corrente
	 */
	public CertifierStatus getStatoCorrente() {
		return testElab.getCurrentStatus();
	}
	
	
		/**
		* funzione di inzio del test dati i dati di generazione
		*
		* @param result    dati di generazione strutturati come DatiGenerazione
		* 
		* @return      Stream immagine
		*/		
	public static Scelta iniziaTest(final DatiGenerazione result) {
		
		
		if(risultato.getAngolo()==-1) {
			risultato=result;
		}
		else {
			risultato.setXBar(risultato.getLivMax());
		}
		
		testElab=new BestPestElaboratorNew(risultato.getLivMax(), risultato.getLivMin());
		risultato.setAngolo(AbstractAngleCalculus.calcolaAngolo(risultato));
		return Scelta.CORRETTO;
		
				
	}
	/**
	 * controllo della risposta e restituzione della scelta
	 * 
	 * @param rispostaData la risposta data dall'utente
	 * @return Scelta scelta decisa dal tester
	 */
	public Scelta controlloRisposta(final String rispostaData) {
		Scelta res;
		final int currentDepth = testElab.getCurrentDepth(); // Initialized before computeNextDepth()
		if (/*rispostaData=="stop"*/"stop".equals(rispostaData)) {
			testElab.computeNextDepth(AbstractPestBase.Soluzione.STOP);
			res = Scelta.FINISCI;
		} else if (/*rispostaData==*/(risultato.isPos() ? "forward" : "behind").equals(rispostaData)) {
			testElab.computeNextDepth(AbstractPestBase.Soluzione.GIUSTA);
			if(Result.CONTINUA==testElab.getCurrentStatus().currentResult) { //call chain e inversione (!=)
				res = Scelta.CORRETTO;
			}else {
				res=Scelta.FINISCI;
			}
		} else {
			assert /*rispostaData != */!(risultato.isPos() ? "forward" : "behind").equals(rispostaData) && /*rispostaData !=*/ !"stop".equals(rispostaData);
			testElab.computeNextDepth(AbstractPestBase.Soluzione.SBAGLIATA);
			if(/*testElab.getCurrentStatus().currentResult!=*/Result.CONTINUA==testElab.getCurrentStatus().currentResult) { //call chain e inversione
				res = Scelta.SBAGLIATO;
			}else {
				res=Scelta.FINISCI;
			}


		}

		risultato.setXBar(getProfonditaCorrente());
		sessionStory.add(new RispostaSingola(rispostaData, risultato.isPos() ? "forward" : "behind", currentDepth, risultato.getAngolo(), new Date()));

		return res;
	}
	
	/**
	 * Ritorna la lista di stringhe rappresentanti la sessione
	 * 
	 * @return result
	 */
	public List<String> getSessionResults() {
		final List<String> result = new ArrayList</*String*/>();
		for (final RispostaSingola sa : sessionStory) {
			result.add(sa.toString());
		}
		return result;
	}

	/**
	 * Ritorna le risposte della sessione
	 *
	 * @return risposte della sessione
	 */
	public List<RispostaSingola> getSessionAnswers() {
		return Collections.unmodifiableList(sessionStory); //static property access
	}
	
	
}
