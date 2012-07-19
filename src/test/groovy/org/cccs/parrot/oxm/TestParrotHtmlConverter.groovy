package org.cccs.parrot.oxm

import org.junit.Test
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.junit.runner.RunWith
import org.springframework.test.context.ContextConfiguration
import org.springframework.oxm.xstream.XStreamMarshaller
import org.springframework.beans.factory.annotation.Autowired

import org.custommonkey.xmlunit.XMLTestCase;
import org.custommonkey.xmlunit.XMLUnit;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource
import org.junit.Ignore

@ContextConfiguration(locations = ["classpath:parrotContext.xml", "classpath:marshallingContext.xml"])
@RunWith(SpringJUnit4ClassRunner.class)
class TestParrotHtmlConverter extends XMLTestCase {

    static {
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);
    }

    @Autowired
    private XStreamMarshaller marshaller;

    String expectedAll = """
        <resources>
            <principal id="2">
                <id>2</id>
                <shortName>CraigCook</shortName>
                <password>password</password>
                <foreName>Craig</foreName>
                <surName>Cook</surName>
                <phoneNumber>07918880501</phoneNumber>
                <email>craig.cook@bt.com</email>
                <friends/>
                <friendRequests/>
            </principal>
        </resources>
    """

    @Ignore
    @Test
    public void marshallingListShouldWork() throws IOException {
        String marshalled = marshal(null);
        println marshalled;
        assertXMLEqual("Comparing marshalled xml to expected xml", expectedAll, marshalled);
    }

    public static String strip(String xml) {
        String removedIds = xml.replaceAll("id=\".+?\"", "id=\"0\"");
        return removedIds.replaceAll("<id>.*</id>", "<id>0</id>");
    }

    private String marshal(final Object obj) throws IOException {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        marshal(obj, new BufferedOutputStream(bos));
        return bos.toString();
    }

    private void marshal(final Object obj, final OutputStream stream) throws IOException {
        final Result result = new StreamResult(stream);
        marshaller.marshal(obj, result);
    }

    public Object unmarshal(final String data) throws IOException {
        return unmarshal(new ByteArrayInputStream(data.getBytes()));
    }

    public Object unmarshal(final InputStream stream) throws IOException {
        return marshaller.unmarshal(new StreamSource(stream));
    }
}
