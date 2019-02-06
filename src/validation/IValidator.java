package validation;

import java.util.List;

import property.CityConfig;

public interface IValidator {
	
	public boolean validate();
    public List<String> getValidationMessages();
    
    public List<CityConfig> getValidCities(List<CityConfig> cities);
    public void printValidCities(List<CityConfig> cities);
    
}
