package test;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author stefa
 * Classe DatiGenerazione
 */
public class DatiGenerazione {
	//PMD1: conversione di tutte le variabili con le lettere 
	//       maiuscole iniziali in minuscole
	//PMD2: aggiunta dei commenti a ogni attributo e metodo della classe
	//PMD3: aggiunta parentesi graffe negli if-else
	//PMD4: aggiunto final a alcune variabili che potevano essere non inizializzate
	// (MethodArgumentCouldBeFinal: Parameter is not assigned and could be declared
	//  final)
	
	
	
	/**
	 * dimensione
	 */

	private Dimension dimensione/*=new Dimension(0,0)*/;
	
	/**
	 * nome persona
	 */

	private String nome/*=""*/;
	
	/**
	 * sesso persona
	 */

	private String sesso/*=""*/;
	
	
	/**
	 * data di nascita
	 */
	private Calendar dataNasc/*=Calendar.getInstance(TimeZone.getTimeZone("Europe/Rome"),Locale.ITALY)*/;
	
	/**
	 *  dimensione del monitor in diagonale in pollici
	 */
	private int monitorSize/*=-2*/;
	
	/**
	 *  larghezza rettangolo immagine in pixel
	 */

	private int wRect/*=-2*/;
	
	/**
	 *  altezza rettangolo immagine in pixel
	 */

	private int hRect/*=-2*/;
	
	/**
	 * altezza barre interne
	 */

	private int hBar/*=-2*/;
	
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
	 * data esame
	 */
	private transient Calendar dataEsame/*=Calendar.getInstance()*/;
	/**
	 * TIMEZONE
	 */
	private final static String TIMEZONE="Europe/Rome";
	/**
	 * costruttore vuoto
	 */
	public DatiGenerazione(){
		dimensione=new Dimension(1,1);
		nome="Unnamed";
		sesso="null";
		dataNasc=Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE),Locale.ITALY);
		monitorSize=0;
		wRect=-1;
		hRect=-1;
		hBar=-1;
		xBar=-1;
		distSchermo=0;
		color1=Color.RED;
		color2=Color.BLUE;
		livMin=-1;
		livMax=-1;
		pos=false;
		livello=-1;
		angolo=-1;
		dataEsame=Calendar.getInstance();
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
		nome=name;
		sesso=sex;
		monitorSize=monSize;
		wRect=wrect;
	}
	
	
	/**
	 * @param vari parametri di configurazione
	 */
	public DatiGenerazione(final String name,final String sex,final int monSize, final int dimx,final int dimy,final int wrect,final int hrect,final int hbar,final int disSchermo,final int livmin,final int livmax){
		this();
		dimensione=new Dimension(dimx,dimy);
		nome=name;
		sesso=sex;
		monitorSize=monSize;
		wRect=wrect;
		hRect=hrect;
		hBar=hbar;
		xBar=-1;
		distSchermo=disSchermo;
		livMin=livmin;
		livMax=livmax;
	}
	
	
	public String getNome() {
		
			return nome;

		
	}

	

	/**
	 * @param nome
	 */
	public void setNome(final String nome) {
		this.nome=nome;
		
	}

	public String getSesso() {
		return sesso;
	}

	/**
	 * @param sesso
	 */
	public void setSesso(final String sesso) {
		this.sesso = sesso;
	}
	
	/**
	 * @return the dataNasc
	 */
	public Calendar getDataNasc() {
		return dataNasc;
	}
	/**
	 * @param dn the dataNasc to set
	 */
	public void setDataNasc(final Calendar datanascita) {
		final Calendar dateNow=Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE),Locale.ITALY);
		if(dateNow.after(datanascita)) {
		this.dataNasc = datanascita;}
	}
	/**
	 * @param dataNasc the dataNasc to set in dd/mm/yyyy
	 */
	public void setDataNasc(final String dataNascString) {
		if(dataNascString.charAt(2)=='/' && dataNascString.charAt(5)=='/') {
			GregorianCalendar data=new GregorianCalendar();
			dataEsame=dataNasc;
			dataEsame.setTime(data.getTime());
			final char[] charDate=dataNascString.toCharArray();
			final String anno=""+charDate[6]+charDate[7]+charDate[8]+charDate[9];
			final String mese=""+charDate[3]+charDate[4];
			final String giorno=""+charDate[0]+charDate[1];
			if(Integer.parseInt(mese)<=12&&Integer.parseInt(giorno)<=31) {
				data=new GregorianCalendar(Integer.parseInt(anno),Integer.parseInt(mese)-1, Integer.parseInt(giorno));
				data.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
		
				if(dataEsame.getTime().after(data.getTime())) {
					this.dataNasc.setTime(data.getTime());
				}
			}
		}
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

	public int getHRect() {
		return hRect;
	}

	/**
	 * @param hRect
	 */
	public void setHRect(final int hRect) {
		if(hRect<(dimensione.getHeight()-20)&&hRect>0) { this.hRect = hRect;}
		else {this.hRect=(int) (dimensione.getHeight()-20);}
	}

	public int getHBar() {
		return hBar;
	}

	/**
	 * @param hBar
	 */
	public void setHBar(final int hBar) {
		if(hBar<(hRect-10)&&hBar>0) {	this.hBar = hBar;}
		else {this.hBar=hRect-10;}
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
		else if(livMax<1) {
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
		assert distSchermo>0;
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
	

	/**
	 * @return
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
			this.setC1(getC2());
			this.setC2(cTemp);
		}
		this.pos = pos;
		
	}


	/**
	 * @return
	 */
	public double getLivello() {
		return livello;
	}

	/**
	 * @param i
	 */
	public void setLivello(final double level) {
		livello = level;
	}

	/**
	 * @return
	 */
	public double getAngolo() {
		return angolo;
	}
	/**
	 * @param d the angolo to set
	 */
	public void setAngolo(final double angle) {
		this.angolo = angle;
	}
	/**
	 * @return the dataEsame
	 */

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

	/**
	 * @return the dataEsame
	 */
	public Calendar getDataEsame() {
		return dataEsame;
	}
	
	
}
