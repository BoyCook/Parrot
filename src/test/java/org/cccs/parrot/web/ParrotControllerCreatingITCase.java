package org.cccs.parrot.web;

import org.cccs.parrot.domain.Cat;
import org.cccs.parrot.domain.Country;
import org.cccs.parrot.domain.Dog;
import org.cccs.parrot.domain.Person;
import org.cccs.parrot.finder.GenericFinder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.cccs.parrot.finder.FinderAssertions.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * User: boycook
 * Date: 18/06/2012
 * Time: 12:05
 */
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ParrotControllerCreatingITCase extends JettyIntegrationTestEnvironment {

    private GenericFinder finder;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void beforeEach() throws Exception {
        finder = new GenericFinder(entityManagerFactory);
        super.beforeEach();
    }

    @Test
    public void createCountryShouldWork() {
        client.put(serviceBaseURL + "country", new Country("England"));
        assertEngland(finder.find(Country.class, "name", "England"));
    }

    @Test
    public void createPersonShouldWork() {
        Person craig = new Person("Craig Cook");
        craig.setEmail("craig@craigcook.co.uk");
        craig.setPhone("07345123456");
        client.put(serviceBaseURL + "person", craig);
        assertCraig(finder.find(Person.class, "name", "Craig Cook"));
    }

    @Test
    public void createCatShouldWork() {
        client.put(serviceBaseURL + "cat", new Cat("Bagpuss"));
        assertBagpuss(finder.find(Cat.class, "name", "Bagpuss"));
    }

    @Test
    public void createDogShouldWork() {
        client.put(serviceBaseURL + "dog", new Dog("Fido"));
        assertFido(finder.find(Dog.class, "name", "Fido"));
    }
}
