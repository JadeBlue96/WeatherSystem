package test.com.isoft.integration;

import static org.assertj.core.api.Assertions.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import main.com.isoft.Main;
import main.com.isoft.rest.db.model.Additional;
import main.com.isoft.rest.db.model.ConfigData;
import main.com.isoft.rest.db.model.WeatherData;
import main.com.isoft.rest.db.model.Wind;
import main.com.isoft.rest.db.repository.WeatherRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes=Main.class)
public class WeatherRepositoryIntegrationTest {
    
    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private WeatherRepository weatherRepository;
    
    @Test
    public void whenFindById_thenReturnWeather() {
        // given
        Wind wind = new Wind(2.5, "безветрие", "север");
        Additional add = new Additional(1, 0, 1);
        ConfigData config = new ConfigData("Burgas", "BG", "TestSite");
        WeatherData w_data = new WeatherData(0, 0, "облачно", wind, add, config, Timestamp.from(Instant.now()));
        entityManager.persist(wind);
        entityManager.persist(add);
        entityManager.persist(config);
        entityManager.persist(w_data);
        entityManager.flush();
     
        // when
        WeatherData found = weatherRepository.findWeatherById(w_data.getId());
     
        // then
        assertThat(found.getId())
          .isEqualTo(w_data.getId());
    }
    
    @Test
    public void whenFindByYear_thenReturnWeather() {
        // given
        Wind wind = new Wind(2.5, "безветрие", "север");
        Additional add = new Additional(1, 0, 1);
        ConfigData config = new ConfigData("Burgas", "BG", "TestSite");
        WeatherData w_data = new WeatherData(0, 0, "облачно", wind, add, config, Timestamp.from(Instant.now()));
        entityManager.persist(wind);
        entityManager.persist(add);
        entityManager.persist(config);
        entityManager.persist(w_data);
        entityManager.flush();
     
        // when
        List<WeatherData> found = weatherRepository.findWeatherByYear(w_data.getYear());
     
        // then
        assertThat(found.get(0).getYear())
          .isEqualTo(w_data.getYear());
    }
    
    @Test
    public void whenFindByMonthAndYear_thenReturnWeather() {
        // given
        Wind wind = new Wind(2.5, "безветрие", "север");
        Additional add = new Additional(1, 0, 1);
        ConfigData config = new ConfigData("Burgas", "BG", "TestSite");
        WeatherData w_data = new WeatherData(0, 0, "облачно", wind, add, config, Timestamp.from(Instant.now()));
        entityManager.persist(wind);
        entityManager.persist(add);
        entityManager.persist(config);
        entityManager.persist(w_data);
        entityManager.flush();
     
        // when
        List<WeatherData> found = weatherRepository.findWeatherByMonthAndYear(w_data.getMonth(), w_data.getYear());
     
        // then
        assertThat(found.get(0).getYear())
          .isEqualTo(w_data.getYear());
        
        // then
        assertThat(found.get(0).getMonth())
          .isEqualTo(w_data.getMonth());
    }
    
    @Test
    public void whenFindByDayAndMonthAndYear_thenReturnWeather() {
        // given
        Wind wind = new Wind(2.5, "безветрие", "север");
        Additional add = new Additional(1, 0, 1);
        ConfigData config = new ConfigData("Burgas", "BG", "TestSite");
        WeatherData w_data = new WeatherData(0, 0, "облачно", wind, add, config, Timestamp.from(Instant.now()));
        entityManager.persist(wind);
        entityManager.persist(add);
        entityManager.persist(config);
        entityManager.persist(w_data);
        entityManager.flush();
     
        // when
        List<WeatherData> found = weatherRepository.findWeatherByDayAndMonthAndYear(w_data.getDay(), w_data.getMonth(), w_data.getYear());
     
        // then
        assertThat(found.get(0).getYear())
          .isEqualTo(w_data.getYear());
        
        // then
        assertThat(found.get(0).getMonth())
          .isEqualTo(w_data.getMonth());
        
        // then
        assertThat(found.get(0).getDay())
          .isEqualTo(w_data.getDay());
    }
    
}
