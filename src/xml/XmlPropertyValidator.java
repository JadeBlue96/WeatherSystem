package xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import logging.PropLogger;
import property.CityConfig;
import validation.IValidator;

public class XmlPropertyValidator extends validation.Validator implements IValidator{
	private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
	private List<String> errorMessages = new ArrayList<String>();
	private List<CityConfig> valid_cities = new ArrayList<CityConfig>();
	
	public static final String XML_FILE = "src/xml/resource/wdata.xml";
    public static final String SCHEMA_FILE = "src/xml/resource/wdata.xsd";
    
    public boolean validateXmlSchema(String xmlFile, String schemaFile) {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = schemaFactory.newSchema(new File(schemaFile));

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlFile)));
            return true;
        } catch (SAXException | IOException e) {
        	String message = this.getClass().getName() + ": Incorrect XML or XSD format.";
        	errorMessages.add(message);
            logger.log(Level.SEVERE, message);
            return false;
        }
    }
    
    public boolean validateXML()
    {
    	XmlPropertyValidator XMLValidator = new XmlPropertyValidator();
        boolean valid = XMLValidator.validateXmlSchema(XML_FILE, SCHEMA_FILE);
        System.out.printf("%s Validation result = %b.\n\n", XML_FILE, valid);
        return valid;
    }
    

	@Override
	public boolean validate() {
		boolean isValid = validateXML();
		logger.info("Validation complete for "+ this.getClass().getName() + " with result: "+ isValid);
		return isValid;
	}

    
    

}
