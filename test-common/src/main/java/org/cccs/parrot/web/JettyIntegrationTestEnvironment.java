package org.cccs.parrot.web;

import org.cccs.parrot.DataDrivenTestEnvironment;
import org.junit.AfterClass;
import org.junit.Before;
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
    public static final XStreamMarshaller MARSHALLER = new XStreamMarshaller();
    public static final MarshallingHttpMessageConverter XML_CONVERTER = new MarshallingHttpMessageConverter(MARSHALLER);
    private RestTemplate client;
    private static URL baseUrl;
    private static JettyServer jetty;
    private static AbstractHttpMessageConverter jsonConverter;

    public static void startJetty() {
        jetty = new JettyServer();
        jetty.start();
        setBaseUrl(jetty.getBaseUrl());
    }

    public static void startJetty(String webXml, String war) {
        jetty = new JettyServer(webXml, war);
        jetty.start();
        setBaseUrl(jetty.getBaseUrl());
    }

    @SuppressWarnings("unchecked")
    @Before
    public void beforeEach() throws Exception {
        //TODO make it easier not to install data
        super.beforeEach();
        client = new RestTemplate();
        client.setMessageConverters(CollectionUtils.arrayToList(
                new HttpMessageConverter<?>[]{
                        getJsonConverter(), XML_CONVERTER, new SourceHttpMessageConverter()
                }
        ));
    }

    @AfterClass
    public static void stopWebService() throws Exception {
        jetty.stop();
    }

    public String getServiceBaseURL() {
        return getBaseUrl() + "/service/";
    }

    public static URL getBaseUrl() {
        return baseUrl;
    }

    public static void setBaseUrl(URL baseUrl) {
        JettyIntegrationTestEnvironment.baseUrl = baseUrl;
    }

    public RestTemplate getClient() {
        return client;
    }

    public static AbstractHttpMessageConverter getJsonConverter() {
        return jsonConverter;
    }

    public static void setJsonConverter(AbstractHttpMessageConverter jsonConverter) {
        JettyIntegrationTestEnvironment.jsonConverter = jsonConverter;
    }
}
