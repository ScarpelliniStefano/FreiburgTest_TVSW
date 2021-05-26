package fstTest;

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

	//@ spec_public
	private Dimension dimensione=new Dimension(0,0);
	
	/**
	 * nome persona
	 */

	//@ spec_public
	private String nome="";
	
	/**
	 * sesso persona
	 */

	//@ spec_public
	private String sesso="";
	
	/**
	 *  dimensione del monitor in diagonale in pollici
	 */
	//@ spec_public
	private Integer monitorSize=-2;
	
	/**
	 *  larghezza rettangolo immagine in pixel
	 */
	//@ spec_public
	private Integer wRect=-2;
	
	/**
	 *  altezza rettangolo immagine in pixel
	 */
	//@ spec_public
	private Integer hRect=-2;
	
	/**
	 * altezza barre interne
	 */
	//@ spec_public
	private Integer hBar=-2;
	
	/**
	 * larghezza tra le barre attuale
	 */
	//@ spec_public
	private Integer xBar=-2;
	
	
	/**
	 * livello massimo del test
	 */
	//@ spec_public
	private Integer livMax=-2;
	
	
	/**
	 * livello minimo del test
	 */
	//@ spec_public
	private Integer livMin=-2;
	
	/**
	 * distanza dallo schermo
	 */
	//@ spec_public
	private Integer distSchermo=-2;
	
	/**
	 * colore prima barra (colore occhio sinistro solitamente)
	 */
	//@ spec_public
	private Color color1=Color.BLACK;
	
	/**
	 * colore seconda barra (colore occhio destro solitamente)
	 */
	//@ spec_public
	private Color color2=Color.BLACK;
	
	/**
	 * posizione (false=indietro, true=avanti)
	 */
	//@ spec_public
	private Boolean pos=false;
	
	/**
	 * livello attuale del test
	 */
	//@ spec_public
	private Double livello=-2.0;
	
	/**
	 * angolo di generazione
	 */
	//@ spec_public
	private Double angolo=-2.0;
	

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
		livello=-1.0;
		angolo=-1.0;
		
	}
	
	//@ ensures nome==name && sesso==sex && monitorSize==monSize;
	//@ ensures dimensione.width==100 && dimensione.height==100;
	//@ ensures wRect==wrect && hRect==-1 && hBar==-1 && xBar==-1;
	//@ ensures distSchermo==0 && livMin==-1 && livMax==-1;
	public DatiGenerazione(/*@ non_null@*/ String name,/*@ non_null@*/ String sex,/*@ non_null@*/ int monSize,/*@ non_null@*/ int wrect){
		dimensione=new Dimension(100,100);
		setNome(name);
		setSesso(sex);
		setMonitorSize(monSize);
		setWRect(wrect);
		hRect=-1;
		hBar=-1;
		xBar=-1;
		distSchermo=0;
		color1=Color.RED;
		color2=Color.BLUE;
		livMin=-1;
		livMax=-1;
		pos=false;
		livello=-1.0;
		angolo=-1.0;
		
	}
	
	//@ requires dimx>=0 && dimy>=0; 
	//@ requires hrect>=0 && hbar>=0;
	//@ requires disSchermo>=0; 
	//@ requires livmin>0 && livmax>0 && livmax>=livmin;
	//@ ensures nome==name && sesso==sex && monitorSize==monSize;
	//@ ensures dimensione.width==dimx && dimensione.height==dimy;
	//@ ensures wRect==wrect && hRect==hrect && hBar==hbar && xBar==-1;
	//@ ensures disSchermo==disSchermo && livMin==livmin && livMax==livmax;
	public DatiGenerazione(final /*@ non_null@*/ String name,final /*@ non_null@*/ String sex,final /*@ non_null@*/ int monSize, final /*@ non_null@*/ int dimx,final /*@ non_null@*/ int dimy,final /*@ non_null@*/ int wrect,final /*@ non_null@*/ int hrect,final /*@ non_null@*/ int hbar,final /*@ non_null@*/ int disSchermo,final /*@ non_null@*/ int livmin,final /*@ non_null@*/ int livmax){
		dimensione=new Dimension(dimx,dimy);
		setNome(name);
		setSesso(sex);
		setMonitorSize(monSize);
		setWRect(wrect);
		hRect=hrect;
		hBar=hbar;
		xBar=-1;
		distSchermo=disSchermo;
		color1=Color.RED;
		color2=Color.BLUE;
		livMax=livmax;
		livMin=livmin;
		pos=false;
		livello=-1.0;
		angolo=-1.0;
		
	}
	
	
	/*@ ensures \result == this.nome;
	  @ assignable \nothing;
	  @*/
	public /*@ pure@*/ String getNome() {
		
			return nome;

		
	}

	

	//@ ensures this.nome==nome;
	//@ assignable this.nome;
	public void setNome(final /*@ non_null@*/ String nome) {
		this.nome=nome;
		
	}

	/*@ ensures \result == this.sesso;
	  @ assignable \nothing;
	  @*/
	public /*@ pure@*/ String getSesso() {
		return sesso;
	}


	//@ requires sesso=="M"||sesso=="F"||sesso=="m"||sesso=="f"||sesso=="man"||sesso=="female"; 
	//@ ensures this.sesso==sesso;
	//@ assignable this.sesso;
	public void setSesso(final /*@ non_null@*/ String sesso) {
		this.sesso = sesso;
	}

	//@ ensures \result == this.monitorSize; 
	//@ assignable \nothing;
	public /*@ pure@*/ int getMonitorSize() {
		return monitorSize;
	}


	//@ requires dimension>=0;
	//@ ensures this.monitorSize==dimension || this.monitorSize==-1;
	//@ assignable this.monitorSize;
	public void setMonitorSize(final /*@ non_null@*/ int dimension) {
		if(dimension>0) {
			/*@ assert dimension>0;@*/
			monitorSize = dimension;
		}else {
			/*@ assert dimension<=0;@*/
			monitorSize=-1;
		}
	}

	//@ ensures \result == this.wRect; 
	//@ assignable \nothing;
	public /*@ pure@*/ int getWRect() {
		return wRect;
	}

	/*@ requires wRect>=0;
	  @ ensures this.wRect==wRect || this.wRect==dimensione.width-20;
	  @ assignable this.wRect;
	  @*/
	public void setWRect(final /*@ non_null@*/  int wRect) {
		if(wRect<(dimensione.getWidth()-20)&&wRect>0) {/*@ assert wRect<(dimensione.getWidth()-20) && wRect>0;@*/ this.wRect = wRect;}
		else {/*@ assert wRect>=(dimensione.getWidth()-20) || wRect<=0;@*/  this.wRect=(int) (dimensione.getWidth()-20);}
	}

	//@ ensures \result == this.hRect; 
	//@ assignable \nothing;
	public /*@ pure@*/ int getHRect() {
		return hRect;
	}


	//@ requires hrect>=0;
	//@ ensures hRect==hrect || hRect==dimensione.height-20;
	//@ assignable this.hRect;
	public void setHRect(final /*@ non_null@*/  int hrect) {
		if(hrect<(dimensione.getHeight()-20)&&hrect>0) {/*@ assert hrect<(dimensione.getHeight()-20) && hrect>0;@*/  this.hRect = hrect;}
		else {/*@ assert hrect>=(dimensione.getHeight()-20) || hrect<=0;@*/this.hRect=(int) (dimensione.getHeight()-20);}
	}

	//@ ensures \result == this.hBar; 
	//@ assignable \nothing;
	public /*@ pure@*/ int getHBar() {
		return hBar;
	}

	//@ requires hbar>=0;
	//@ ensures this.hBar==hbar || this.xBar==hRect-10;
	//@ assignable this.hBar;
	public void setHBar(final /*@ non_null@*/ int hbar) {
		if(hbar<(hRect-10)&&hbar>0) {/*@ assert hbar<(hRect-20) && hbar>0;@*/	this.hBar = hbar;}
		else {/*@ assert hbar>=(hRect-20) || hbar<=0;@*/ this.hBar=hRect-10;}
	}

	//@ ensures \result == this.xBar; 
	//@ assignable \nothing;
	public /*@ pure@*/ int getXBar() {
		return xBar;
	}

	
	//@ requires xBar>0 && this.livMax>-1;
	//@ ensures this.xBar==xBar || this.xBar==livMax;
	//@ assignable this.xBar;
	//@ assignable this.livMin;
	public void setXBar(final /*@ non_null@*/ int xBar) {
		assert xBar>0;
		if(livMin==-1&&livMax>=livMin) {
			/*@ assert livMin==-1&&livMax>=livMin;@*/
			this.livMin=livMax;
		}
		if(xBar<=livMax && xBar>=livMin) {/*@ assert xBar<=livMax && xBar>=livMin;@*/	this.xBar = xBar;}
		else{/*@ assert xBar>=livMax || xBar<livMin;@*/ this.xBar=livMax; 	}
	}

	//@ ensures \result == this.livMax; 
	//@ assignable \nothing;
	public /*@ pure@*/ int getLivMax() {
		return livMax;
	}



	/**
	 * @param livMax
	 */
	/*@ requires livMax>0;
	  @ ensures this.livMax==livMax && this.livMax<=this.wRect-10;
	  @ ensures this.xBar==this.livMax;
	  @ assignable this.livMax;
	  @ assignable xBar;
	  @*/
	public void setLivMax(final/*@ non_null@*/  int livMax) {
		assert livMax>0;
		if(livMax<(wRect-10)&&livMax>0) {/*@assert livMax<(wRect-10)&&livMax>0;@*/	this.livMax = livMax;}
		else {/*@assert livMax>=(wRect-10)||livMax<=0;@*/ this.livMax = wRect-10;}
		this.xBar=this.livMax;
	}



	//@ ensures \result == this.livMin; 
	//@ assignable \nothing;
	public /*@ pure@*/ int getLivMin() {
		return livMin;
	}



	/*@ requires livMin>0;
	  @ ensures this.livMin==livMin && this.livMin<=this.livMax;
	  @ assignable this.livMax;
	  @ assignable this.livMin;
	  @*/
	public void setLivMin(final /*@ non_null@*/  int livMin) {
		assert livMin>0;
		if(livMax==-2) {
			//@assert livMax!=-2;
			livMax=livMin;
		}
		if(livMin<(wRect-10)&&livMin>0&&livMin<=livMax) {/*@assert livMin<(wRect-10)&&livMin>0&&livMin<=livMax;@*/	this.livMin = livMin;}
		else {
			//@assert livMin>(wRect-10) || livMin<=0 || livMin>livMax;
			this.livMin=livMax;
		}
	}



	//@ ensures \result == this.distSchermo; 
	//@ assignable \nothing;
	public /*@ pure@*/ int getDistSchermo() {
		return distSchermo;
	}


	/*@ requires distSchermo>0;
	  @ ensures this.distSchermo==distSchermo*10;
	  @ assignable this.distSchermo;
	  @*/
	public void setDistSchermo(final /*@ non_null@*/  int distSchermo) {
		assert distSchermo>0;
		this.distSchermo = distSchermo*10;
	}


	//@ ensures \result == this.color1; 
	//@ assignable \nothing;
	public /*@ pure@*/ Color getC1() {
		return color1;
	}


	/*@ requires color1!=null;
	  @ ensures this.color1==color1;
	  @ assignable this.color1;
	  @*/
	public void setC1(final /*@ non_null@*/ Color color1) {
		this.color1 = color1;
	}


	//@ ensures \result == this.color2; 
	//@ assignable \nothing;
	public /*@ pure@*/ Color getC2() {
		return color2;
	}


	/*@ requires color2!=null;
	  @ ensures this.color2==color2;
	  @ assignable this.color2;
	  @*/
	public void setC2(final /*@ non_null@*/ Color color2) {
		this.color2 = color2;
	}
	

	//@ ensures \result == this.pos;
	//@ assignable \nothing;
	public /*@ pure@*/ boolean isPos() {
		return pos;
	}



	//@ requires true;
	//@ ensures \old(pos)!=pos ==> color1==\old(color2) && color2==\old(color1);
	//@ assignable this.pos;
	public void setPos(final /*@ non_null@*/ boolean position) {
		if(this.pos!=position) {
			//@assert this.pos!=position;
			final Color cTemp=getC1();
			this.setC1(getC2());
			this.setC2(cTemp);
		}
		this.pos = position;
		
	}


	/*@ ensures \result == this.livello;
	  @ assignable \nothing;
	  @*/
	public /*@ pure@*/ double getLivello() {
		return livello;
	}


	//@ requires level>=0 && level>=this.livMin && level<=this.livMax;
	//@ assignable livello;
	public void setLivello(final /*@ non_null@*/ double level) {
		livello = level;
	}


	/*@ ensures \result == this.angolo;
      @ assignable \nothing;
	  @*/
	public /*@ pure@*/ double getAngolo() {
		return angolo;
	}


	/* @ requires angle>=0; 
	   @ assignable this.angolo;
	   @*/
	public void setAngolo(final /*@ non_null@*/ double angle) {
		this.angolo = angle;
	}


	/* @ requires dimensione.width>=0 && dimensione.height>=0; 
	   @ assignable this.dimensione;
	   @*/
	public void setDimensione(final Dimension dimensione) {
		this.dimensione = dimensione;
	}
	

	 /* @ ensures \result == this.dimensione;
        @ assignable \nothing;
	    @*/
	public /*@ pure@*/ Dimension getDimensione() {
		return dimensione;
	}
	


	/* @ requires width>=0 && height>=0;
	   @ assignable this.dimensione;
	   @*/
	public void setDimensione(final /*@ non_null@*/ int width,final /*@ non_null@*/  int height) {
		this.dimensione = new Dimension(width,height);
	}
	


	/* @ requires width>=0 && height>=0;
	   @ assignable this.dimensione;
	   @*/
	public void setDimensione(final /*@ non_null@*/ double width,final /*@ non_null@*/ double height) {
		this.dimensione = new Dimension((int)width,(int)height);
	}


	
	
}
