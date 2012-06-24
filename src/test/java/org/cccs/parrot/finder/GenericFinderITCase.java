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

import static org.cccs.parrot.finder.FinderAssertions.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * User: boycook
 * Date: 24/06/2012
 * Time: 14:19
 */
@ContextConfiguration(locations = "classpath:testApplicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class GenericFinderITCase extends DataDrivenTestEnvironment {

    protected GenericFinder finder;

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
}
