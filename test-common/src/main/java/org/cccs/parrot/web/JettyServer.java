package org.cccs.parrot.web;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * User: boycook
 * Date: 20/09/2012
 * Time: 10:58
 */
public class JettyServer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private String webXML;
    private String warDir;
    private URL baseUrl;
    private Server server;

    public JettyServer() {
        this("src/test/resources/web.xml", "src/test/webapp");
    }

    public JettyServer(String webXML, String warDir) {
        this.webXML = webXML;
        this.warDir = warDir;
        server = new Server();
    }

    public void start() {
        try {
            if (server.isStopped()) {
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

                webapp.setDescriptor(webXML);

                server.setHandler(webapp);
                log.debug("Starting jetty test instance...");
                server.start();
                log.debug("Jetty startup complete.");
                baseUrl = new URL("http://localhost:" + connector.getLocalPort());
                log.debug("Server started:  " + baseUrl);
            }
        } catch (Exception e) {
            log.debug("Error starting web service: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void stop() throws Exception {
        log.debug("Stopping jetty test instance");
        server.stop();
        log.debug("Jetty test instance stopped");
    }

    public URL getBaseUrl() {
        return baseUrl;
    }

    public Server getServer() {
        return server;
    }
}
