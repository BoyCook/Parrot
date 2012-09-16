package org.cccs.parrot.context;

import org.cccs.parrot.domain.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * User: boycook
 * Date: 22/06/2012
 * Time: 10:00
 */
public class TestParrotContext {

    public static final String PACKAGE_NAME = "org.cccs.parrot.domain";
    private Map<String, Class> expectedRequestMappings;
    private Map<String, Entity> expectedModel;

    static {
        ContextBuilder.init(PACKAGE_NAME);
    }

    @Before
    public void before() {
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
        expectedRequestMappings.put("/cat", Cat.class);
        expectedRequestMappings.put("/cat/{catid}", Cat.class);
        expectedRequestMappings.put("/cat/{catid}/{attribute}", Cat.class);
        expectedRequestMappings.put("/dog", Dog.class);
        expectedRequestMappings.put("/dog/{dogid}", Dog.class);
        expectedRequestMappings.put("/dog/{dogid}/{attribute}", Dog.class);

        expectedModel = new HashMap<String, Entity>();
        expectedModel.put("Country", new Entity("Country", "Country", Country.class));
        expectedModel.put("Person", new Entity("Person", "Person", Person.class));
        expectedModel.put("Cat", new Entity("Cat", "Cat", Cat.class));
        expectedModel.put("Dog", new Entity("Dog", "Dog", Dog.class));
    }

    @Test
    public void getContextShouldWork() {
        assertNotNull(ContextBuilder.getContext());
    }

    @Test
    public void contextShouldHaveCorrectPaths() {
        ParrotContext context = ContextBuilder.getContext();
        assertContext(context);
        assertMapEquals(context.getRequestMappings(), expectedRequestMappings);
    }

    @Test
    public void contextShouldHaveCorrectModel() {
        ParrotContext context = ContextBuilder.getContext();
        assertContext(context);
        Map<String, Entity> model = context.getModel();
        assertThat(model.size(), is(equalTo(expectedModel.size())));
        assertMapEquals(context.getModel(), expectedModel);

        assertThat(model.get("Country").getAttributes().size(), is(equalTo(4)));
        assertThat(model.get("Person").getAttributes().size(), is(equalTo(6)));
        assertThat(model.get("Cat").getAttributes().size(), is(equalTo(4)));
        assertThat(model.get("Dog").getAttributes().size(), is(equalTo(4)));
    }

    private void assertContext(ParrotContext context) {
        assertNotNull(context);
        assertThat(context.getPackageName(), is(equalTo(PACKAGE_NAME)));
    }

    private void assertMapEquals(Map actual, Map expected) {
        assertThat(actual.size(), is(equalTo(expected.size())));
        for (Object key : actual.keySet()) {
            System.out.println("Checking key: " + key);
            assertTrue(expected.containsKey(key));
            assertThat(actual.get(key), is(equalTo(expected.get(key))));
        }
    }
}
