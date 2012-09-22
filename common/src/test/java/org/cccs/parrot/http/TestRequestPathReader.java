package org.cccs.parrot.http;

import org.junit.Test;
import org.springframework.http.server.ServletServerHttpRequest;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * User: boycook
 * Date: 22/09/2012
 * Time: 12:10
 */
public class TestRequestPathReader {

    private PathReader pathReader = mock(RequestPathReader.class);
    private ServletServerHttpRequest request = mock(ServletServerHttpRequest.class);

    @Test
    public void getPathShouldWork() {
        when(pathReader.getPath(request)).thenReturn(anyString());
    }
}
