package org.springframework.http.client;

import org.cccs.parrot.oxm.PathReader;
import org.cccs.parrot.web.PathMatcher;
import org.springframework.http.HttpInputMessage;

/**
 * User: boycook
 * Date: 12/07/2012
 * Time: 13:45
 */
public class ResponsePathReader implements PathReader {

    @Override
    public String getPath(HttpInputMessage message) {
        SimpleClientHttpResponse response = (SimpleClientHttpResponse) message;
        String path = response.getURL().getPath();
        return path.substring(PathMatcher.SERVICE_PATH.length());
    }
}
