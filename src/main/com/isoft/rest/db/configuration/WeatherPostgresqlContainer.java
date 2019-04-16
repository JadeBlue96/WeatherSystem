package main.com.isoft.rest.db.configuration;

import org.testcontainers.containers.PostgreSQLContainer;

public class WeatherPostgresqlContainer extends PostgreSQLContainer<WeatherPostgresqlContainer>{
    private static final String IMAGE_VERSION = "postgres:11.1";
    private static WeatherPostgresqlContainer container;
 
    private WeatherPostgresqlContainer() {
        super(IMAGE_VERSION);
    }
 
    public static WeatherPostgresqlContainer getInstance() {
        if (container == null) {
            container = new WeatherPostgresqlContainer();
        }
        return container;
    }
 
    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }
 
    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
