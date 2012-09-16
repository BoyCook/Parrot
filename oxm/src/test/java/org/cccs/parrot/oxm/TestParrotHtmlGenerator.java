package org.cccs.parrot.oxm;

import org.cccs.parrot.ParrotTestUtils;
import org.cccs.parrot.context.ContextBuilder;
import org.cccs.parrot.domain.Entity;
import org.cccs.parrot.util.Utils;
import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Map;

/**
 * User: boycook
 * Date: 04/07/2012
 * Time: 22:05
 */
public class TestParrotHtmlGenerator extends XMLTestCase {

    private static final String PACKAGE_NAME = "org.cccs.parrot.domain";
    private static final String EXPECTED_MESSAGE = "Comparing marshalled xml to expected xml";
    private static final String PERSON_FILE = "/html/person.html";
    private static final String PEOPLE_FILE = "/html/people.html";
    private static ParrotHtmlGenerator converter;

    static {
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);
        ContextBuilder.init(PACKAGE_NAME);
        converter = new ParrotHtmlGenerator();
    }

    @Test
    public void testWriteForObjectShouldWork() throws IOException, SAXException {
        assertXMLEqual(EXPECTED_MESSAGE, Utils.readFile(PERSON_FILE), converter.convert(ParrotTestUtils.getCraig()).toString());
    }

    @Test
    public void testWriteForCollectionShouldWork() throws IOException, SAXException{
        assertXMLEqual(EXPECTED_MESSAGE, Utils.readFile(PEOPLE_FILE), converter.convert(ParrotTestUtils.getPeople()).toString());
    }
}
