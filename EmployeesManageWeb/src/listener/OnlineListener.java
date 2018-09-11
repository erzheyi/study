package listener;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class OnlineListener
 *
 */
@WebListener
public class OnlineListener implements HttpSessionListener {

    /**
     * Default constructor. 
     */
    public OnlineListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent event)  { 
    	int num = 0;
    	ServletContext application = event.getSession().getServletContext();
    	if(application.getAttribute("online")!=null) {
    		num = (Integer) application.getAttribute("online");
    	}
    	num++;
    	application.setAttribute("online", num);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent event)  { 
    	int num = 0;
    	ServletContext application = event.getSession().getServletContext();
    	if(application.getAttribute("online")!=null) {
    		num = (Integer) application.getAttribute("online");
    	}
    	num--;
    	application.setAttribute("online", num);
    }
	
}
