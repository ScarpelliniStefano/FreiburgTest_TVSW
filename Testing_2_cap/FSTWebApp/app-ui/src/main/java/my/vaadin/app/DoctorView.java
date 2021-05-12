package my.vaadin.app;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.jooq.DSLContext;
import org.jooq.InsertValuesStep4;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Record4;
import org.jooq.SelectConditionStep;
import org.jooq.types.UInteger;
import org.json.JSONObject;
import org.vaadin.ui.NumberField;

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

import static se4med.jooq.tables.Doctorapp.DOCTORAPP;
import static se4med.jooq.tables.Patientdoc.PATIENTDOC;
import static se4med.jooq.tables.ResultNotRegistered.RESULT_NOT_REGISTERED;
import unibg.se4med.FSTdatabase;
import my.vaadin.app.Person;
import se4med.jooq.tables.records.PatientdocRecord;
import se4med.json.FreiburgTestJson;

  
  public class DoctorView extends VerticalLayout implements View {
	  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NAME = "doctor";
	

    protected VerticalLayout layout = new VerticalLayout();
  	
	public DoctorView(){
		  DSLContext database=FSTdatabase.getDB();
		  
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
		 		   
		 		   
		 		  SelectConditionStep<Record4<String,String,Date,UInteger>> result=database.select(PATIENTDOC.NAME,PATIENTDOC.SURNAME,PATIENTDOC.DATEOFBIRTH,PATIENTDOC.ID).from(PATIENTDOC).where(PATIENTDOC.EMAILDOC.eq(FSTdatabase.email));
		 		  boolean TestDone=false;
		 		  for(Record4<String, String, Date, UInteger> r:result) {
		    		       Person p=new Person((String)r.getValue(0),(String)r.getValue(1),(Date)r.getValue(2),(UInteger)r.getValue(3));
		    		       SelectConditionStep<Record2<Timestamp,String>> risultatoPersona=database.select(RESULT_NOT_REGISTERED.DATEANDTIME,RESULT_NOT_REGISTERED.RESULT).from(RESULT_NOT_REGISTERED).where(RESULT_NOT_REGISTERED.IDUTENTE.eq(p.getId()).and(RESULT_NOT_REGISTERED.IDAPP.eq("Freiburg")));
		    		       List<String> risultatiTest = new ArrayList<>();
		    		       JSONObject Resultfreiburg=new JSONObject();
		    		       TestDone=false;
		    		       for(Record2<Timestamp, String> rResult:risultatoPersona) {
			    		       Resultfreiburg=new JSONObject(rResult.getValue(1).toString());
			    		       risultatiTest.add(((Timestamp)rResult.getValue(0)).toString() + " - Angolo: " + Resultfreiburg.getInt("angle") + "'' , M= " + Resultfreiburg.getInt("m") + "µm , "+Resultfreiburg.getString("status"));
			    		       TestDone=true;
			    	      }
		    		       if(TestDone) {
		    		    	   p=new Person((String)r.getValue(0),(String)r.getValue(1),(Date)r.getValue(2),(UInteger)r.getValue(3),risultatiTest);
		    		       }else {
		    		           p=new Person((String)r.getValue(0),(String)r.getValue(1),(Date)r.getValue(2),(UInteger)r.getValue(3));
		    		       }
		    		       people.add(p);
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
		 				   UInteger idMod=((Person)clickEvent.getItem()).getId();
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
				    					int ok=database.execute(database.update(PATIENTDOC).set(PATIENTDOC.NAME,txNome.getValue()).set(PATIENTDOC.SURNAME,txCognome.getValue()).set(PATIENTDOC.DATEOFBIRTH, (Date)null).where(PATIENTDOC.ID.eq(idMod))); 
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

				    					database.execute(database.update(PATIENTDOC).set(PATIENTDOC.NAME,txNome.getValue()).set(PATIENTDOC.SURNAME,txCognome.getValue()).set(PATIENTDOC.DATEOFBIRTH, datasql).where(PATIENTDOC.ID.eq(idMod))); 
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
		 				   database.execute(database.delete(PATIENTDOC).where(PATIENTDOC.ID.eq(((Person)clickEvent.getItem()).getId())));
		 				   grid.setItems(people);
		 			   }));
		 			   
		 			   // Render a button that generate the test 
		 			   grid.addColumn(person -> "Test", new ButtonRenderer<Object>(clickEvent -> {
		 				   layout.removeAllComponents();
		 				   String patientName=((Person)clickEvent.getItem()).getNome()+" "+((Person)clickEvent.getItem()).getCognome();
		 				   UInteger patientID=((Person)clickEvent.getItem()).getId();
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
		    					int ok=database.execute(database.insertInto(PATIENTDOC,PATIENTDOC.NAME,PATIENTDOC.SURNAME,PATIENTDOC.DATEOFBIRTH,PATIENTDOC.EMAILDOC).values(txNome.getValue(), txCognome.getValue(),null,FSTdatabase.email));
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
		    				}else if(dataNasc.getValue().toString()!="") {
		    				Date now=new Date(Calendar.getInstance().getTimeInMillis());
		    				java.sql.Date datasql=FSTdatabase.getMilliseconds(dataNasc.getValue().getYear(), dataNasc.getValue().getMonthValue(), dataNasc.getValue().getDayOfMonth());
		    						    				
		    				if(datasql.before(now)) {
		    							    					
		    					InsertValuesStep4<PatientdocRecord, String, String, Date, String> insert=database.insertInto(PATIENTDOC,PATIENTDOC.NAME,PATIENTDOC.SURNAME,PATIENTDOC.DATEOFBIRTH,PATIENTDOC.EMAILDOC).values(txNome.getValue(), txCognome.getValue(),datasql,FSTdatabase.email );
		    					int ok=insert.execute();
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
    					
		    				}else {
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
		    		SelectConditionStep<Record1<Integer>> contatore=database.selectCount().from(DOCTORAPP).where(DOCTORAPP.EMAILDOCTOR.eq(FSTdatabase.email).and(DOCTORAPP.IDAPP.eq("Freiburg")));
		    		for(Record1<Integer> c : contatore) {
		    		if((int)c.getValue(0)>0) {
		    			SelectConditionStep<Record1<String>> selezionesettaggi=database.select(DOCTORAPP.SETTINGS).from(DOCTORAPP).where(DOCTORAPP.EMAILDOCTOR.eq(FSTdatabase.email).and(DOCTORAPP.IDAPP.eq("Freiburg")));
		    			JSONObject jsonobj = null;
		    			for(Record1<String> r:selezionesettaggi) {
		    				jsonobj=new JSONObject((String)(r.getValue(0)));
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
		    		}
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
		    				SelectConditionStep<Record1<Integer>> contatore2=database.selectCount().from(DOCTORAPP).where(DOCTORAPP.EMAILDOCTOR.eq(FSTdatabase.email).and(DOCTORAPP.IDAPP.eq("Freiburg")));
		    				for(Record1<Integer> r : contatore2) {
		    					if((int)r.getValue(0)>0) {
		    						database.execute(database.update(DOCTORAPP).set(DOCTORAPP.SETTINGS,freiburgSettings).where(DOCTORAPP.EMAILDOCTOR.eq(FSTdatabase.email).and(DOCTORAPP.IDAPP.eq("Freiburg"))));
		    					}else {
		    						database.execute(database.insertInto(DOCTORAPP,DOCTORAPP.EMAILDOCTOR,DOCTORAPP.IDAPP,DOCTORAPP.SETTINGS).values(FSTdatabase.email,"Freiburg",freiburgSettings));
		    					}
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
		   
		   
		  SelectConditionStep<Record4<String, String, Date, UInteger>> result=database.select(PATIENTDOC.NAME,PATIENTDOC.SURNAME,PATIENTDOC.DATEOFBIRTH,PATIENTDOC.ID).from(PATIENTDOC).where(PATIENTDOC.EMAILDOC.eq(FSTdatabase.email));
		  boolean TestDone=false;
 		  for(Record4<String, String, Date, UInteger> r:result) {
    		       Person p=new Person((String)r.getValue(0),(String)r.getValue(1),(Date)r.getValue(2),(UInteger)r.getValue(3));
    		       SelectConditionStep<Record2<Timestamp,String>> risultatoPersona=database.select(RESULT_NOT_REGISTERED.DATEANDTIME,RESULT_NOT_REGISTERED.RESULT).from(RESULT_NOT_REGISTERED).where(RESULT_NOT_REGISTERED.IDUTENTE.eq(p.getId()).and(RESULT_NOT_REGISTERED.IDAPP.eq("Freiburg")));
    		       List<String> risultatiTest = new ArrayList<>();
    		       JSONObject Resultfreiburg=new JSONObject();
    		       TestDone=false;
    		       for(Record2<Timestamp, String> rResult:risultatoPersona) {
	    		       Resultfreiburg=new JSONObject(rResult.getValue(1).toString());
	    		       risultatiTest.add(((Timestamp)rResult.getValue(0)).toString() + " - Angolo: " + Resultfreiburg.getInt("angle") + "'' , M= " + Resultfreiburg.getInt("m") + "µm , "+Resultfreiburg.getString("status"));
	    		       TestDone=true;
	    	      }
    		       if(TestDone) {
    		    	   p=new Person((String)r.getValue(0),(String)r.getValue(1),(Date)r.getValue(2),(UInteger)r.getValue(3),risultatiTest);
    		       }
    		       people.add(p);
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
				   UInteger idMod=((Person)clickEvent.getItem()).getId();
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
		    					int ok=database.execute(database.update(PATIENTDOC).set(PATIENTDOC.NAME,txNome.getValue()).set(PATIENTDOC.SURNAME,txCognome.getValue()).set(PATIENTDOC.DATEOFBIRTH, (Date)null).where(PATIENTDOC.ID.eq(idMod))); 
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

		    					database.execute(database.update(PATIENTDOC).set(PATIENTDOC.NAME,txNome.getValue()).set(PATIENTDOC.SURNAME,txCognome.getValue()).set(PATIENTDOC.DATEOFBIRTH, datasql).where(PATIENTDOC.ID.eq(idMod))); 
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
				   database.execute(database.delete(PATIENTDOC).where(PATIENTDOC.ID.eq(((Person)clickEvent.getItem()).getId())));
				   grid.setItems(people);
			   }));
			   
			   // Render a button that generate the test 
			   grid.addColumn(person -> "Test", new ButtonRenderer<Object>(clickEvent -> {
				   layout.removeAllComponents();
				   String patientName=((Person)clickEvent.getItem()).getNome()+" "+((Person)clickEvent.getItem()).getCognome();
				   UInteger patientID=((Person)clickEvent.getItem()).getId();
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