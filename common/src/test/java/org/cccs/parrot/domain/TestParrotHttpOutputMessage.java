package org.cccs.parrot.domain;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.io.OutputStream;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * User: boycook
 * Date: 22/09/2012
 * Time: 13:29
 */
public class TestParrotHttpOutputMessage {

    private ParrotHttpOutputMessage message;
    private OutputStream outputStream;
    private HttpHeaders headers;

    @Before
    public void setup() {
        outputStream = mock(OutputStream.class);
        headers = mock(HttpHeaders.class);
    }

    @Test
    public void getBodyShouldWork() throws IOException {
        message = new ParrotHttpOutputMessage(outputStream);
        assertNotNull(message.getBody());
        assertThat(message.getCode(), is(equalTo(200)));
    }

    @Test
    public void getHeadersShouldWork() {
        message = new ParrotHttpOutputMessage(outputStream);
        assertNotNull(message.getHeaders());
        assertThat(message.getCode(), is(equalTo(200)));
    }

    @Test
    public void getBodyShouldWorkWithHeaders() throws IOException {
        message = new ParrotHttpOutputMessage(outputStream, headers, 200);
        assertNotNull(message.getBody());
        assertThat(message.getCode(), is(equalTo(200)));
    }

    @Test
    public void getHeadersShouldWorkWhenHeadersSet() {
        message = new ParrotHttpOutputMessage(outputStream, headers, 200);
        assertNotNull(message.getHeaders());
        assertThat(message.getCode(), is(equalTo(200)));
    }
}
