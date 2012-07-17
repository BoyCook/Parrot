package org.cccs.parrot.oxm;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.server.ServletServerHttpRequest;

/**
 * User: boycook
 * Date: 12/07/2012
 * Time: 13:34
 */
public class RequestPathReader implements PathReader {

    @Override
    public String getPath(HttpInputMessage message) {
        return ((ServletServerHttpRequest) message).getServletRequest().getPathInfo();
    }
}
