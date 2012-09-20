package org.cccs.parrot.web;

import org.cccs.parrot.ParrotTestUtils;
import org.cccs.parrot.domain.Cat;
import org.cccs.parrot.domain.Country;
import org.cccs.parrot.domain.Dog;
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

import javax.persistence.EntityNotFoundException;

import static org.cccs.parrot.Assert.*;

/**
 * User: boycook
 * Date: 30/07/2012
 * Time: 14:14
 */
@ContextConfiguration(locations = "classpath:parrotContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ParrotControllerDeletingITCase extends JettyIntegrationTestEnvironment {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private GenericFinder finder;

    @BeforeClass
    public static void setupJetty() {
        jsonConverter = new ParrotJSONHttpMessageConverter(new ResponsePathReader(), new ReplaceHibernateModifier());
        startJetty();
    }

    @Before
    public void beforeEach() throws Exception {
        finder = new GenericFinder(entityManagerFactory);
        setDataFileNames(ParrotTestUtils.DEFAULT_TABLES);
        setDeleteTables(ParrotTestUtils.DELETE_TABLES);
        setTearDown(true);
        super.beforeEach();
    }

    @Test
    public void deleteWithManyToManyForCountryShouldWork() {
        thrown.expect(EntityNotFoundException.class);
        thrown.expectMessage("Unable to find entity [Country] with [id] as [1]");

        assertEngland(finder.find(Country.class, 1));
        client.delete(serviceBaseURL + "Country/1");
        finder.find(Country.class, 1);
    }

    @Test
    public void deleteWithManyToOneForCatShouldWork() {
        thrown.expect(EntityNotFoundException.class);
        thrown.expectMessage("Unable to find entity [Cat] with [id] as [1]");

        assertBagpussWithOwner(finder.find(Cat.class, 1));
        client.delete(serviceBaseURL + "Cat/1");
        finder.find(Cat.class, 1);
    }

    @Test
    public void deleteWithManyToOneForDogShouldWork() {
        thrown.expect(EntityNotFoundException.class);
        thrown.expectMessage("Unable to find entity [Dog] with [id] as [1]");

        assertFidoWithOwner(finder.find(Dog.class, 1));
        client.delete(serviceBaseURL + "Dog/1");
        finder.find(Dog.class, 1);
    }

    @Test
    public void deleteInvalidTypeShouldFail() {
        thrown.expect(HttpClientErrorException.class);
        thrown.expectMessage("404 Not Found");
        client.delete(serviceBaseURL + "FooBar/1");
    }

    @Test
    public void deleteInvalidEntityShouldFail() {
        thrown.expect(HttpClientErrorException.class);
        thrown.expectMessage("404 Not Found");
        client.delete(serviceBaseURL + "Person/123");
    }

    @Test
    public void deleteMandatoryForeignKeyForPersonShouldFail() {
        thrown.expect(HttpClientErrorException.class);
        thrown.expectMessage("422 Unprocessable Entity");
        client.delete(serviceBaseURL + "Person/1");
    }
}
