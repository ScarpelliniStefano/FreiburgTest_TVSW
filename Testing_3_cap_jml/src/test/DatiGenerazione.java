package test;

import java.awt.Color;
import java.awt.Dimension;

/**
 * @author stefa
 * Classe DatiGenerazione
 */
public class DatiGenerazione {
	
	
	
	/**
	 * dimensione
	 */
	/*@ non_null @*/ 
	/*@ spec_public @*/
	private Dimension dimensione=new Dimension(0,0);
	
	/**
	 * nome persona
	 */
	/*@ non_null @*/ 
	/*@ spec_public @*/
	private String nome="";
	
	/**
	 * sesso persona
	 */
	/*@ non_null @*/ 
	/*@ spec_public @*/
	private String sesso="";
	
	/**
	 *  dimensione del monitor in diagonale in pollici
	 */
	/*@ spec_public @*/
	private int monitorSize=-2;
	
	/**
	 *  larghezza rettangolo immagine in pixel
	 */
	/*@ spec_public @*/
	private int wRect=-2;
	
	/**
	 *  altezza rettangolo immagine in pixel
	 */
	/*@ spec_public @*/
	private int hRect=-2;
	
	/**
	 * altezza barre interne
	 */
	/*@ spec_public @*/
	private int hBar=-2;
	
	/**
	 * larghezza tra le barre attuale
	 */
	/*@ spec_public @*/
	private int xBar=-2;
	
	
	/**
	 * livello massimo del test
	 */
	/*@ spec_public @*/
	private int livMax=-2;
	
	
	/**
	 * livello minimo del test
	 */
	/*@ spec_public @*/
	private int livMin=-2;
	
	/**
	 * distanza dallo schermo
	 */
	/*@ spec_public @*/
	private int distSchermo=-2;
	
	/**
	 * colore prima barra (colore occhio sinistro solitamente)
	 */
	/*@ non_null @*/
	/*@ spec_public @*/
	private Color color1=Color.BLACK;
	
	/**
	 * colore seconda barra (colore occhio destro solitamente)
	 */
	/*@ non_null @*/
	/*@ spec_public @*/
	private Color color2=Color.BLACK;
	
	/**
	 * posizione (false=indietro, true=avanti)
	 */
	/*@ spec_public @*/
	private boolean pos=false;
	
	/**
	 * livello attuale del test
	 */
	/*@ spec_public @*/
	private double livello=-2;
	
	/**
	 * angolo di generazione
	 */
	/*@ spec_public @*/
	private double angolo=-2;
	
	// @ invariant dimensione.width>=0 && dimensione.height>=0;
	// @ invariant angolo>=-2 && livello>=-2;
	// @ invariant distSchermo>=-2 && livMin>=-2 && livMax>=-2;
	// @ invariant xBar>=-2 && hBar>=-2 && hRect>=-2 && wRect>=-2 && monitorSize>=-2;
	
	// @ ensures nome=="Unnamed" && sesso=="null" 
	//   && monitorSize==0 && wRect==-1 && hRect==-1 ;
	// @ ensures hBar==-1 && xBar==-1 && disSchermo==0 &&
	//   livMIn==-1 && livMax==-1 && pos==false && livello==-1 && angolo==-1;
	/**
	 * costruttore vuoto
	 */
	public DatiGenerazione(){
		dimensione=new Dimension(1,1);
		nome="Unnamed";
		sesso="null";
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
		
	}
	
	// @ requires name!=null && (sex=="man" or sex=="female")
	//    && monSize>=0 && wrect>=0
	// @ ensures nome==name && nome!=\old(nome) && sesso==sex 
	//    && monitorSize==monSize && rRect==wrect && hRect==-1 ;
	// @ ensures hBar==-1 && xBar==-1 && disSchermo==0 && livMIn==-1
	//    && livMax==-1 && pos==false && livello==-1 && angolo==-1;
	/**
	 * costruttore della classe
	 * @param name
	 * @param sex
	 * @param monSize
	 * @param wrect
	 */
	public DatiGenerazione(final String name,final String sex,final int monSize,final int wrect){
		dimensione=new Dimension(1,1);
		nome=name;
		sesso=sex;
		monitorSize=monSize;
		wRect=wrect;
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
		
	}
	
	// @ requires name!=null && (sex=="man" or sex=="female") 
	//   && monSize>=0 && wRect>=0;
	// @ ensures nome==name && sesso==sex && monitorSize==monSize 
	//   && dimensione.width==dimx && dimensione.height==dimy && 
	//   wRect==wrect && hRect==hrect ;
	// @ ensures hBar==hbar && xBar==-1 && disSchermo==disSchermo 
	//   && livMin==livmin && livMax==livmax && pos==false && 
	//   livello==-1 && angolo==-1;
	/**
	 * @param vari parametri di configurazione
	 */
	public DatiGenerazione(final String name,final String sex,final int monSize, final int dimx,final int dimy,final int wrect,final int hrect,final int hbar,final int disSchermo,final int livmin,final int livmax){
		dimensione=new Dimension(dimx,dimy);
		nome=name;
		sesso=sex;
		monitorSize=monSize;
		wRect=wrect;
		hRect=hrect;
		hBar=hbar;
		xBar=-1;
		distSchermo=disSchermo;
		color1=Color.RED;
		color2=Color.BLUE;
		livMin=livmin;
		livMax=livmax;
		pos=false;
		livello=-1;
		angolo=-1;
		
	}
	
	
	/*@ ensures \result == this.nome; 
	  @*/
	public String getNome() {
		
			return nome;

		
	}

	
	// @ requires nome!="";
	// @ ensures this.nome==nome;
	/**
	 * @param nome
	 */
	public void setNome(final String nome) {
		this.nome=nome;
		
	}

	/*@ ensures \result == this.sesso; 
	  @*/
	public String getSesso() {
		return sesso;
	}

	/**
	 * @param sesso
	 */
	/*@ requires sesso=="M"||sesso=="F"||sesso=="man"||sesso=="female"; 
	  @ ensures this.sesso==sesso;
	  @*/
	public void setSesso(final String sesso) {
		this.sesso = sesso;
	}

	/*@ ensures \result == this.monitorSize; 
	  @*/
	public int getMonitorSize() {
		return monitorSize;
	}

	/**
	 * @param d
	 */
	/*@ requires dimension>=0;
	  @ ensures this.monitorSize==dimension || this.monitorSize==-1;
	  @*/
	public void setMonitorSize(final int dimension) {
		if(dimension>0) {
			monitorSize = dimension;
		}else {
			monitorSize=-1;
		}
	}

	/*@ ensures \result == this.wRect; 
	  @*/
	public int getWRect() {
		return wRect;
	}

	/**
	 * @param wRect
	 */
	/*@ requires wRect>=0;
	  @ ensures this.wRect==wRect || this.wRect==dimensione.width-20;
	  @*/
	public void setWRect(final int wRect) {
		if(wRect<(dimensione.getWidth()-20)&&wRect>0) { this.wRect = wRect;}
		else { this.wRect=(int) (dimensione.getWidth()-20);}
	}

	/*@     ensures \result == this.hRect; 
	  @*/
	public int getHRect() {
		return hRect;
	}

	/**
	 * @param hRect
	 */
	/*@ requires hRect>=0;
	  @ ensures this.hRect==hRect || this.hRect==dimensione.height-20;
	  @*/
	public void setHRect(final int hRect) {
		if(hRect<(dimensione.getHeight()-20)&&hRect>0) { this.hRect = hRect;}
		else {this.hRect=(int) (dimensione.getHeight()-20);}
	}

	/*@ ensures \result == this.hBar; 
	  @*/
	public int getHBar() {
		return hBar;
	}

	/**
	 * @param hBar
	 */
	/*@ requires hBar>=0;
	  @ ensures this.xBar==xBar || this.xBar==hRect-10;
	  @*/
	public void setHBar(final int hBar) {
		if(hBar<(hRect-10)&&hBar>0) {	this.hBar = hBar;}
		else {this.hBar=hRect-10;}
	}

	/*@ ensures \result == this.xBar; 
	  @*/
	public int getXBar() {
		return xBar;
	}

	/**
	 * @param xBar
	 * @throws ArithmeticException
	 */
	/*@ requires xBar>0 && this.livMax>-2;
	  @ ensures this.xBar==xBar || this.xBar==livMax;
	  @*/
	public void setXBar(final int xBar) {
		assert xBar>0;
		if(livMin==-1&&livMax>=livMin) {
			this.livMin=livMax;
		}
		if(xBar<=livMax && xBar>=livMin) {	this.xBar = xBar;}
		else{
				this.xBar=livMax;
		}
	}

	/*@ ensures \result == this.livMax; 
	  @*/
	public int getLivMax() {
		return livMax;
	}



	/**
	 * @param livMax
	 */
	/*@ requires livMax>0;
	  @ ensures this.livMax==livMax && this.livMax<=this.wRect-10;
	  @ ensures this.xBar==this.livMax;
	  @*/
	public void setLivMax(final int livMax) {
		assert livMax>0;
		if(livMax<(wRect-10)&&livMax>0) {	this.livMax = livMax;}
		else { this.livMax = wRect-10;}
		this.xBar=this.livMax;
	}



	/*@ ensures \result == this.livMin; 
	  @*/
	public int getLivMin() {
		return livMin;
	}



	/**
	 * @param livMin
	 */
	/*@ requires livMin>0;
	  @ ensures this.livMin==livMin && this.livMin<=this.livMax;
	  @*/
	public void setLivMin(final int livMin) {
		assert livMin>0;
		if(livMax==-2) {
			livMax=livMin;
		}
		if(livMin<(wRect-10)&&livMin>0&&livMin<=livMax) {	this.livMin = livMin;}
		else { this.livMin=livMax;}
	}



	/*@ ensures \result == this.distSchermo; 
	  @*/
	public int getDistSchermo() {
		return distSchermo;
	}

	/**
	 * @param distSchermo
	 */
	/*@ requires distSchermo>0;
	  @ ensures this.distSchermo==distSchermo*10;
	  @*/
	public void setDistSchermo(final int distSchermo) {
		assert distSchermo>0;
		this.distSchermo = distSchermo*10;
	}

	/**
	 * @return
	 */
	/*@ ensures \result == this.color1; 
	 @*/
	public Color getC1() {
		return color1;
	}

	/**
	 * @param color1
	 */
	/*@ requires color1!=null;
	  @ ensures this.color1==color1;
	  @*/
	public void setC1(final Color color1) {
		this.color1 = color1;
	}

	/**
	 * @return
	 */
	/*@ ensures \result == this.color2; 
	  @*/
	public Color getC2() {
		return color2;
	}

	/**
	 * @param color2
	 */
	/*@ requires color2!=null;
	  @ ensures this.color2==color2;
	  @*/
	public void setC2(final Color color2) {
		this.color2 = color2;
	}
	

	/**
	 * @return
	 */
	/*@ ensures \result == this.pos; @*/
	public boolean isPos() {
		return pos;
	}



	/**
	 * @param pos
	 */
	/* @ requires pos==true || pos==false; @
	   @ ensures this.pos!=\old(this.pos) => this.color1==\old(this.color2) && this.color2==\old(this.color1);
	@ */
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
	/*@ ensures \result == this.livello; @*/
	public double getLivello() {
		return livello;
	}

	/**
	 * @param livello
	 */
	/* @ requires level>=0 && level>=this.livMin && level<=this.livMax; @*/
	public void setLivello(final double level) {
		livello = level;
	}

	/**
	 * @return angolo
	 */
	/*@ ensures \result == this.angolo; @*/
	public double getAngolo() {
		return angolo;
	}
	/**
	 * @param angle the angolo to set
	 */
	/* @ requires angle>=0; @*/
	public void setAngolo(final double angle) {
		this.angolo = angle;
	}

	/**
	 * @param dimensione the dimension to set (Dimension format)
	 */
	/* @ requires dimensione.width>=0 && dimensione.height>=0; @*/
	public void setDimensione(final Dimension dimensione) {
		this.dimensione = dimensione;
	}
	
	/**
	 * @return dimensione
	 */
	 /* @ ensures \result == this.dimensione; @*/
	public Dimension getDimensione() {
		return dimensione;
	}
	
	/**
	 * @param dimensione the dimension to set (width and height in integer)
	 */
	/* @ requires width>=0 && height>=0; @*/
	public void setDimensione(final int width,final int height) {
		this.dimensione = new Dimension(width,height);
	}
	
	/**
	 * @param dimensione the dimension to set (width and height in double)
	 */
	/* @ requires width>=0 && height>=0; @*/
	public void setDimensione(final double width,final double height) {
		this.dimensione = new Dimension((int)width,(int)height);
	}


	
	
}
