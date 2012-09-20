package org.cccs.parrot.finder;

import org.cccs.parrot.DataDrivenTestEnvironment;
import org.cccs.parrot.ParrotTestUtils;
import org.cccs.parrot.domain.Cat;
import org.cccs.parrot.domain.Country;
import org.cccs.parrot.domain.Dog;
import org.cccs.parrot.domain.Person;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.*;

import static org.cccs.parrot.Assert.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * User: boycook
 * Date: 24/06/2012
 * Time: 14:19
 */
@ContextConfiguration(locations = "classpath:parrotContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class GenericFinderITCase extends DataDrivenTestEnvironment {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    protected GenericFinder finder;

    @Before
    public void beforeEach() throws Exception {
        finder = new GenericFinder(entityManagerFactory);
        setDataFileNames(ParrotTestUtils.DEFAULT_TABLES);
        setDeleteTables(ParrotTestUtils.DELETE_TABLES);
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
    public void findCountryByNameShouldWork() {
        assertEngland(finder.find(Country.class, "name", "England"));
    }

    @Test
    public void findAllPeopleShouldWork() {
        assertThat(finder.all(Person.class).size(), is(greaterThan(0)));
    }

    @Test
    public void findPersonByIdShouldWork() {
        assertCraigWithRelations(finder.find(Person.class, 1));
    }

    @Test
    public void findPersonByNameShouldWork() {
        assertCraigWithRelations(finder.find(Person.class, "name", "Craig Cook"));
    }

    @Test
    public void findAllCatsShouldWork() {
        assertThat(finder.all(Cat.class).size(), is(greaterThan(0)));
    }

    @Test
    public void findCatByIdShouldWork() {
        assertBagpussWithOwner(finder.find(Cat.class, 1));
    }

    @Test
    public void findCatByNameShouldWork() {
        assertBagpussWithOwner(finder.find(Cat.class, "name", "Bagpuss"));
    }

    @Test
    public void findAllDogsShouldWork() {
        assertThat(finder.all(Dog.class).size(), is(greaterThan(0)));
    }

    @Test
    public void findDogByIdShouldWork() {
        assertFidoWithOwner(finder.find(Dog.class, 1));
    }

    @Test
    public void findDogByNameShouldWork() {
        assertFidoWithOwner(finder.find(Dog.class, "name", "Fido"));
    }

    @Test
    public void findPersonByInvalidIdShouldFail() {
        thrown.expect(EntityNotFoundException.class);
        thrown.expectMessage("Unable to find entity [Person] with [id] as [999]");
        finder.find(Person.class, 999);
    }

    @Test
    public void findPersonByInvalidNameShouldFail() {
        thrown.expect(EntityNotFoundException.class);
        thrown.expectMessage("Unable to find entity [Person] with [name] as [Invalid]");
        finder.find(Person.class, "name", "Invalid");
    }

    @Test
    public void findCountryByInvalidIdShouldFail() {
        thrown.expect(EntityNotFoundException.class);
        thrown.expectMessage("Unable to find entity [Country] with [id] as [999]");
        finder.find(Country.class, 999);
    }

    @Test
    public void findCountryByInvalidNameShouldFail() {
        thrown.expect(EntityNotFoundException.class);
        thrown.expectMessage("Unable to find entity [Country] with [name] as [Invalid]");
        finder.find(Country.class, "name", "Invalid");
    }

    @Test
    public void findCatByInvalidIdShouldFail() {
        thrown.expect(EntityNotFoundException.class);
        thrown.expectMessage("Unable to find entity [Cat] with [id] as [999]");
        finder.find(Cat.class, 999);
    }

    @Test
    public void findCatByInvalidNameShouldFail() {
        thrown.expect(EntityNotFoundException.class);
        thrown.expectMessage("Unable to find entity [Cat] with [name] as [Invalid]");
        finder.find(Cat.class, "name", "Invalid");
    }

    @Test
    public void findDogByInvalidIdShouldFail() {
        thrown.expect(EntityNotFoundException.class);
        thrown.expectMessage("Unable to find entity [Dog] with [id] as [999]");
        finder.find(Dog.class, 999);
    }

    @Test
    public void findDogByInvalidNameShouldFail() {
        thrown.expect(EntityNotFoundException.class);
        thrown.expectMessage("Unable to find entity [Dog] with [name] as [Invalid]");
        finder.find(Dog.class, "name", "Invalid");
    }

    @Test
    public void findOnInvalidEntityShouldFail() {
        thrown.expect(EntityNotFoundException.class);
        thrown.expectMessage("Unable to find entity [String] with [id] as [999]");
        finder.find(String.class, 999);
    }

    @Test
    public void findOnInvalidEntityByNameFail() {
        thrown.expect(EntityNotFoundException.class);
        thrown.expectMessage("Unable to find entity [String] with [name] as [Invalid]");
        finder.find(String.class, "name", "Invalid");
    }

    @Ignore
    @Test
    public void findCatViaPersonShouldWork() {
        assertBagpussWithOwner(finder.find(Cat.class, 1));
    }

    @Ignore
    @Test
    public void findDogViaPersonShouldWork() {
        assertFidoWithOwner((Dog) find());
    }

    public <T> T find() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        T result = null;

        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Dog> query = builder.createQuery(Dog.class);
            Root<Dog> dogRoot = query.from(Dog.class);
            query.select(dogRoot);
            Join<Dog, Person> personJoin = dogRoot.join("owner");

            Predicate dogId = builder.and(builder.equal(personJoin.get("id"), 1));
            Predicate personId = builder.and(builder.equal(dogRoot.get("id"), 1));

            query.where(builder.and(personId, dogId));
            result = (T) entityManager.createQuery(query).getSingleResult();
        } catch (Exception e) {
            //TODO: work out specific exception
//            log.error("Error finding object", e);
        } finally {
//            log.debug("Closing entityManager");
            entityManager.close();
        }

//        if (result == null) {
//            throw new EntityNotFoundException(format("No ticket found for Product [%d] Ticket Type [%s]", id, type));
//        }
        return result;
    }
}
