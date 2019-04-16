package main.com.isoft.base.validation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.com.isoft.base.logging.PropLogger;
import main.com.isoft.base.property.CityConfig;
import main.com.isoft.base.property.SequentialProperties;

public class PropFileValidator extends Validator implements IValidator{
    
    private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
    private List<String> errorMessages = new ArrayList<String>();
    
    public Properties validateFormat(Properties test_prop, String file_path) {
        
        InputStream is = null;
        test_prop = new SequentialProperties();
        
        
        try { 
            is = this.getClass().getResourceAsStream(file_path);
            if(is != null)
            {
            test_prop.load(is);
            }
            return test_prop;
        } catch (FileNotFoundException e) {
            String message = this.getClass().toString() + ": Missing property file.";
            errorMessages.add(message);
            logger.log(Level.SEVERE, message);
            return null;
        } catch (IOException e) {
            String message = this.getClass().toString() + ": Incorrect property file format.";
            errorMessages.add(message);
            logger.log(Level.SEVERE, message);
            return null;
        } 
    }
    
    public boolean validatePropData(Properties test_prop) {
        if(test_prop != null)
        {
            String[] city_data, url_data;
            HashMap<String, String> url_map = new HashMap<String,String>();
            try {
                    for (Map.Entry<Object,Object> e : (Set<Map.Entry<Object,Object>>)test_prop.entrySet()){
                        String city_str = test_prop.getProperty(e.getKey().toString());
                        city_data = city_str.split(";");
                        for(int i=1; i<city_data.length; i++) {
                            url_data = city_data[i].split(",");
                            url_map.put(url_data[0], url_data[1]);
                        }
                    }
                    return true;
                }
           
            catch(ArrayIndexOutOfBoundsException e)
            {
                String message = this.getClass().toString() + ": Invalid test property key-value pairs.";
                errorMessages.add(message);
                logger.log(Level.SEVERE, message);
                return false;
            }
        }
        String message = this.getClass().toString() + ": Testing property file not initialized.";
        errorMessages.add(message);
        logger.log(Level.SEVERE, message);
        return false;
    }
    
    public static boolean validatePropFile()
    {
        String propfile_path = "../resources/prop_configs/metdata.properties";
        Properties test_prop = new SequentialProperties();
        boolean isValid = false;    
        PropFileValidator file_validator = new PropFileValidator();
        
        logger.info(file_validator.getClass().toString() + ": Validating test property file.");
        test_prop = file_validator.validateFormat(test_prop, propfile_path);
        isValid = file_validator.validatePropData(test_prop);
        logger.info(file_validator.getClass().toString() + ": File Validation complete.");
        return isValid;
    
    }
    
    @Override
    public boolean validate() {
        boolean isValidPropFile = validatePropFile();
        logger.info("Validation complete for "+ this.getClass().getName());
        return isValidPropFile;
    }

    @Override
    public List<String> getValidationMessages() {
        return errorMessages;
    }

    @Override
    public List<CityConfig> getValidCities(List<CityConfig> cities) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void printValidCities(List<CityConfig> cities) {
        // TODO Auto-generated method stub
        
    }
}
