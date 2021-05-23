package session;

import session.TestSession.Result;

/**
 * @author stefa
 *	classe base pest
 */
public abstract class AbstractPestBase {

	

	/**
	 * The Enum Solution
	 */
	public enum Soluzione {
		/** Corretta */
		GIUSTA,
		/** Sbagliata */
		SBAGLIATA,
		/** Fine */
		STOP
	}
	
	

	public static CertifierStatus certifierStatus = new CertifierStatus();

	/**
	 * Classe CertifierStatus
	 */
	static public class CertifierStatus {

		/** Profondità corrente */
		public transient int currentDepth;

		/** Risultato corrente */
		public transient TestSession.Result currentResult=Result.CONTINUA; 
		/*
		 * Bug: Read of unwritten public or protected field 
		 * currentResult in session.PestBase$CertifierStatus.toString()
		 * Se nullo o non refereziato, potrebbe puntare a 
		 * un oggetto non inizializzato */

		@Override
		public String toString() {
			String risposta="";
			switch (currentResult) {
			case FINE_CERTIFICATA:
				risposta= "CERTIFICATO a livello: " + currentDepth;
				break;
			case FINE_NON_CERTIFICATA:
				risposta= "FINITO ma NON CERTIFICATO fino al livello: " + currentDepth;
				break;
			case CONTINUA:
				risposta= "NON COMPLETATO (Testing fermato a " + currentDepth + ")";
				break;
			}
			return risposta;
		}
	}

	/**
	 * Get profondità corrente
	 *
	 * @return la profondità corrente
	 */
	public static int getCurrentDepth() {
		return certifierStatus.currentDepth;
	}

	/**
	 * metodo per computare la profondità
	 * successiva
	 * @param sol
	 */
	protected abstract void computeNextDepth(Soluzione sol);

	/**
	 * Get stato corrente
	 * 
	 * @return
	 */
	protected abstract CertifierStatus getCurrentStatus();
}