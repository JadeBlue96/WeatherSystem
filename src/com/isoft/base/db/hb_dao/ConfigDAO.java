package com.isoft.base.db.hb_dao;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.isoft.base.db.HBConnector;
import com.isoft.base.db.hb_dao.AdditionalDAO;
import com.isoft.base.db.model.ConfigData;
import com.isoft.base.db.model.WeatherData;


@Repository
public class ConfigDAO {
    
    private final static Logger logger = Logger.getLogger(AdditionalDAO.class.getName());
    
    Transaction transaction = null;
    
    
    public void deleteConfig(long config_id)
    {
        Session session = HBConnector.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        ConfigData config = session.get(ConfigData.class, config_id);
        if (config != null) {
                session.delete(config);
                logger.info("Config with id + " + config_id + " was successfully deleted.");
        }
            
        transaction.commit();
    }
    
    public void insertConfig(WeatherData weather_data)
    {
        if (weather_data != null)
        {
            Session session = HBConnector.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(weather_data.getConfig_data());    
            transaction.commit();
            
            logger.info("Config record was successfully inserted.");
        }
    }
    
    public void updateConfig(WeatherData weather_data, long config_id)
    {
        if (weather_data != null)
        {
            Session session = HBConnector.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            ConfigData config = session.get(ConfigData.class, config_id);
            if (config != null) {
                    config.setCity(weather_data.getConfig_data().getCity());
                    config.setCountry(weather_data.getConfig_data().getCountry());
                    config.setSite(weather_data.getConfig_data().getSite());
                    session.update(config);
                    logger.info("Config with id + " + config_id + " was successfully updated.");
            }
                
            transaction.commit();
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<ConfigData> getConfigData()
    {
        List<ConfigData> config_list = new ArrayList<>();

        Session session = HBConnector.getSessionFactory().openSession();
        transaction = session.beginTransaction();
             
        CriteriaQuery<ConfigData> cq = session.getCriteriaBuilder().createQuery(ConfigData.class);
        cq.from(ConfigData.class);
        config_list = session.createQuery(cq).getResultList();
             
        transaction.commit();
        
        logger.info("Config records were successfully selected.");
        return config_list;
    }
}
