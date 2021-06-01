package fstTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
//import java.util.Collections;
//import java.util.Date;
import java.util.List;

import fstTest.FSTest.Scelta;
import fstTest.PestBase.CertifierStatus;

//import fstTest.FSTest.Scelta;
//import fstTest.PestBase.CertifierStatus;


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
	//@ spec_public
	//@ non_null
    private static DatiGenerazione risultato=new DatiGenerazione();
	/** the tester. */
    //@ spec_public 
	private static BestPestElaboratorNew testElab;
	/** the list of session story */
	//@ spec_public
	//@ non_null
	private static List<RispostaSingola> sessionStory = new ArrayList</*RispostaSingola*/>();	
		/**
		 * @author stefa
		 *Single answer given by the user
		 */
		public static class RispostaSingola {
			/**
			 * risposta
			 */
			/*@ spec_public @*/
			private final transient String risposta; //aggiunto "transient"
			//membro che, se viene serializzata la classe, non viene trasferito ma perso
			
			/**
			 * Costruzione della singola risposta della sessione
			 * @param date 
			 * 
			 * @param timeTaken istante nel quale è stata data la risposta risposta 
			 */
			/*@ requires sceltaFatta=="stop" || sceltaFatta=="forward" || sceltaFatta=="behind";
			  @	requires sceltaCorrente=="stop" || sceltaCorrente=="forward" || sceltaCorrente=="behind";
			  @ requires profCorrente<=risultato.getLivMax() && profCorrente>=risultato.getLivMin();
			  @ requires angolo>0.0;
			  @ requires timeTaken!=null;
			  @ ensures risposta!=null;
			  @*/
			public RispostaSingola(final String sceltaFatta,final String sceltaCorrente,final int profCorrente,final double angolo,final Date timeTaken) {
				//String sceltaFattaS = (sceltaFatta == "stop") ? "skip" : sceltaFatta.toString();
				final String sceltaFattaS = "stop".equals(sceltaFatta) ? "skip" : sceltaFatta.toString();
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
	
	
	
	
	public TestSession() {
		sessionStory.clear();
	}
	
		
	
	
	
	//@ ensures \result==testElab.getCurrentDepth();
	public /*@ pure @*/ int getProfonditaCorrente() {
		return testElab.getCurrentDepth();
	}

	//@ ensures \result==testElab.getCurrentStatus();
	public /*@ pure @*/ CertifierStatus getStatoCorrente() {
		return testElab.getCurrentStatus();
	}
	
	
	//@ requires risult!=null && risult.getLivMax()>=0;
	//@ ensures \result==Scelta.CORRETTO;
	//@ ensures testElab!=null;
	//@ ensures \old(risultato.getAngolo())!=risultato.getAngolo();
	public static Scelta iniziaTest(final DatiGenerazione risult) {
		
		
		if(risultato.getAngolo()==-1) {
			risultato=risult;
		}
		else {
			risultato.setXBar(risultato.getLivMax());
		}
		
		testElab=new BestPestElaboratorNew(risultato.getLivMax(), risultato.getLivMin());
		risultato.setAngolo(AbstractAngleCalculus.calcolaAngolo(risultato));
		return Scelta.CORRETTO;
		
				
	}
	
	//@ requires risultato!=null && risultato.getLivMax()>=0;
	//@ requires rispostaData=="stop" || rispostaData=="forward" || rispostaData=="behind";
	//@ ensures \result!=null;
	//@ ensures sessionStory.size()>\old(sessionStory.size());
	public Scelta controlloRisposta(final String rispostaData) {
		Scelta res;
		final int currentDepth = testElab.getCurrentDepth(); // Initialized before computeNextDepth()
		//@ assert currentDepth>=0;
		if ("stop".equals(rispostaData)) {
			testElab.computeNextDepth(PestBase.Soluzione.STOP);
			res = Scelta.FINISCI;
		} else if ((risultato.isPos() ? "forward" : "behind").equals(rispostaData)) {
			testElab.computeNextDepth(PestBase.Soluzione.GIUSTA);
			if(Result.CONTINUA==testElab.getCurrentStatus().currentResult) { //call chain e inversione (!=)
				res = Scelta.CORRETTO;
			}else {
				res=Scelta.FINISCI;
			}
		} else {
			//assert /*rispostaData != (risultato.isPos() ? "forward" : "behind").equals(rispostaData) &&  !"stop".equals(rispostaData);
			testElab.computeNextDepth(PestBase.Soluzione.SBAGLIATA);
			if(Result.CONTINUA==testElab.getCurrentStatus().currentResult) { //call chain e inversione
				res = Scelta.SBAGLIATO;
			}else {
				res=Scelta.FINISCI;
			}


		}

		risultato.setXBar(getProfonditaCorrente());
		sessionStory.add(new RispostaSingola(rispostaData, risultato.isPos() ? "forward" : "behind", currentDepth, risultato.getAngolo(), new Date()));
		return res;
	}
	
	//@ ensures \result!=null;
	public List<String> getSessionResults() {
		final List<String> result = new ArrayList<>();
		for (final RispostaSingola sa : sessionStory) {
			result.add(sa.toString());
		}
		return result;
	}

	//@ ensures \result!=null;
	public List<RispostaSingola> getSessionAnswers() {
		return Collections.unmodifiableList(sessionStory); //static property access
	}
	
	
}
