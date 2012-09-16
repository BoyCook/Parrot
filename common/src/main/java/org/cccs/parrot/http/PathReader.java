package org.cccs.parrot.http;

import org.springframework.http.HttpInputMessage;

/**
 * User: boycook
 * Date: 12/07/2012
 * Time: 13:33
 */
public interface PathReader {

    /**
     * Gets the path from a URL
     * @return
     */
    String getPath(HttpInputMessage message);

}
