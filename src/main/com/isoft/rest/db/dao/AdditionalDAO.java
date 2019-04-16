package main.com.isoft.rest.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.com.isoft.base.logging.PropLogger;
import main.com.isoft.rest.db.DBConnector;
import main.com.isoft.rest.db.model.Additional;
import main.com.isoft.rest.db.model.WeatherData;
import main.com.isoft.rest.db.model.Wind;

public class AdditionalDAO {
    private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
    DBConnector db = null;
    
    
    private static final String SQL_DELETE_ADDITIONAL  = "DELETE FROM \"Additional\" WHERE add_id = ?\n";
    private static final String SQL_INSERT_ADDITIONAL  = "INSERT INTO \"Additional\"(humidity, visibility, pressure) "
            + "VALUES(?,?,?)\n";
    private static final String SQL_SELECT_ADDITIONAL_ALL = "SELECT * from \"Additional\"";
    private static final String SQL_UPDATE_ADDITIONAL = "UPDATE \"Additional\" SET humidity = ?, visibility = ?, pressure = ? WHERE add_id = ?\n";
    
    public AdditionalDAO(DBConnector db)
    {
        this.db = db;
    }
    
    public int deleteAdditional(String SQL_ADD, WeatherData weather_data, long add_id)
    {
        int affectedRows = 0;
        
        if (weather_data != null)
        {
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(SQL_ADD,Statement.RETURN_GENERATED_KEYS)) 
            {
                pstmt.setLong(1, add_id);
                affectedRows = pstmt.executeUpdate();
            } 
            catch (SQLException ex) 
            {
                logger.log(Level.SEVERE, this.getClass().getName().toString() + ": " + ex.getMessage());
                return 0;
            } 
        }
        return affectedRows;
    }
    
    public Long insertAdditional(String SQL_ADD, WeatherData weather_data, long add_id)
    {
        if (weather_data != null)
        {
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(SQL_ADD,Statement.RETURN_GENERATED_KEYS)) 
            {
                if(weather_data.getAdditional_data().getHumidity() != null) 
                {
                    pstmt.setDouble(1, weather_data.getAdditional_data().getHumidity());
                }
                else 
                {
                    pstmt.setNull(1, Types.INTEGER);
                }
                if(weather_data.getAdditional_data().getVisibility() != null) 
                {
                    pstmt.setDouble(2, weather_data.getAdditional_data().getVisibility());
                }
                else 
                {
                    pstmt.setNull(2, Types.DOUBLE);
                }
                if(weather_data.getAdditional_data().getPressure() != null) 
                {
                    pstmt.setInt(3, weather_data.getAdditional_data().getPressure());
                }
                else 
                {
                    pstmt.setNull(3, Types.INTEGER);
                }
     
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) 
                {
                    try (ResultSet rs = pstmt.getGeneratedKeys()) 
                    {
                        if (rs.next()) 
                        {
                            add_id = rs.getLong("add_id");
                        }
                    } 
                    catch (SQLException ex) 
                    {
                        logger.log(Level.SEVERE, this.getClass().getName().toString() + ": " + ex.getMessage());
                        return null;
                    }
                }
            } 
            catch (SQLException ex) 
            {
                logger.log(Level.SEVERE, this.getClass().getName().toString() + ": " + ex.getMessage());
                return null;
            } 
        }
        return add_id;
    }
    
    public Long updateAdditional(String SQL_ADD, WeatherData weather_data, long add_id)
    {
        long affectedRows = 0;
        if (weather_data != null)
        {
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(SQL_ADD,Statement.RETURN_GENERATED_KEYS)) 
            {
             
                if(weather_data.getAdditional_data().getHumidity() != null) 
                {
                    pstmt.setDouble(1, weather_data.getAdditional_data().getHumidity());
                }
                else 
                {
                    pstmt.setNull(1, Types.INTEGER);
                }
                if(weather_data.getAdditional_data().getVisibility() != null) 
                {
                    pstmt.setDouble(2, weather_data.getAdditional_data().getVisibility());
                }
                else 
                {
                    pstmt.setNull(2, Types.DOUBLE);
                }
                if(weather_data.getAdditional_data().getPressure() != null) 
                {
                    pstmt.setInt(3, weather_data.getAdditional_data().getPressure());
                }
                else 
                {
                    pstmt.setNull(3, Types.INTEGER);
                }
                pstmt.setLong(4, add_id);
     
                affectedRows = pstmt.executeUpdate();
            } 
            catch (SQLException ex) 
            {
                logger.log(Level.SEVERE, this.getClass().getName().toString() + ": " + ex.getMessage());
                return null;
            } 
        }
        return affectedRows;
    }
    
    public List<Additional> getAddData()
    {
        List<Additional> add_list = new ArrayList<>();
        Additional add_data = new Additional();
         try (Statement stmt = db.getConnection().createStatement()) 
         {
                ResultSet rs = stmt.executeQuery(SQL_SELECT_ADDITIONAL_ALL);
                while (rs.next()) 
                {
                    long id = rs.getLong("add_id");
                    int humidity = rs.getInt("humidity");
                    double visibility = rs.getDouble("visibility");
                    int pressure = rs.getInt("pressure");
                    add_data = new Additional(id, humidity, visibility, pressure);
                    add_list.add(add_data);
                }
         } 
         catch (SQLException e) 
         {
            logger.log(Level.SEVERE, this.getClass().getName().toString() + ": " + e.getMessage());
            return null;
         }
         return add_list;
    }
    
}
