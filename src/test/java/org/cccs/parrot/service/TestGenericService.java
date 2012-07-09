package org.cccs.parrot.service;

import org.cccs.parrot.domain.Person;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * This tests that the validation is being applied to the entities correctly
 *
 * User: boycook
 * Date: 09/07/2012
 * Time: 12:55
 */
@Ignore //Getting some problem with TraversableResolver
public class TestGenericService {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private GenericService service;

    @Before
    public void beforeEach(){
        service = new GenericService(null);
    }

    @Test
    public void createPersonWithNullShouldFail() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Entity must not be null");
        service.create(null);
    }

    @Test
    public void createPersonWithNullNameShouldFail() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Name must be specified");
        Person person = new Person();
        service.create(person);
    }

    @Test
    public void createPersonWithEmptyNameShouldFail() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Name must be specified");
        Person person = new Person();
        service.create(person);
    }

    @Test
    public void createPersonWithNullEmailShouldFail() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Email must be specified");
        Person person = new Person();
        service.create(person);
    }

    @Test
    public void createPersonWithEmptyEmailShouldFail() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Email must be specified");
        Person person = new Person();
        service.create(person);
    }

    @Test
    public void createPersonWithNullPhoneShouldFail() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Phone must be specified");
        Person person = new Person();
        service.create(person);
    }

    @Test
    public void createPersonWithEmptyPhoneShouldFail() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Phone must be specified");
        Person person = new Person();
        service.create(person);
    }
}
