package test;

import session.AbstractPestBase;
import session.BestPestElaboratorNew;
import session.AbstractPestBase.Soluzione;
import session.TestSession.Result;

public class OtherFunction {
	public static Result PossibleCert(final int leftLim, final int rightLim,final boolean maxDepth, final int chance, final AbstractPestBase.Soluzione sol) throws Exception{
		if(leftLim>0 && rightLim>0 && rightLim<=leftLim && sol!=null && leftLim<21 && rightLim<21 && chance>=0) {
			if (sol == Soluzione.SBAGLIATA && chance == 0 && maxDepth) {
				return Result.FINE_NON_CERTIFICATA;
			} else if (sol == Soluzione.STOP) {
				return Result.FINE_NON_CERTIFICATA;
			} else if (sol == Soluzione.GIUSTA) {
				if ((leftLim - rightLim) <= 1) {
					return Result.FINE_CERTIFICATA;
				}
			} else {
				if ((leftLim - rightLim) <= 1) {
					return Result.FINE_CERTIFICATA;
				}
			}
			return Result.CONTINUA;
		}
		else
			throw new Exception("Errore in input");
		
	}
}
