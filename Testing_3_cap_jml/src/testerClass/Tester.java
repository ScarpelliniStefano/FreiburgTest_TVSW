package testerClass;

import java.awt.Color;

import test.DatiGenerazione;

public class Tester {

	public static void main(String[] args) {
		System.out.println("ciao");
		DatiGenerazione dgen=new DatiGenerazione("gino","man",-5,-1); 
		/*Dead store to dgen in TesterClass.Tester.main(String[])
		 * => aggiunto un dgen.setC1();
		 */
		dgen.setC1(Color.BLUE);
		

	}

}
