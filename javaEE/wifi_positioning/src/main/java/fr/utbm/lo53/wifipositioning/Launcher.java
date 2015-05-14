package fr.utbm.lo53.wifipositioning;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.core.joran.spi.JoranException;
import fr.utbm.lo53.wifipositioning.util.FilesUtils;

public class Launcher
{
	/** Logger of the class */
	private final static Logger	s_logger	= LoggerFactory.getLogger(Launcher.class);

	public static void main(
			final String[] args) throws IOException, JoranException
	{
		/* Initializes the logger */
		FilesUtils.initLogger();

		s_logger.info("Launching the server ...");

		/* Loading of properties */
		s_logger.debug("Loading properties...");
		Properties properties = new Properties();
		properties.load(Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("conf/server.properties"));

		/* Retrieves the APs properties */
		System.setProperty("ap.port", properties.getProperty("ap.port"));
		System.setProperty("ap.ips", properties.getProperty("ap.ips"));

		/* Retrieves the properties concerning the calibration */
		System.setProperty("calibrate.port", properties.getProperty("calibrate.port"));

		/* Retrieves the properties concerning the location */
		System.setProperty("locate.port", properties.getProperty("locate.port"));

		s_logger.debug("Properties loaded.");

		/* Runs standalone controllers */
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				CalibrateStandalone.main(null);
			}
		}).start();

		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				LocateStandalone.main(null);
			}
		}).start();
	}
}
