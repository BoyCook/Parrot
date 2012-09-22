package org.cccs.parrot.http;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.server.ServletServerHttpRequest;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * User: boycook
 * Date: 22/09/2012
 * Time: 12:10
 */
public class TestRequestPathReader {

    private PathReader pathReader;
    private ServletServerHttpRequest serverRequest;
    private HttpServletRequest request;

    @Before
    public void setup() {
        pathReader = new RequestPathReader();
        serverRequest = mock(ServletServerHttpRequest.class);
        request = mock(HttpServletRequest.class);
    }

    @Test
    public void getPathShouldWork() {
        when(serverRequest.getServletRequest()).thenReturn(request);
        when(request.getPathInfo()).thenReturn("SOME_PATH");
        pathReader.getPath(serverRequest);
    }
}
