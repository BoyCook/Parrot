package org.cccs.parrot;

import org.cccs.parrot.domain.Cat;
import org.cccs.parrot.domain.Country;
import org.cccs.parrot.domain.Dog;
import org.cccs.parrot.domain.Person;
import org.hamcrest.CoreMatchers;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * User: boycook
 * Date: 17/07/2012
 * Time: 13:47
 */
public final class Assert {

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

    public static void assertBagpussWithOwner(final Cat cat) {
        assertBagpuss(cat);
        assertCraig(cat.getOwner());
    }

    public static void assertFido(final Dog dog) {
        assertNotNull(dog);
        assertThat(dog.getId(), is(greaterThanOrEqualTo(1l)));
        assertThat(dog.getName(), is(equalTo("Fido")));
    }

    public static void assertFidoWithOwner(final Dog dog) {
        assertFido(dog);
        assertCraig(dog.getOwner());
    }

    public static void assertCraig(final Person person) {
        assertNotNull(person);
        assertThat(person.getId(), is(greaterThanOrEqualTo(1l)));
        assertThat(person.getName(), is(equalTo("Craig Cook")));
        assertThat(person.getEmail(), is(equalTo("craig@craigcook.co.uk")));
        assertThat(person.getPhone(), is(equalTo("07345123456")));
    }

    public static void assertDave(final Person person) {
        assertNotNull(person);
        assertThat(person.getId(), is(greaterThanOrEqualTo(1l)));
        assertThat(person.getName(), is(equalTo("Dave Jones")));
        assertThat(person.getEmail(), is(equalTo("dave@jones.com")));
        assertThat(person.getPhone(), is(equalTo("07345123456")));
    }

    public static void assertJonn(final Person person) {
        assertNotNull(person);
        assertThat(person.getId(), is(greaterThanOrEqualTo(1l)));
        assertThat(person.getName(), is(equalTo("Jonn Jonzz")));
        assertThat(person.getEmail(), is(equalTo("jonn@jonzz.com")));
        assertThat(person.getPhone(), is(equalTo("012341234")));
    }

    public static void assertCraigWithRelations(final Person person) {
        assertCraig(person);
        assertThat(person.getCats().size(), is(equalTo(2)));
        assertThat(person.getDogs().size(), is(equalTo(2)));
    }

    public void assertIntItem(List<Integer> results, int value) {
        junit.framework.Assert.assertNotNull(results);
        assertThat(results.get(0), CoreMatchers.is(CoreMatchers.equalTo(value)));
    }

    public void assertStringItem(List<String> results, String value) {
        junit.framework.Assert.assertNotNull(results);
        assertThat(results.get(0), CoreMatchers.is(CoreMatchers.equalTo(value)));
    }
}
