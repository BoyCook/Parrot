package org.cccs.parrot.web;

import org.cccs.parrot.context.ParrotContext;
import org.cccs.parrot.domain.Person;
import org.cccs.parrot.oxm.ReplaceHibernateModifier;
import org.cccs.parrot.web.converter.ParrotJSONHttpMessageConverter;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.http.client.ResponsePathReader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;

/**
 * User: boycook
 * Date: 31/07/2012
 * Time: 17:20
 */
@ContextConfiguration(locations = "classpath:parrotContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ParrotControllerITCase extends JettyIntegrationTestEnvironment {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setupJetty() {
        setJsonConverter(new ParrotJSONHttpMessageConverter(new ResponsePathReader(), new ReplaceHibernateModifier()));
        startJetty();
    }

    @Test
    public void getContextShouldWork() {
        getClient().getForObject(getServiceBaseURL() + "context", ParrotContext.class);
    }

    @Test
    public void getModelShouldWork() {
        getClient().getForObject(getServiceBaseURL() + "model", HashMap.class);
    }

    @Test
    public void getResourcesShouldWork() {
        getClient().getForObject(getServiceBaseURL() + "resources", HashMap.class);
    }

    @Test
    public void getExampleEntityShouldWork() {
        getClient().getForObject(getServiceBaseURL() + "example/Person", Person.class);
    }
}
