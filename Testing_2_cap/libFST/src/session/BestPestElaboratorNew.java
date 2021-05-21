package session;



import session.TestSession.Result;


/**
 * Algoritmo PEST 
 */

public class BestPestElaboratorNew extends AbstractPestBase {

	/**
	 * prossima profondità
	 */
	private int nextDepth;
	
	/**
	 * massima profondità
	 */
	private transient int maxDepth;  //non è possibile renderlo final
	/**
	 * limite sinistro
	 */
	private transient int leftLimit;
	
	/**
	 * limite destro
	 */
	private transient int rightLimit;
	/**
	 * possibilità se all'inizio del test
	 */
	private transient int chance;
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
		if(nextDepth<=maxDepth && nextDepth>=getRightLimit()) {
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
		setRightLimit(minValue);
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
	 */public void computeNextDepth(final AbstractPestBase.Soluzione solution) {
		double value;

		if (solution == Soluzione.SBAGLIATA && chance > 0 && certifierStatus.currentDepth == maxDepth) {
			chance--;
		} else if (solution == Soluzione.SBAGLIATA && chance == 0
				&& certifierStatus.currentDepth == maxDepth) {
			certifierStatus.currentResult = Result.FINE_NON_CERTIFICATA;
		} else if (solution == Soluzione.STOP) {
			// Nothing (end button)
			certifierStatus.currentResult=Result.FINE_NON_CERTIFICATA;
		} else if (solution == Soluzione.GIUSTA) {
			leftLimit = certifierStatus.currentDepth;

			// Numerical rounding (Floor: round down)
			value = ((double) leftLimit + getRightLimit()) / 2;
			nextDepth = (int) (Math.floor(value));

			// Next depth
			certifierStatus.currentDepth = nextDepth;

			if ((leftLimit - getRightLimit()) <= 1) { //uso di letterale
				certifierStatus.currentResult = Result.FINE_CERTIFICATA;
				certifierStatus.currentDepth = leftLimit;
			}
		} else {
			setRightLimit(certifierStatus.currentDepth);

			// Numerical rounding (Ceil: round up)
			value = ((double) leftLimit + getRightLimit()) / 2;
			nextDepth = (int) (Math.ceil(value));

			// Next depth
			certifierStatus.currentDepth = nextDepth;

			if ((leftLimit - getRightLimit()) <= 1) { //uso di letterale
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

	public int getRightLimit() {
		return rightLimit;
	}

	public void setRightLimit(int rightLimit) {
		this.rightLimit = rightLimit;
	}
}
