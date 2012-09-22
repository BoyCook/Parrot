package org.cccs.parrot;

import org.cccs.parrot.domain.Cat;
import org.cccs.parrot.domain.Country;
import org.cccs.parrot.domain.Dog;
import org.cccs.parrot.domain.Person;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

/**
 * User: boycook
 * Date: 22/09/2012
 * Time: 13:14
 */
public class TestDomain {

    @Test
    public void newPersonShouldWork() {
        assertNotNull(new Person());
    }

    @Test
    public void newCountryShouldWork() {
        assertNotNull(new Country());
    }

    @Test
    public void newDogShouldWork() {
        assertNotNull(new Dog());
    }

    @Test
    public void newCatShouldWork() {
        assertNotNull(new Cat());
    }
}
