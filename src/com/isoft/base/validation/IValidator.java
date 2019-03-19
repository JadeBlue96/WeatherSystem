package com.isoft.base.validation;

import java.util.List;

import com.isoft.base.property.CityConfig;

/*
 * Initiates the proper validation function depending on the validation level - property file, object or xml file.
 * Retrieves and prints the valid rows from the configuration and creates configuration objects.
 * Returns information about the incorrect data in the configuration.
 */
public interface IValidator {
    
    public boolean validate();
    public List<String> getValidationMessages();
    
    public List<CityConfig> getValidCities(List<CityConfig> cities);
    public void printValidCities(List<CityConfig> cities);

}
