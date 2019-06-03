package hr.fer.zemris.java.hw13.utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class AppInfoListener that
 * stores the time when the app was created.
 *
 */
@WebListener
public class AppInfoListener implements ServletContextListener {


	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("appStartTime", System.currentTimeMillis());
	}


}
