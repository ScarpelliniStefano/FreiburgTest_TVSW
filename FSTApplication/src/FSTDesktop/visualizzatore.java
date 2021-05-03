package FSTDesktop;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.io.*;

import org.jdatepicker.*;

import libFSTest.test.DatiGenerazione;
import libFSTest.test.FSTest;
import libFSTest.test.FSTest.Scelta;


class visualizzatore {
	/** Frame di visualizzazione  */
    static JFrame frame=new JFrame("Freiburg Test");

    static FSTest fst=new FSTest();
    
    /** Risultato */
	private static DatiGenerazione result=new DatiGenerazione();

	/**
	 * main visualizzatore
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
	    //dimensionamento e settaggio parametri base frame
	    Dimension ScreenSize=Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize((int)ScreenSize.getWidth(), (int)ScreenSize.getHeight());
	    frame.setIconImage(Toolkit.getDefaultToolkit().getImage("./img/images.jpg"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		//creazione panel button
		JPanel panelBT= new JPanel();
		//creazione button di inserimento dati utente
		JButton btDati = new JButton("Dati");
		panelBT.add(btDati);
		//creazione button inizio test
		JButton btGenera=new JButton("Inizia Test");
		btGenera.setEnabled(false);
		
		//creazione button fine test
		JButton btInterrompi = new JButton("Interrompi");
		btInterrompi.setEnabled(false);
		
		//creazione button avanti
		JButton btAvanti= new JButton("Avanti");
		btAvanti.setEnabled(false);
		
		//creazione button avanti
		JButton btIndietro= new JButton("Indietro");
		btIndietro.setEnabled(false);
		
		//evento click pulsante dati
		btDati.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){
				//creazione e settaggio form secondaria inserimento dati
				JDialog dialogDati=new JDialog();
				dialogDati.setIconImage(Toolkit.getDefaultToolkit().getImage("./img/images.jpg"));
				dialogDati.setTitle("Dati");
    		    dialogDati.setSize(450, 300);
    		    dialogDati.setLocation(frame.getLocation().x+100,frame.getLocation().y+100);
    		    //dialogDati.setAlwaysOnTop(true);
    		    
    		    //creazione e settaggio layout panel dati nella form secondaria
    		    JPanel panelDati= new JPanel();
    			panelDati.setLayout(new FlowLayout());
    			
    			//container dati paziente (nome, sesso e data di nascita)
    			Container grpDatiPaziente=new Container();
    		    grpDatiPaziente.setName("Dati paziente");
    			GridLayout griglia=new GridLayout();
    			griglia.setColumns(2);
    			griglia.setRows(3);
    			grpDatiPaziente.setLayout(griglia);
    		    JTextField txNome=new JTextField();
    			JLabel lbNome=new JLabel(),lbSesso=new JLabel(),lbDataNasc=new JLabel();
    		    
    			//settaggi inserimento nome
    			lbNome.setText("Nome e Cognome");
    			grpDatiPaziente.add(lbNome);
    			grpDatiPaziente.add(txNome);
    				
    			//settaggi label "Genere"
    			lbSesso.setText("Genere");
    			grpDatiPaziente.add(lbSesso);
    			
    			//generazione radioButton maschio/femmina
    			JRadioButton btnM = new JRadioButton("M");
    			JRadioButton btnF = new JRadioButton("F");
    			btnM.setSelected(true);
    			Container sex=new Container();
    			GridLayout grSex=new GridLayout();
    			grSex.setRows(1);grSex.setColumns(2);sex.setLayout(grSex);
    			ButtonGroup rbGroup = new ButtonGroup();
    			//label non visibile per sesso selezionato
    			JLabel sesso=new JLabel();
    			sesso.setText("Maschio");
				sesso.setVisible(false);
				//cambio selezione radioButton
    			ActionListener sliceActionListener = new ActionListener() {
    			      public void actionPerformed(ActionEvent actionEvent) {
    			        AbstractButton aButton = (AbstractButton) actionEvent.getSource();
    			        if (aButton.getText()=="M") sesso.setText("Maschio");
    			        else sesso.setText("Femmina");
    			      }
    			    };
    			//aggiunta a group sex dei radioButton e degli eventi    
    			sex.add(btnM);
    			sex.add(btnF);
    			rbGroup.add(btnM);
    			rbGroup.add(btnF);
    			btnM.addActionListener(sliceActionListener);
    		    btnF.addActionListener(sliceActionListener);
    			grpDatiPaziente.add(sex);

    			//gestione campo data di nascita (tramite JDatePicker)
    			lbDataNasc.setText("Data di nascita");
    			DatePicker dpDataNasc=new JDatePicker();
    			dpDataNasc.setTextEditable(true);
    			dpDataNasc.setShowYearButtons(true);
    			grpDatiPaziente.add(lbDataNasc);
    			grpDatiPaziente.add((JComponent)dpDataNasc);
    			
    			//creazione campi dati del test
    			JSpinner spMonitorSize=new JSpinner(), spWRect=new JSpinner(), spHRect=new JSpinner(), spHBar=new JSpinner(),spWBarInizio=new JSpinner(),spWBarFine=new JSpinner(),spDistanzaSchermo=new JSpinner();
    		    JLabel lbDimensioneMonitor=new JLabel(),lbWRect=new JLabel(),lbHRect=new JLabel(),lbHBar=new JLabel(),lbWBarInizio=new JLabel(),lbWBarFine=new JLabel(),lbDistSchermo=new JLabel();

    		    //creazione container campi dati test e inserimento dei dati
    			Container panelMonitor=new Container();
    			panelMonitor.setName("Dati monitor");
    			GridLayout grMonitor=new GridLayout();
    			grMonitor.setColumns(2);
    			grMonitor.setRows(7);
    			panelMonitor.setLayout(grMonitor);
    			lbDimensioneMonitor.setText("Dimensione monitor (inch)");
    			panelMonitor.add(lbDimensioneMonitor);
    			spMonitorSize.setValue(17.3);
    			panelMonitor.add(spMonitorSize);
    			lbWRect=new JLabel("Larghezza rettangolo:");
    			lbHRect=new JLabel("Altezza rettangolo:");
    			lbHBar=new JLabel("Altezza barre:");
    			lbWBarInizio=new JLabel("Disparita barre iniziale:");
    			lbWBarFine=new JLabel("Disparita barre finale:");
    			lbDistSchermo=new JLabel("Distanza dallo schermo (cm):");
    			spWRect.setValue(200);
    			spHRect.setValue(400);
    			spHBar.setValue(300);
    			spWBarInizio.setValue(12);
    			spWBarFine.setValue(1);
    			spDistanzaSchermo.setValue(40);
    			//aggiunta campi a panelMonitor
    			panelMonitor.add(lbDistSchermo);
    			panelMonitor.add(spDistanzaSchermo);
    			panelMonitor.add(lbWRect);
    			panelMonitor.add(spWRect);
    			panelMonitor.add(lbHRect);
    			panelMonitor.add(spHRect);
    			panelMonitor.add(lbHBar);
    			panelMonitor.add(spHBar);
    			panelMonitor.add(lbWBarInizio);
    			panelMonitor.add(spWBarInizio);
    			panelMonitor.add(lbWBarFine);
    			panelMonitor.add(spWBarFine);
    			
    			//container colori anaglifi
    			Container panelColor=new Container();
    			panelColor.setLayout(new FlowLayout());
    			//button colori 1 e 2
    			JButton btColo1 = new JButton("Colore 1");
    			btColo1.setSize(20, 5);
    			JButton btColo2 = new JButton("Colore 2");
    			btColo2.setSize(20, 5);
    			
    			btColo1.setBackground(Color.RED);
    			btColo2.setBackground(Color.BLUE);
    			panelColor.add(btColo1);
    			panelColor.add(btColo2);
    			//evento cambio colore
    			ActionListener cambioColore=new ActionListener(){  
    				public void actionPerformed(ActionEvent e){  
    					JButton eButton = (JButton) e.getSource();
    					Color cTemp=JColorChooser.showDialog(eButton, "Scegli colore", eButton.getBackground());
    	    		     eButton.setBackground(cTemp);
    				    }  
    			};  
    			
    			//aggiunta evento button colore
    			btColo1.addActionListener(cambioColore);
    			btColo2.addActionListener(cambioColore);
    			
    			//controllo precedente inserimento dati e completamento campi
    			if(result.getNome()!="Unnamed") {
					txNome.setText(result.getNome());
					if(result.getSesso()=="Maschio") btnM.setSelected(true);
					else btnF.setSelected(true);
					spMonitorSize.setValue(result.getMonitorSize());
					spWRect.setValue(result.getWRect());
					spHRect.setValue(result.getHRect());
					spHBar.setValue(result.getHBar());
					spWBarInizio.setValue(result.getLivMax());
					spWBarFine.setValue(result.getLivMin());
					spDistanzaSchermo.setValue(result.getDistSchermo());
					btColo1.setBackground(result.getC1());
					btColo2.setBackground(result.getC2());
				}
    			
    			//creazione container button salvataggio dati
    			Container panelSalva=new Container();
    			JButton btSalva = new JButton("Salva dati");
    			btSalva.setSize(20, 5);
    			panelSalva.add(btSalva);
    			
    			//evento click button salva
    			btSalva.addActionListener(new ActionListener(){  
    				public void actionPerformed(ActionEvent e){  
    					//completamento nuovi dati utente
    					DatiGenerazione nuoviDati = new DatiGenerazione();
						nuoviDati.setNome(txNome.getText());
						nuoviDati.setSesso(sesso.getText());
						
						//controllo campo data nascita con segnalazione eventuale errore
						if((Calendar)dpDataNasc.getModel().getValue()==null&&result.getDataNasc()==null) {
							String st = "Inserisci data nascita";
							JOptionPane.showMessageDialog(null, st);
							return;
						}else if((Calendar)dpDataNasc.getModel().getValue()==null&&result.getDataNasc()!=null){
							nuoviDati.setDataNasc(result.getDataNasc());
						}else {
						Calendar dn = (Calendar) dpDataNasc.getModel().getValue();
						nuoviDati.setDataNasc(dn);}
						
						//correzione valore dimensione monitor in decimi di pollice
						if(spMonitorSize.getValue().toString().contains(".")) {
							String monitorSizetmp;
							monitorSizetmp=spMonitorSize.getValue().toString().replace(".", "");
							spMonitorSize.setValue(Integer.parseInt(monitorSizetmp));
						}
						
						//inserimento dati test e colori
						nuoviDati.setMonitorSize((int)spMonitorSize.getValue());
						nuoviDati.setDimensione(ScreenSize);
						nuoviDati.setWRect((int)spWRect.getValue());
						nuoviDati.setHRect((int)spHRect.getValue());
						nuoviDati.setHBar((int)spHBar.getValue());
						nuoviDati.setXBar((int)spWBarInizio.getValue());
						nuoviDati.setLivMax((int)spWBarInizio.getValue());
						nuoviDati.setLivMin((int)spWBarFine.getValue());
						nuoviDati.setDistSchermo((int)spDistanzaSchermo.getValue());
						nuoviDati.setC1(btColo1.getBackground());
		    			nuoviDati.setC2(btColo2.getBackground());
		    			nuoviDati.setPos(false);
		    			nuoviDati.setAngolo(-1);
						result=nuoviDati;
						
						//abilitazione button generazione test
						btGenera.setEnabled(true);
						dialogDati.dispose();
    				    }  
    			});  
    			
    			//aggiunta container alla form secondaria
    			panelDati.add(grpDatiPaziente);
    			panelDati.add(panelMonitor);
				panelDati.add(panelColor);
				panelDati.add(btSalva);

    		    
    			dialogDati.add(panelDati);
    			
    			dialogDati.setVisible(true);

			}

		});  
         //creazione nuovo panel per contenimento immagine
                JPanel panelImg=new JPanel();
    		    frame.add(panelImg,BorderLayout.CENTER);
        //evento click button generazione immagine
		btGenera.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				
			    result.setDimensione(ScreenSize);
			    
			    //controllo veridicità della possibilità di generazione immagine dai dati inseriti
    			if(result.getHBar()>(result.getHRect()-10)||result.getXBar()>(result.getWRect()-10)) {
    				JLabel lbErrore=new JLabel();
    				lbErrore.setText("Errore! Dimensioni troppo grandi per il rettangolo");
    				lbErrore.setForeground(Color.RED);
    				panelImg.removeAll();
    				panelImg.add(lbErrore);
    			}
    			else {
    				//pulizia panel immagine
    				panelImg.removeAll();
    				
    				fst=new FSTest();
    				InputStream img=FSTest.IniziaTest(result);
    			    Image immg = null;
    			    //prova di streaming immagine
				    try {
					    immg = ImageIO.read(img);
				    } catch (IOException e1) {
					   return;
				    }
				    //inserimento immagine nel panel immagine
    			    JLabel picture=new JLabel((Icon) new ImageIcon(immg));	
                    panelImg.add(picture);
                    
                    //abilitazione button interrompi test
					btInterrompi.setEnabled(true);
					//abilitazione button mostra soluzioni test
					btAvanti.setEnabled(true);
					//abilitazione button mostra soluzioni test
					btIndietro.setEnabled(true);
                    
    			}

    		 frame.setVisible(true);
    		 }
			    });  
		ActionListener eventoClickSoluzione=new ActionListener(){  
			public void actionPerformed(ActionEvent e){  
				//disabilitazione button interrompi test
				btInterrompi.setEnabled(false);
				//disabilitazione button mostra soluzioni test
				btAvanti.setEnabled(false);
				//disabilitazione button mostra soluzioni test
				btIndietro.setEnabled(false);
				JButton eButton = (JButton) e.getSource();
				//creazione e settaggio form secondaria inserimento dati
					JDialog dialogFine=new JDialog();
					dialogFine.setIconImage(Toolkit.getDefaultToolkit().getImage("./img/images.jpg"));
					dialogFine.setTitle("Risultato");
					dialogFine.setBounds((int)result.getDimensione().getWidth()/2, (int)result.getDimensione().getHeight()/2, 260, 100);
				//stream immagine
				InputStream img;
                JTextArea taAngolo=new JTextArea();
                Image immg = null;
                Scelta scelta;
				switch(eButton.getText()){ 
				//ferma test
				case "Interrompi":
					
	    		    //scrittura angolo corrente
					//stream immagine
    				scelta=FSTest.ControlloRisposta("stop");
			       
    				//textarea risultato
                    taAngolo.setText("L'angolo di test è: "+(int)result.getAngolo()+ "'' con m="+(int)result.getLivello()+"µm \n "+FSTest.getCurrentStatus().toString());
                    taAngolo.setWrapStyleWord(true);
                    //panelImg.removeAll();
                    //panelImg.add(lbAngolo);
                    dialogFine.add(taAngolo);
                    dialogFine.setVisible(true);
                    dialogFine.setAlwaysOnTop(true);
                    break;
				case "Avanti":
					//stream immagine
    				scelta=FSTest.ControlloRisposta("forward");
    				if(scelta==Scelta.FINISCI) {
    					//label angolo corrente
    			        taAngolo=new JTextArea();
    			        
    			      //textarea risultato
                        taAngolo.setText("L'angolo di test è: "+(int)result.getAngolo()+ "'' con m="+(int)result.getLivello()+"µm \n"+FSTest.getCurrentStatus().toString());
                        taAngolo.setWrapStyleWord(true);
                        //panelImg.removeAll();
                        //panelImg.add(lbAngolo);
                        dialogFine.add(taAngolo);
                        dialogFine.setVisible(true);
                        dialogFine.setAlwaysOnTop(true);
    				}else {
    					//pulizia panel immagine
        				panelImg.removeAll();
        				img=FSTest.settaNuovaImg();
        				//stream immagine
        				immg = null;
        			    //prova di streaming immagine
    				    try {
    					    immg = ImageIO.read(img);
    				    } catch (IOException e1) {
    					   return;
    				    }
    				    //inserimento immagine nel panel immagine
        			    JLabel picture=new JLabel((Icon) new ImageIcon(immg));	
        			    //panelImg.repaint();
                        panelImg.add(picture);
                        frame.validate();
                        //abilitazione button soluzioni
				        btInterrompi.setEnabled(true);
						btAvanti.setEnabled(true);
						btIndietro.setEnabled(true);
    				}
					break;
				case "Indietro":
					//stream immagine
    				scelta=FSTest.ControlloRisposta("behind");
    				if(scelta==Scelta.FINISCI) {
    					//label angolo corrente
    			        taAngolo=new JTextArea();
    					//label angolo corrente
    					 taAngolo.setText("L'angolo di test è: "+(int)result.getAngolo()+ "'' con m="+(int)result.getLivello()+"µm \n "+FSTest.getCurrentStatus().toString());
                         taAngolo.setWrapStyleWord(true);
                        //panelImg.removeAll();
                        //panelImg.add(lbAngolo);
                        dialogFine.add(taAngolo);
                        dialogFine.setVisible(true);
                        dialogFine.setAlwaysOnTop(true);
    				}else {
    					//pulizia panel immagine
        				panelImg.removeAll();
        				img=FSTest.settaNuovaImg();
        				//stream immagine
        				immg = null;
        			    //prova di streaming immagine
    				    try {
    					    immg = ImageIO.read(img);
    				    } catch (IOException e1) {
    					   return;
    				    }
    				    //inserimento immagine nel panel immagine
        			    JLabel picture=new JLabel((Icon) new ImageIcon(immg));	
        			    //panelImg.repaint();
                        panelImg.add(picture);
                        frame.validate();
                        //abilitazione button soluzioni
				        btInterrompi.setEnabled(true);
						btAvanti.setEnabled(true);
						btIndietro.setEnabled(true);
    				}
					break;
				default:
					break;
				}
				
			}
		};
		
		//assegnazione evento click pulsante
		btInterrompi.addActionListener(eventoClickSoluzione);
		btAvanti.addActionListener(eventoClickSoluzione);
		btIndietro.addActionListener(eventoClickSoluzione);
		
		//aggiunta campi in panel button
		panelBT.add(btGenera);
		panelBT.add(btInterrompi);
		panelBT.add(btAvanti);
		panelBT.add(btIndietro);
		frame.add(panelBT,BorderLayout.NORTH);
		frame.setVisible(true);
		
		
	}

}