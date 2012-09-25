package org.cccs.parrot.web;

import org.cccs.parrot.ParrotTestUtils;
import org.cccs.parrot.domain.*;
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

import java.util.List;

import static org.cccs.parrot.Assert.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * User: boycook
 * Date: 18/06/2012
 * Time: 12:05
 */
@ContextConfiguration(locations = "classpath:parrotContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ParrotControllerFindingITCase extends JettyIntegrationTestEnvironment {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setupJetty() {
        setJsonConverter(new ParrotJSONHttpMessageConverter(new ResponsePathReader(), new ReplaceHibernateModifier()));
        startJetty();
    }

    @Before
    public void beforeEach() throws Exception {
        setDataFileNames(ParrotTestUtils.DEFAULT_TABLES);
        setDeleteTables(ParrotTestUtils.DELETE_TABLES);
        super.beforeEach();
    }

    @Test
    public void findAllPeopleShouldWork() {
        List all = getClient().getForObject(getServiceBaseURL() + "person", List.class);
        assertThat(all.size(), is(greaterThan(0)));
    }

    @Test
    public void findPersonByIdShouldWork() {
        assertCraigWithRelations(getClient().getForObject(getServiceBaseURL() + "person/1", Person.class));
    }

    @Test
    public void findAllCountriesShouldWork() {
        List all = getClient().getForObject(getServiceBaseURL() + "country", List.class);
        assertThat(all.size(), is(greaterThan(0)));
    }

    @Test
    public void findCountriesViaCatShouldWork() {
        List all = getClient().getForObject(getServiceBaseURL() + "person/1/cat/1/country", List.class);
        assertThat(all.size(), is(greaterThan(0)));
    }

    @Test
    public void findCountriesViaDogShouldWork() {
        List all = getClient().getForObject(getServiceBaseURL() + "person/1/dog/1/country", List.class);
        assertThat(all.size(), is(greaterThan(0)));
    }

    @Test
    public void findCountryByIdShouldWork() {
        assertEngland(getClient().getForObject(getServiceBaseURL() + "country/1", Country.class));
    }

    @Test
    public void getCountryAttributeByIdShouldWork() {
        assertEnglandKeyValue(getClient().getForObject(getServiceBaseURL() + "country/1/name", KeyValue.class));
    }

    @Test
    public void findCountryByIdViaCatShouldWork() {
        assertEngland(getClient().getForObject(getServiceBaseURL() + "person/1/cat/1/country/1", Country.class));
    }

    @Test
    public void getCountryAttributeByIdViaCatShouldWork() {
        assertEnglandKeyValue(getClient().getForObject(getServiceBaseURL() + "person/1/cat/1/country/1/name", KeyValue.class));
    }

    @Test
    public void findCountryByIdViaDogShouldWork() {
        assertEngland(getClient().getForObject(getServiceBaseURL() + "person/1/dog/1/country/1", Country.class));
    }

    @Test
    public void getCountryAttributeByIdViaDogShouldWork() {
        assertEnglandKeyValue(getClient().getForObject(getServiceBaseURL() + "person/1/dog/1/country/1/name", KeyValue.class));
    }

    @Test
    public void findAllCatsShouldWork() {
        List all = getClient().getForObject(getServiceBaseURL() + "person/1/cat", List.class);
        assertThat(all.size(), is(greaterThan(0)));
    }

    @Test
    public void findCatsViaCountryShouldWork() {
        List all = getClient().getForObject(getServiceBaseURL() + "country/1/cat", List.class);
        assertThat(all.size(), is(greaterThan(0)));
    }

    @Test
    public void findCatByIdShouldWork() {
        assertBagpuss(getClient().getForObject(getServiceBaseURL() + "person/1/cat/1", Cat.class));
    }

    @Test
    public void findCatViaCountryShouldWork() {
        assertBagpuss(getClient().getForObject(getServiceBaseURL() + "country/1/cat/1", Cat.class));
    }

    @Test
    public void findAllDogsShouldWork() {
        List all = getClient().getForObject(getServiceBaseURL() + "person/1/dog", List.class);
        assertThat(all.size(), is(greaterThan(0)));
    }

    @Test
    public void findDogsViaCountryShouldWork() {
        List all = getClient().getForObject(getServiceBaseURL() + "/country/1/dog", List.class);
        assertThat(all.size(), is(greaterThan(0)));
    }

    @Test
    public void findDogByIdShouldWork() {
        assertFido(getClient().getForObject(getServiceBaseURL() + "person/1/dog/1", Dog.class));
    }

    @Test
    public void findDogViaCountryShouldWork() {
        assertFido(getClient().getForObject(getServiceBaseURL() + "country/1/dog/1", Dog.class));
    }

    @Test
    public void findPersonWithInvalidIdShouldFail() {
        thrown.expect(HttpClientErrorException.class);
        thrown.expectMessage("404 Not Found");
        getClient().getForObject(getServiceBaseURL() + "person/999", Person.class);
    }

    @Test
    public void findCountryWithInvalidIdShouldFail() {
        thrown.expect(HttpClientErrorException.class);
        thrown.expectMessage("404 Not Found");
        getClient().getForObject(getServiceBaseURL() + "country/999", Country.class);
    }

    @Test
    public void findCatWithInvalidIdShouldFail() {
        thrown.expect(HttpClientErrorException.class);
        thrown.expectMessage("404 Not Found");
        getClient().getForObject(getServiceBaseURL() + "cat/999", Cat.class);
    }

    @Test
    public void findDogWithInvalidIdShouldFail() {
        thrown.expect(HttpClientErrorException.class);
        thrown.expectMessage("404 Not Found");
        getClient().getForObject(getServiceBaseURL() + "dog/999", Dog.class);
    }

    @Test
    public void getOnInvalidEntityShouldFail() {
        thrown.expect(HttpClientErrorException.class);
        thrown.expectMessage("404 Not Found");
        getClient().getForObject(getServiceBaseURL() + "foo", Object.class);
    }

    @Test
    public void getOnInvalidEntityWithIdShouldFail() {
        thrown.expect(HttpClientErrorException.class);
        thrown.expectMessage("404 Not Found");
        getClient().getForObject(getServiceBaseURL() + "foo/bar", Object.class);
    }

    @Test
    public void getOnInvalidEntityAttributeShouldFail() {
        thrown.expect(HttpClientErrorException.class);
        thrown.expectMessage("404 Not Found");
        getClient().getForObject(getServiceBaseURL() + "foo/bar/foo", Object.class);
    }

    @Test
    public void getOnInvalidUrlShouldFail() {
        thrown.expect(HttpClientErrorException.class);
        thrown.expectMessage("404 Not Found");
        getClient().getForObject(getServiceBaseURL() + "foo/bar/foo/foo", Object.class);
    }
}
