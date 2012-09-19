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
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

/**
 * User: Craig Cook
 * Date: May 14, 2010
 * Time: 9:58:56 AM
 */
public abstract class JettyIntegrationTestEnvironment extends DataDrivenTestEnvironment {

    protected static final Logger log = LoggerFactory.getLogger(JettyIntegrationTestEnvironment.class);
    protected static XStreamMarshaller marshaller;
    protected static MarshallingHttpMessageConverter xmlConverter;
    protected static AbstractHttpMessageConverter jsonConverter;
    protected static URL baseUrl;
    protected static String webXML = "src/test/resources/web.xml";
    protected static String warDir = "src/test/webapp";
    protected static boolean overrideWebXML = true;
    protected String serviceBaseURL;
    protected RestTemplate client;
    public static Server server;

    @BeforeClass
    public static void runOnce() {
        marshaller = new XStreamMarshaller();
        xmlConverter = new MarshallingHttpMessageConverter(marshaller);
        startWebService();
    }

    @SuppressWarnings("unchecked")
    @Before
    public void beforeEach() throws Exception {
        //TODO make it easier not to install data
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
            log.debug("Using war: " + warDir);
            webapp.setContextPath("/");
            webapp.setWar(warDir);

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
}
