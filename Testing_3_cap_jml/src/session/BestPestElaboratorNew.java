package session;



import session.TestSession.Result;


/**
 * Algoritmo PEST 
 */

public class BestPestElaboratorNew extends PestBase {

	/**
	 * prossima profondità
	 */
	private int nextDepth=-1;
	
	/**
	 * massima profondità
	 */
	private transient int maxDepth=-1;  //non è possibile renderlo final
	/**
	 * limite sinistro
	 */
	private transient int leftLimit=-1;
	
	/**
	 * limite destro
	 */
	protected transient int rightLimit=-1;
	/**
	 * possibilità se all'inizio del test
	 */
	private transient int chance=-1;
	/**
	 * @return prossima profondità
	 */
	public int getNextDepth() {
		return nextDepth;
	}

	/**
	 * @param nextDepth profondità successiva
	 */
	public void setNextDepth(final int nextDepth) {
		if(nextDepth<=maxDepth && nextDepth>=rightLimit) {
			this.nextDepth = nextDepth;
		}else {
			throw new IllegalArgumentException();
		}
	}

	

	

	/** costructor
	 * @param maxVal
	 * @param minVal
	 */
	public BestPestElaboratorNew(final int maxVal,final int minVal) {
		super();
		certifierStatus = new CertifierStatus();
		if (maxVal >= 1) { //usati letterali
			certifierStatus.currentDepth = maxVal;
		}else {
			throw new IllegalArgumentException();
		}
		int minValue=minVal;
		if(minVal<1 || minVal>maxVal) {
			minValue=maxVal;
		}
		maxDepth = maxVal;
		leftLimit = maxVal;
		rightLimit = minValue;
		nextDepth=maxVal;
		chance = 1;

		certifierStatus.currentResult = Result.CONTINUA;
	}

	
	/**
	 * metodo per decidere la prossima profondità
	 */
	@Override
	/*
	 * metodo con alta complessità e preso da un 
	 * metodo astratto (non posso implementare un metodo astratto)
	 */
	protected void computeNextDepth(final PestBase.Soluzione solution) {
		double value;

		if (solution == PestBase.Soluzione.SBAGLIATA && chance > 0 && certifierStatus.currentDepth == maxDepth) {
			chance--;
		} else if (solution == PestBase.Soluzione.SBAGLIATA && chance == 0
				&& certifierStatus.currentDepth == maxDepth) {
			certifierStatus.currentResult = Result.FINE_NON_CERTIFICATA;
		} else if (solution == PestBase.Soluzione.STOP) {
			// Nothing (end button)
			certifierStatus.currentResult=Result.FINE_NON_CERTIFICATA;
		} else if (solution == PestBase.Soluzione.GIUSTA) {
			leftLimit = certifierStatus.currentDepth;

			// Numerical rounding (Floor: round down)
			value = ((double) leftLimit + rightLimit) / 2;
			nextDepth = (int) (Math.floor(value));

			// Next depth
			certifierStatus.currentDepth = nextDepth;

			if ((leftLimit - rightLimit) <= 1) { //uso di letterale
				certifierStatus.currentResult = Result.FINE_CERTIFICATA;
				certifierStatus.currentDepth = leftLimit;
			}
		} else {
			rightLimit = certifierStatus.currentDepth;

			// Numerical rounding (Ceil: round up)
			value = ((double) leftLimit + rightLimit) / 2;
			nextDepth = (int) (Math.ceil(value));

			// Next depth
			certifierStatus.currentDepth = nextDepth;

			if ((leftLimit - rightLimit) <= 1) { //uso di letterale
				certifierStatus.currentResult = Result.FINE_CERTIFICATA;
				certifierStatus.currentDepth = leftLimit;
			}
		}
	}

	@Override
	public CertifierStatus getCurrentStatus() {
		assert maxDepth!=-1;
		return certifierStatus;
	}
}
