package com.isoft.rest.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WeatherUser")
public class WeatherUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;
    
    @Column(name = "user_name")
    private String name;
    
    @Column(name = "user_email")
    private String email;
    
    public WeatherUser(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    
    public WeatherUser() {
        id = 0;
        name = email = "";
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
