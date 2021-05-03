package my.vaadin.app;

import javax.servlet.annotation.WebServlet;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.Page.PopStateEvent;
import com.vaadin.server.Page.PopStateListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;


//porta 8888
/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of an HTML page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@SuppressWarnings({ "serial" })
@Theme("valo")
public class FreiburgUI extends UI {

	

	@Override
	protected void init(VaadinRequest request) {
        new Navigator(this, this);
		getNavigator().addView(LoginView.NAME,LoginView.class);
		
		Page.getCurrent().addPopStateListener(new PopStateListener() {

			@Override
			public void uriChanged(PopStateEvent event) {
				router(event.getUri());
			}
			
		});
		
		
		router("");
		

	}
	
	
	private void router(String route){
		Notification.show(route);
		if(getSession().getAttribute("user") != null){
			getNavigator().addView(DoctorView.NAME, DoctorView.class);
			getNavigator().addView(PatientView.NAME, PatientView.class);
			if(route.equals("!patient")){
				getNavigator().navigateTo(PatientView.NAME);
			}else{
				getNavigator().navigateTo(DoctorView.NAME);
			}
		}else{
			getNavigator().navigateTo(LoginView.NAME);
		}
	}

	@WebServlet(urlPatterns = "/*", name = "myServlet", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = FreiburgUI.class)
	public static class myServlet extends VaadinServlet {
		
	}

}
