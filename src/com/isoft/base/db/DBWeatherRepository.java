package com.isoft.base.db;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import com.isoft.base.db.dao.WeatherListDAO;
import com.isoft.base.db.model.WeatherData;
import com.isoft.base.logging.PropLogger;

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
    
    public void DBDelete(List<WeatherData> weather_list)
    {
        wlist_dao.deleteWeatherList(weather_list);
    }
    
    public void DBUpdate(List<WeatherData> weather_list)
    {
        wlist_dao.updateWeatherList(weather_list);
    }
    
    public void DBInsert(List<WeatherData> weather_list)
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
