package org.cccs.parrot.util;

import org.cccs.parrot.domain.Person;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.cccs.parrot.util.Utils.extractParameter;
import static org.cccs.parrot.util.Utils.extractParameterFromEnd;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TestUtils {

    private final String PATH = "/org/cccs/boycook/parrot";

    @Test
    public void extractParameterShouldWork() {
        assertThat(extractParameter(PATH, 1), is(equalTo("org")));
        assertThat(extractParameter(PATH, 2), is(equalTo("cccs")));
        assertThat(extractParameter(PATH, 3), is(equalTo("boycook")));
        assertThat(extractParameter(PATH, 4), is(equalTo("parrot")));
    }

    @Test
    public void extractParameterFromEndShouldWork() {
        assertThat(extractParameterFromEnd(PATH, 1), is(equalTo("parrot")));
        assertThat(extractParameterFromEnd(PATH, 2), is(equalTo("boycook")));
        assertThat(extractParameterFromEnd(PATH, 3), is(equalTo("cccs")));
        assertThat(extractParameterFromEnd(PATH, 4), is(equalTo("org")));
    }

    public static Person getCraig() {
        Person person = new Person("Craig Cook", "craig@cook.com", "012341234");
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

