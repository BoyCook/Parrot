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
    private static XStreamMarshaller marshaller;
    protected String serviceBaseURL;
    protected RestTemplate client;
    public static URL baseUrl;
    public static JettyServer jetty;
    public static MarshallingHttpMessageConverter xmlConverter;
    public static AbstractHttpMessageConverter jsonConverter;

    @BeforeClass
    public static void runOnce() {
        log.debug("RunOnce: setup converters");
        marshaller = new XStreamMarshaller();
        xmlConverter = new MarshallingHttpMessageConverter(marshaller);
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
        jetty.stop();
    }
}
