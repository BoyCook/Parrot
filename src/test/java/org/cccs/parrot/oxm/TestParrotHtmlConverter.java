package org.cccs.parrot.oxm;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

import static org.cccs.parrot.util.TestUtils.getPerson;

/**
 * User: boycook
 * Date: 04/07/2012
 * Time: 22:05
 */
public class TestParrotHtmlConverter {

    private ParrotHtmlConverter converter;

    @Before
    public void setup() {
        converter = new ParrotHtmlConverter();
    }

    @Test
    public void writeHtmlShouldWork() throws IOException, InvocationTargetException, IllegalAccessException {
        OutputStream out = new ByteArrayOutputStream();
        converter.writeHtml(out, getPerson());
        System.out.println(out.toString());
    }
}
