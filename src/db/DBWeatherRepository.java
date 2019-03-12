package db;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import logging.PropLogger;
import model.WeatherData;

public class DBWeatherRepository implements IWeatherRepository{
    
    private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
    private final static DBConnector db = DBConnector.getInstance();
    
    public DBWeatherRepository()
    {
        Connection db_conn = db.getConnection();
        if(db_conn != null)
        {
            logger.info(this.getClass().getName() + ": Connection to database successful.");
        }
    }
    
    public List<WeatherData> DBSelect()
    {
        DBWeatherSelector dbw_select = new DBWeatherSelector(db);
        List<WeatherData> db_weather_list = dbw_select.DBOToModelObjects();
        return db_weather_list;
    }
    
    public void DBDelete(List<WeatherData> weather_list)
    {
        DBWeatherDeleter dbw_delete = new DBWeatherDeleter(db);
        dbw_delete.deleteWeatherList(weather_list);
    }
    
    public void DBUpdate(List<WeatherData> weather_list)
    {
        DBWeatherUpdater dbw_update = new DBWeatherUpdater(db);
        dbw_update.updateWeatherList(weather_list);
    }
    
    public void DBInsert(List<WeatherData> weather_list)
    {
        DBWeatherInserter dbw_ins = new DBWeatherInserter(DBConnector.getInstance());
        dbw_ins.insertWeatherList(weather_list);
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
