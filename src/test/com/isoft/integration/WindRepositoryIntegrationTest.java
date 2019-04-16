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
import main.com.isoft.rest.db.model.Wind;
import main.com.isoft.rest.db.repository.ConfigRepository;
import main.com.isoft.rest.db.repository.WindRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes=Main.class)
public class WindRepositoryIntegrationTest {
    
    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private WindRepository windRepository;
    
    @Test
    public void whenFindById_thenReturnWind() {
        // given
        Wind wind = new Wind(4.2, "безветрие", "Север");
        entityManager.persist(wind);
        entityManager.flush();
     
        // when
        Wind found = windRepository.findWindById(wind.getId());
     
        // then
        assertThat(found.getId())
          .isEqualTo(wind.getId());
    }
    
}
