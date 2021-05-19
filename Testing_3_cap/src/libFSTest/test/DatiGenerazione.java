package libFSTest.test;

import java.awt.Color;
import java.awt.Dimension;

public class DatiGenerazione {
	/*@ non_null @*/ private Dimension dimensione=new Dimension(0,0);
	/*@ non_null @*/ private String Nome="";
	/*@ non_null @*/ private String Sesso="";
	/*@ non_null @*/private int MonitorSize=-2;
	/*@ non_null @*/private int WRect=-2;
	/*@ non_null @*/private int HRect=-2;
	/*@ non_null @*/private int HBar=-2;
	/*@ non_null @*/private int XBar=-2;
	/*@ non_null @*/private int livMax=-2;
	/*@ non_null @*/private int livMin=-2;
	/*@ non_null @*/private int distSchermo=-2;
	/*@ non_null @*/private Color c1=Color.BLACK;
	/*@ non_null @*/private Color c2=Color.BLACK;
	private boolean Pos=false;
	/*@ non_null @*/private double Livello=-2;
	/*@ non_null @*/private double Angolo=-2;
	
	// @ invariant dimensione.width>=0 && dimensione.height>=0;
	
	// @ ensures Nome=="Unnamed" && Sesso=="null" && MonitorSize==0 && WRect==-1 && HRect==-1 ;
	// @ ensures HBar==-1 && XBar==-1 && disSchermo==0 && livMIn==-1 && livMax==-1 && pos==false && Livello==-1 && Angolo==-1;
	public DatiGenerazione(){
		dimensione=new Dimension(1,1);
		Nome="Unnamed";
		Sesso="null";
		MonitorSize=0;
		WRect=-1;
		HRect=-1;
		HBar=-1;
		XBar=-1;
		distSchermo=0;
		c1=Color.RED;
		c2=Color.BLUE;
		livMin=-1;
		livMax=-1;
		Pos=false;
		Livello=-1;
		Angolo=-1;
		
	}
	
	// @ requires name!=null && (sex=="man" or sex=="female") && monSize>=0 && wrect>=0
	// @ ensures Nome==name && Nome!=\old(name) && Sesso==sex && MonitorSize==monSize && WRect==wrect && HRect==-1 ;
	// @ ensures HBar==-1 && XBar==-1 && disSchermo==0 && livMIn==-1 && livMax==-1 && pos==false && Livello==-1 && Angolo==-1;
	public DatiGenerazione(String name, String sex, int monSize, int wrect){
		dimensione=new Dimension(1,1);
		Nome=name;
		Sesso=sex;
		MonitorSize=monSize;
		WRect=wrect;
		HRect=-1;
		HBar=-1;
		XBar=-1;
		distSchermo=0;
		c1=Color.RED;
		c2=Color.BLUE;
		livMin=-1;
		livMax=-1;
		Pos=false;
		Livello=-1;
		Angolo=-1;
		
	}
	
	// @ requires name!=null && (sex=="man" or sex=="female") && monSize>=0 && wRect>=0;
	// @ ensures Nome==name && Sesso==sex && MonitorSize==monSize && dimensione.width==dimx && dimensione.height==dimy && WRect==wrect && HRect==hrect ;
	// @ ensures HBar==hbar && XBar==-1 && disSchermo==disSchermo && livMin==livmin && livMax==livmax && pos==false && Livello==-1 && Angolo==-1;
	public DatiGenerazione(String name, String sex, int monSize,int dimx,int dimy, int wrect,int hrect, int hbar, int disSchermo, int livmin,int livmax){
		dimensione=new Dimension(dimx,dimy);
		Nome=name;
		Sesso=sex;
		MonitorSize=monSize;
		WRect=wrect;
		HRect=hrect;
		HBar=hbar;
		XBar=-1;
		distSchermo=disSchermo;
		c1=Color.RED;
		c2=Color.BLUE;
		livMin=livmin;
		livMax=livmax;
		Pos=false;
		Livello=-1;
		Angolo=-1;
		
	}
	
	
	public String getNome() {
		
			return Nome;

		
	}

	
	// @ requires nome!="";
	// @ ensures Nome==nome;
	public void setNome(String nome) {
		this.Nome=nome;
		
	}

	public String getSesso() {
		return Sesso;
	}

	public void setSesso(String sesso) {
		Sesso = sesso;
	}

	public int getMonitorSize() {
		return MonitorSize;
	}

	public void setMonitorSize(int d) {
		if(d>0)
			MonitorSize = d;
		else
			MonitorSize=0;
	}

	public int getWRect() {
		return WRect;
	}

	public void setWRect(int wRect) {
		if(wRect<(dimensione.getWidth()-20)&&wRect>0) WRect = wRect;
		else WRect=(int) (dimensione.getWidth()-20);
	}

	public int getHRect() {
		return HRect;
	}

	public void setHRect(int hRect) {
		if(hRect<(dimensione.getHeight()-20)&&hRect>0) HRect = hRect;
		else HRect=(int) (dimensione.getHeight()-20);
	}

	public int getHBar() {
		return HBar;
	}

	public void setHBar(int hBar) {
		if(hBar<(HRect-10)&&hBar>0)	HBar = hBar;
		else HBar=HRect-10;
	}

	public int getXBar() {
		return XBar;
	}

	public void setXBar(int xBar) throws ArithmeticException {
		if(livMin==-1&&livMax>=livMin)
			this.livMin=livMax;
		if(xBar<=livMax && xBar>=livMin)	this.XBar = xBar;
		else if(livMax==-2) 
				throw new ArithmeticException("Inserisci un livello massimo corretto");
			else
				this.XBar=livMax;
	}

	public int getLivMax() {
		return livMax;
	}



	public void setLivMax(int livMax) {
		if(livMax<(WRect-10)&&livMax>0)	this.livMax = livMax;
		else this.livMax = WRect-10;
		this.XBar=this.livMax;
	}



	public int getLivMin() {
		return livMin;
	}



	public void setLivMin(int livMin) {
		if(livMax==-2)
			livMax=livMin;
		if(livMin<(WRect-10)&&livMin>0&&livMin<=livMax)	this.livMin = livMin;
		else this.livMin=livMax;
	}



	public int getDistSchermo() {
		return distSchermo;
	}

	public void setDistSchermo(int distSchermo) {
		this.distSchermo = distSchermo*10;
	}

	public Color getC1() {
		return c1;
	}

	public void setC1(Color c1) {
		this.c1 = c1;
	}

	public Color getC2() {
		return c2;
	}

	public void setC2(Color c2) {
		this.c2 = c2;
	}
	

	public boolean getPos() {
		return Pos;
	}



	public void setPos(boolean pos) {
		if(pos!=Pos) {
			Color cTemp=getC1();
			this.setC1(getC2());
			this.setC2(cTemp);
		}
		Pos = pos;
		
	}


	public double getLivello() {
		return Livello;
	}

	public void setLivello(double i) {
		Livello = i;
	}

	public double getAngolo() {
		return Angolo;
	}
	/**
	 * @param d the angolo to set
	 */
	public void setAngolo(double d) {
		this.Angolo = d;
	}
	/**
	 * @return the dataEsame
	 */

	/**
	 * @param dimensione the dimension to set (Dimension format)
	 */
	public void setDimensione(Dimension dimensione) {
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
	public void setDimensione(int w,int h) {
		this.dimensione = new Dimension(w,h);
	}
	/**
	 * @param dimensione the dimension to set (width and height in double)
	 */
	public void setDimensione(double w,double h) {
		this.dimensione = new Dimension((int)w,(int)h);
	}


	
	
}
