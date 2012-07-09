package org.cccs.parrot.web;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
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

    @Override
    public ModelAndView resolveException(final HttpServletRequest request,
                                         final HttpServletResponse response,
                                         final Object handler, final Exception error) {
        log.debug(String.format("resolving exception: %s[%s]", error.getClass().getSimpleName(), error.getMessage()));
        log.debug(ExceptionUtils.getFullStackTrace(error));
        final Throwable mappedException = extractMappedExceptionIfPresent(error);
        final Throwable ex = mappedException == null ? error : mappedException;
        final int responseCode = applyExceptionToHttpStatusCodeMappings(response, ex);
        renderResponse(response, ex, responseCode);
        return new ModelAndView();
    }

    private void renderResponse(final HttpServletResponse response,
                                final Throwable ex,
                                int responseCode) {
        log.info(format("Writing exception [%d] to response body", responseCode));
        try {
            new ObjectMapper().writeValue(response.getOutputStream(), ex);
        } catch (IOException e) {
            log.error("There was an error writing exception to response stream - continuing with response");
        }
    }

    private int applyExceptionToHttpStatusCodeMappings(final HttpServletResponse response, final Throwable ex) {
        log.info("resolving exception " + ex.getClass().getSimpleName() + " to http status code");
        for (Class<? extends Throwable> exClazz : getStatusCodeMappings().keySet()) {
            if (exClazz.isAssignableFrom(ex.getClass())) {
                final Integer responseCode = getStatusCodeMappings().get(exClazz);
                response.setStatus(responseCode);
                return responseCode;
            }
        }

        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
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
}
