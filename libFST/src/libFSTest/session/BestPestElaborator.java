package libFSTest.session;



import libFSTest.session.Test_session.Result;


/**
 * Algoritmo PEST 
 */

public class BestPestElaborator extends PestBase {

	private int nextDepth;
	public int getNextDepth() {
		return nextDepth;
	}

	public void setNextDepth(int nextDepth) {
		this.nextDepth = nextDepth;
	}

	private int maxDepth;
	private int leftLimit;
	private int rightLimit;
	private int chance;
	private double value;

	

	public BestPestElaborator(int maxValue,int minValue) {
		certifierStatus = new CertifierStatus();

		if (maxValue >= 1)
			certifierStatus.currentDepth = maxValue;
		else
			throw new IllegalArgumentException();

		maxDepth = maxValue;
		leftLimit = maxValue;
		rightLimit = minValue;
		chance = 1;

		certifierStatus.currentResult = Result.CONTINUA;
	}

	
	@Override
	void computeNextDepth(PestBase.Soluzione solution) {
		

		if (solution == PestBase.Soluzione.SBAGLIATA && chance > 0 && certifierStatus.currentDepth == maxDepth) {
			chance--;
		} else if (solution == PestBase.Soluzione.SBAGLIATA && chance == 0
				&& certifierStatus.currentDepth == maxDepth) {
			certifierStatus.currentResult = Result.FINE_NON_CERTIFICATA;
		} else if (solution == PestBase.Soluzione.STOP) {
			// Nothing (end button)
		} else if (solution == PestBase.Soluzione.GIUSTA) {
			leftLimit = certifierStatus.currentDepth;

			// Numerical rounding (Floor: round down)
			value = ((double) leftLimit + rightLimit) / 2;
			nextDepth = (int) (Math.floor(value));

			// Next depth
			certifierStatus.currentDepth = nextDepth;

			if ((leftLimit - rightLimit) <= 1) {
				certifierStatus.currentResult = Result.FINE_CERTIFICATA;
				certifierStatus.currentDepth = leftLimit;
			}
		} else if (solution == PestBase.Soluzione.SBAGLIATA) {
			rightLimit = certifierStatus.currentDepth;

			// Numerical rounding (Ceil: round up)
			value = ((double) leftLimit + rightLimit) / 2;
			nextDepth = (int) (Math.ceil(value));

			// Next depth
			certifierStatus.currentDepth = nextDepth;

			if ((leftLimit - rightLimit) <= 1) {
				certifierStatus.currentResult = Result.FINE_CERTIFICATA;
				certifierStatus.currentDepth = leftLimit;
			}
		}
	}

	@Override
	public CertifierStatus getCurrentStatus() {
		return certifierStatus;
	}
}
