package model;

public class Additional {
    private Long id;
    private Integer humidity;
    private Double visibility;
    private Integer pressure;
    
    public Additional() {
        humidity = pressure = null;
        visibility = null;
        id = (long) 0;
    }
    public Additional(Integer humidity, Double visibility, Integer pressure) {
        this.humidity = humidity;
        this.visibility = visibility;
        this.pressure = pressure;
    }
    public Additional(Long id,Integer humidity, Double visibility, Integer pressure) {
        this.id = id;
        this.humidity = humidity;
        this.visibility = visibility;
        this.pressure = pressure;
    }
    public Integer getHumidity() {
        return humidity;
    }
    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }
    public Double getVisibility() {
        return visibility;
    }
    public void setVisibility(Double visibility) {
        this.visibility = visibility;
    }
    public Integer getPressure() {
        return pressure;
    }
    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }
    @Override
    public String toString() {
        return "Additional [humidity=" + humidity + " %, visibility=" + visibility + " km, pressure=" + pressure + " hPa]";
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    
    
}
