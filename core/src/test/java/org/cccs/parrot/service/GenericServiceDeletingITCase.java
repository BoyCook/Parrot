package org.cccs.parrot.service;

import org.cccs.parrot.DataDrivenTestEnvironment;
import org.cccs.parrot.ParrotTestUtils;
import org.cccs.parrot.domain.Cat;
import org.cccs.parrot.domain.Country;
import org.cccs.parrot.domain.Dog;
import org.cccs.parrot.domain.Person;
import org.cccs.parrot.finder.GenericFinder;
import org.cccs.parrot.util.EntityFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityNotFoundException;

import static org.cccs.parrot.Assert.*;

/**
 * User: boycook
 * Date: 30/07/2012
 * Time: 11:53
 */
@ContextConfiguration(locations = "classpath:parrotContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class GenericServiceDeletingITCase extends DataDrivenTestEnvironment {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private GenericService service;
    private GenericFinder finder;

    @Before
    public void beforeEach() throws Exception {
        service = new GenericService(entityManagerFactory);
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

        Country entity = EntityFactory.get("Country", 1);
        assertEngland(finder.find(Country.class, 1));
        service.delete(entity);
        finder.find(Country.class, 1);
    }

    @Test
    public void deleteWithManyToOneForCatShouldWork() {
        thrown.expect(EntityNotFoundException.class);
        thrown.expectMessage("Unable to find entity [Cat] with [id] as [1]");

        Cat entity = EntityFactory.get("Cat", 1);
        assertBagpussWithOwner(finder.find(Cat.class, 1));
        service.delete(entity);
        finder.find(Cat.class, 1);
    }

    @Test
    public void deleteWithManyToOneForDogShouldWork() {
        thrown.expect(EntityNotFoundException.class);
        thrown.expectMessage("Unable to find entity [Dog] with [id] as [1]");

        Dog entity = EntityFactory.get("Dog", 1);
        assertFidoWithOwner(finder.find(Dog.class, 1));
        service.delete(entity);
        finder.find(Dog.class, 1);
    }

    @Test
    public void deleteInvalidTypeShouldFail() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Not an entity:class java.lang.Object");
        service.delete(new Object());
    }

    @Test
    public void deleteInvalidEntityShouldFail() {
        thrown.expect(EntityNotFoundException.class);
        thrown.expectMessage("Unable to find entity [Person] with [id] as [123]");

        Person entity = EntityFactory.get("Person", 123);
        service.delete(entity);
    }

    @Test
    public void deleteMandatoryForeignKeyForPersonShouldFail() {
        thrown.expect(ConstraintViolationException.class);
        thrown.expectMessage("integrity constraint violation: NOT NULL check constraint;");
        Person entity = EntityFactory.get("Person", 1);
        service.delete(entity);
    }
}
