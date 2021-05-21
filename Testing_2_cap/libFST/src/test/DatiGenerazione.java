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
	//CODE REFACTOR:
	//GOD CLASS: la classe conteneva molti attributi che sono stati refattorizzati inserendoli in sottoclassi
	
	
	//PMD1: conversione di tutte le variabili con le lettere 
	//       maiuscole iniziali in minuscole
	//PMD2: aggiunta dei commenti a ogni attributo e metodo della classe
	//PMD3: aggiunta parentesi graffe negli if-else
	//PMD4: aggiunto final a alcune variabili che potevano essere non inizializzate
	// (MethodArgumentCouldBeFinal: Parameter is not assigned and could be declared
	//  final)
	
	/**
	 * data test
	 */
	private transient final DatiGenerazioneTestData dataTest = new DatiGenerazioneTestData();

	/**
	 * person class
	 */
	private transient final DatiGenerazionePersona dataPersona = new DatiGenerazionePersona();
	
	/**
	 * TIMEZONE
	 */
	private final static String TIMEZONE="Europe/Rome";
	
	/**
	 * @author stefa
	 *dati generazione test
	 */
	public static class DatiGenerazioneTestData {
		/**
		 *  dimensione del monitor in diagonale in pollici
		 */
		public int monitorSize;
		/**
		 * livello massimo del test
		 */
		public int livMax;
		/**
		 * livello minimo del test
		 */
		public int livMin;
		/**
		 * distanza dallo schermo
		 */
		public int distSchermo;
		
		/**
		 * livello attuale del test
		 */
		public double livello;
		/**
		 * angolo di generazione
		 */
		public double angolo;
		
		/**
		 * dataTest per generazione immagine
		 */
		public transient DatiGenerazioneTestDataData data = new DatiGenerazioneTestDataData();
		
		/**
		 * @author stefa
		 *	dataTest per generazione
		 */
		public static class DatiGenerazioneTestDataData {
			/**
			 * dimensione
			 */
			public transient Dimension dimensione;
			/**
			 *  larghezza rettangolo immagine in pixel
			 */
			public transient int wRect;
			/**
			 *  altezza rettangolo immagine in pixel
			 */
			public transient int hRect;
			/**
			 * altezza barre interne
			 */
			public transient int hBar;
			/**
			 * larghezza tra le barre attuale
			 */
			public transient int xBar;
			/**
			 * colore prima barra (colore occhio sinistro solitamente)
			 */
			public transient Color color1;
			/**
			 * colore seconda barra (colore occhio destro solitamente)
			 */
			public transient Color color2;
			/**
			 * posizione (false=indietro, true=avanti)
			 */
			public transient boolean pos;

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

		/**
		 * @return wrect
		 */
		public int getWRect() {
			return data.wRect;
		}

		/**
		 * @param wRect
		 */
		public void setWRect(final int wRect) {
			if(wRect<(data.dimensione.getWidth()-20)&&wRect>0) { this.data.wRect = wRect;} //call chain
			else { this.data.wRect=(int) (data.dimensione.getWidth()-20);} //call chain
		}

		/**
		 * @return hRect
		 */
		public int getHRect() {
			return data.hRect;
		}

		/**
		 * @param hRect
		 */
		public void setHRect(final int hRect) {
			if(hRect<(data.dimensione.getHeight()-20)&&hRect>0) { this.data.hRect = hRect;} //call chain
			else {this.data.hRect=(int) (data.dimensione.getHeight()-20);} //call chain
		}

		/**
		 * @return hBar
		 */
		public int getHBar() {
			return data.hBar;
		}

		/**
		 * @param hBar
		 */
		public void setHBar(final int hBar) {
			if(hBar<(data.hRect-10)&&hBar>0) {	this.data.hBar = hBar;}
			else {this.data.hBar=data.hRect-10;}
		}

		/**
		 * @return xBar
		 */
		public int getXBar() {
			return data.xBar;
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
			if(xBar<=livMax && xBar>=livMin) {	this.data.xBar = xBar;}
			else if(livMax<1) { //linear
					throw new ArithmeticException("Inserisci un livello massimo corretto");
			}else {
					this.data.xBar=livMax;
			}
		}

		public int getLivMax() {
			return livMax;
		}



		/**
		 * @param livMax
		 */
		public void setLivMax(final int livMax) {
			if(livMax<(data.wRect-10)&&livMax>0) {	this.livMax = livMax;}
			else { this.livMax = data.wRect-10;}
			this.data.xBar=this.livMax;
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
			if(livMin<(data.wRect-10)&&livMin>0&&livMin<=livMax) {	this.livMin = livMin;}
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
			return data.color1;
		}

		/**
		 * @param color1
		 */
		public void setC1(final Color color1) {
			this.data.color1 = color1;
		}

		/**
		 * @return
		 */
		public Color getC2() {
			return data.color2;
		}

		/**
		 * @param color2
		 */
		public void setC2(final Color color2) {
			this.data.color2 = color2;
		}
		

		/**
		 * @return
		 */
		public boolean isPos() {
			return data.pos;
		}



		/**
		 * @param pos
		 */
		public void setPos(final boolean pos) {
			if(this.data.pos!=pos) {
				final Color cTemp=getC1();
				this.setC1(getC2());
				this.setC2(cTemp);
			}
			this.data.pos = pos;
			
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
			this.data.dimensione = dimensione;
		}
		/**
		 * @return dimensione
		 */
		public Dimension getDimensione() {
			return data.dimensione;
		}
		/**
		 * @param dataTest.dimensione the dimension to set (width and height in integer)
		 */
		public void setDimensione(final int width,final int height) {
			this.data.dimensione = new Dimension(width,height);
		}
		/**
		 * @param dataTest.dimensione the dimension to set (width and height in double)
		 */
		public void setDimensione(final double width,final double height) {
			this.data.dimensione = new Dimension((int)width,(int)height);
		}
		
	}

	/**
	 * @author stefa
	 * dati generazione - persona
	 */
	public static class DatiGenerazionePersona {
		/**
		 * nome persona
		 */
		public String nome;
		/**
		 * sesso persona
		 */
		public String sesso;
		/**
		 * data di nascita
		 */
		public Calendar dataNasc;
		/**
		 * data esame
		 */
		public transient Calendar dataEsame;


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
			if(dateNow.after(datanascita)) { //object not locally created
				this.dataNasc = datanascita;}
		}
		/**
		 * @param dataPersona.dataNasc the dataNasc to set in dd/mm/yyyy
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

					if(dataEsame.getTime().after(data.getTime())) { //call chain
						this.dataNasc.setTime(data.getTime());
					}
				}
			}
		}
		
		/**
		 * @return the dataEsame
		 */
		public Calendar getDataEsame() {
			return dataEsame;
		}
	}

	

	
	/**
	 * costruttore vuoto
	 */
	public DatiGenerazione(){
		dataTest.data.dimensione=new Dimension(1,1);
		dataPersona.nome="Unnamed";
		dataPersona.sesso="null";
		dataPersona.dataNasc=Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE),Locale.ITALY);
		dataTest.monitorSize=0;
		dataTest.data.wRect=-1;
		dataTest.data.hRect=-1;
		dataTest.data.hBar=-1;
		dataTest.data.xBar=-1;
		dataTest.distSchermo=0;
		dataTest.data.color1=Color.RED;
		dataTest.data.color2=Color.BLUE;
		dataTest.livMin=-1;
		dataTest.livMax=-1;
		dataTest.data.pos=false;
		dataTest.livello=-1;
		dataTest.angolo=-1;
		dataPersona.dataEsame=Calendar.getInstance();
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
		dataPersona.nome=name;
		dataPersona.sesso=sex;
		dataTest.monitorSize=monSize;
		dataTest.data.wRect=wrect;
	}
	
	
	/**
	 * @param vari parametri di configurazione
	 */
	public DatiGenerazione(final String name,final String sex,final int monSize, final int dimx,final int dimy,final int wrect,final int hrect,final int hbar,final int disSchermo,final int livmin,final int livmax){
		this();
		dataTest.data.dimensione=new Dimension(dimx,dimy);
		dataPersona.nome=name;
		dataPersona.sesso=sex;
		dataTest.monitorSize=monSize;
		dataTest.data.wRect=wrect;
		dataTest.data.hRect=hrect;
		dataTest.data.hBar=hbar;
		dataTest.data.xBar=-1;
		dataTest.distSchermo=disSchermo;
		dataTest.livMin=livmin;
		dataTest.livMax=livmax;
	}
	
	/**
	 * @return nome
	 */
	public String getNome() {
		return dataPersona.getNome();
	}
	
	/**
	 * @param nome
	 */
	public void setNome(final String nome) {
		dataPersona.setNome(nome);

	}

	/**
	 * @return sesso
	 */
	public String getSesso() {
		return dataPersona.getSesso();
	}

	/**
	 * @param sesso
	 */
	public void setSesso(final String sesso) {
		dataPersona.setSesso(sesso);
	}

	/**
	 * @return the dataNasc
	 */
	public Calendar getDataNasc() {
		return dataPersona.getDataNasc();
	}
	/**
	 * @param dn the dataNasc to set
	 */
	public void setDataNasc(final Calendar datanascita) {
		dataPersona.setDataNasc(datanascita);
	}
	/**
	 * @param dataPersona.dataNasc the dataNasc to set in dd/mm/yyyy
	 */
	public void setDataNasc(final String dataNascString) {
		dataPersona.setDataNasc(dataNascString);
	}
	
	/**
	 * @return the dataEsame
	 */
	public Calendar getDataEsame() {
		return dataPersona.getDataEsame();
	}
	
	/**
	 * @return monitor size
	 */
	public int getMonitorSize() {
		return dataTest.getMonitorSize();
	}
	
	/**
	 * @param dimension
	 */
	public void setMonitorSize(final int dimension) {
		dataTest.setMonitorSize(dimension);
	}

	/**
	 * @return wrect
	 */
	public int getWRect() {
		return dataTest.getWRect();
	}

	/**
	 * @param wRect
	 */
	public void setWRect(final int wRect) {
		dataTest.setWRect(wRect);
	}

	/**
	 * @return hrect
	 */
	public int getHRect() {
		return dataTest.getHRect();
	}

	/**
	 * @param hRect
	 */
	public void setHRect(final int hRect) {
		dataTest.setHRect(hRect);
	}

	/**
	 * @return hbar
	 */
	public int getHBar() {
		return dataTest.getHBar();
	}

	/**
	 * @param hBar
	 */
	public void setHBar(final int hBar) {
		dataTest.setHBar(hBar);
	}

	/**
	 * @return xbar
	 */
	public int getXBar() {
		return dataTest.getXBar();
	}

	/**
	 * @param xBar
	 * @throws ArithmeticException
	 */
	public void setXBar(final int xBar) {
		dataTest.setXBar(xBar);
	}

	/**
	 * @return livmax
	 */
	public int getLivMax() {
		return dataTest.getLivMax();
	}



	/**
	 * @param livMax
	 */
	public void setLivMax(final int livMax) {
		dataTest.setLivMax(livMax);
	}



	/**
	 * @return livmin
	 */
	public int getLivMin() {
		return dataTest.getLivMin();
	}



	/**
	 * @param livMin
	 */
	public void setLivMin(final int livMin) {
		dataTest.setLivMin(livMin);
	}



	/**
	 * @return distanza schermo
	 */
	public int getDistSchermo() {
		return dataTest.getDistSchermo();
	}

	/**
	 * @param distSchermo
	 */
	public void setDistSchermo(final int distSchermo) {
		dataTest.setDistSchermo(distSchermo);
	}

	/**
	 * @return color1
	 */
	public Color getC1() {
		return dataTest.getC1();
	}

	/**
	 * @param color1
	 */
	public void setC1(final Color color1) {
		dataTest.setC1(color1);
	}

	/**
	 * @return color2
	 */
	public Color getC2() {
		return dataTest.getC2();
	}

	/**
	 * @param color2
	 */
	public void setC2(final Color color2) {
		dataTest.setC2(color2);
	}
	

	/**
	 * @return positive
	 */
	public boolean isPos() {
		return dataTest.isPos();
	}



	/**
	 * @param pos
	 */
	public void setPos(final boolean pos) {
		dataTest.setPos(pos);
		
	}


	/**
	 * @return livello
	 */
	public double getLivello() {
		return dataTest.getLivello();
	}

	/**
	 * @param level
	 */
	public void setLivello(final double level) {
		dataTest.setLivello(level);
	}

	/**
	 * @return angolo
	 */
	public double getAngolo() {
		return dataTest.getAngolo();
	}
	/**
	 * @param angolo the angolo to set
	 */
	public void setAngolo(final double angle) {
		dataTest.setAngolo(angle);
	}
	/**
	 * @return the dataEsame
	 */

	/**
	 * @param dimensione the dimension to set (Dimension format)
	 */
	public void setDimensione(final Dimension dimensione) {
		dataTest.setDimensione(dimensione);
	}
	/**
	 * @return dimensione
	 */
	public Dimension getDimensione() {
		return dataTest.getDimensione();
	}
	/**
	 * @param dataTest.dimensione the dimension to set (width and height in integer)
	 */
	public void setDimensione(final int width,final int height) {
		dataTest.setDimensione(width, height);
	}
	/**
	 * @param dataTest.dimensione the dimension to set (width and height in double)
	 */
	public void setDimensione(final double width,final double height) {
		dataTest.setDimensione(width, height);
	}
	

	
	
	
}
