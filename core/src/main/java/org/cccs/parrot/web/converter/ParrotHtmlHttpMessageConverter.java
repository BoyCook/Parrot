package org.cccs.parrot.web.converter;

import org.apache.commons.io.IOUtils;
import org.cccs.parrot.generator.DOMElement;
import org.cccs.parrot.oxm.ParrotHtmlGenerator;
import org.cccs.parrot.util.ClassUtils;
import org.cccs.parrot.util.Utils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;

/**
 * User: boycook
 * Date: 04/07/2012
 * Time: 21:48
 */
public class ParrotHtmlHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private static final String ENTITY_TEMPLATE_FILE = "/html/entity.html";
    private static final String ENTITIES_TEMPLATE_FILE = "/html/entities.html";
    private static String entityTemplate;
    private static String entitiesTemplate;

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
            IOUtils.write(String.format(getEntitiesTemplate(), dom.toString()), out.getBody());
        } else {
            IOUtils.write(String.format(getEntityTemplate(),
                    o.getClass().getSimpleName(),
                    ClassUtils.getIdValue(o),
                    dom.toString()), out.getBody());
        }
    }

    private ParrotHtmlGenerator getGenerator() {
        return new ParrotHtmlGenerator();
    }

    private String getEntityTemplate() {
        if (entityTemplate == null) {
            entityTemplate = Utils.readFile(ENTITY_TEMPLATE_FILE);
        }
        return entityTemplate;
    }

    private String getEntitiesTemplate() {
        if (entitiesTemplate == null) {
            entitiesTemplate = Utils.readFile(ENTITIES_TEMPLATE_FILE);
        }
        return entitiesTemplate;
    }
}
