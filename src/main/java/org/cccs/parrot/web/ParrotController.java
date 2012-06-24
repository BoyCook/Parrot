package org.cccs.parrot.web;

import org.apache.commons.lang.math.NumberUtils;
import org.cccs.parrot.context.ContextBuilder;
import org.cccs.parrot.context.ParrotContext;
import org.cccs.parrot.finder.GenericFinder;
import org.cccs.parrot.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UrlPathHelper;

import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

import static java.lang.String.format;
import static org.cccs.parrot.context.ContextBuilder.getUniquePath;

/**
 * User: boycook
 * Date: 22/06/2012
 * Time: 11:41
 */
@Controller
@Scope("session")
public class ParrotController {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected final UrlPathHelper urlPathHelper = new UrlPathHelper();
    protected static final String SERVICE_PATH = "/service";

    @Autowired
    protected EntityManagerFactory entityManagerFactory;

    //TODO: do this automatically somewhere
    static {
        ContextBuilder.init("org.cccs.parrot.domain");
    }

    @RequestMapping(value = "/context", method = RequestMethod.GET)
    @ResponseBody
    public ParrotContext getParrotContext() {
        return ContextBuilder.getContext();
    }

    @RequestMapping(value = "/resources", method = RequestMethod.GET)
    @ResponseBody
    public Set<String> getResources() {
        return ContextBuilder.getContext().getRequestMappings().keySet();
    }

    @RequestMapping(value = "/**", method = RequestMethod.GET)
    @ResponseBody
    public Object getParrotEntity(HttpServletRequest request,
                                  HttpServletResponse response) {
        String inboundPath = urlPathHelper.getPathWithinApplication(request);
        inboundPath = inboundPath.substring(SERVICE_PATH.length());
        log.debug("Inbound URL: " + inboundPath);

        final String matchedPath = PathMatcher.getMatcher().match(inboundPath);
        if (matchedPath != null) {
            Class clazz = ContextBuilder.getContext().getRequestMappings().get(matchedPath);
            log.debug(format("Found resource match [%s] as [%s]", matchedPath, clazz.getSimpleName()));

            //TODO: build query from FULL path hierarchy, not just last entity
            if (matchedPath.endsWith(getUniquePath(clazz))) {
                String key = extractParameterFromEnd(request, 1);
                return getFinder().find(clazz, NumberUtils.toLong(key));
            } else {
                return getFinder().all(clazz);
            }
        } else {
            log.error("No resource match found");
            response.setStatus(404);
        }
        return null;
    }

    public String extractParameter(HttpServletRequest request, int position) {
        return Utils.extractParameter(urlPathHelper.getPathWithinApplication(request), position);
    }

    public String extractParameterFromEnd(HttpServletRequest request, int position) {
        return Utils.extractParameterFromEnd(urlPathHelper.getPathWithinApplication(request), position);
    }

    private GenericFinder getFinder() {
        return new GenericFinder(entityManagerFactory);
    }
}
