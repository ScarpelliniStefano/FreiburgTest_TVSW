package test;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author stefa
 * Classe DatiGenerazione
 */
public class DatiGenerazione {
	//CODE REFACTOR:
	//GOD CLASS: suddivisione di una parte della classe in sottoclassi
	
	//PMD1: conversione di tutte le variabili con le lettere 
	//       maiuscole iniziali in minuscole
	//PMD2: aggiunta dei commenti a ogni attributo e metodo della classe
	//PMD3: aggiunta parentesi graffe negli if-else
	//PMD4: aggiunto final a alcune variabili che potevano essere non inizializzate
	// (MethodArgumentCouldBeFinal: Parameter is not assigned and could be declared
	//  final)
	
	
	
	/**
	 * dati generazione immagine
	 */
	private final transient DatiGenerazioneImage dgenI = new DatiGenerazioneImage();

	/**
	 * dati generazione persona
	 */
	private final transient DatiGenerazionePerson dgenP = new DatiGenerazionePerson();
	
	
	/**
	 * posizione (false=indietro, true=avanti)
	 */
	private boolean pos/*=false*/;
	
	/**
	 * livello attuale del test
	 */

	private double livello/*=-2*/;
	
	/**
	 * angolo di generazione
	 */

	private double angolo/*=-2*/;
	
	/**
	 * TIMEZONE
	 */
	public final static String TIMEZONE="Europe/Rome";
	/**
	 * costruttore vuoto
	 */
	public DatiGenerazione(){
		dgenI.setDimensione(new Dimension(1,1));
		dgenP.setNome("Unnamed");
		dgenP.setSesso("null");
		dgenP.setDataNascNC(Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE), Locale.ITALY));
		dgenI.setMonitorSize(0);
		dgenI.setWRectNC(-1);
		dgenI.setHRectNC(-1);
		dgenI.setHBarNC(-1);
		dgenI.setDistSchermo(0);
		dgenI.setC1(Color.RED);
		dgenI.setC2(Color.BLUE);
		dgenI.setLivMaxNC(-1);
		dgenI.setLivMinNC(-1);
		dgenI.setXBarNC(-1);
		pos=false;
		livello=-1;
		angolo=-1;
		dgenP.setDataEsame(Calendar.getInstance());
	}
	

	/**
	 * costruttore della classe
	 * @param name
	 * @param sex
	 * @param monSize
	 * @param wrect
	 */
	public DatiGenerazione(final String name,final String sex,final int monSize,final int wrect){
		this();
		dgenP.setNome(name);
		dgenP.setSesso(sex);
		dgenI.setMonitorSizeNC(monSize);
		dgenI.setWRectNC(wrect);
	}
	
	
	/**
	 * @param vari parametri di configurazione
	 */
	public DatiGenerazione(final String name,final String sex,final int monSize, final int dimx,final int dimy,final int wrect,final int hrect,final int hbar,final int disSchermo,final int livmin,final int livmax){
		this();
		dgenI.setDimensioneNC(new Dimension(dimx,dimy));
		dgenP.setNome(name);
		dgenP.setSesso(sex);
		dgenI.setMonitorSizeNC(monSize);
		dgenI.setWRectNC(wrect);
		dgenI.setHRectNC(hrect);
		dgenI.setHBarNC(hbar);
		dgenI.setXBarNC(-1);
		dgenI.setDistSchermoNC(disSchermo);
		dgenI.setLivMinNC(livmin);
		dgenI.setLivMaxNC(livmax);
	}
	
	
	/**
	 * @return nome
	 */
	public String getNome() {
		
			return dgenP.getNome();

		
	}

	

	/**
	 * @param nome
	 */
	public void setNome(final String nome) {
		dgenP.setNome(nome);
		
	}

	/**
	 * @return sesso
	 */
	public String getSesso() {
		return dgenP.getSesso();
	}

	/**
	 * @param sesso
	 */
	public void setSesso(final String sesso) {
		dgenP.setSesso(sesso);
	}
	
	/**
	 * @return the dataNasc
	 */
	public Calendar getDataNasc() {
		return dgenP.getDataNasc();
	}
	/**
	 * @param dn the dataNasc to set
	 */
	public void setDataNasc(final Calendar datanascita) {
		dgenP.setDataNasc(datanascita);
	}
	/**
	 * @param dataNasc the dataNasc to set in dd/mm/yyyy
	 */
	public void setDataNasc(final String dataNascString) {
		dgenP.setDataNasc(dataNascString);
	}

	/**
	 * @return monitor size
	 */
	public int getMonitorSize() {
		return dgenI.getMonitorSize();
	}

	/**
	 * @param dimension
	 */
	public void setMonitorSize(final int dimension) {
		dgenI.setMonitorSize(dimension);
	}

	/**
	 * @return wrect
	 */
	public int getWRect() {
		return dgenI.getWRect();
	}

	/**
	 * @param wRect
	 */
	public void setWRect(final int wRect) {
		dgenI.setWRect(wRect);
	}

	/**
	 * @return hrect
	 */
	public int getHRect() {
		return dgenI.getHRect();
	}

	/**
	 * @param hRect
	 */
	public void setHRect(final int hRect) {
		dgenI.setHRect(hRect);
	}

	/**
	 * @return hbar
	 */
	public int getHBar() {
		return dgenI.getHBar();
	}

	/**
	 * @param hBar
	 */
	public void setHBar(final int hBar) {
		dgenI.setHBar(hBar);
	}

	/**
	 * @return xbar
	 */
	public int getXBar() {
		return dgenI.getXBar();
	}

	/**
	 * @param xBar
	 * @throws ArithmeticException
	 */
	public void setXBar(final int xBar) {
		dgenI.setXBar(xBar);
	}

	/**
	 * @return liv max
	 */
	public int getLivMax() {
		return dgenI.getLivMax();
	}



	/**
	 * @param livMax
	 */
	public void setLivMax(final int livMax) {
		dgenI.setLivMax(livMax);
	}



	/**
	 * @return liv min
	 */
	public int getLivMin() {
		return dgenI.getLivMin();
	}



	/**
	 * @param livMin
	 */
	public void setLivMin(final int livMin) {
		dgenI.setLivMin(livMin);
	}



	/**
	 * @return distanza schermo
	 */
	public int getDistSchermo() {
		return dgenI.getDistSchermo();
	}

	/**
	 * @param distSchermo
	 */
	public void setDistSchermo(final int distSchermo) {
		dgenI.setDistSchermo(distSchermo);
	}

	/**
	 * @return colore 1
	 */
	public Color getC1() {
		return dgenI.getC1();
	}

	/**
	 * @param color1
	 */
	public void setC1(final Color color1) {
		dgenI.setC1(color1);
	}

	/**
	 * @return colore 2
	 */
	public Color getC2() {
		return dgenI.getC2();
	}

	/**
	 * @param color2
	 */
	public void setC2(final Color color2) {
		dgenI.setC2(color2);
	}
	

	/**
	 * @return posizione
	 */
	public boolean isPos() {
		return pos;
	}



	/**
	 * @param pos
	 */
	public void setPos(final boolean pos) {
		if(this.pos!=pos) {
			final Color cTemp=getC1();
			dgenI.setC1(dgenI.getC2());
			dgenI.setC2(cTemp);
		}
		this.pos = pos;
		
	}


	/**
	 * @return livello
	 */
	public double getLivello() {
		return livello;
	}

	/**
	 * @param livello
	 */
	public void setLivello(final double level) {
		livello = level;
	}

	/**
	 * @return angolo
	 */
	public double getAngolo() {
		return angolo;
	}
	/**
	 * @param angle the angolo to set
	 */
	public void setAngolo(final double angle) {
		this.angolo = angle;
	}

	/**
	 * @param dimensione the dimension to set (Dimension format)
	 */
	public void setDimensione(final Dimension dimensione) {
		dgenI.setDimensione(dimensione);
	}
	/**
	 * @return dimensione
	 */
	public Dimension getDimensione() {
		return dgenI.getDimensione();
	}
	/**
	 * @param dimensione the dimension to set (width and height in integer)
	 */
	public void setDimensione(final int width,final int height) {
		dgenI.setDimensione(width, height);
	}
	/**
	 * @param dimensione the dimension to set (width and height in double)
	 */
	public void setDimensione(final double width,final double height) {
		dgenI.setDimensione(width, height);
	}

	/**
	 * @return the dataEsame
	 */
	public Calendar getDataEsame() {
		return dgenP.getDataEsame();
	}
	
	
}


