package main.com.isoft.rest.db.hb_dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import main.com.isoft.rest.db.HBConnector;
import main.com.isoft.rest.db.model.WeatherData;
import main.com.isoft.rest.db.model.Wind;


@Repository
public class WindDAO {
private final static Logger logger = Logger.getLogger(AdditionalDAO.class.getName());
    
    Transaction transaction = null;
    
    
    public void deleteWind(long wind_id)
    {
        Session session = HBConnector.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        Wind wind = session.get(Wind.class, wind_id);
        if (wind != null) {
                session.delete(wind);
                logger.info("Wind with id + " + wind_id + " was successfully deleted.");
        }
            
        transaction.commit();
    }
    
    public void insertWind(WeatherData weather_data)
    {
        if (weather_data != null)
        {
            Session session = HBConnector.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(weather_data.getWind_data());    
            transaction.commit();
            
            logger.info("Wind record was successfully inserted.");
        }
    }
    
    public void updateWind(WeatherData weather_data, long wind_id)
    {
        if (weather_data != null)
        {
            Session session = HBConnector.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Wind wind = session.get(Wind.class, wind_id);
            if (wind != null) {
                    wind.setWind_direction(weather_data.getWind_data().getWind_direction());
                    wind.setWind_spd(weather_data.getWind_data().getWind_spd());
                    wind.setWind_status(weather_data.getWind_data().getWind_status());
                    session.update(wind);
                    logger.info("Wind with id + " + wind_id + " was successfully updated.");
            }
                
            transaction.commit();
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<Wind> getWindData()
    {
        List<Wind> wind_list = new ArrayList<>();

        Session session = HBConnector.getSessionFactory().openSession();
        transaction = session.beginTransaction();
             
        CriteriaQuery<Wind> cq = session.getCriteriaBuilder().createQuery(Wind.class);
        cq.from(Wind.class);
        wind_list = session.createQuery(cq).getResultList();
             
        transaction.commit();
        
        logger.info("Wind records were successfully selected.");
        return wind_list;
    }
    
}
