package org.cccs.parrot.web;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * User: boycook
 * Date: 18/06/2012
 * Time: 12:05
 */
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Ignore
public class ParrotControllerITCase extends JettyIntegrationTestEnvironment {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void findAllTypesShouldWork() {
        client.getForObject(serviceBaseURL + "test", List.class);
    }
}
