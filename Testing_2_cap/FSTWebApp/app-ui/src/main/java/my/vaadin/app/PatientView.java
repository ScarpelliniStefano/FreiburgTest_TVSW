package my.vaadin.app;

  import java.awt.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.StreamResource.StreamSource;
  import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
  import com.vaadin.ui.Button;
  import com.vaadin.ui.Button.ClickListener;
  import com.vaadin.ui.GridLayout;
  import com.vaadin.ui.HorizontalLayout;
  import com.vaadin.ui.Image;
  import com.vaadin.ui.Label;
  import com.vaadin.ui.UI;
  import com.vaadin.ui.VerticalLayout;

  import libFSTest.test.DatiGenerazione;
  import libFSTest.test.FSTest.Scelta;
import se4med.json.FreiburgTestJson;
import unibg.se4med.FSTdatabase;



  
  public class PatientView extends VerticalLayout implements View{
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	  public static final String NAME = "patient";
      /** The result */
	  private static DatiGenerazione result=new DatiGenerazione(); 
	  private static StreamSource imgsource= null;
	  protected VerticalLayout layout = new VerticalLayout();
	  public PatientView() throws SQLException{
		  Connection connection = FSTdatabase.getConn();
		Integer ID=(Integer) VaadinSession.getCurrent().getAttribute("patientID");
		
		layout.addComponent(new Label("<h1><b>Test di Freiburg di "+VaadinSession.getCurrent().getAttribute("patientName").toString()+"</b></h1>",ContentMode.HTML));
		  //settaggio layout button 
		  final GridLayout layoutBTN=new GridLayout(2,1);
		  layoutBTN.setWidth("100%"); layoutBTN.setHeight("100%");
		  
		  final HorizontalLayout lytBtnDati=new HorizontalLayout(); 
		  //creazione button di inserimento dati utente 
		  Button btReturn = new Button("Torna a visualizzazione dottore");
		  lytBtnDati.addComponent(btReturn);
		  lytBtnDati.setComponentAlignment(btReturn, Alignment.TOP_LEFT);
		  
		  //creazione button di inizio test 
		  Button btGenera = new Button("Inizia test");
		  
		  final GridLayout lytTest=new GridLayout(1,1); 
		  //layout btn per il test final
		  HorizontalLayout lytBtnTest=new HorizontalLayout();
		  lytBtnDati.addComponent(btGenera); 
		  lytBtnDati.setComponentAlignment(btGenera, Alignment.TOP_CENTER);
		  
		  //evento click btn dati 
		  btReturn.addClickListener(e -> {
			  layout.removeAllComponents();
			  getUI().getNavigator().addView(DoctorView.NAME, DoctorView.class);
  			  getUI().getNavigator().addView(PatientView.NAME, PatientView.class);
  			  Page.getCurrent().setUriFragment("!"+DoctorView.NAME);
		  });
		  
		  ClickListener btnSolutionTestClick=eBtn -> { 
			  //generazione immagine 
			  //stream immagine 
			  // Find the application directory 
			  lytTest.removeAllComponents();
			  Scelta scelta; 
			  StreamResource resource=null;
			  Label lbAngolo=null;
			  java.util.Date dt = null;
			  Timestamp currentTime=null;
			  String Resultfreiburg="";
			  switch(eBtn.getButton().getCaption()){
			  		//ferma test 
			  		case "Interrompi Test": 
			  			scelta=FSTestSource.ControlloRisposta("stop");
			            lbAngolo=new Label("<br><center><h3>L'angolo di test e': "+(int)result.getAngolo()+"'' con m="+(int)result.getLivello()+"&#181;m - "+FSTestSource.getCurrentStatus().toString()+"</h3></center>",ContentMode.HTML);
			           
			            Resultfreiburg = new JSONObject().put("angle", ((int)result.getAngolo()))
			            										.put("m",  ((int)result.getLivello()))
			            										.put("status", FSTestSource.getCurrentStatus().toString())
			            										.toString(); 
			            dt=new java.util.Date();
			            currentTime = new Timestamp(dt.getTime());
			            try {
							Statement stmtIns=connection.createStatement();
							String update="INSERT INTO result_not_registered (idapp,dateandtime,result,idutente) VALUES (\"Freiburg\", \""+
											currentTime+"\",\""+Resultfreiburg.replaceAll("\"", "\'")+"\", "+ID+")";
							stmtIns.executeUpdate(update);
							stmtIns.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			            btGenera.setEnabled(true);
			            lytTest.removeAllComponents();
			            lytBtnTest.removeAllComponents(); 
			            lytTest.addComponent(lbAngolo);
			            break; 
			        case "Davanti":
			        	scelta=FSTestSource.ControlloRisposta("forward");
			        	if(scelta==Scelta.FINISCI)
			        	{ 
			        		lbAngolo=new Label("<br><center><h3>L'angolo di test e': "+(int)result.getAngolo()+"'' con m="+(int)result.getLivello()+"&#181;m - "+FSTestSource.getCurrentStatus().toString()+"</h3></center>",ContentMode.HTML); 
			        		
				            Resultfreiburg = new JSONObject().put("angle",((int)result.getAngolo()))
				            								 .put("m", ((int)result.getLivello()))
				            								 .put("status", FSTestSource.getCurrentStatus().toString())
				            								 .toString();
				            dt=new java.util.Date();
				            currentTime = new Timestamp(dt.getTime());
				            try {
								Statement stmtIns=connection.createStatement();
								String update="INSERT INTO result_not_registered (idapp,dateandtime,result,idutente) VALUES (\"Freiburg\", \""+
												currentTime+"\",\""+Resultfreiburg.replaceAll("\"", "\'")+"\", "+ID+")";
								stmtIns.executeUpdate(update);
								stmtIns.close();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
				            btGenera.setEnabled(true);
				            lytTest.removeAllComponents();
				            lytBtnTest.removeAllComponents();
				            lytTest.addComponent(lbAngolo); 
				        }else {
				        	//stream immagine 
				        	resource=new StreamResource(imgsource,"test"+imgsource.toString()+".png");
				        	lytTest.addComponent(new Image(null,resource));
				        } 
			        	break;
			        case "Dietro":
			        	scelta=FSTestSource.ControlloRisposta("behind");
			        	if(scelta==Scelta.FINISCI) {
			        		lbAngolo=new Label("<br><center><h3>L'angolo di test e': "+(int)result.getAngolo()+ "'' con m="+(int)result.getLivello()+"&#181;m - "+FSTestSource.getCurrentStatus().toString()+"</h3></center>",ContentMode.HTML); 
			        		 Resultfreiburg = new JSONObject().put("angle",((int)result.getAngolo()))
    								 .put("m", ((int)result.getLivello()))
    								 .put("status", FSTestSource.getCurrentStatus().toString())
    								 .toString();
			        		 dt=new java.util.Date();
			        		 currentTime = new Timestamp(dt.getTime());
			        		 try {
									Statement stmtIns=connection.createStatement();
									String update="INSERT INTO result_not_registered (idapp,dateandtime,result,idutente) VALUES (\"Freiburg\", \""+
													currentTime+"\",\""+Resultfreiburg.replaceAll("\"", "\'")+"\", "+ID+")";
									stmtIns.executeUpdate(update);
									stmtIns.close();
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
			        		 btGenera.setEnabled(true);
			        		 lytTest.removeAllComponents();
			        		 lytBtnTest.removeAllComponents();
			        		 lytTest.addComponent(lbAngolo);  
			        	 }else {
					        	//stream immagine 
					        	resource=new StreamResource(imgsource,"test"+imgsource.toString()+".png");
					        	lytTest.addComponent(new Image(null,resource));
					     } 
				         break; 
			  	}
		  }; 
			  	
		  //evento click btn Genera 
		btGenera.addClickListener(e -> { 
			
			         Statement stmtInterno;
					int c;
					try {
						stmtInterno = connection.createStatement();
						 String queryAllPatientDoc="SELECT * FROM patientdoc WHERE (id="+ID+")";
						 ResultSet risultatoPersona=stmtInterno.executeQuery(queryAllPatientDoc);
						
						c = 0;
						while(c<1) {
						while(risultatoPersona.next()) {
							if(!(risultatoPersona.getString(3).isEmpty())||!(risultatoPersona.getString(2).isEmpty())) {
						        result.setNome(risultatoPersona.getString(3) + " " + risultatoPersona.getString(2)); 
							}else {
								result.setNome("unnamed patient"); 
							}
						try {
						Calendar cdn = Calendar.getInstance();
						cdn.setTime((java.util.Date)risultatoPersona.getDate(5));
						result.setDataNasc(cdn); 
						}catch(Exception excp) {
							result.setDataNasc("01/01/0001"); 
						}
						}
						c+=1;
						}
						risultatoPersona.close();
						stmtInterno.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			  		
			  		int w=UI.getCurrent().getPage().getBrowserWindowWidth(); 
			  		int h=UI.getCurrent().getPage().getBrowserWindowHeight(); 
			  		result.setDimensione(w,h);
			  		JSONObject jsonobj;
			  		
					try {
						stmtInterno=connection.createStatement();
						String querysettings="SELECT settings FROM doctorapp WHERE (emaildoctor=\""+FSTdatabase.email+"\" AND idapp=\"Freiburg\")";
						 ResultSet rsSett=stmtInterno.executeQuery(querysettings);
						
						jsonobj = new JSONObject();
						c=0;
						while(rsSett.next()) {
							jsonobj=new JSONObject(rsSett.getString(1));
							c++;
						}
						rsSett.close();
						stmtInterno.close();
						if(c==0) {
				  			getUI().getNavigator().addView(DoctorView.NAME, DoctorView.class);
				  			getUI().getNavigator().addView(PatientView.NAME, PatientView.class);
				  			Page.getCurrent().setUriFragment("!"+DoctorView.NAME);
				  		}else {
				  		result.setDistSchermo((int) jsonobj.get(FreiburgTestJson.distscreen));
				  		result.setHRect((int)jsonobj.get(FreiburgTestJson.hrect));
				  		result.setWRect((int)jsonobj.get(FreiburgTestJson.wrect));
				  		result.setHBar((int)jsonobj.get(FreiburgTestJson.hbar));
				  		result.setLivMax((int)jsonobj.get(FreiburgTestJson.xbarmax));
				  		result.setLivMin((int)jsonobj.get(FreiburgTestJson.xbarmin));
				  		result.setXBar((int)jsonobj.get(FreiburgTestJson.xbarmax));
				  		result.setMonitorSize((int)(Double.parseDouble((String) jsonobj.get(FreiburgTestJson.monitorsize))*10));
				  		result.setPos(false);
				  		String colorc1=(String) jsonobj.get(FreiburgTestJson.colorleft);
		    			String[] cdivide1=colorc1.split("-");
		    			int r1=Integer.parseInt(cdivide1[0]),g1=Integer.parseInt(cdivide1[1]),b1=Integer.parseInt(cdivide1[2]);
		    			result.setC1(new java.awt.Color(r1, g1, b1));
		    			String colorc2=(String) jsonobj.get(FreiburgTestJson.colorright);
		    			String[] cdivide2=colorc2.split("-");
		    			int r2=Integer.parseInt(cdivide2[0]),g2=Integer.parseInt(cdivide2[1]),b2=Integer.parseInt(cdivide2[2]);
				  		result.setC2(new java.awt.Color(r2,g2,b2)); 
				  		}
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			  		
			  		
			  		
			  		//controllo veridicità della possibilità di generazione immagine dai dati inseriti
			  		if(result.getHBar()>(result.getHRect()-10)||result.getXBar()>(result.getWRect()-10)) {
			  			Label lbErrore=new Label("<br><center><h3 style='color: red;'>Errore! Dimensioni troppo grandi per il rettangolo</h3></center>");
			  			lytTest.removeAllComponents(); 
			  			lytTest.addComponent(lbErrore);
			  		} else {
			  			lytBtnTest.removeAllComponents(); 
			  			btGenera.setEnabled(false);
			  			
			  			//creazione button di interruzione test
			  			Button btInterrompi = new Button("Interrompi Test");
			  			btInterrompi.addClickListener(btnSolutionTestClick);
			  			lytBtnTest.addComponent(btInterrompi);
			  			lytBtnTest.setComponentAlignment(btInterrompi, Alignment.TOP_CENTER);
			  			
			  			Label testo=new Label("Dove vedi la barra?");
			  			lytBtnTest.addComponent(testo);
			  			lytBtnTest.setComponentAlignment(testo, Alignment.TOP_CENTER);
			  			
			  			//creazione button di test avanti 
			  			Button btAvanti = new Button("Davanti");
			  			btAvanti.addClickListener(btnSolutionTestClick);
			  			lytBtnTest.addComponent(btAvanti); 
			  			lytBtnTest.setComponentAlignment(btAvanti,Alignment.TOP_CENTER);
			  			
			  			//creazione button di test indietro 
			  			Button btIndietro = new Button("Dietro"); 
			  			btIndietro.addClickListener(btnSolutionTestClick);
			  			lytBtnTest.addComponent(btIndietro);
			  			lytBtnTest.setComponentAlignment(btIndietro, Alignment.TOP_CENTER);
			  			
			  			//generazione immagine 
			  			//stream immagine 
			  			lytTest.removeAllComponents(); 
			  			imgsource= new FSTestSource(result);
			  			StreamResource resource=new StreamResource(imgsource,"test"+imgsource.toString()+".png");
			  			lytTest.addComponent(new Image(null,resource));
			  		}
			  		
		  
		  });
		  
		  
		  layoutBTN.addComponent(lytBtnDati,0,0);
		  layoutBTN.addComponent(lytBtnTest,1,0); 
		  layout.addComponent(layoutBTN);
		  layout.addComponent(lytTest); 
		  addComponent(layout);
      }

      
  }