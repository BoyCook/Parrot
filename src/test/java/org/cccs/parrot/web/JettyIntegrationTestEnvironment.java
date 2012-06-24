package org.cccs.parrot.web;

import org.cccs.parrot.DataDrivenTestEnvironment;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * User: Craig Cook
 * Date: May 14, 2010
 * Time: 9:58:56 AM
 */
public abstract class JettyIntegrationTestEnvironment extends DataDrivenTestEnvironment {

    protected static final Logger log = LoggerFactory.getLogger(JettyIntegrationTestEnvironment.class);
    protected static XStreamMarshaller marshaller;
    protected static MarshallingHttpMessageConverter xmlConverter;
    protected static MappingJacksonHttpMessageConverter jsonConverter;
    protected static URL baseUrl;
    protected static String webXML = "src/test/resources/web.xml";
    protected static boolean overrideWebXML = true;
    protected String serviceBaseURL;
    protected RestTemplate client;
    public static Server server;

    @BeforeClass
    public static void runOnce() {
        marshaller = new XStreamMarshaller();
        xmlConverter = new MarshallingHttpMessageConverter(marshaller);
        jsonConverter = new MappingJacksonHttpMessageConverter();
        startWebService();
    }

    @SuppressWarnings("unchecked")
    @Before
    public void beforeEach() throws Exception {
        super.beforeEach();
        client = new RestTemplate();
        client.setMessageConverters(CollectionUtils.arrayToList(
            new HttpMessageConverter<?>[] {
                jsonConverter, xmlConverter, new SourceHttpMessageConverter()
            }
        ));
        serviceBaseURL = baseUrl + "/service/";
    }

    @AfterClass
    public static void stopWebService() throws Exception {
        log.debug("Stopping jetty test instance");
        server.stop();
        log.debug("Jetty test instance stopped");
    }

    public static void startWebService() {
        try {
            server = new Server();
            SelectChannelConnector connector = new SelectChannelConnector();
            connector.setMaxIdleTime(30000);
            connector.setAcceptors(2);
            connector.setStatsOn(true);
            connector.setLowResourcesConnections(5000);
            server.addConnector(connector);
            WebAppContext webapp = new WebAppContext();
            webapp.setContextPath("/");
            webapp.setWar("src/main/webapp");

            if (overrideWebXML) {
                webapp.setDescriptor(webXML);
            }

            server.setHandler(webapp);
            log.debug("Starting jetty test instance...");
            server.start();
            log.debug("Jetty startup complete.");
            baseUrl = new URL("http://localhost:" + connector.getLocalPort());
            log.debug("Server started:  " + baseUrl);
        } catch (Exception e) {
            log.debug("Error starting web service: " + e.getMessage());
            e.printStackTrace();
        }
    }

    protected void assertIntItem(List<Integer> results, int value) {
        assertNotNull(results);
        assertThat(results.get(0), is(equalTo(value)));
    }

    protected void assertStringItem(List<String> results, String value) {
        assertNotNull(results);
        assertThat(results.get(0), is(equalTo(value)));
    }
}
