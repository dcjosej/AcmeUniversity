package utilities.other;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesLoader {

	private static Logger log = Logger.getLogger(PropertiesLoader.class);
	
	public static String getProperty(String propertyName){
		Properties prop = new Properties();
		InputStream input = null;
		String result = "";
		
		try {
			input = new FileInputStream("application.properties");
			prop.load(input);
			
			result = prop.getProperty(propertyName);
			
		} catch (Exception e) {
			log.debug("PropertiesLoades could not load the resource");
		}
		
		return result;
	}
}
