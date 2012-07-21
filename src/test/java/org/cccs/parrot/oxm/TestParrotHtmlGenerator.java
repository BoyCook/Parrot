package org.cccs.parrot.oxm;

import org.cccs.parrot.generator.DOMElement;
import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.cccs.parrot.ParrotTestUtils.getCraig;
import static org.cccs.parrot.ParrotTestUtils.getPeople;
import static org.cccs.parrot.util.Utils.readFile;

/**
 * User: boycook
 * Date: 04/07/2012
 * Time: 22:05
 */
@ContextConfiguration(locations = "classpath:context/parrotContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestParrotHtmlGenerator extends XMLTestCase {

    private static final String EXPECTED_MESSAGE = "Comparing marshalled xml to expected xml";
    private static final String PERSON_FILE = "/html/person.html";
    private static final String PEOPLE_FILE = "/html/people.html";
    private ParrotHtmlGenerator converter;

    static {
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);
    }

    @Before
    public void setup() {
        converter = new ParrotHtmlGenerator();
    }

    @Test
    public void writeForObjectShouldWork() throws IOException, SAXException {
        assertXMLEqual(EXPECTED_MESSAGE, readFile(PERSON_FILE), converter.convert(getCraig()).toString());
    }

    @Test
    public void writeForCollectionShouldWork() throws IOException, SAXException{
        assertXMLEqual(EXPECTED_MESSAGE, readFile(PEOPLE_FILE), converter.convert(getPeople()).toString());
    }
}
