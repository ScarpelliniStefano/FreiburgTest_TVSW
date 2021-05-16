package my.vaadin.app;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;



import com.vaadin.navigator.*;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickListener;

import unibg.FSTAIMO.FSTdatabaseAIMO;
import unibg.se4med.FSTdatabase;
import unibg.se4med.HashFunction;

public class LoginView extends VerticalLayout implements View {

private static final long serialVersionUID = 1L;

public static final String NAME = "";

protected TextField user = null;

protected PasswordField password = null;

protected VerticalLayout viewLayout = new VerticalLayout();


public LoginView(){
	Button loginButton = null;

	Label title = new Label("<h1><b>Login</b></h1>", ContentMode.HTML);

	// Create the user input field
	user = new TextField("Mail:");
	user.setWidth("300px");
	user.setRequiredIndicatorVisible(true);
	//user.setValue("address@mail.com");

	// Create the password input field
	password = new PasswordField("Password:");
	password.setWidth("300px");
	password.setRequiredIndicatorVisible(true);
	//password.setValue("password");

	// Create login button
	loginButton = new Button("Login");
    Label strErr=new Label();
    
	ClickListener loginButtonlistener = eBtn -> {
       
		String username = user.getValue();
		String password = this.password.getValue();
		String psw = null;
		strErr.setValue("");
		strErr.setContentMode(ContentMode.HTML);
		try {
			psw = HashFunction.toSha256(password);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//
		// Validate username and password with database here. For examples sake
		// I use a dummy username and password.
		//
		viewLayout.removeComponent(strErr);
		boolean isDoctor = false;
		try {
			FSTdatabase.IniziaConn();
			//FSTdatabaseAIMO.IniziaConn();
			Connection connection = FSTdatabase.getConn();
		    Statement statement = connection.createStatement();
			
			// Create and execute a SELECT SQL statement.
            String selectSql = "SELECT name, surname, activated, password FROM user JOIN doctor ON (user.email=doctor.email) WHERE user.email=\'"+ username+"\'";
            ResultSet rs = statement.executeQuery(selectSql);
			
			while ( rs.next()) {
				if((rs.getString(4).contentEquals(psw)) && (rs.getInt(3)==1)) {
					FSTdatabase.nomeUtente = rs.getString(2) + " " + rs.getString(1);
					FSTdatabase.email = username;
					isDoctor = true;
				}else if(rs.getInt(3)==0) {
					strErr.setValue("<h4><font color=#ff0000>Errore: email non attiva</font></h4>");
				}else{
					strErr.setValue("<h4><font color=#ff0000>Errore: password non corretta</font></h4>");
				}
			}
			rs.close();
			statement.close();
		} catch (Exception excp) {
			excp.printStackTrace();
		}

		if (isDoctor) {

			// Store the current user in the service session
			getSession().setAttribute("user", username);
			getUI().getNavigator().addView(DoctorView.NAME, DoctorView.class);
			getUI().getNavigator().addView(PatientView.NAME, PatientView.class);
			Page.getCurrent().setUriFragment("!"+DoctorView.NAME);

		} else {
			
			if(strErr.getValue()=="") {
			strErr.setValue("<h4><font color=#ff0000>Errore: utente non trovato</font></h4>");
			}
			viewLayout.addComponent(strErr);
			
			// Wrong password clear the password field and refocuses it
			this.password.setValue("");
			this.password.focus();

		}
	};
	loginButton.addClickListener(loginButtonlistener);

	// Add both to a panel
	VerticalLayout fields = new VerticalLayout(title, user, password, loginButton);
	
	fields.setSpacing(true);
	fields.setMargin(new MarginInfo(true, true, true, false));
	fields.setSizeUndefined();

	// The view root layout
	viewLayout = new VerticalLayout(fields);
	viewLayout.setSizeFull();
	viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);

	addComponent(viewLayout);
}

}
