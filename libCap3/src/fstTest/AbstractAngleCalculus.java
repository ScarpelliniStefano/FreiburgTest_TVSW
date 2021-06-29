package fstTest;

import java.awt.Dimension;



/**
 * @author stefa
 * classe calcolo angoli
 */
public abstract class AbstractAngleCalculus {
	/**
	* funzione di calcolo angolo
	*
	* @param data    dati di generazione
	* 
	* @return      angolo (secondi d'arco)
	*/	
	//@requires data.getMonitorSize()>0;
	//@requires data.getDimensione().width>0;
	//@requires data.getDimensione().height>0;
	//@requires data.getXBar()>0;
	//@requires data.getDistSchermo()>0;
	//@ensures \result>0;
	public static int calcolaAngolo(final DatiGenerazione data) {
				
				final int angle=calcolaAngolo(data.getMonitorSize(),data.getDimensione(),data.getXBar(),data.getDistSchermo()/10);
				data.setAngolo(Double.valueOf(angle));
				return angle;
	}
	
	/**
	 * metodo astratto
	 */
	public abstract void calcolaAngoloAbstract();
	/**
	 * costruttore
	 */
	private transient int a=-1;
	/**
	 * costruttore (inutile)
	 */
	public AbstractAngleCalculus() {
		a=1;
	}
	
	/**
	* funzione di calcolo angolo
	*
	* @param data    dati di generazione
	* 
	* @return      angolo (secondi d'arco)
	*/	
	//@requires monitorSize>0;
	//@requires dimenSchermo.width>0;
	//@requires dimenSchermo.height>0;
	//@requires xBar>0;
	//@requires distSchermo>0;
	//@ensures \result>0;
	public static int calcolaAngolo(final int monitorSize,final Dimension dimenSchermo, final int xBar,final int distSchermo) {    
		//calcolo angolo
		final double distanzaBarreMM=(monitorWidthMM(monitorSize,(int)dimenSchermo.getWidth(),(int)dimenSchermo.getHeight())*xBar)/(int)dimenSchermo.getWidth();
		//assert distanzaBarreMM>0;
		final double angRad=Math.atan((distanzaBarreMM/2)/(distSchermo*10));
		//assert angRad>0 && angRad<2;
		return (int)(angRad*206265);
	}
	
	/**
	* restituisce la misura in millimetri della larghezza dello schermo
	*
	* @param diagonale  la diagonale del monitor in decimi di pollice
	 * @return      misura in mm larghezza schermo
	 */	
	//@requires diagonale>0;
	//@requires width>0;
	//@requires height>0;
	//@ensures \result>0;
	public static double monitorWidthMM(final double diagonale,final int width,final int height) {
	final double dPixel=Math.sqrt(Math.pow(width, 2)+Math.pow(height, 2)); //diagonale in pixel
	final double wPollici=width*((diagonale/10)/dPixel); //larghezza in pollici
	//@assert wPollici==width*((diagonale/10)/dPixel);
	return wPollici*25.4;//larghezza in mm
	}
}
