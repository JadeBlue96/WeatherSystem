package com.isoft.rest.db;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import com.isoft.base.logging.PropLogger;
import com.isoft.rest.db.dao.WeatherListDAO;
import com.isoft.rest.db.model.WeatherData;

public class DBWeatherRepository implements IWeatherRepository{
    
    private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
    private final static DBConnector db = DBConnector.getInstance();
    private WeatherListDAO wlist_dao;
    
    public DBWeatherRepository()
    {
        Connection db_conn = db.getConnection();
        wlist_dao = new WeatherListDAO(db);
        if(db_conn != null)
        {
            logger.info(this.getClass().getName() + ": Connection to database successful.");
        }
    }
    
    public List<WeatherData> DBSelect()
    {
        List<WeatherData> db_weather_list = wlist_dao.DBOToModelObjects();
        return db_weather_list;
    }
    
    public void DBDeleteList(List<WeatherData> weather_list)
    {
        wlist_dao.deleteWeatherList(weather_list);
    }
    
    public void DBUpdateList(List<WeatherData> weather_list)
    {
        wlist_dao.updateWeatherList(weather_list);
    }
    
    public void DBInsertList(List<WeatherData> weather_list)
    {
        wlist_dao.insertWeatherList(weather_list);
    }
    
    public void close()
    {
        db.close();
        logger.info(this.getClass().getName().toString() + ": Connection to database closed.");
    }
    
    public DBConnector getConnector() {
        return db;
    }
    
}
