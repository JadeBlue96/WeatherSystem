package test.com.isoft.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import main.com.isoft.Main;
import main.com.isoft.rest.db.model.ConfigData;
import main.com.isoft.rest.db.repository.ConfigRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes=Main.class)
public class ConfigRepositoryIntegrationTest {
    
    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private ConfigRepository configRepository;
    
    @Test
    public void whenFindBySite_thenReturnConfig() {
        // given
        ConfigData config = new ConfigData("Burgas", "BG", "TestSite");
        entityManager.persist(config);
        entityManager.flush();
     
        // when
        List<ConfigData> found = configRepository.findConfigBySite(config.getSite());
     
        // then
        assertThat(found.get(0).getSite())
          .isEqualTo(config.getSite());
    }
    
    @Test
    public void whenFindById_thenReturnConfig() {
        // given
        ConfigData config = new ConfigData("Burgas", "BG", "TestSite");
        entityManager.persist(config);
        entityManager.flush();
     
        // when
        ConfigData found = configRepository.findConfigById(config.getId());
     
        // then
        assertThat(found.getId())
          .isEqualTo(config.getId());
    }
    
}
