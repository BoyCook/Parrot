package org.cccs.parrot.oxm

import org.custommonkey.xmlunit.XMLTestCase
import org.custommonkey.xmlunit.XMLUnit
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.oxm.xstream.XStreamMarshaller
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import javax.xml.transform.Result
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

import static org.cccs.parrot.ParrotTestUtils.getCraig
import org.junit.Ignore

@ContextConfiguration(locations = ["classpath:context/parrotContext.xml", "classpath:context/marshallingContext.xml"])
@RunWith(SpringJUnit4ClassRunner.class)
class TestParrotHtmlConverter extends XMLTestCase {

    static {
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);
    }

    @Autowired
    private XStreamMarshaller marshaller;

    String expected = """
        <table>
            <tr>
                <th>Email</th>
                <td>craig@craigcook.co.uk</td>
            </tr>
            <tr>
                <th>ID</th>
                <td>1</td>
            </tr>
            <tr>
                <th>Name</th>
                <td>Craig Cook</td>
            </tr>
            <tr>
                <th>Phone</th>
                <td>07345123456</td>
            </tr>
        </table>
    """

    @Ignore("Need to stop having root node for entity type")
    @Test
    public void marshallingPersonShouldWork() throws IOException {
        String marshalled = marshal(getCraig());
        println marshalled;
        assertXMLEqual("Comparing marshalled xml to expected xml", expected, marshalled);
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
