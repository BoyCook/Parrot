package org.cccs.parrot.service;

import org.cccs.parrot.DataDrivenTestEnvironment;
import org.cccs.parrot.ParrotTestUtils;
import org.cccs.parrot.domain.Cat;
import org.cccs.parrot.domain.Country;
import org.cccs.parrot.domain.Dog;
import org.cccs.parrot.domain.Person;
import org.cccs.parrot.finder.GenericFinder;
import org.cccs.parrot.web.ResourceConflictException;
import org.cccs.parrot.web.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.cccs.parrot.Assert.*;
import static org.cccs.parrot.ParrotTestUtils.getCraig;

/**
 * User: boycook
 * Date: 24/06/2012
 * Time: 18:18
 */
@ContextConfiguration(locations = "classpath:parrotContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class GenericServiceCreatingITCase extends DataDrivenTestEnvironment {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private GenericService service;
    private GenericFinder finder;
    private Person craig;

    @Before
    public void beforeEach() throws Exception {
        service = new GenericService(entityManagerFactory);
        finder = new GenericFinder(entityManagerFactory);
        craig = new Person("Craig Cook");
        craig.setEmail("craig@craigcook.co.uk");
        craig.setPhone("07345123456");
        setDataFileNames(new String[]{"/db/people.xml"});
        setDeleteTables(ParrotTestUtils.DELETE_TABLES);
        setTearDown(true);
        super.beforeEach();
    }

    @Test
    public void createCountryShouldWork() {
        service.create(new Country("England"));
        assertEngland(finder.find(Country.class, "name", "England"));
    }

    @Test
    public void createDuplicateCountryShouldFail() {
        thrown.expect(ResourceConflictException.class);
        thrown.expectMessage("[Country] [Country{name='England'}] already exists");
        service.create(new Country("England"));
        service.create(new Country("England"));
    }

    @Test
    public void createPersonShouldWork() {
        service.create(getPerson());
        assertDave(finder.find(Person.class, "name", "Dave Jones"));
    }

    @Test
    public void createDuplicatePersonShouldFail() {
        thrown.expect(ResourceConflictException.class);
        thrown.expectMessage("[Person] [Person{name='Dave Jones', email='dave@jones.com', phone='07345123456'}");
        service.create(getPerson());
        service.create(getPerson());
    }

    @Test
    public void createCatShouldWork() {
        service.create(new Cat("Bagpuss", getCraig()));
        assertBagpuss(finder.find(Cat.class, "name", "Bagpuss"));
    }

    @Test
    public void createCatWithInvalidPersonShouldFail() {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage("Cannot find related entity [owner] [Person] to persist [Cat] as [Cat{name='Bagpuss'}]");
        service.create(new Cat("Bagpuss", getPerson()));
        assertBagpuss(finder.find(Cat.class, "name", "Bagpuss"));
    }

    @Test
    public void createDogShouldWork() {
        service.create(new Dog("Fido", getCraig()));
        assertFido(finder.find(Dog.class, "name", "Fido"));
    }

    @Test
    public void createDogWithInvalidPersonShouldFail() {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage("Cannot find related entity [owner] [Person] to persist [Dog] as [Dog{name='Fido'}]");
        service.create(new Dog("Fido", getPerson()));
        assertFido(finder.find(Dog.class, "name", "Fido"));
    }

    private Person getPerson() {
        return new Person("Dave Jones", "dave@jones.com", "07345123456");
    }
}
