package org.cccs.parrot.oxm;

import com.cedarsoftware.util.io.JsonReader;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import org.cccs.parrot.DataDrivenTestEnvironment;
import org.cccs.parrot.domain.Person;
import org.cccs.parrot.finder.GenericFinder;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;

import static org.cccs.parrot.Assert.assertJonn;
import static org.cccs.parrot.ParrotTestUtils.getJonn;
import static org.cccs.parrot.util.Utils.readFile;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.text.IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace;
import static org.junit.Assert.assertThat;

/**
 * User: boycook
 * Date: 28/06/2012
 * Time: 12:58
 */
@ContextConfiguration(locations = "classpath:context/testApplicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestJSONConverting extends DataDrivenTestEnvironment {

    protected GenericFinder finder;
    private Person dbPerson;
    private static final String PERSON_FILE = "/json/person.json";
    private static final String JACKSON_PERSON_FILE = "/json/jackson.person.json";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void beforeEach() throws Exception {
        finder = new GenericFinder(entityManagerFactory);
        setDataFileNames(DEFAULT_TABLES);
        super.beforeEach();

        dbPerson = finder.find(Person.class, 1);
    }

    @Test
    public void createPersonObjectWithJacksonShouldWork() throws IOException {
        JsonNode jsonNode = getMapper().readTree(readFile(JACKSON_PERSON_FILE));
        Person converted = getMapper().readValue(jsonNode, Person.class);
        assertJonn(converted);
    }

    @Test
    public void readAndWritePersonWithJacksonShouldWork() throws IOException {
        String jsonStr = getMapper().writeValueAsString(getJonn());
        JsonNode jsonNode = getMapper().readTree(jsonStr);
        Person converted = getMapper().readValue(jsonNode, Person.class);
        assertJonn(converted);
    }

    @Ignore
    @Test
    public void generateObjectFromJSONShouldWork() throws IOException {
        InputStream stream = TestJSONConverting.class.getResourceAsStream(PERSON_FILE);
        JsonReader reader = new JsonReader(stream);
        Person person = (Person) reader.readObject();
        assertThat(person, is(equalTo(finder.find(Person.class, 1))));
    }

    @Ignore
    @Test
    public void testWithXStream() {
        XStream xstream = new XStream(new JettisonMappedXmlDriver());
//        xstream.setMode(XStream.NO_REFERENCES);
//        xstream.setMode(XStream.ID_REFERENCES);
        xstream.setMode(XStream.XPATH_ABSOLUTE_REFERENCES);
//        xstream.setMode(XStream.XPATH_RELATIVE_REFERENCES);
        System.out.println(xstream.toXML(dbPerson));
    }

    private void assertJSONEquals(String actual, String expected) {
        actual = cleanJSON(actual);
        expected = cleanJSON(expected);
        assertThat(actual, CoreMatchers.is(equalToIgnoringWhiteSpace(expected)));
    }

    private static String cleanJSON(String value) {
        return value.
                replaceAll("\"id\":.+?,", "\"id\":0,").
                replaceAll("\"@id\":.+?,", "\"@id\":0,").
                replaceAll("\"@ref\":.+?", "\"@ref\":0").
                replaceAll("\n", "");
    }

    private ObjectMapper getMapper() {
        return new ObjectMapper();
    }
}
