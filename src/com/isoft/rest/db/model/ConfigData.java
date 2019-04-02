package com.isoft.rest.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "Config")
@EntityListeners(AuditingEntityListener.class)
public class ConfigData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "config_id")
    private long id;
    
    @Column(name = "city", length = 50)
    private String city;
    
    @Column(name = "country", length = 50)
    private String country;
    
    @Column(name = "site", length = 50)
    private String site;
    
    public ConfigData() {
        city = country = site = "";
    }
    public ConfigData(String city, String country, String site)
    {
        this.city = city;
        this.country = country;
        this.site = site;
    }
    public ConfigData(long id, String city, String country, String site)
    {
        this.id = id;
        this.city = city;
        this.country = country;
        this.site = site;
    }
    
    
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    
    
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    
    
    public String getSite() {
        return site;
    }
    public void setSite(String site) {
        this.site = site;
    }
    @Override
    public String toString() {
        return "ConfigData [city=" + city + ", country=" + country + ", site=" + site + "]";
    }
    public void setId(long id)
    {
        this.id = id;
    }
    
    
    public Long getId() {
        return id;
    }
    
    

}
