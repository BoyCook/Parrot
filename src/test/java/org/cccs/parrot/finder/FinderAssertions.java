package org.cccs.parrot.finder;

import org.cccs.parrot.domain.Cat;
import org.cccs.parrot.domain.Country;
import org.cccs.parrot.domain.Dog;
import org.cccs.parrot.domain.Person;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * User: boycook
 * Date: 24/06/2012
 * Time: 20:44
 */
public final class FinderAssertions {

    public static void assertEngland(final Country country) {
        assertNotNull(country);
        assertThat(country.getId(), is(greaterThanOrEqualTo(1l)));
        assertThat(country.getName(), is(equalTo("England")));
    }

    public static void assertBagpuss(final Cat cat) {
        assertNotNull(cat);
        assertThat(cat.getId(), is(greaterThanOrEqualTo(1l)));
        assertThat(cat.getName(), is(equalTo("Bagpuss")));
    }

    public static void assertFido(final Dog dog) {
        assertNotNull(dog);
        assertThat(dog.getId(), is(greaterThanOrEqualTo(1l)));
        assertThat(dog.getName(), is(equalTo("Fido")));
    }

    public static void assertCraig(final Person person) {
        assertNotNull(person);
        assertThat(person.getId(), is(greaterThanOrEqualTo(1l)));
        assertThat(person.getName(), is(equalTo("Craig Cook")));
        assertThat(person.getEmail(), is(equalTo("craig@craigcook.co.uk")));
        assertThat(person.getPhone(), is(equalTo("07345123456")));
    }
}
