package main.com.isoft.rest.db.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "Wind")
@SequenceGenerator(name = "wind_id",  sequenceName = "WIND_WIND_ID_SEQ",
allocationSize = 1, schema = "public")
@EntityListeners(AuditingEntityListener.class)
public class Wind {
    
    @Id
    @GeneratedValue(generator = "wind_id", strategy = GenerationType.SEQUENCE)  
    @Column(name = "wind_id")
    private long id;
    
    @Column(name = "wind_spd")
    private Double wind_spd;
    
    @Column(name = "wind_status", length = 20)
    private String wind_status;
    
    @Column(name = "wind_direction", length = 20)
    private String wind_direction;
    
    public Wind() {
        wind_spd = null;
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
