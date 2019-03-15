package model;

public class Wind {
    private long id;
    private double wind_spd;
    private String wind_status;
    private String wind_direction;
    
    public Wind() {
        wind_spd = 0.0;
        wind_status = wind_direction = "";
    }
    public Wind(double wind_spd, String wind_status, String wind_direction) {
        this.wind_spd = wind_spd;
        this.wind_status = wind_status;
        this.wind_direction = wind_direction;
    }
    public Wind(long id, double wind_spd, String wind_status, String wind_direction) {
        this.id = id;
        this.wind_spd = wind_spd;
        this.wind_status = wind_status;
        this.wind_direction = wind_direction;
    }
    public Double getWind_spd() {
        return wind_spd;
    }
    public void setWind_spd(double wind_spd) {
        this.wind_spd = wind_spd;
    }
    public String getWind_status() {
        return wind_status;
    }
    public void setWind_status(String wind_status) {
        this.wind_status = wind_status;
    }
    public String getWind_direction() {
        return wind_direction;
    }
    public void setWind_direction(String wind_direction) {
        this.wind_direction = wind_direction;
    }
    @Override
    public String toString() {
        return "Wind [wind_spd=" + wind_spd + " m/s, wind_status=" + wind_status + ", wind_direction=" + wind_direction
                + "]";
    }
    public Long getId() {
        return id;
    }
    public void setId(long id)
    {
        this.id = id;
    }
    
}
