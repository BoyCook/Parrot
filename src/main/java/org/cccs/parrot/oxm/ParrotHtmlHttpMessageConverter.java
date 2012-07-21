package org.cccs.parrot.oxm;

import org.apache.commons.io.IOUtils;
import org.cccs.parrot.generator.DOMElement;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.Collection;

import static java.lang.String.format;
import static org.cccs.parrot.util.Utils.readFile;

/**
 * User: boycook
 * Date: 04/07/2012
 * Time: 21:48
 */
public class ParrotHtmlHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private static final String ENTITY_TEMPLATE_FILE = "/html/entity.html";
    private static final String ENTITIES_TEMPLATE_FILE = "/html/entities.html";
    private static String ENTITY_TEMPLATE;
    private static String ENTITIES_TEMPLATE;

    public ParrotHtmlHttpMessageConverter() {
        super(new MediaType("text", "html", DEFAULT_CHARSET));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        throw new UnsupportedOperationException("Inbound HTML message conversion is not supported");
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage out) throws IOException, HttpMessageNotWritableException {
        DOMElement dom = getGenerator().convert(o);

        if (Collection.class.isAssignableFrom(o.getClass())) {
            IOUtils.write(format(getEntitiesTemplate(), dom.toString()), out.getBody());
        } else {
            IOUtils.write(format(getEntityTemplate(), dom.toString()), out.getBody());
        }
    }

    private ParrotHtmlGenerator getGenerator() {
        return new ParrotHtmlGenerator();
    }

    private String getEntityTemplate() {
        if (ENTITY_TEMPLATE == null) {
            ENTITY_TEMPLATE = readFile(ENTITY_TEMPLATE_FILE);
        }
        return ENTITY_TEMPLATE;
    }

    private String getEntitiesTemplate() {
        if (ENTITIES_TEMPLATE == null) {
            ENTITIES_TEMPLATE = readFile(ENTITIES_TEMPLATE_FILE);
        }
        return ENTITIES_TEMPLATE;
    }
}
