package draw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.concurrent.ThreadLocalRandom;

import test.DatiGenerazione;


/**
 * @author stefa
 *	classe generazione immagine
 */
public class GeneraImg extends Observable{
		//PMD1: conversione di tutti i nomi di metodi 
		// 		con le lettere maiuscole iniziali in minuscole	
		//PMD2: aggiunta dei commenti a ogni attributo e metodo della classe
		//PMD3: rimosse parentesi tonde inutili
		//PMD4: aggiunta attributo final
	
	/**
	 * dimensione bordo rettangolo
	 */
	private final static int DIMBORDORECT=5;
	/**
	 * dimensione barre
	 */
	private final static int DIMBARRE=5;
	/**
	 * funzione di modifica della distanza delle barre
	 *    
	 * @param m la distanza delle barre
	 * @param risultato i dati di generazione per generare la nuova immagine
	 * @return immagine da visualizzare
	 */
	public static BufferedImage modificaM(final int distance,final DatiGenerazione risultato) {
		return generaImmagine(risultato.getWRect(), risultato.getHRect(), risultato.getHBar(), distance, risultato.getC1(), risultato.getC2());
		
	}
	
	/**
	 * funzione di modifica della distanza delle barre
	 *    
	 * @param m la distanza delle barre
	 * @param risultato i dati di generazione per generare la nuova immagine
	 * @return immagine da visualizzare
	 */
	/*public static BufferedImage modificaMWeb(final int distance,final DatiGenerazione risultato) {
		return generaImmagine(risultato.getDimensione().width-80,risultato.getDimensione().height-80,risultato.getWRect(), risultato.getHRect(), risultato.getHBar(), distance, risultato.getC1(), risultato.getC2());
		
	}*/
		/**
		* funzione di generazione immagine del test di 
		* freiburg dati i parametri di generazione
		* @param wRec               larghezza rettangolo (pixel)
		* @param hRec               altezza rettangolo (pixel)
		* @param hBar               altezza barre (pixel)
		* @param xBar               distanza barre (pixel)
		* @param c1                 colore barra sinistra (pixel)
		* @param c2                 colore barra destra (pixel)
		* @param diagonale          diagonale del monitor (in decimi di pollice)
		* @return      Stream immagine
		*/	
		public static BufferedImage generaImmagine(final int wRec,final int hRec,final int hBar,final int xBar,final Color color1,final Color color2) {
			final Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize(); //dimensione monitor //call chain
			//larghezza e altezza immagine
			final int wFin=(int) screenSize.getWidth();  //oggetto non creato localmente
			final int hFin=(int) screenSize.getHeight(); //oggetto non creato localmente
			return generaImmagine(wFin, hFin, wRec, hRec, hBar, xBar, color1, color2);

	    }
		
		/**
		* funzione di generazione immagine del test di 
		* freiburg dati i parametri di generazione
		*
		* @param width              larghezza immagine
		* @param height             altezza immagine
		* @param wRec               larghezza rettangolo (pixel)
		* @param hRec               altezza rettangolo (pixel)
		* @param hBar               altezza barre (pixel)
		* @param xBar               distanza barre (pixel)
		* @param c1                 colore barra sinistra 
		* @param c2                 colore barra destra 
		* @param diagonale          diagonale del monitor (in decimi di pollice)
		* 
		* @return      Stream immagine
		*/	
		public static BufferedImage generaImmagine(final int width,final int height,final int wRec,final int hRec,final int hBar,final int xBar,final Color color1,final Color color2) {
			//larghezza e altezza immagine
			final int wFin=width;
			final int hFin=height;
		
		
		//settaggio parametri interni per la generazione
		final BufferedImage image=new BufferedImage(wFin,hFin,BufferedImage.TYPE_INT_RGB);
		final Graphics2D drawable=image.createGraphics();

		final int centroImgx=wFin/2;
	    final int centroImgy=hFin/2;
	    
	    //disegno fondo nero
		drawable.setColor(Color.BLACK); //oggetto non creato localmente
		drawable.drawRect(1, 1, wFin, hFin); //oggetto non creato localmente

		//disegno quadrati randomici bianchi per miglioramento della profondit�
		Shape[][] figure= new Shape[50][50]; 
		for (int i=0; i<50; i++){ 
			for (int j=0; j<50; j++){ 
			//Bug: Random object created and used only once in 
			//draw.GeneraImg.generaImmagine(int, int, int, int, int, int, Color, Color)
			
			drawable.setColor( ThreadLocalRandom.current().nextFloat() > 0.75 ? Color.WHITE : Color.BLACK); 
			figure [i][j] = new Rectangle2D.Double(i*(wFin/50),j*(hFin/50),wFin/50,hFin/50); 
			drawable.fill(figure [i][j]); 
			} 
		} 
		
		//disegno rettangolo bianco contenente le barre
		for(int x=centroImgx-(wRec/2);x<=centroImgx+(wRec/2);x++) {
			for(int y=centroImgy-(hRec/2);y<=centroImgy+(hRec/2);y++) {
				
					drawable.setColor(Color.BLACK);
					drawable.drawLine(x, y, x, y);
			}
		}
		
		//disegno bordo del rettangolo contenente le barre 
		//(con uso di colere visibile da entrambi gli occhi)
		final Color cAssign=new Color((color1.getRed()+color2.getRed())/2,0,(color1.getBlue()+color2.getBlue())/2);
		for(int y=centroImgy-(hRec/2);y<=(centroImgy-(hRec/2))+DIMBORDORECT;y++) {
				drawable.setColor(cAssign);
				drawable.drawLine(centroImgx-(wRec/2), y, centroImgx+(wRec/2), y);
		}
		for(int y=centroImgy+(hRec/2)-DIMBORDORECT;y<=centroImgy+(hRec/2);y++) {
			drawable.setColor(cAssign);
			drawable.drawLine(centroImgx-(wRec/2), y, centroImgx+(wRec/2), y);
	    }
		for(int x=centroImgx+(wRec/2)-DIMBORDORECT;x<=centroImgx+(wRec/2);x++) {
			drawable.setColor(cAssign);
			drawable.drawLine(x,centroImgy-(hRec/2), x, centroImgy+(hRec/2));
	    }
		for(int x=centroImgx-(wRec/2);x<=(centroImgx-(wRec/2))+DIMBORDORECT;x++) {
			drawable.setColor(cAssign);
			drawable.drawLine(x,centroImgy-(hRec/2), x, centroImgy+(hRec/2));
	    }
		
		//disegno barre con controllo della distanza tra esse
		for(int y=centroImgy-(hBar/2);y<=centroImgy+(hBar/2);y++) {
			for(int x=centroImgx-(xBar/2)-(DIMBARRE/2);x<=centroImgx-(xBar/2)+(DIMBARRE/2);x++) {
			    drawable.setColor(color1);
			    drawable.drawLine(x, y, x, y);
			}
			for(int x=centroImgx+(xBar/2)-(DIMBARRE/2);x<=centroImgx+(xBar/2)+(DIMBARRE/2);x++) {
			    drawable.setColor(color2);
			    drawable.drawLine(x, y, x, y);
			}
			/*if((centroImgx-(xBar/2)+(DIMBARRE/2))>=(centroImgx+(xBar/2)-(DIMBARRE/2))) {
				for(int x=centroImgx+(xBar/2)-(DIMBARRE/2);x<=centroImgx-(xBar/2)+(DIMBARRE/2);x++) {
					drawable.setColor(cAssign);
				    drawable.drawLine(x, y, x, y);
				}
				
			}*/
		}
		//drawable.setColor(Color.WHITE);
		//drawable.fillRect((centroImgx-(dimBarre/2)),
		//(centroImgy-(hBar/2)), dimBarre, hBar);
		return image;
		
		

	}

	
}
