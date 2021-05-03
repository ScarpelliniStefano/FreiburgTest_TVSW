package libFSTest.draw;

import libFSTest.test.DatiGenerazione;

public class AngleCalculus {
	/**
	* funzione di calcolo angolo
	*
	* @param diag    diagonale schermo
	* @param xBar    distanza barre
	* @param wFin    larghezza finestra (pixel)
	* @param dist    distanza utente dallo schermo (mm)
	* 
	* @return      angolo (secondi d'arco)
	*/	
	public static int calcolaAngolo(DatiGenerazione data) {
		        //calcolo angolo
				double distanzaBarreMM=(MonitorWidthMM(data.getMonitorSize(),(int)data.getDimensione().getWidth(),(int)data.getDimensione().getHeight())*data.getXBar())/(int)data.getDimensione().getWidth();
				double angRad=Math.atan((distanzaBarreMM/2)/data.getDistSchermo());
				
				int angle=(int)(angRad*206265);
				data.setAngolo(angle);
				return angle;
	}
	
	/**
	* restituisce la misura in millimetri della larghezza dello schermo
	*
	* @param diagonale  la diagonale del monitor in decimi di pollice
	 * @return      misura in mm larghezza schermo
	 */	
	public static double MonitorWidthMM(double diagonale,int w,int h) {
	double dPixel=Math.sqrt(Math.pow(w, 2)+Math.pow(h, 2)); //diagonale in pixel
	double wPollici=w*((diagonale/10)/dPixel); //larghezza in pollici
	double wMM=wPollici*25.4; //larghezza in mm
	return wMM;
	}
}
