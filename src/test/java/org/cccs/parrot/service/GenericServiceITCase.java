package org.cccs.parrot.service;

import org.cccs.parrot.DataDrivenTestEnvironment;
import org.cccs.parrot.domain.Cat;
import org.cccs.parrot.domain.Country;
import org.cccs.parrot.domain.Dog;
import org.cccs.parrot.domain.Person;
import org.cccs.parrot.finder.GenericFinder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.cccs.parrot.finder.FinderAssertions.*;

/**
 * User: boycook
 * Date: 24/06/2012
 * Time: 18:18
 */
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class GenericServiceITCase extends DataDrivenTestEnvironment {

    private GenericService service;
    protected GenericFinder finder;

    @Before
    public void beforeEach() throws Exception {
        service = new GenericService(entityManagerFactory);
        finder = new GenericFinder(entityManagerFactory);
        super.beforeEach();
    }

    @Test
    public void createCountryShouldWork() {
        service.create(new Country("England"));
        assertEngland(finder.find(Country.class, "name", "England"));
    }

    @Test
    public void createPersonShouldWork() {
        Person craig = new Person("Craig Cook");
        craig.setEmail("craig@craigcook.co.uk");
        craig.setPhone("07345123456");
        service.create(craig);
        assertCraig(finder.find(Person.class, "name", "Craig Cook"));
    }

    @Test
    public void createCatShouldWork() {
        service.create(new Cat("Bagpuss"));
        assertBagpuss(finder.find(Cat.class, "name", "Bagpuss"));
    }

    @Test
    public void createDogShouldWork() {
        service.create(new Dog("Fido"));
        assertFido(finder.find(Dog.class, "name", "Fido"));
    }
}
