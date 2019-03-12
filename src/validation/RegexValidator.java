package validation;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import logging.PropLogger;

public class RegexValidator {
    private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
    private List<String> errorMessages = new ArrayList<String>();
    
    public boolean validateRegex(String regex, int idx)
    {
        String inputPattern = regex;
        try {
            Pattern.compile(inputPattern);
        } catch (PatternSyntaxException exception) {
            String message = this.getClass().getName() + ": The regex "+regex+" is invalid at line " + idx + ".";
            errorMessages.add(message);
            logger.log(Level.SEVERE, message);
            return false;
        }
        return true;
    }
}
