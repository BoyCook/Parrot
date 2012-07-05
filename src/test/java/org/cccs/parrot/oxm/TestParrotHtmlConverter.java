package org.cccs.parrot.oxm;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

import static org.cccs.parrot.util.TestUtils.getCraig;
import static org.cccs.parrot.util.TestUtils.getPeople;
import static org.cccs.parrot.util.Utils.readFile;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.text.IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace;
import static org.junit.Assert.assertThat;

/**
 * User: boycook
 * Date: 04/07/2012
 * Time: 22:05
 */
@ContextConfiguration(locations = "classpath:parrotContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestParrotHtmlConverter {

    private static final String PERSON_FILE = "/html/person.html";
    private static final String PEOPLE_FILE = "/html/people.html";

    private ParrotHtmlConverter converter;
    private OutputStream out;

    @Before
    public void setup() {
        converter = new ParrotHtmlConverter();
        out = new ByteArrayOutputStream();
    }

    @Test
    public void writeForObjectShouldWork() throws IOException, InvocationTargetException, IllegalAccessException {
        converter.writeHtml(out, getCraig());
        assertThat(out.toString(), is(equalToIgnoringWhiteSpace(readFile(PERSON_FILE))));
    }

    @Test
    public void writeForCollectionShouldWork() throws IOException, InvocationTargetException, IllegalAccessException {
        converter.writeHtml(out, getPeople());
        assertThat(out.toString(), is(equalToIgnoringWhiteSpace(readFile(PEOPLE_FILE))));
    }
}
