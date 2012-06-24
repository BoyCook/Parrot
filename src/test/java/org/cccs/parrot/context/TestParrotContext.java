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
        ContextBuilder.init(PACKAGE_NAME);
        expectedRequestMappings = new HashMap<String, Class>();
        expectedRequestMappings.put("/person", Person.class);
        expectedRequestMappings.put("/person/{personid}", Person.class);
        expectedRequestMappings.put("/person/{personid}/{attribute}", Person.class);
        expectedRequestMappings.put("/person/{personid}/cat", Cat.class);
        expectedRequestMappings.put("/person/{personid}/cat/{catid}", Cat.class);
        expectedRequestMappings.put("/person/{personid}/cat/{catid}/{attribute}", Cat.class);
        expectedRequestMappings.put("/person/{personid}/dog", Dog.class);
        expectedRequestMappings.put("/person/{personid}/dog/{dogid}", Dog.class);
        expectedRequestMappings.put("/person/{personid}/dog/{dogid}/{attribute}", Dog.class);
        expectedRequestMappings.put("/person/{personid}/cat/{catid}/country", Country.class);
        expectedRequestMappings.put("/person/{personid}/cat/{catid}/country/{countryid}", Country.class);
        expectedRequestMappings.put("/person/{personid}/cat/{catid}/country/{countryid}/{attribute}", Country.class);
        expectedRequestMappings.put("/person/{personid}/dog/{dogid}/country", Country.class);
        expectedRequestMappings.put("/person/{personid}/dog/{dogid}/country/{countryid}", Country.class);
        expectedRequestMappings.put("/person/{personid}/dog/{dogid}/country/{countryid}/{attribute}", Country.class);
        expectedRequestMappings.put("/country", Country.class);
        expectedRequestMappings.put("/country/{countryid}", Country.class);
        expectedRequestMappings.put("/country/{countryid}/{attribute}", Country.class);
        expectedRequestMappings.put("/country/{countryid}/cat", Cat.class);
        expectedRequestMappings.put("/country/{countryid}/cat/{catid}", Cat.class);
        expectedRequestMappings.put("/country/{countryid}/cat/{catid}/{attribute}", Cat.class);
        expectedRequestMappings.put("/country/{countryid}/dog", Dog.class);
        expectedRequestMappings.put("/country/{countryid}/dog/{dogid}", Dog.class);
        expectedRequestMappings.put("/country/{countryid}/dog/{dogid}/{attribute}", Dog.class);
    }

    @Test
    public void buildContextShouldWork() {
        assertNotNull(ContextBuilder.getContext());
    }

    @Test
    public void buildPathsShouldCreateCorrectPaths() {
        ParrotContext context = ContextBuilder.getContext();
        assertNotNull(context);
        assertThat(context.getPackageName(), is(equalTo(PACKAGE_NAME)));
        assertMapEquals(context.getRequestMappings(), expectedRequestMappings);
    }

    private void assertMapEquals(Map actual, Map expected) {
        assertThat(actual.size(), is(equalTo(expected.size())));
        for (Object key : actual.keySet()) {
            assertTrue(expected.containsKey(key));
            assertThat(actual.get(key), is(equalTo(expected.get(key))));
        }
    }
}
