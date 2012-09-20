package org.cccs.parrot;

import org.cccs.parrot.domain.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * User: boycook
 * Date: 24/06/2012
 * Time: 20:44
 */
public final class ParrotTestUtils {

    public static final String[] DEFAULT_TABLES = new String[]{
            "/db/countries.xml",
            "/db/people.xml",
            "/db/cats.xml",
            "/db/dogs.xml"

    };
    public static final String[] DELETE_TABLES = new String[]{
            "country",
            "cat",
            "dog",
            "person"
    };

    public static Person getCraig() {
        Person person = new Person("Craig Cook", "craig@craigcook.co.uk", "07345123456");
        person.setId(1l);
        return person;
    }

    public static Person getBob() {
        Person person = new Person("Bob Smith", "bob@smith.com", "012341234");
        person.setId(2l);
        return person;
    }

    public static Person getJonn() {
        Person person = new Person("Jonn Jonzz", "jonn@jonzz.com", "012341234");
        person.setId(3l);
        return person;
    }

    public static List<Person> getPeople() {
        List<Person> people = new ArrayList<Person>();
        people.add(getCraig());
        people.add(getBob());
        people.add(getJonn());
        return people;
    }
}
