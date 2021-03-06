package org.cccs.parrot.web.converter;

import org.apache.commons.io.IOUtils;
import org.cccs.parrot.domain.ParrotHttpOutputMessage;
import org.cccs.parrot.generator.DOMElement;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * User: boycook
 * Date: 30/07/2012
 * Time: 20:54
 */
public class ParrotHtmlExceptionConverter extends AbstractHttpMessageConverter<Object> {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    public ParrotHtmlExceptionConverter() {
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
        DOMElement dom = getGenerator().convert((Exception) o, ((ParrotHttpOutputMessage) out).getCode());
        IOUtils.write(dom.toString(), out.getBody());
    }

    private ParrotHtmlExceptionGenerator getGenerator() {
        return new ParrotHtmlExceptionGenerator();
    }
}
