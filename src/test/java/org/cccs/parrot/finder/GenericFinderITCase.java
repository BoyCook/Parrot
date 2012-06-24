package org.cccs.parrot.finder;

import org.cccs.parrot.DataDrivenTestEnvironment;
import org.cccs.parrot.domain.Cat;
import org.cccs.parrot.domain.Country;
import org.cccs.parrot.domain.Dog;
import org.cccs.parrot.domain.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * User: boycook
 * Date: 24/06/2012
 * Time: 14:19
 */
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class GenericFinderITCase extends DataDrivenTestEnvironment {

    private GenericFinder finder;

    @Before
    public void beforeEach() throws Exception {
        finder = new GenericFinder(entityManagerFactory);
        setDataFileNames(DEFAULT_TABLES);
        super.beforeEach();
    }

    @Test
    public void findAllCountriesShouldWork() {
        assertThat(finder.all(Country.class).size(), is(greaterThan(0)));
    }

    @Test
    public void findCountryByIdShouldWork() {
        assertEngland(finder.find(Country.class, 1));
    }

    @Test
    public void findAllCatsShouldWork() {
        assertThat(finder.all(Cat.class).size(), is(greaterThan(0)));
    }

    @Test
    public void findCatByIdShouldWork() {
        assertBagpuss(finder.find(Cat.class, 1));
    }

    @Test
    public void findAllDogsShouldWork() {
        assertThat(finder.all(Dog.class).size(), is(greaterThan(0)));
    }

    @Test
    public void findDogByIdShouldWork() {
        assertFido(finder.find(Dog.class, 1));
    }

    @Test
    public void findAllPeopleShouldWork() {
        assertThat(finder.all(Person.class).size(), is(greaterThan(0)));
    }

    @Test
    public void findPersonByIdShouldWork() {
        assertCraig(finder.find(Person.class, 1));
    }

    private void assertEngland(final Country country) {
        assertNotNull(country);
        assertThat(country.getId(), is(equalTo(1l)));
        assertThat(country.getName(), is(equalTo("England")));
    }

    private void assertBagpuss(final Cat cat) {
        assertNotNull(cat);
        assertThat(cat.getId(), is(equalTo(1l)));
        assertThat(cat.getName(), is(equalTo("Bagpuss")));
    }

    private void assertFido(final Dog dog) {
        assertNotNull(dog);
        assertThat(dog.getId(), is(equalTo(1l)));
        assertThat(dog.getName(), is(equalTo("Fido")));
    }

    private void assertCraig(final Person person) {
        assertNotNull(person);
        assertThat(person.getId(), is(equalTo(1l)));
        assertThat(person.getName(), is(equalTo("Craig Cook")));
    }
}
