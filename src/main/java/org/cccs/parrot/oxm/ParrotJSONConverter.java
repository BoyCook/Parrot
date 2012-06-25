package org.cccs.parrot.oxm;

import org.cccs.parrot.context.ContextBuilder;
import org.cccs.parrot.web.PathMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;

import java.io.IOException;

import static java.lang.String.format;

/**
 * User: boycook
 * Date: 25/06/2012
 * Time: 10:07
 */
public class ParrotJSONConverter extends MappingJacksonHttpMessageConverter {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        final String inboundPath = ((ServletServerHttpRequest) inputMessage).getServletRequest().getPathInfo();
        final String matchedPath = PathMatcher.getMatcher().match(inboundPath);

        if (matchedPath != null) {
            Class convertClazz = ContextBuilder.getContext().getRequestMappings().get(matchedPath);
            log.debug(format("Found resource match for inbound URL path [%s], using class [%s] for JSON convertion", inboundPath, convertClazz.getName()));
            return super.readInternal(convertClazz, inputMessage);
        }

        return super.readInternal(clazz, inputMessage);
    }
}
