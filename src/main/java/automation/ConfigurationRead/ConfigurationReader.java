package automation.ConfigurationRead;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/*@author : M.Karthik
 * Description : This class is used to read the property files
 * (object repository,environment files,etc)
 */
public class ConfigurationReader{
	private static String current_environent=null;
	private static String global_properties_path=null;
	public static ConfigurationReader configReader=null;
	public static Properties properties;
	private static Logger log=Logger.getLogger(ConfigurationReader.class);
	
	/*
	 * @author Karthik
	 *This method initializes the instance of ConfigurationReader.
	 *It also loads the global config properties
	 *It assigns the current execution environment
	 */
	private static ConfigurationReader getInstance() throws Exception {
        if (configReader == null) {
        	current_environent = System.getProperty("env", "local");
            properties = new Properties();
            loadCommonEnvironmentProperties();
            loadEnvPropertyDetails(current_environent);
            configReader = new ConfigurationReader();
        }
        return configReader;
    }
	/*
	 * @author Karthik
	 *loads the properties of current environment
	 */
	private static void loadEnvPropertyDetails(String required_environment) throws IOException {
	    current_environent = String.format("src/test/resources/config/%s.properties", required_environment);
        loadPropertiesFromFile(current_environent);
	}
	/*
	 * @author Karthik
	 *loads the properties from specified path
	 */
	private static void loadPropertiesFromFile(String filePath) throws IOException {
		File file=new File(filePath);
		if(file.exists())
		{
			InputStream input=new FileInputStream(file);
			properties.load(input);
			input.close();
		}
		else
		{
			log.info("Properties file is missing :"+filePath);
		}
	}
	/*
	 * @author Karthik
	 *loads the common properties
	 */
	private static void loadCommonEnvironmentProperties() throws IOException
	{
		global_properties_path = "src/test/resources/config/global.properties";
        loadPropertiesFromFile(global_properties_path);
	}
	/*
	 * @author Karthik
	 *used to get property as per specified key
	 */
	private  String getProperty(String key)
	{
        String value = properties.getProperty(key);
        if (value == null) {
            throw new Error(String.format("Key %s not configured for environment %s", key, current_environent));
        }
        return value;
	}
	/*
	 * @author Karthik
	 *used to get property
	 */
	public static String getValue(final String keyName) throws Exception 
	{
	    return getInstance().getProperty(keyName);
	}
}
