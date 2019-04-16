package main.com.isoft.rest.db.hb_dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import main.com.isoft.base.logging.PropLogger;
import main.com.isoft.rest.db.DBConnector;
import main.com.isoft.rest.db.HBConnector;
import main.com.isoft.rest.db.model.WeatherData;


@Repository
public class WeatherDAO {
    DBConnector db = null;
    
    private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
    Transaction transaction = null;
    
    public void deleteWeather(long weather_id)
    {
        Session session = HBConnector.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        WeatherData weather = session.get(WeatherData.class, weather_id);
        if (weather != null) {
                session.delete(weather);
                logger.info("WeatherData with id + " + weather_id + " was successfully deleted.");
        }
            
        transaction.commit();
    }
    
    public void insertWeather(WeatherData weather_data)
    {
        if (weather_data != null)
        {
            Session session = HBConnector.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(weather_data);    
            transaction.commit();
            
            logger.info("WeatherData record was successfully inserted.");
        }
    }
    
    public void updateWeather(WeatherData weather_data, long weather_id)
    {
        if (weather_data != null)
        {
            Session session = HBConnector.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            WeatherData weather = session.get(WeatherData.class, weather_id);
            if (weather != null) {
                    weather.setTemp(weather_data.getTemp());
                    weather.setStatus(weather_data.getStatus());
                    weather.setQuery_date();
                    weather.setFeel_temp(weather_data.getFeel_temp());
                    weather.setAdditional_data(weather_data.getAdditional_data());
                    weather.setConfig_data(weather_data.getConfig_data());
                    weather.setWind_data(weather_data.getWind_data());
                    session.update(weather);
                    logger.info("WeatherData with id + " + weather_id + " was successfully updated.");
            }
                
            transaction.commit();
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<WeatherData> getWeatherData()
    {
        List<WeatherData> weather_list = new ArrayList<>();

        Session session = HBConnector.getSessionFactory().openSession();
        transaction = session.beginTransaction();
             
        CriteriaQuery<WeatherData> cq = session.getCriteriaBuilder().createQuery(WeatherData.class);
        cq.from(WeatherData.class);
        weather_list = session.createQuery(cq).getResultList();
             
        transaction.commit();
        
        logger.info("WeatherData records were successfully selected.");
        return weather_list;
    }
}
