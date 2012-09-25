package org.cccs.parrot.web;

import org.cccs.parrot.ParrotTestUtils;
import org.cccs.parrot.domain.Cat;
import org.cccs.parrot.domain.Country;
import org.cccs.parrot.domain.Dog;
import org.cccs.parrot.domain.Person;
import org.cccs.parrot.finder.GenericFinder;
import org.cccs.parrot.oxm.ReplaceHibernateModifier;
import org.cccs.parrot.web.converter.ParrotJSONHttpMessageConverter;
import org.junit.Before;
import org.junit.BeforeClass;
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
@ContextConfiguration(locations = "classpath:parrotContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ParrotControllerCreatingITCase extends JettyIntegrationTestEnvironment {

    private GenericFinder finder;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setupJetty() {
        setJsonConverter(new ParrotJSONHttpMessageConverter(new ResponsePathReader(), new ReplaceHibernateModifier()));
        startJetty();
    }

    @Before
    public void beforeEach() throws Exception {
        finder = new GenericFinder(entityManagerFactory);
        setDataFileNames(new String[]{"/db/people.xml"});
        setDeleteTables(ParrotTestUtils.DELETE_TABLES);
        setTearDown(true);
        super.beforeEach();
    }

    @Test
    public void createCountryShouldWork() {
        getClient().put(getServiceBaseURL() + "country", new Country("England"));
        assertEngland(finder.find(Country.class, "name", "England"));
    }

    @Test
    public void createPersonShouldWork() {
        Person person = new Person("Dave Jones");
        person.setEmail("dave@jones.com");
        person.setPhone("07345123456");
        getClient().put(getServiceBaseURL() + "person", person);
        assertDave(finder.find(Person.class, "name", "Dave Jones"));
    }

    @Test
    public void createCatShouldWork() {
        getClient().put(getServiceBaseURL() + "cat", new Cat("Bagpuss", getCraig()));
        assertBagpuss(finder.find(Cat.class, "name", "Bagpuss"));
    }

    @Test
    public void createCatWithNoPersonShouldFail() {
        thrown.expect(HttpClientErrorException.class);
        thrown.expectMessage("422 Unprocessable Entity");
        getClient().put(getServiceBaseURL() + "cat", new Cat("Bagpuss"));
        assertBagpuss(finder.find(Cat.class, "name", "Bagpuss"));
    }

    @Test
    public void createCatWithInvalidPersonShouldFail() {
        thrown.expect(HttpClientErrorException.class);
        thrown.expectMessage("404 Not Found");
        getClient().put(getServiceBaseURL() + "cat", new Cat("Bagpuss", getPerson()));
        assertBagpuss(finder.find(Cat.class, "name", "Bagpuss"));
    }

    @Test
    public void createDogShouldWork() {
        getClient().put(getServiceBaseURL() + "dog", new Dog("Fido", getCraig()));
        assertFido(finder.find(Dog.class, "name", "Fido"));
    }

    @Test
    public void createDogWithNoPersonShouldFail() {
        thrown.expect(HttpClientErrorException.class);
        thrown.expectMessage("422 Unprocessable Entity");
        getClient().put(getServiceBaseURL() + "dog", new Dog("Fido"));
        assertFido(finder.find(Dog.class, "name", "Fido"));
    }

    @Test
    public void createDogWithInvalidPersonShouldFail() {
        thrown.expect(HttpClientErrorException.class);
        thrown.expectMessage("404 Not Found");
        getClient().put(getServiceBaseURL() + "dog", new Dog("Fido", getPerson()));
        assertFido(finder.find(Dog.class, "name", "Fido"));
    }

    @Test
    public void createInvalidTypeShouldFail() {
        thrown.expect(HttpClientErrorException.class);
        thrown.expectMessage("404 Not Found");
        getClient().put(getServiceBaseURL() + "invalid", new Object());
    }

    @Test
    public void createInvalidTypeWithIdShouldFail() {
        thrown.expect(HttpClientErrorException.class);
        thrown.expectMessage("404 Not Found");
        getClient().put(getServiceBaseURL() + "invalid/invalid", new Object());
    }

    private Person getPerson() {
        return new Person("Dave Jones", "dave@jones.com", "07345123456");
    }

    private Person getCraig() {
        return finder.find(Person.class, "name", "Craig Cook");
    }
}
