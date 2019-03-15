package model;

public class Additional {
    private long id;
    private int humidity;
    private double visibility;
    private int pressure;
    
    public Additional() {
        humidity = pressure = 0;
        visibility = 0.0;
        id = (long) 0;
    }
    public Additional(int humidity, double visibility, int pressure) {
        this.humidity = humidity;
        this.visibility = visibility;
        this.pressure = pressure;
    }
    public Additional(long id, int humidity, double visibility, int pressure) {
        this.id = id;
        this.humidity = humidity;
        this.visibility = visibility;
        this.pressure = pressure;
    }
    public Integer getHumidity() {
        return humidity;
    }
    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
    public Double getVisibility() {
        return visibility;
    }
    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }
    public Integer getPressure() {
        return pressure;
    }
    public void setPressure(int pressure) {
        this.pressure = pressure;
    }
    @Override
    public String toString() {
        return "Additional [humidity=" + humidity + " %, visibility=" + visibility + " km, pressure=" + pressure + " hPa]";
    }
    public void setId(long id)
    {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    
    
}
