package test.com.isoft.integration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import main.com.isoft.Main;
import main.com.isoft.rest.db.model.Additional;
import main.com.isoft.rest.db.model.Wind;
import main.com.isoft.rest.db.repository.AdditionalRepository;
import main.com.isoft.rest.db.repository.WindRepository;


@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes=Main.class)
public class AdditionalRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private AdditionalRepository additionalRepository;
    
    @Test
    public void whenFindById_thenReturnAdditional() {
        // given
        Additional add = new Additional(1, 0.0, 1);
        entityManager.persist(add);
        entityManager.flush();
     
        // when
        Additional found = additionalRepository.findAdditionalById(add.getId());
     
        // then
        assertThat(found.getId())
          .isEqualTo(add.getId());
    }
}
