package org.cccs.parrot.context;

import org.cccs.parrot.domain.Cat;
import org.cccs.parrot.domain.Country;
import org.cccs.parrot.domain.Dog;
import org.cccs.parrot.domain.Person;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * User: boycook
 * Date: 22/06/2012
 * Time: 10:00
 */
public class TestParrotContext {

    private static final String PACKAGE_NAME = "org.cccs.parrot.domain";
    private Map<String, Class> expectedRequestMappings;

    @Before
    public void before() {
        expectedRequestMappings = new HashMap<String, Class>();
        expectedRequestMappings.put("/person/{personid}", Person.class);
        expectedRequestMappings.put("/person/{personid}/cat/{catid}", Cat.class);
        expectedRequestMappings.put("/person/{personid}/dog/{dogid}", Dog.class);
        expectedRequestMappings.put("/person/{personid}/cat/{catid}/country/{countryid}", Country.class);
        expectedRequestMappings.put("/person/{personid}/dog/{dogid}/country/{countryid}", Country.class);
        expectedRequestMappings.put("/country/{countryid}", Country.class);
        expectedRequestMappings.put("/country/{countryid}/cat/{catid}", Cat.class);
        expectedRequestMappings.put("/country/{countryid}/dog/{dogid}", Dog.class);
    }

    @Test
    public void buildContextShouldWork() {
        assertNotNull(getContext());
    }

    @Test
    public void buildPathsShouldCreateCorrectPaths() {
        ParrotContext context = getContext();
        assertNotNull(context);
        assertThat(context.getPackageName(), is(equalTo(PACKAGE_NAME)));
        assertMapEquals(context.getRequestMappings(), expectedRequestMappings);
    }

    private void assertMapEquals(Map actual, Map expected) {
        assertThat(actual.size(), is(equalTo(expected.size())));
        for (Object key : actual.keySet()) {
            System.out.println("Matching: " + key);
            assertTrue(expected.containsKey(key));
            assertThat(actual.get(key), is(equalTo(expected.get(key))));
        }
    }

    private ParrotContext getContext() {
        ContextBuilder builder = new ContextBuilder();
        return builder.create(PACKAGE_NAME);
    }
}
