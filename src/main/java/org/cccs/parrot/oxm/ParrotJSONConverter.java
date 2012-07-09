package org.cccs.parrot.oxm;

import org.cccs.parrot.context.ContextBuilder;
import org.cccs.parrot.web.PathMatcher;
import org.cccs.parrot.web.ResourceNotFoundException;
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

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        try {
            final String inboundPath = ((ServletServerHttpRequest) inputMessage).getServletRequest().getPathInfo();
            final String matchedPath = PathMatcher.getMatcher().match(inboundPath);
            Class convertClazz = ContextBuilder.getContext().getRequestMappings().get(matchedPath);
            logger.debug(format("Found resource match for inbound URL path [%s], using class [%s] for JSON conversion", inboundPath, convertClazz.getName()));
            return super.readInternal(convertClazz, inputMessage);
        } catch (ResourceNotFoundException e) {
            return super.readInternal(clazz, inputMessage);
        }
    }
}
