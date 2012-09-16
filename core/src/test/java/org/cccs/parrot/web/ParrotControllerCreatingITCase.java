package org.cccs.parrot.web;

import org.cccs.parrot.domain.Cat;
import org.cccs.parrot.domain.Country;
import org.cccs.parrot.domain.Dog;
import org.cccs.parrot.domain.Person;
import org.cccs.parrot.finder.GenericFinder;
import org.cccs.parrot.oxm.ReplaceHibernateModifier;
import org.cccs.parrot.web.converter.ParrotJSONHttpMessageConverter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.http.client.ResponsePathReader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;

import static org.cccs.parrot.Assert.*;

/**
 * User: boycook
 * Date: 18/06/2012
 * Time: 12:05
 */
@ContextConfiguration(locations = {"classpath:context/testApplicationContext.xml", "classpath:parrotContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ParrotControllerCreatingITCase extends JettyIntegrationTestEnvironment {

    private GenericFinder finder;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void beforeEach() throws Exception {
        jsonConverter = new ParrotJSONHttpMessageConverter(new ResponsePathReader(), new ReplaceHibernateModifier());
        finder = new GenericFinder(entityManagerFactory);
        setDataFileNames(new String[]{"/db/people.xml"});
        setTearDown(true);
        super.beforeEach();
    }

    @Test
    public void createCountryShouldWork() {
        client.put(serviceBaseURL + "country", new Country("England"));
        assertEngland(finder.find(Country.class, "name", "England"));
    }

    @Test
    public void createPersonShouldWork() {
        Person person = new Person("Dave Jones");
        person.setEmail("dave@jones.com");
        person.setPhone("07345123456");
        client.put(serviceBaseURL + "person", person);
        assertDave(finder.find(Person.class, "name", "Dave Jones"));
    }

    @Test
    public void createCatShouldWork() {
        client.put(serviceBaseURL + "cat", new Cat("Bagpuss", getCraig()));
        assertBagpuss(finder.find(Cat.class, "name", "Bagpuss"));
    }

    @Test
    public void createCatWithNoPersonShouldFail() {
        thrown.expect(HttpClientErrorException.class);
        thrown.expectMessage("422 Unprocessable Entity");
        client.put(serviceBaseURL + "cat", new Cat("Bagpuss"));
        assertBagpuss(finder.find(Cat.class, "name", "Bagpuss"));
    }

    @Test
    public void createCatWithInvalidPersonShouldFail() {
        thrown.expect(HttpClientErrorException.class);
        thrown.expectMessage("404 Not Found");
        client.put(serviceBaseURL + "cat", new Cat("Bagpuss", getPerson()));
        assertBagpuss(finder.find(Cat.class, "name", "Bagpuss"));
    }

    @Test
    public void createDogShouldWork() {
        client.put(serviceBaseURL + "dog", new Dog("Fido", getCraig()));
        assertFido(finder.find(Dog.class, "name", "Fido"));
    }

    @Test
    public void createDogWithNoPersonShouldFail() {
        thrown.expect(HttpClientErrorException.class);
        thrown.expectMessage("422 Unprocessable Entity");
        client.put(serviceBaseURL + "dog", new Dog("Fido"));
        assertFido(finder.find(Dog.class, "name", "Fido"));
    }

    @Test
    public void createDogWithInvalidPersonShouldFail() {
        thrown.expect(HttpClientErrorException.class);
        thrown.expectMessage("404 Not Found");
        client.put(serviceBaseURL + "dog", new Dog("Fido", getPerson()));
        assertFido(finder.find(Dog.class, "name", "Fido"));
    }

    @Test
    public void createInvalidTypeShouldFail() {
        thrown.expect(HttpClientErrorException.class);
        thrown.expectMessage("404 Not Found");
        client.put(serviceBaseURL + "invalid", new Object());
    }

    @Test
    public void createInvalidTypeWithIdShouldFail() {
        thrown.expect(HttpClientErrorException.class);
        thrown.expectMessage("404 Not Found");
        client.put(serviceBaseURL + "invalid/invalid", new Object());
    }

    private Person getPerson() {
        return new Person("Dave Jones", "dave@jones.com", "07345123456");
    }

    private Person getCraig() {
        return finder.find(Person.class, "name", "Craig Cook");
    }
}
