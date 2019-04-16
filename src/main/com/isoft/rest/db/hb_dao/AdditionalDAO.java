package main.com.isoft.rest.db.hb_dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import main.com.isoft.rest.db.HBConnector;
import main.com.isoft.rest.db.model.Additional;
import main.com.isoft.rest.db.model.WeatherData;

@Repository
public class AdditionalDAO {
    
    private final static Logger logger = Logger.getLogger(AdditionalDAO.class.getName());
    
    Transaction transaction = null;
    
    
    public void deleteAdditional(long add_id)
    {
        Session session = HBConnector.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        Additional additional = session.get(Additional.class, add_id);
        if (additional != null) {
                session.delete(additional);
                logger.info("Additional with id + " + add_id + " was successfully deleted.");
        }
            
        transaction.commit();
    }
    
    public void insertAdditional(WeatherData weather_data)
    {
        if (weather_data != null)
        {
            Session session = HBConnector.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(weather_data.getAdditional_data());    
            transaction.commit();
            
            logger.info("Additional record was successfully inserted.");
        }
    }
    
    public void updateAdditional(WeatherData weather_data, long add_id)
    {
        if (weather_data != null)
        {
            Session session = HBConnector.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Additional additional = session.get(Additional.class, add_id);
            if (additional != null) {
                    additional.setHumidity(weather_data.getAdditional_data().getHumidity());
                    additional.setPressure(weather_data.getAdditional_data().getPressure());
                    additional.setVisibility(weather_data.getAdditional_data().getVisibility());
                    
                    session.update(additional);
                    logger.info("Additional with id + " + add_id + " was successfully updated.");
            }
                
            transaction.commit();
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<Additional> getAddData()
    {
        List<Additional> add_list = new ArrayList<>();

        Session session = HBConnector.getSessionFactory().openSession();
        transaction = session.beginTransaction();
             
        CriteriaQuery<Additional> cq = session.getCriteriaBuilder().createQuery(Additional.class);
        cq.from(Additional.class);
        add_list = session.createQuery(cq).getResultList();
             
        transaction.commit();
        
        logger.info("Additional records were successfully selected.");
        return add_list;
    }
    
}
