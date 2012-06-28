package org.cccs.parrot.oxm;

import org.cccs.parrot.domain.Person;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: boycook
 * Date: 28/06/2012
 * Time: 12:58
 */
public class TestJSONConverting {

    private Person person;
    private String personJson = "{\"id\":\"1\",\"name\":\"Jonn Jonzz\",\"email\":\"jonn@jonzz.com\",\"phone\":\"012341234\",\"dogs\":[],\"cats\":[]}";

    @Before
    public void setup() {
        person = new Person();
        person.setId(1l);
        person.setName("Jonn Jonzz");
        person.setEmail("jonn@jonzz.com");
        person.setPhone("012341234");
    }

    @Test
    public void writePersonJSONToObjectShouldWork() throws IOException {
        getMapper().writeValueAsString(person);
    }

    @Test
    public void readPersonJSONToObjectShouldWork() throws IOException {
        JsonNode jsonNode = getMapper().readTree(personJson);
        Person converted = getMapper().readValue(jsonNode, Person.class);
        assertThat(person, is(equalTo(converted)));
    }

    private ObjectMapper getMapper() {
        return new ObjectMapper();
    }
}
