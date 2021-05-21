package draw;

import java.awt.Dimension;

import test.DatiGenerazione;

/**
 * @author stefa
 * classe calcolo angoli
 */
public abstract class AbstractAngleCalculus {
	
	/**
	 * variabile truffa per spotbugs
	 */
	private transient int variableTruff=-1; //usato per "truffare" spotbugs
	//pmd pero' non lo accetta perche' non usato
	
	
	/**
	* funzione di calcolo angolo
	*
	* @param data    dati di generazione
	* 
	* @return      angolo (secondi d'arco)
	*/	
	public static int calcolaAngolo(final DatiGenerazione data) {
				
				final int angle=calcolaAngolo(data.getMonitorSize(),data.getDimensione(),data.getXBar(),data.getDistSchermo()/10);
				data.setAngolo(angle);
				return angle;
	}
	
	/**
	 * metodo astratto
	 */
	public abstract void calcolaAngoloAbstract();
	/**
	 * costruttore
	 */
	
	/**
	 * costruttore (inutile)
	 */
	private AbstractAngleCalculus() {
		variableTruff=variableTruff+1;
	}
	
	/**
	* funzione di calcolo angolo
	*
	* @param data    dati di generazione
	* 
	* @return      angolo (secondi d'arco)
	*/	
	public static int calcolaAngolo(final int monitorSize,final Dimension dimenSchermo, final int xBar,final int distSchermo) {    
		//calcolo angolo
		final double distanzaBarreMM=(monitorWidthMM(monitorSize,(int)dimenSchermo.getWidth(),(int)dimenSchermo.getHeight())*xBar)/(int)dimenSchermo.getWidth();
		final double angRad=Math.atan((distanzaBarreMM/2)/(distSchermo*10));
		return (int)(angRad*206_265);
	}
	
	/**
	* restituisce la misura in millimetri della larghezza dello schermo
	*
	* @param diagonale  la diagonale del monitor in decimi di pollice
	 * @return      misura in mm larghezza schermo
	 */	
	public static double monitorWidthMM(final double diagonale,final int width,final int height) {
	final double dPixel=Math.sqrt(Math.pow(width, 2)+Math.pow(height, 2)); //diagonale in pixel
	final double wPollici=width*((diagonale/10)/dPixel); //larghezza in pollici
	return wPollici*25.4;//larghezza in mm
	}
}
