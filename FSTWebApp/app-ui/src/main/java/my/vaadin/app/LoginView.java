package my.vaadin.app;

import static se4med.jooq.tables.User.USER;
import static se4med.jooq.tables.Doctor.DOCTOR;

import org.jooq.DSLContext;
import org.jooq.Record4;
import org.jooq.SelectConditionStep;

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
			DSLContext database = FSTdatabase.DatabaseOperator();

			SelectConditionStep<Record4<String, String, Byte, String>> result = database.select(USER.NAME, USER.SURNAME,USER.ACTIVATED,USER.PASSWORD)
					.from(USER).join(DOCTOR).on(USER.EMAIL.eq(DOCTOR.EMAIL)).where(USER.EMAIL.eq(username));
			
			for (Record4<String, String, Byte, String> r : result) {
				if(((String)r.getValue(3)).contentEquals(psw) && ((Byte)r.getValue(2)).intValue()==1) {
					FSTdatabase.nomeUtente = r.getValue(1) + " " + r.getValue(0);
					FSTdatabase.email = username;
					isDoctor = true;
				}else if(((Byte) r.getValue(2)).intValue()==0) {
					strErr.setValue("<h4><font color=#ff0000>Errore: email non attiva</font></h4>");
				}else{
					strErr.setValue("<h4><font color=#ff0000>Errore: password non corretta</font></h4>");
				}
			}
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
