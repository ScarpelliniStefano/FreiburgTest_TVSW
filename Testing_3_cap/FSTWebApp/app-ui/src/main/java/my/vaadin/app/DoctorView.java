package my.vaadin.app;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jooq.types.UInteger;
import org.json.JSONException;
import org.json.JSONObject;

import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.colorpicker.Color;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ColorPicker;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.renderers.ButtonRenderer;


import unibg.se4med.FSTdatabase;
import my.vaadin.app.Person;
import se4med.json.FreiburgTestJson;

  
  public class DoctorView extends VerticalLayout implements View {
	  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NAME = "doctor";
	

    protected VerticalLayout layout = new VerticalLayout();
  	
    
    
	public DoctorView() throws SQLException{
		  
		  Connection connection = FSTdatabase.getConn();
		 
		  layout.addComponent(new  Label("<h1><b>Benvenuto dottor "+FSTdatabase.nomeUtente+"</b></h1>",ContentMode.HTML));
		  TabSheet tabsheet = new TabSheet();
		  
		  String[] tabs = {"Visualizza pazienti","Inserisci paziente","Aggiorna settaggi" }; 
		  for (String caption: tabs) { 
			  tabsheet.addTab(new VerticalLayout(), caption);
		  }
		  
		  tabsheet.addSelectedTabChangeListener(e->{ 
			   Layout tab=(Layout)(e.getTabSheet().getSelectedTab()); 
			   String caption=tabsheet.getTab(tab).getCaption();
		       tab.removeAllComponents(); 
		       switch(caption) 
		       {
		       case "Visualizza pazienti": 
		    	// Have some data 
		 		  List<Person> people = new ArrayList<>();
				  
				try { 
					Statement stmt = connection.createStatement();
					String query="SELECT patientdoc.name, patientdoc.surname, patientdoc.dateofbirth,patientdoc.id FROM patientdoc WHERE patientdoc.emaildoc=\""+FSTdatabase.email+"\" ";
					ResultSet rs = stmt.executeQuery(query);
				
				
		 		  boolean TestDone=false;
					while(rs.next()) {
							       Person p=new Person(rs.getString(1),rs.getString(2),rs.getDate(3),rs.getInt(4));
							       
							       Statement stmtInterno=connection.createStatement();
							       String timestampR="SELECT dateandtime,result FROM result_not_registered WHERE (idutente=\""+p.getId()+"\" AND idapp=\"Freiburg\")";
							       ResultSet risultatoPersona=stmtInterno.executeQuery(timestampR);
							       List<String> risultatiTest = new ArrayList<>();
							       JSONObject Resultfreiburg=new JSONObject();
							       TestDone=false;
							       while(risultatoPersona.next()) {
								       Resultfreiburg=new JSONObject(risultatoPersona.getString(2));
								       risultatiTest.add((risultatoPersona.getTimestamp(1)).toString() + " - Angolo: " + Resultfreiburg.getInt("angle") + "'' , M= " + Resultfreiburg.getInt("m") + "µm , "+Resultfreiburg.getString("status"));
								       TestDone=true;
							      }
							       risultatoPersona.close();
							       stmtInterno.close();
							       if(TestDone) {
							    	   p.setResultTest(risultatiTest);
							       }
							       people.add(p);
						      }
					rs.close();
				    stmt.close();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	   	 
		    	   	  
		    	   	  if(!people.isEmpty()) {
		    	   	  // Create a grid bound to the list 
		    	   	  Grid<Person> grid = new Grid<>(Person.class);
		    	   	  grid.setItems(people);
		    	      //grid.removeColumn("id");
		    	      grid.getColumn("id").setHidden(true);

		    	      // The Grid<>(Person.class) sorts the properties and in order to
		    	      // reorder the properties we use the 'setColumns' method.
		    	      grid.setColumns("nome", "cognome", "dataNasc");
		    	      grid.getColumn("nome").setCaption("Nome");
		 	          grid.getColumn("cognome").setCaption("Cognome");
		 	          grid.getColumn("dataNasc").setCaption("Data di nascita");
		    	   	  
		 			   grid.setSelectionMode(SelectionMode.SINGLE); // Render a button that deletes the data row (item) 
		 			   VerticalLayout vlDatiVis=new VerticalLayout();
		 			   grid.addColumn(person -> "Modifica", new  ButtonRenderer<Object>(clickEvent -> {
		 				   vlDatiVis.removeAllComponents();
		 				   Integer idMod=((Person)clickEvent.getItem()).getId();
		 				   GridLayout lytDati=new GridLayout(2,6);
		 				    Label title=new Label("<h5><b>Nuovi dati</b></h5>",ContentMode.HTML);
		 				    lytDati.addComponent(title,0,0);
		 		    		TextField txNome=new TextField(),txCognome=new TextField();
		 		    		Label lbNome=new Label("Nome:"),lbCognome=new Label("Cognome:"),lbDataNasc=new Label("Data di nascita:");
		 		    		
		 		    		//aggiunta campo nome 
		 		    		lytDati.addComponent(lbNome,0,1);
		 		    		txNome.setValue(((Person)clickEvent.getItem()).getNome());
		 		    		lytDati.addComponent(txNome,1,1);
		 		    		
		 		    		//aggiunta campo cognome
		 		    		lytDati.addComponent(lbCognome,0,2);
		 		    		txCognome.setValue(((Person)clickEvent.getItem()).getCognome());
		 		    		lytDati.addComponent(txCognome,1,2);
		 		    		
		 		    		//aggiunta campo data nascita 
		 		    		lytDati.addComponent(lbDataNasc,0,3);
		 		    		HorizontalLayout lytDataNasc=new HorizontalLayout();
				    		CheckBox cbOpzionalDate=new CheckBox("Data nulla");
				    		cbOpzionalDate.setValue(false);

				    		
		 		    		DateField  dataNasc=new DateField();
		 		    		java.util.Date dt=((Person)clickEvent.getItem()).getDataNasc();
		 		    		if(dt==null) {
				    			dataNasc.setValue(new Date(Calendar.getInstance().getTimeInMillis()).toLocalDate());
				    		}else {
				    		    dataNasc.setValue(new Date(dt.getTime()).toLocalDate());
				    		}
		 		    		lytDataNasc.addComponent(dataNasc);
		 		    		lytDataNasc.addComponent(cbOpzionalDate);
		 		    		lytDati.addComponent(lytDataNasc,1,3);

		 		    		Button btSalva = new Button("Salva");
		 		    		lytDati.addComponent(btSalva,1,4);
		 		    		lytDati.setComponentAlignment(btSalva, Alignment.BOTTOM_CENTER);
		 		    		Label err=new Label("");
				    		err.setContentMode(ContentMode.HTML);
				    		lytDati.addComponent(err,1,5);
				    		btSalva.addClickListener(event -> {
				    			if(txNome.getValue()!="" &&  txCognome.getValue()!="") {
				    				if(cbOpzionalDate.getValue()) {
				    					people.remove(((Person)clickEvent.getItem()));
				    					Person p=new Person(txNome.getValue(), txCognome.getValue(), null, idMod);
				    					people.add(p);
				    					Statement stmtUp = null;
				    					int ok=0;
				    					try {
											stmtUp=connection.createStatement();
											String update="UPDATE patientdoc SET patientdoc.name=\""+txNome.getValue()+"\", patientdoc.surname=\""+
				    									txCognome.getValue()+"\", patientdoc.dateofbirth=null WHERE patientdoc.id="+idMod;
										
											ok = stmtUp.executeUpdate(update);
											stmtUp.close();
										} catch (SQLException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
				    					if(ok>0) {
					    					lytDati.removeAllComponents();
					    					grid.setItems(people);
					    					grid.getDataProvider().refreshAll();
					    				}else {
					    						lytDati.removeComponent(err);
					    						err.setValue("<h4><font color=#ff0000>Errore: inserimento non andato a buon fine</font></h4>");
					    						lytDati.addComponent(err,1,4);
					    				}
				    				}else{
				    				Date now=new Date(Calendar.getInstance().getTimeInMillis());
				    				java.sql.Date datasql=FSTdatabase.getMilliseconds(dataNasc.getValue().getYear(), dataNasc.getValue().getMonthValue(), dataNasc.getValue().getDayOfMonth());
				    						    				
				    				if(datasql.before(now)) {
				    							    			
				    					people.remove(((Person)clickEvent.getItem()));
				    					Person p=new Person(txNome.getValue(), txCognome.getValue(), new Date(datasql.getTime()), idMod);
				    					people.add(p);
				    					Statement stmtUp=null;
				    					try {
											stmtUp=connection.createStatement();
										
				    					String update="UPDATE patientdoc SET patientdoc.name=\""+txNome.getValue()+"\", patientdoc.surname=\""+
				    									txCognome.getValue()+"\", patientdoc.dateofbirth="+datasql+" WHERE patientdoc.id="+idMod;
				    					int ok=stmtUp.executeUpdate(update);
				    					stmtUp.close();
				    					} catch (SQLException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
				    					lytDati.removeAllComponents();
				    					grid.setItems(people);
				    					grid.getDataProvider().refreshAll();
				    				}else {
				    					lytDati.removeComponent(err);
					    				err.setValue("<h4><font color=#ff0000>Errore: data di nascita non corretta</font></h4>");
			    						lytDati.addComponent(err,1,5);
				    				}
				    			}
				    			}
				    		});
		 		    		
		 		    		vlDatiVis.addComponent(lytDati);
		 			   }));
		 			   grid.addColumn(person -> "Cancella", new  ButtonRenderer<Object>(clickEvent -> {
		 				   people.remove(clickEvent.getItem());
		 				   try {
							Statement stmtDel=connection.createStatement();
		 			      
		 			        stmtDel.executeUpdate("DELETE from patientdoc WHERE patientdoc.id="+((Person)clickEvent.getItem()).getId());
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		 				   grid.setItems(people);
		 			   }));
		 			   
		 			   // Render a button that generate the test 
		 			   grid.addColumn(person -> "Test", new ButtonRenderer<Object>(clickEvent -> {
		 				   layout.removeAllComponents();
		 				   String patientName=((Person)clickEvent.getItem()).getNome()+" "+((Person)clickEvent.getItem()).getCognome();
		 				   Integer patientID=((Person)clickEvent.getItem()).getId();
		 				   VaadinSession.getCurrent().setAttribute("patientName", patientName);
		 				   VaadinSession.getCurrent().setAttribute("patientID", patientID);
		 				   getUI().getNavigator().addView(DoctorView.NAME, DoctorView.class);
		 				   getUI().getNavigator().addView(PatientView.NAME, PatientView.class);
		 				   Page.getCurrent().setUriFragment("!"+PatientView.NAME);
		 			   }));
		 			   
		 			   // Render a button that create the list of result 
		 			   grid.addColumn(person -> "Risultati", new ButtonRenderer<Object>(clickEvent -> {
		 				  vlDatiVis.removeAllComponents();
		 				    GridLayout lytDati=new GridLayout(1,2);
		 				   Label title=new Label("<h3><b>Risultati test paziente "+((Person)clickEvent.getItem()).getCognome() + " "+ ((Person)clickEvent.getItem()).getNome() +"</b></h3>",ContentMode.HTML);
		 				    lytDati.addComponent(title,0,0);
		 				    // Create a grid bound to the list 
		 				    List<String> risultati=((Person)clickEvent.getItem()).getResultTest();
				    	   	Label gridResult=new Label();
				    	   	gridResult.setContentMode(ContentMode.HTML);
				    	   	for(String s:risultati) {
				    	   		gridResult.setValue(gridResult.getValue()+"<br>"+s);
				    	   	}
				    	   	lytDati.addComponent(gridResult,0,1);
				    	   	vlDatiVis.addComponent(lytDati);
		 			   }));

		 			 
		 			  grid.setSizeFull();
		 			   tab.addComponent(grid); 
		 			   tab.addComponent(vlDatiVis);
		 			   }else {
		 				   tab.addComponent(new Label("Non hai pazienti"));
		 			   }
		    	   break;
		    	case "Inserisci paziente": 
		    		GridLayout lytDati=new GridLayout(2,5);
		    		TextField txNome=new TextField(),txCognome=new TextField();
		    		Label lbNome=new Label("Nome:"),lbCognome=new Label("Cognome:"),lbDataNasc=new Label("Data di nascita:");
		    		
		    		//aggiunta campo nome 
		    		lytDati.addComponent(lbNome,0,0);
		    		lytDati.addComponent(txNome,1,0);
		    		
		    		//aggiunta campo cognome
		    		lytDati.addComponent(lbCognome,0,1);
		    		lytDati.addComponent(txCognome,1,1);
		    		
		    		//aggiunta campo data nascita 
		    		lytDati.addComponent(lbDataNasc,0,2);
		    		HorizontalLayout lytDataNasc=new HorizontalLayout();
		    		DateField  dataNasc=new DateField();
		    		CheckBox cbOpzionalDate=new CheckBox("Data nulla");
		    		cbOpzionalDate.setValue(false);
		    		lytDataNasc.addComponent(dataNasc);
		    		lytDataNasc.addComponent(cbOpzionalDate);
		    		lytDati.addComponent(lytDataNasc,1,2);
		    		
		    		Button btSalva = new Button("Salva");
		    		lytDati.addComponent(btSalva,1,3);
		    		lytDati.setComponentAlignment(btSalva, Alignment.BOTTOM_CENTER);
		    		Label err=new Label("");
		    		err.setContentMode(ContentMode.HTML);
		    		lytDati.addComponent(err,1,4);
		    		btSalva.addClickListener(event -> {
		    			if(txNome.getValue()!="" &&  txCognome.getValue()!="") {
		    				if(cbOpzionalDate.getValue()) {
		    					int ok=0;
								try {
									Statement stmtUp=connection.createStatement();
									String update="INSERT INTO patientdoc (name,surname,dateofbirth,emaildoc) VALUES (\""+txNome.getValue()+"\", \""+
													txCognome.getValue()+"\",null, \""+FSTdatabase.email+"\")";
									ok = stmtUp.executeUpdate(update);
									stmtUp.close();
								} catch (SQLException e1) {
									err.setValue("<h4><font color=#ff0000>Errore: inserimento non andato a buon fine. Già presente</font></h4>");
								}
		    					if(ok>0) {
			    					dataNasc.setValue(LocalDate.now());
			    					txNome.setValue("");
			    					txCognome.setValue("");
			    					tabsheet.setSelectedTab(0);
			    				}else {
			    						lytDati.removeComponent(err);
			    						err.setValue("<h4><font color=#ff0000>Errore: inserimento non andato a buon fine</font></h4>");
			    						lytDati.addComponent(err,1,4);
			    				}
		    					
		    				}else 
		    					if(!dataNasc.isEmpty()) {
		    						Date now=new Date(Calendar.getInstance().getTimeInMillis());
		    						java.sql.Date datasql=FSTdatabase.getMilliseconds(dataNasc.getValue().getYear(), dataNasc.getValue().getMonthValue(), dataNasc.getValue().getDayOfMonth());
		    						    				
		    						if(datasql.before(now)) {
		    							int ok=0;
		    						
		    						try {
		    							 Statement stmtIns=connection.createStatement();
		    							 String update="INSERT INTO patientdoc (name,surname,dateofbirth,emaildoc) VALUES (\""+txNome.getValue()+"\", \""+
													txCognome.getValue()+"\",\""+datasql+"\", \""+FSTdatabase.email+"\")";
		    							 ok=stmtIns.executeUpdate(update);
		    							 stmtIns.close();
		    						} catch (SQLException e1) {
		    							// TODO Auto-generated catch block
		    							e1.printStackTrace();
		    						}
		    						if(ok>0) {
		    								dataNasc.setValue(LocalDate.now());
		    								txNome.setValue("");
		    								txCognome.setValue("");
		    								tabsheet.setSelectedTab(0);
		    						}else {
		    								lytDati.removeComponent(err);
		    								err.setValue("<h4><font color=#ff0000>Errore: inserimento non andato a buon fine</font></h4>");
		    								lytDati.addComponent(err,1,4);
		    						}
		    					
		    						}else {
		    							lytDati.removeComponent(err);
		    							err.setValue("<h4><font color=#ff0000>Errore: data di nascita non corretta</font></h4>");
		    							lytDati.addComponent(err,1,4);
		    						}
    					
		    					}
		    					else {
		    						lytDati.removeComponent(err);
		    						err.setValue("<h4><font color=#ff0000>Errore: Spuntare la casella di data nulla</font></h4>");
		    						lytDati.addComponent(err,1,4);
		    					}
		    			}else {
		    				lytDati.removeComponent(err);
		    				err.setValue("<h4><font color=#ff0000>Errore: Nome e/o cognome non inseriti</font></h4>");
    						lytDati.addComponent(err,1,4);
		    			}
		    			
		    		});
		    		
		    		tab.addComponent(lytDati);
		    		break;
		    	case "Aggiorna settaggi":
		    		GridLayout lytDatitest=new GridLayout(2,11);
		    		lytDatitest.addComponent(new Label("<h5><b>Dati test di Freiburg</b></h5>",ContentMode.HTML), 0, 0);
		    		
		    		//aggiunta campi test
		    		TextField nfMonitorSize=new TextField();
		    		TextField nfWRect=new TextField(), nfHRect=new TextField(), nfHBar=new TextField(),nfWBarMax=new TextField(),nfWBarMin=new TextField(),nfDistanzaSchermo=new TextField();
		    		Label lbDimensioneMonitor=new Label("Dimensione monitor (inch):"),lbWRect=new Label("Larghezza rettangolo:"),lbHRect=new Label("Altezza rettangolo:"),lbHBar=new Label("Altezza barre:"),lbWBarMax=new Label("Disparita barre iniziale:"),lbWBarMin=new Label("Disparita barre finale:"),lbDistSchermo=new Label("Distanza dallo schermo (cm):");
		    		lytDatitest.addComponent(lbDimensioneMonitor,0,1);
		    		nfMonitorSize.setValue("17.3");
		    		nfMonitorSize.setCaptionAsHtml(true);
		    		lytDatitest.addComponent(nfMonitorSize,1,1);
		    		lytDatitest.addComponent(lbDistSchermo,0,2);
		    		nfDistanzaSchermo.setValue("40");
		    		nfDistanzaSchermo.setCaptionAsHtml(true);
		    		lytDatitest.addComponent(nfDistanzaSchermo,1,2);
		    		lytDatitest.addComponent(lbWRect,0,3); 
		    		nfWRect.setValue("200");
		    		nfWRect.setCaptionAsHtml(true);
		    		lytDatitest.addComponent(nfWRect,1,3);
		    		lytDatitest.addComponent(lbHRect,0,4);
		    		nfHRect.setValue("400");
		    		nfHRect.setCaptionAsHtml(true);
		    		lytDatitest.addComponent(nfHRect,1,4);
		    		lytDatitest.addComponent(lbHBar,0,5);
		    		nfHBar.setValue("300");
		    		nfHBar.setCaptionAsHtml(true);
		    		lytDatitest.addComponent(nfHBar,1,5);
		    		lytDatitest.addComponent(lbWBarMax,0,6);
		    		nfWBarMax.setValue("12");
		    		nfWBarMax.setCaptionAsHtml(true);
		    		lytDatitest.addComponent(nfWBarMax,1,6);
		    		lytDatitest.addComponent(lbWBarMin,0,7);
		    		nfWBarMin.setValue("1");
		    		nfWBarMin.setCaptionAsHtml(true);
		    		lytDatitest.addComponent(nfWBarMin,1,7);
		    		
		    		//aggiunta campi colori 
		    		lytDatitest.addComponent(new Label("<h5><b>Colori</b></h5>",ContentMode.HTML), 0, 8);
		    		ColorPicker cp1=new ColorPicker("Colore 1"),cp2=new ColorPicker("Colore 2");
		    		cp1.setCaption("Colore 1");
		    		cp1.setValue(Color.RED);
		    		cp1.setHSVVisibility(false);
		    		cp1.setSwatchesVisibility(false);
		    		cp1.setPosition(200, 200);
		    		cp2.setCaption("Colore 2");
		    		cp2.setValue(Color.BLUE);
		    		cp2.setHSVVisibility(false);
		    		cp2.setSwatchesVisibility(false);
		    		cp2.setPosition(200, 200);
		    		lytDatitest.addComponent(cp1,0,9);
		    		lytDatitest.addComponent(cp2,1,9);
		    		try {
		    		Statement stmtCont=connection.createStatement();
		    		String query="SELECT COUNT(*) FROM doctorapp WHERE (doctorapp.emaildoctor=\""+FSTdatabase.email+"\" AND doctorapp.idapp=\"Freiburg\")";
			 		ResultSet rsCont= stmtCont.executeQuery(query);
		    		while(rsCont.next()) {
		    		if(rsCont.getInt(1)>0) {
		    			Statement stmtSett=connection.createStatement();
		    			String querySelSett="SELECT doctorapp.settings FROM doctorapp WHERE (doctorapp.emaildoctor=\""+FSTdatabase.email+"\" AND doctorapp.idapp=\"Freiburg\")";
		    			ResultSet rsSetting=stmtSett.executeQuery(querySelSett);
		    			//database.select(DOCTORAPP.SETTINGS).from(DOCTORAPP).where(DOCTORAPP.EMAILDOCTOR.eq(FSTdatabase.email).and(DOCTORAPP.IDAPP.eq("Freiburg")));
		    			JSONObject jsonobj = null;
		    			while(rsSetting.next()) {
		    				jsonobj=new JSONObject(rsSetting.getString(1));
		    			}
		    			nfMonitorSize.setValue(String.valueOf(jsonobj.get((FreiburgTestJson.monitorsize))));
		    			nfDistanzaSchermo.setValue(String.valueOf(((int)jsonobj.get(FreiburgTestJson.distscreen))));
		    			nfWRect.setValue(String.valueOf(((int)jsonobj.get(FreiburgTestJson.wrect))));
		    			nfHRect.setValue(String.valueOf(((int)jsonobj.get(FreiburgTestJson.hrect))));
		    			nfHBar.setValue(String.valueOf(((int)jsonobj.get(FreiburgTestJson.hbar))));
		    			nfWBarMax.setValue(String.valueOf(((int)jsonobj.get(FreiburgTestJson.xbarmax))));
		    			nfWBarMin.setValue(String.valueOf(((int)jsonobj.get(FreiburgTestJson.xbarmin))));
		    			String colorc1=(String) jsonobj.get(FreiburgTestJson.colorleft);
		    			String[] cdivide1=colorc1.split("-");
		    			int r1=Integer.parseInt(cdivide1[0]),g1=Integer.parseInt(cdivide1[1]),b1=Integer.parseInt(cdivide1[2]);
		    			cp1.setValue(new Color(r1, g1, b1));
		    			String colorc2=(String) jsonobj.get(FreiburgTestJson.colorright);
		    			String[] cdivide2=colorc2.split("-");
		    			int r2=Integer.parseInt(cdivide2[0]),g2=Integer.parseInt(cdivide2[1]),b2=Integer.parseInt(cdivide2[2]);
		    			cp2.setValue(new Color(r2, g2, b2));
		    			rsSetting.close();
		    			stmtSett.close();
		    		}
		    		}
		    		rsCont.close();
		    		stmtCont.close();
		    		}catch(SQLException e1) {
		    			// TODO Auto-generated catch block
						e1.printStackTrace();
		    		}
		    		Button btSalvaDatiTest = new Button("Salva");
		    		lytDatitest.addComponent(btSalvaDatiTest,1,10);
		    		lytDatitest.setComponentAlignment(btSalvaDatiTest, Alignment.BOTTOM_CENTER);
		    		
		    		btSalvaDatiTest.addClickListener(event -> {
		    			controllo c=new controllo();
		    			nfDistanzaSchermo.setValue(c.ControlloIntero(nfDistanzaSchermo, true,0));
		    			nfWRect.setValue(c.ControlloIntero(nfWRect, true,0));
		    			nfHRect.setValue(c.ControlloIntero(nfHRect, true,0));
		    			nfHBar.setValue(c.ControlloIntero(nfHBar, true,0));
		    			nfWBarMax.setValue(c.ControlloIntero(nfWBarMax, true,1));
		    			nfWBarMin.setValue(c.ControlloIntero(nfWBarMin, true,1));
		    			
		    			String d=c.ControlloIntero(nfMonitorSize, false,0);
		    			nfMonitorSize.setValue(d);
		    			if(nfDistanzaSchermo.getValue()!="Non è un numero"||nfWRect.getValue()!="Non è un numero"||nfHRect.getValue()!="Non è un numero"||nfHBar.getValue()!="Non è un numero"||nfWBarMax.getValue()!="Non è un numero"||nfWBarMin.getValue()!="Non è un numero"||d!="Non è un numero") {
		    				if(nfDistanzaSchermo.isEnabled()&&nfWRect.isEnabled()&&nfHRect.isEnabled()&&nfHBar.isEnabled()&&nfWBarMax.isEnabled()&&nfWBarMin.isEnabled()&&nfMonitorSize.isEnabled()) {
		    					if(Integer.parseInt(nfWBarMin.getValue())<=Integer.parseInt(nfWBarMax.getValue())) {
		    						
		    					
		    					String freiburgSettings = new JSONObject().put(FreiburgTestJson.distscreen, (Integer.parseInt(nfDistanzaSchermo.getValue())))
		    					                                  .put(FreiburgTestJson.wrect, (Integer.parseInt(nfWRect.getValue())))
		    					                                  .put(FreiburgTestJson.hrect, (Integer.parseInt(nfHRect.getValue())))
		    					                                  .put(FreiburgTestJson.hbar, (Integer.parseInt(nfHBar.getValue())))
		    					                                  .put(FreiburgTestJson.xbarmax, (Integer.parseInt(nfWBarMax.getValue())))
		    					                                  .put(FreiburgTestJson.xbarmin, (Integer.parseInt(nfWBarMin.getValue())))
		    					                                  .put(FreiburgTestJson.monitorsize, d)
		    					                                  .put(FreiburgTestJson.colorleft, cp1.getValue().getRed()+"-"+cp1.getValue().getGreen()+"-"+cp1.getValue().getBlue())
		    					                                  .put(FreiburgTestJson.colorright, cp2.getValue().getRed()+"-"+cp2.getValue().getGreen()+"-"+cp2.getValue().getBlue()).toString();
		    					try {
									Statement stmtCont=connection.createStatement();
									String queryC="SELECT COUNT(*) FROM doctorapp WHERE (doctorapp.emaildoctor=\""+FSTdatabase.email+"\" AND doctorapp.idapp=\"Freiburg\")";
									ResultSet rsCont2= stmtCont.executeQuery(queryC);
									while(rsCont2.next()) {
										Statement stmtUpInsSett=connection.createStatement();
										String queryUpIns;
										if(rsCont2.getInt(1)>0) {
											queryUpIns="UPDATE doctorapp SET doctorapp.settings=\""+freiburgSettings.replaceAll("\"", "\'")+"\" WHERE (doctorapp.emaildoctor=\""+FSTdatabase.email+"\" AND doctorapp.idapp=\"Freiburg\")";
										}else {
											queryUpIns="INSERT INTO doctorapp (emaildoctor,idapp,settings) VALUES (\""+FSTdatabase.email+"\",\"Freiburg\",\""+freiburgSettings.replaceAll("\"", "\'")+"\")";
										}
										stmtUpInsSett.executeUpdate(queryUpIns);
										stmtUpInsSett.close();
									}
									rsCont2.close();
									stmtCont.close();
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
		    				}else {
		    					nfWBarMin.setCaption("<h4><font color=#ff0000>Errore: il valore non può essere maggiore della distanza massima</font></h4>");
		    				}
		    			}else {
		    				nfDistanzaSchermo.setEnabled(true);
		    				nfWRect.setEnabled(true);
		    				nfHRect.setEnabled(true);
		    				nfHBar.setEnabled(true);
		    				nfWBarMax.setEnabled(true);
		    				nfWBarMin.setEnabled(true);
		    				nfMonitorSize.setEnabled(true);
		    			}
		    				}
		    	  }); 
		    	
		    	  tab.addComponent(lytDatitest);
		    	  break;
		     default: break; 
		     }
		       
		     
		     
		  }); // tabs 
		  Layout tab=(Layout)(tabsheet.getSelectedTab());
		  // Have some data 
		  List<Person> people = new ArrayList<>();
		   
		  Statement stmt=connection.createStatement();
  		  String query="SELECT name,surname,dateofbirth,id FROM patientdoc WHERE emaildoc=\""+FSTdatabase.email+"\"";
	 	  ResultSet rs= stmt.executeQuery(query);
		  boolean TestDone=false;
 		  while(rs.next()) {
    		       Person p=new Person(rs.getString(1),rs.getString(2),rs.getDate(3),rs.getInt(4));
    		       Statement stmtNR=connection.createStatement();
    		  	   query="SELECT dateandtime,result FROM result_not_registered WHERE (idutente="+p.getId()+" AND idapp=\"Freiburg\")";
    			   ResultSet rsRisPersona= stmtNR.executeQuery(query);
    		       List<String> risultatiTest = new ArrayList<>();
    		       JSONObject Resultfreiburg=new JSONObject();
    		       TestDone=false;
    		       while(rsRisPersona.next()) {
	    		       Resultfreiburg=new JSONObject(rsRisPersona.getString(2));
	    		       risultatiTest.add((rsRisPersona.getTimestamp(1)).toString() + " - Angolo: " + Resultfreiburg.getInt("angle") + "'' , M= " + Resultfreiburg.getInt("m") + "µm , "+Resultfreiburg.getString("status"));
	    		       TestDone=true;
	    	       }
    		       rsRisPersona.close();
    		       stmtNR.close();
    		       if(TestDone) {
    		    	   p.setResultTest(risultatiTest);
    		       }
    		       people.add(p);
    		       
    	      }
 		  rs.close();
 		  stmt.close();
   	   	  if(!people.isEmpty()) {
   	   	  // Create a grid bound to the list 
   	   	  Grid<Person> grid = new Grid<>(Person.class);
   	   	  grid.setItems(people);
   	      //grid.removeColumn("id");
   	      grid.getColumn("id").setHidden(true);

   	      // The Grid<>(Person.class) sorts the properties and in order to
   	      // reorder the properties we use the 'setColumns' method.
   	      grid.setColumns("nome", "cognome", "dataNasc");
   	      grid.getColumn("nome").setCaption("Nome");
	      grid.getColumn("cognome").setCaption("Cognome");
	      grid.getColumn("dataNasc").setCaption("Data di nascita");
   	   	  
			   grid.setSelectionMode(SelectionMode.SINGLE); // Render a button that deletes the data row (item) 
			   VerticalLayout vlDatiVis=new VerticalLayout();
			   grid.addColumn(person -> "Modifica", new  ButtonRenderer<Object>(clickEvent -> {
				   vlDatiVis.removeAllComponents();
				   Integer idMod=((Person)clickEvent.getItem()).getId();
				   GridLayout lytDati=new GridLayout(2,6);
				    Label title=new Label("<h5><b>Nuovi dati</b></h5>",ContentMode.HTML);
				    lytDati.addComponent(title,0,0);
		    		TextField txNome=new TextField(),txCognome=new TextField();
		    		Label lbNome=new Label("Nome:"),lbCognome=new Label("Cognome:"),lbDataNasc=new Label("Data di nascita:");
		    		
		    		//aggiunta campo nome 
		    		lytDati.addComponent(lbNome,0,1);
		    		txNome.setValue(((Person)clickEvent.getItem()).getNome());
		    		lytDati.addComponent(txNome,1,1);
		    		
		    		//aggiunta campo cognome
		    		lytDati.addComponent(lbCognome,0,2);
		    		txCognome.setValue(((Person)clickEvent.getItem()).getCognome());
		    		lytDati.addComponent(txCognome,1,2);
		    		
		    		//aggiunta campo data nascita 
 		    		lytDati.addComponent(lbDataNasc,0,3);
 		    		HorizontalLayout lytDataNasc=new HorizontalLayout();
		    		CheckBox cbOpzionalDate=new CheckBox("Data nulla");
		    		cbOpzionalDate.setValue(false);

		    		
 		    		DateField  dataNasc=new DateField();
 		    		java.util.Date dt=((Person)clickEvent.getItem()).getDataNasc();
 		    		if(dt==null) {
		    			dataNasc.setValue(new Date(Calendar.getInstance().getTimeInMillis()).toLocalDate());
		    		}else {
		    		    dataNasc.setValue(new Date(dt.getTime()).toLocalDate());
		    		}
 		    		lytDataNasc.addComponent(dataNasc);
 		    		lytDataNasc.addComponent(cbOpzionalDate);
 		    		lytDati.addComponent(lytDataNasc,1,3);
		    		
		    		Button btSalva = new Button("Salva");
		    		lytDati.addComponent(btSalva,1,4);
		    		lytDati.setComponentAlignment(btSalva, Alignment.BOTTOM_CENTER);
		    		Label err=new Label("");
		    		err.setContentMode(ContentMode.HTML);
		    		lytDati.addComponent(err,1,5);
		    		btSalva.addClickListener(event -> {
		    			if(txNome.getValue()!="" &&  txCognome.getValue()!="") {
		    				if(cbOpzionalDate.getValue()) {
		    					people.remove(((Person)clickEvent.getItem()));
		    					Person p=new Person(txNome.getValue(), txCognome.getValue(), null, idMod);
		    					people.add(p);
		    					int ok=0;
								try {
									Statement stmtUp=connection.createStatement();
									String queryUp="UPDATE patientdoc SET patientdoc.name=\""+txNome.getValue()+"\", patientdoc.surname=\""+
											txCognome.getValue()+"\", patientdoc.dateofbirth=null WHERE patientdoc.id="+idMod;
									ok = stmtUp.executeUpdate(queryUp);
									stmtUp.close();
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
		    					if(ok>0) {
			    					lytDati.removeAllComponents();
			    					grid.setItems(people);
			    					grid.getDataProvider().refreshAll();
			    				}else {
			    						lytDati.removeComponent(err);
			    						err.setValue("<h4><font color=#ff0000>Errore: inserimento non andato a buon fine</font></h4>");
			    						lytDati.addComponent(err,1,4);
			    				}
		    				}else{
		    				Date now=new Date(Calendar.getInstance().getTimeInMillis());
		    				java.sql.Date datasql=FSTdatabase.getMilliseconds(dataNasc.getValue().getYear(), dataNasc.getValue().getMonthValue(), dataNasc.getValue().getDayOfMonth());
		    						    				
		    				if(datasql.before(now)) {
		    							    			
		    					people.remove(((Person)clickEvent.getItem()));
		    					Person p=new Person(txNome.getValue(), txCognome.getValue(), new Date(datasql.getTime()), idMod);
		    					people.add(p);
		    					try {
									Statement stmtUp=connection.createStatement();
									String queryUp="UPDATE patientdoc SET patientdoc.name=\""+txNome.getValue()+"\", patientdoc.surname=\""+
											txCognome.getValue()+"\", patientdoc.dateofbirth=\""+datasql+"\" WHERE patientdoc.id="+idMod;
									int ok=stmtUp.executeUpdate(queryUp);
									stmtUp.close();
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
		    					lytDati.removeAllComponents();
		    					grid.setItems(people);
		    					grid.getDataProvider().refreshAll();
		    				}else {
		    					lytDati.removeComponent(err);
			    				err.setValue("<h4><font color=#ff0000>Errore: data di nascita non corretta</font></h4>");
	    						lytDati.addComponent(err,1,5);
		    				}
		    			}
		    			}
		    		});
		    		vlDatiVis.addComponent(lytDati);
			   }));
			   grid.addColumn(person -> "Cancella", new  ButtonRenderer<Object>(clickEvent -> {
			       try {   
			    	   Statement stmtDel = connection.createStatement();
        			   String sql = "DELETE FROM patientdoc WHERE patientdoc.id="+((Person)clickEvent.getItem()).getId();
        			   stmtDel.executeUpdate(sql);
        			   stmtDel.close();
			    	   people.remove(clickEvent.getItem());
			       	} catch (SQLException e1) {
			       		// TODO Auto-generated catch block
			       		e1.printStackTrace();
			       	}
			       
				   grid.setItems(people);
			   }));
			   
			   // Render a button that generate the test 
			   grid.addColumn(person -> "Test", new ButtonRenderer<Object>(clickEvent -> {
				   layout.removeAllComponents();
				   String patientName=((Person)clickEvent.getItem()).getNome()+" "+((Person)clickEvent.getItem()).getCognome();
				   Integer patientID=((Person)clickEvent.getItem()).getId();
				   VaadinSession.getCurrent().setAttribute("patientName", patientName);
 				   VaadinSession.getCurrent().setAttribute("patientID", patientID);
				   getUI().getNavigator().addView(DoctorView.NAME, DoctorView.class);
				   getUI().getNavigator().addView(PatientView.NAME, PatientView.class);
				   Page.getCurrent().setUriFragment("!"+PatientView.NAME);
			   }));
               
			   GridLayout lytDati=new GridLayout(1,2);
			   // Render a button that create the list of result 
 			   grid.addColumn(person -> "Risultati", new ButtonRenderer<Object>(clickEvent -> {
 				    vlDatiVis.removeAllComponents();
 				    lytDati.removeAllComponents();
 				    Label title=new Label("<h3><b>Risultati test paziente "+((Person)clickEvent.getItem()).getCognome() + " "+ ((Person)clickEvent.getItem()).getNome() +"</b></h3>",ContentMode.HTML);
 				    lytDati.addComponent(title,0,0);
 				    // Create a grid bound to the list 
 				    List<String> risultati=((Person)clickEvent.getItem()).getResultTest();
		    	   	Label gridResult=new Label();
		    	   	gridResult.setContentMode(ContentMode.HTML);
		    	   	for(String s:risultati) {
		    	   		gridResult.setValue(gridResult.getValue()+"<br>"+s);
		    	   	}
		    	   	lytDati.addComponent(gridResult,0,1);
		    	   	vlDatiVis.addComponent(lytDati);
 			   }));
 			  
			  grid.setSizeFull();
			   tab.addComponent(grid);
			   tab.addComponent(vlDatiVis);
			   }else {
				   tab.addComponent(new Label("Non hai pazienti"));
			   }
			   
		  layout.addComponent(tabsheet);
			// Create login button
		  Button logoutButton=null;
			logoutButton = new Button("Logout");
		    		    
			ClickListener loginButtonlistener = eBtn -> {
		       
				FSTdatabase.nomeUtente = "";
				FSTdatabase.email = "";
				// Store the current user in the service session
				getSession().setAttribute("user", null);
				getUI().getNavigator().addView(LoginView.NAME, LoginView.class);
				Page.getCurrent().setUriFragment("!"+LoginView.NAME);
			};
			logoutButton.addClickListener(loginButtonlistener);
			layout.addComponent(logoutButton);
		  addComponent(layout);
		
      }
	
	
}