package com.isoft.rest.db;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;

import com.isoft.base.logging.PropLogger;
import com.isoft.rest.db.hb_dao.WeatherDAO;
import com.isoft.rest.db.hb_dao.WeatherListDAO;
import com.isoft.rest.db.model.WeatherData;

public class HBWeatherRepository implements IWeatherRepository{
    
    private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
    private WeatherListDAO wlist_dao;
    private WeatherDAO w_dao;
    
    public HBWeatherRepository()
    {
        SessionFactory session_factory = HBConnector.getSessionFactory();
        if(session_factory != null)
        {
            logger.info(this.getClass().getName() + ": Hibernate connection to database successful.");
            return;
        }
        logger.log(Level.SEVERE, this.getClass().getName() + ": Hibernate connection failed.");
    }
    
    public List<WeatherData> DBSelect()
    {
        wlist_dao = new WeatherListDAO();
        List<WeatherData> db_weather_list = wlist_dao.DBOToModelObjects();
        return db_weather_list;
    }
    
    public void DBDeleteList(List<WeatherData> weather_list)
    {
        wlist_dao = new WeatherListDAO();
        wlist_dao.deleteWeatherList(weather_list);
    }
    
    public void DBUpdateList(List<WeatherData> weather_list)
    {
        wlist_dao = new WeatherListDAO();
        wlist_dao.updateWeatherList(weather_list);
    }
    
    public void DBInsertList(List<WeatherData> weather_list)
    {
        wlist_dao = new WeatherListDAO();
        wlist_dao.insertWeatherList(weather_list);
    }
    
    public void DBDelete(long id)
    {
        w_dao = new WeatherDAO();
        w_dao.deleteWeather(id);
    }
    
    public void DBUpdate(WeatherData weather_data, long id)
    {
        w_dao = new WeatherDAO();
        w_dao.updateWeather(weather_data, id);
    }
    
    public void DBInsert(WeatherData weather_data)
    {
        w_dao = new WeatherDAO();
        w_dao.insertWeather(weather_data);
    }
    
    public void close()
    {
        HBConnector.close();
        logger.info(this.getClass().getName().toString() + ": Connection to database closed.");
    }
    
    public SessionFactory getConnector() {
        return HBConnector.getSessionFactory();
    }
}
