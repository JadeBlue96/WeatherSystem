package validation;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import logging.PropLogger;

public class UrlValidator {
	private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
	private List<String> errorMessages = new ArrayList<String>();
	
	public boolean validateURL(String str_url, int idx) {
		try {
			PropLogger.initLogger();
			URL url = new URL(str_url);
			url.toURI();
			//URLConnection conn = url.openConnection();
		    //conn.connect();
			logger.info("Validation complete for "+ str_url);
			PropLogger.closeHandler();
			return true;
		}
		catch (URISyntaxException exception) {
			String message = this.getClass().getName() + ": The url syntax "+str_url+" is invalid at line " + idx + ".";
			errorMessages.add(message);
			logger.log(Level.SEVERE, message);
			return false;
		}
		catch (MalformedURLException exception) {
			String message = this.getClass().getName() + ": The url "+str_url+" is invalid at line " + idx + ".";
			errorMessages.add(message);
			logger.log(Level.SEVERE, message);
			return false;
		}
		catch (Exception exception)
		{
			String message = this.getClass().getName() + ": The url "+str_url+" is invalid at line " + idx + ".";
			errorMessages.add(message);
			logger.log(Level.SEVERE, message);
			return false;
		}		
	}
}
