package fstTest;

import fstTest.TestSession.Result;

public abstract class PestBase {

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
	
	

	/** stato certificato */
	/*@ spec_public @*/
	private CertifierStatus certifierStatus=new CertifierStatus();

	/**
	 * Classe CertifierStatus
	 */
	static public class CertifierStatus {

		/** ProfonditÓ corrente */
		public int currentDepth;

		/** Risultato corrente */
		public TestSession.Result currentResult=Result.CONTINUA; 
		/*
		 * Bug: Read of unwritten public or protected field 
		 * currentResult in session.PestBase$CertifierStatus.toString()
		 * Se nullo o non refereziato, potrebbe puntare a 
		 * un oggetto non inizializzato */

		@Override
		public String toString() {
			switch (currentResult) {
			case FINE_CERTIFICATA:
				return "CERTIFICATO a livello: " + currentDepth;
			case FINE_NON_CERTIFICATA:
				return "FINITO ma NON CERTIFICATO fino al livello: " + currentDepth;
			case CONTINUA:
				return "NON COMPLETATO (Testing fermato a " + currentDepth + ")";
			}
			return "";
		}
	}

	/**
	 * Get profonditÓ corrente
	 *
	 * @return la profonditÓ corrente
	 */
	
	public /*@pure@*/int getCurrentDepth() {
		return getCertifierStatus().currentDepth;
	}

	abstract void computeNextDepth(Soluzione sol);

	/**
	 * Get stato corrente
	 * 
	 * @return
	 */
	abstract public /*@pure@*/ CertifierStatus getCurrentStatus();

	public /*@pure@*/ CertifierStatus getCertifierStatus() {
		return certifierStatus;
	}

	public void setCertifierStatus(CertifierStatus certifierStatus) {
		this.certifierStatus = certifierStatus;
	}
}