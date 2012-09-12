package org.cccs.parrot.web;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.cccs.parrot.domain.ParrotHttpOutputMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

/**
 * User: boycook
 * Date: 09/07/2012
 * Time: 10:11
 */
public class RenderErrorToResponseExceptionResolver implements HandlerExceptionResolver {

    private Logger log = LoggerFactory.getLogger(getClass());
    private Map<Class<? extends Throwable>, Integer> statusCodeMappings = new HashMap<Class<? extends Throwable>, Integer>();
    private HttpMessageConverter<?>[] messageConverters;

    @Override
    public ModelAndView resolveException(final HttpServletRequest request,
                                         final HttpServletResponse response,
                                         final Object handler, final Exception error) {
        log.debug(String.format("Resolving exception [%s] as [%s]", error.getClass().getName(), error.getMessage()));
        log.debug(ExceptionUtils.getFullStackTrace(error));
        final Throwable mappedException = extractMappedExceptionIfPresent(error);
        final Throwable ex = mappedException == null ? error : mappedException;
        final int responseCode = getMappedStatusCode(response, ex);
        renderResponse(request, response, ex, responseCode);
        return new ModelAndView();
    }

    private void renderResponse(final HttpServletRequest request,
                                final HttpServletResponse response,
                                final Throwable ex,
                                int responseCode) {
        log.info(format("Writing exception [%d] to response body", responseCode));

        try {
            doWrite(getAccept(request), ex, response.getOutputStream());
        } catch (IOException e) {
            log.error("There was an error writing exception to response body stream - continuing with response");
        }
    }

    private void doWrite(List<MediaType> accepted, final Throwable ex, OutputStream stream) {
        for (MediaType type : accepted) {
            for (HttpMessageConverter converter : getMessageConverters()) {
                if (contains(converter.getSupportedMediaTypes(), type)) {
                    try {
                        converter.write(ex, type, new ParrotHttpOutputMessage(stream));
                        return;
                    } catch (IOException e) {
                        log.error("There was an error writing exception to response body stream - continuing with response");
                    }
                }
            }
        }
    }

    private boolean contains(List<MediaType> types, MediaType match) {
        boolean foundMatch = false;
        for (MediaType type : types) {
            if (type.getType().equals(match.getType()) &&
                    type.getSubtype().equals(match.getSubtype())) {
                foundMatch = true;
            }
        }
        return foundMatch;
    }


    private int getMappedStatusCode(final HttpServletResponse response, final Throwable ex) {
        log.info(format("Resolving exception [%s] to http status code", ex.getClass().getName()));
        for (Class<? extends Throwable> exClazz : getStatusCodeMappings().keySet()) {
            if (exClazz.isAssignableFrom(ex.getClass())) {
                final Integer responseCode = getStatusCodeMappings().get(exClazz);
                response.setStatus(responseCode);
                return responseCode;
            }
        }

        //Default to 500
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }

    public List<MediaType> getAccept(final HttpServletRequest request) {
        String value = request.getHeader("Accept");
        return (value != null ? MediaType.parseMediaTypes(value) : Collections.<MediaType>emptyList());
    }


    /**
     * Walks stack trace looking for mapped exceptions. WARNING does not look for sub-classes of mapped
     * exceptions.
     *
     * @param ex
     * @return null if no mapped exception can be found, one of the mapped exceptions
     */
    private Throwable extractMappedExceptionIfPresent(final Exception ex) {
        Throwable throwable = ex;
        while (throwable != null && !statusCodeMappings.containsKey(throwable.getClass())) {
            throwable = throwable.getCause();
        }
        return throwable;
    }

    /**
     * Get the exception to http status code mappings for this instance.
     *
     * @return
     */
    public Map<Class<? extends Throwable>, Integer> getStatusCodeMappings() {
        return statusCodeMappings;
    }

    /**
     * Configure the mapping between exception types and http status codes returned to the client.
     *
     * @param statusCodeMappings
     */
    public void setStatusCodeMappings(final Map<Class<? extends Throwable>, Integer> statusCodeMappings) {
        this.statusCodeMappings = statusCodeMappings;
    }

    /**
     * Set the message body converters to use.
     * <p>These converters are used to convert from and to HTTP requests and responses.
     */
    public void setMessageConverters(HttpMessageConverter<?>[] messageConverters) {
        this.messageConverters = messageConverters;
    }

    /**
     * Return the message body converters that this adapter has been configured with.
     */
    public HttpMessageConverter<?>[] getMessageConverters() {
        return messageConverters;
    }
}
