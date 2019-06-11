package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

@WebListener
public class Inicijalizacija implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		String fileName = sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties");
		ResourceBundle config = ResourceBundle.getBundle(fileName);

		String host = config.getString("host");
		String port = config.getString("port");
		String dbName = config.getString("name");
		String user = config.getString("user");
		String pass = config.getString("password");
		String connectionURL = "jdbc:derby://" + host + ":" + port + "/" + dbName + ";user=" + user + ";password="
				+ pass;

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);

		TableInit.createTableIfNeeded(cpds);

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}

	

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}