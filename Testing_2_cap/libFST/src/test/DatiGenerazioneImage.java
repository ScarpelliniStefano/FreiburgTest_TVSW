package test;

import java.awt.Color;
import java.awt.Dimension;

/**
 * @author stefa
 *	dati generazione immagine
 */
public class DatiGenerazioneImage { //ancora una god class
	/**
	 * dimensione
	 */

	private Dimension dimensione/*=new Dimension(0,0)*/;
	/**
	 * hRect
	 */
	private int hRect;
	/**
	 * hBar
	 */
	private int hBar;
	/**
	 *  dimensione del monitor in diagonale in pollici
	 */
	private int monitorSize/*=-2*/;
	
	/**
	 *  larghezza rettangolo immagine in pixel
	 */

	private int wRect/*=-2*/;
	
	/**
	 * larghezza tra le barre attuale
	 */

	private int xBar/*=-2*/;
	
	
	/**
	 * livello massimo del test
	 */

	private int livMax/*=-2*/;
	
	
	/**
	 * livello minimo del test
	 */

	private int livMin/*=-2*/;
	
	/**
	 * distanza dallo schermo
	 */

	private int distSchermo/*=-2*/;
	
	/**
	 * colore prima barra (colore occhio sinistro solitamente)
	 */

	private transient Color color1/*=Color.BLACK*/;
	
	/**
	 * colore seconda barra (colore occhio destro solitamente)
	 */

	private transient Color color2/*=Color.BLACK*/;

	
	/**
	 * @param dimensione the dimension to set (Dimension format)
	 */
	public void setDimensione(final Dimension dimensione) {
		this.dimensione = dimensione;
	}
	/**
	 * @return dimensione
	 */
	public Dimension getDimensione() {
		return dimensione;
	}
	/**
	 * @param dimensione the dimension to set (width and height in integer)
	 */
	public void setDimensione(final int width,final int height) {
		this.dimensione = new Dimension(width,height);
	}
	/**
	 * @param dimensione the dimension to set (width and height in double)
	 */
	public void setDimensione(final double width,final double height) {
		this.dimensione = new Dimension((int)width,(int)height);
	}
	public int getMonitorSize() {
		return monitorSize;
	}

	/**
	 * @param d
	 */
	public void setMonitorSize(final int dimension) {
		if(dimension>0) {
			monitorSize = dimension;
		}else {
			monitorSize=0;
		}
	}

	public int getWRect() {
		return wRect;
	}

	/**
	 * @param wRect
	 */
	public void setWRect(final int wRect) {
		if(wRect<(dimensione.getWidth()-20)&&wRect>0) { this.wRect = wRect;}
		else { this.wRect=(int) (dimensione.getWidth()-20);}
	}

	
	public int getXBar() {
		return xBar;
	}

	/**
	 * @param xBar
	 * @throws ArithmeticException
	 */
	public void setXBar(final int xBar) {
		//assert xBar>0;
		if(livMin==-1&&livMax>=livMin) {
			this.livMin=livMax;
		}
		if(xBar<=livMax && xBar>=livMin) {	this.xBar = xBar;}
		else if(livMax<1) { //sarebbe da evitare il letterale
				throw new ArithmeticException("Inserisci un livello massimo corretto");
		}else {
				this.xBar=livMax;
		}
	}

	public int getLivMax() {
		return livMax;
	}



	/**
	 * @param livMax
	 */
	public void setLivMax(final int livMax) {
		if(livMax<(wRect-10)&&livMax>0) {	this.livMax = livMax;}
		else { this.livMax = wRect-10;}
		this.xBar=this.livMax;
	}



	public int getLivMin() {
		return livMin;
	}



	/**
	 * @param livMin
	 */
	public void setLivMin(final int livMin) {
		if(livMax<0) {
			livMax=livMin;
		}
		if(livMin<(wRect-10)&&livMin>0&&livMin<=livMax) {	this.livMin = livMin;}
		else { this.livMin=livMax;}
	}



	public int getDistSchermo() {
		return distSchermo;
	}

	/**
	 * @param distSchermo
	 */
	public void setDistSchermo(final int distSchermo) {
		assert distSchermo>=0;
		this.distSchermo = distSchermo*10;
	}

	/**
	 * @return
	 */
	public Color getC1() {
		return color1;
	}

	/**
	 * @param color1
	 */
	public void setC1(final Color color1) {
		this.color1 = color1;
	}

	/**
	 * @return
	 */
	public Color getC2() {
		return color2;
	}

	/**
	 * @param color2
	 */
	public void setC2(final Color color2) {
		this.color2 = color2;
	}
	
	public int getHRect() {
		return hRect;
	}

	public void setHRect(final int hRect) {
		if(hRect<(dimensione.getHeight()-20)&&hRect>0) { this.hRect = hRect;}
		else { this.hRect=(int) (dimensione.getHeight()-20);}
	}

	public int getHBar() {
		return hBar;
	}

	/**
	* @param hBar
	*/
	public void setHBar(final int hBar) {
		if (hBar < (hRect - 10) && hBar > 0) {
			this.hBar = hBar;
		} else {
			this.hBar = hRect - 10;
		}
	}
	/**
	 * @param wrect
	 */
	public void setWRectNC(final int wrect) {
		this.wRect=wrect;
	}
	/**
	 * @param hrect
	 */
	public void setHRectNC(final int hrect) {
		this.hRect=hrect;
	}
	/**
	 * @param hbar
	 */
	public void setHBarNC(final int hbar) {
		this.hBar=hbar;
	}
	/**
	 * @param livmax
	 */
	public void setLivMaxNC(final int livmax) {
		this.livMax=livmax;
	}
	/**
	 * @param livmin
	 */
	public void setLivMinNC(final int livmin) {
		this.livMin=livmin;
	}
	/**
	 * @param xbar
	 */
	public void setXBarNC(final int xbar) {
		this.xBar=xbar;
	}
}