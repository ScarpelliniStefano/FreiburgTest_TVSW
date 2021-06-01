package fstTest;

import fstTest.TestSession.Result;

/**
 * Algoritmo PEST 
 */

public class BestPestElaboratorNew extends PestBase {

	/**
	 * prossima profondità
	 */
	/*@ spec_public @*/
	private int nextDepth=-1;
	
	/**
	 * massima profondità
	 */
	/*@ spec_public @*/
	private transient int maxDepth=-1;  //non è possibile renderlo final
	/**
	 * limite sinistro
	 */
	/*@ spec_public @*/
	private transient int leftLimit=-1;
	
	/**
	 * limite destro
	 */
	/*@ spec_public @*/
	protected transient int rightLimit=-1;
	/**
	 * possibilità se all'inizio del test
	 */
	/*@ spec_public @*/
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
	/*@ requires nextDepth>=0;
	  @ ensures this.nextDepth==nextDepth || this.nextDepth==maxDepth;
	  @*/
	public void setNextDepth(final int nextDepth) {
		if(nextDepth<=maxDepth && nextDepth>=rightLimit) {
			this.nextDepth = nextDepth;
		}else {
			this.nextDepth=maxDepth;
		}
	}

	

	

	/** costructor
	 * @param maxVal
	 * @param minVal
	 */
	/*@ requires maxVal>0 && minVal<=maxVal && minVal>=0;
	  @ ensures certifierStatus!=null;
	  @ ensures this.maxDepth==maxVal && this.nextDepth==maxVal;
	  @ ensures this.leftLimit==maxVal && (this.rightLimit==minVal || this.rightLimit==maxVal);
	  @ ensures chance>0;
	  @*/
	public BestPestElaboratorNew(final int maxVal,final int minVal) {
		super();
		setCertifierStatus(new CertifierStatus());
		if (maxVal >= 1) { //usati letterali
			getCertifierStatus().currentDepth = maxVal;
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

		getCertifierStatus().currentResult = Result.CONTINUA;
	}

	
	/**
	 * metodo per decidere la prossima profondità
	 */
	
	/*
	 * metodo con alta complessità e preso da un 
	 * metodo astratto (non posso implementare un metodo astratto)
	 */
	
	
	/*@ also requires solution==PestBase.Soluzione.SBAGLIATA ||
	 				  solution==PestBase.Soluzione.GIUSTA ||
	 				  solution==PestBase.Soluzione.STOP;
	  @*/
	@Override
	public void computeNextDepth(final PestBase.Soluzione solution) {
		double value;

		if (solution == PestBase.Soluzione.SBAGLIATA && chance > 0 && getCertifierStatus().currentDepth == maxDepth) {
			chance--;
		} else if (solution == PestBase.Soluzione.SBAGLIATA && chance == 0
				&& getCertifierStatus().currentDepth == maxDepth) {
			getCertifierStatus().currentResult = Result.FINE_NON_CERTIFICATA;
		} else if (solution == PestBase.Soluzione.STOP) {
			// Nothing (end button)
			getCertifierStatus().currentResult=Result.FINE_NON_CERTIFICATA;
		} else if (solution == PestBase.Soluzione.GIUSTA) {
			leftLimit = getCertifierStatus().currentDepth;

			// Numerical rounding (Floor: round down)
			value = ((double) leftLimit + rightLimit) / 2;
			/*@assert value>=0;@*/
			
			nextDepth = (int)value;
			//@ assert nextDepth<=value;
			
			// Next depth
			getCertifierStatus().currentDepth = nextDepth;

			if ((leftLimit - rightLimit) <= 1) { //uso di letterale
				getCertifierStatus().currentResult = Result.FINE_CERTIFICATA;
				getCertifierStatus().currentDepth = leftLimit;
			}
		} else {
			rightLimit = getCertifierStatus().currentDepth;

			// Numerical rounding (Ceil: round up)
			value = ((double) leftLimit + rightLimit) / 2;
			/*@assert value>=0;@*/
			
			nextDepth = (int) (value+1);
			//@ assert nextDepth>=value;
			// Next depth
			getCertifierStatus().currentDepth = nextDepth;

			if ((leftLimit - rightLimit) <= 1) { //uso di letterale
				getCertifierStatus().currentResult = Result.FINE_CERTIFICATA;
				getCertifierStatus().currentDepth = leftLimit;
			}
		}
	}

	/*@ also ensures certifierStatus!=null;
	  @*/
	@Override
	public /*@ pure @*/ CertifierStatus getCurrentStatus() {
		assert maxDepth!=-1;
		return getCertifierStatus();
	}

	//@ ensures \result>0;
	public /*@ pure @*/ int getRightLimit() {
		// TODO Auto-generated method stub
		return rightLimit;
	}
}
