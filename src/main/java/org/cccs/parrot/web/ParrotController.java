package org.cccs.parrot.web;

import org.apache.commons.lang.math.NumberUtils;
import org.cccs.parrot.context.ContextBuilder;
import org.cccs.parrot.context.ParrotContext;
import org.cccs.parrot.domain.Entity;
import org.cccs.parrot.finder.GenericFinder;
import org.cccs.parrot.service.GenericService;
import org.cccs.parrot.util.ClassUtils;
import org.cccs.parrot.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UrlPathHelper;

import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;
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

    @RequestMapping(value = "/model", method = RequestMethod.GET)
    @ResponseBody
    public Set<Entity> getModel() {
        return ContextBuilder.getContext().getModel();
    }

    @RequestMapping(value = "/resources", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Class> getResources() {
        return ContextBuilder.getContext().getRequestMappings();
    }

    @RequestMapping(value = "/resources/root", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Class> getRootResources() {
        return ContextBuilder.getContext().getRootMappings();
    }

    @RequestMapping(value = "/example/{entity}", method = RequestMethod.GET)
    @ResponseBody
    public Object getExampleEntity(@PathVariable("entity") String entity) {
        log.debug(format("Looking for example entity [%s]", entity));
        Collection<Class> classes = ContextBuilder.getContext().getRequestMappings().values();
        for (Class clazz : classes) {
            if (clazz.getSimpleName().equalsIgnoreCase(entity)) {
                Object newObject = ClassUtils.getNewObject(clazz);
                log.debug(format("Created new example object [%s] as [%s]", newObject.getClass().getSimpleName(), newObject.toString()));
                return newObject;
            }
        }
        return null;
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

    @RequestMapping(value = "/**", method = RequestMethod.PUT)
    @ResponseBody
    public String createParrotEntity(@RequestBody Object entity,
                                     HttpServletRequest request,
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
                //Create with ID
                getService().create(entity);
            } else {
                //Create without ID
                getService().create(entity);
            }
        } else {
            log.error("No resource match found");
            response.setStatus(404);
        }
        return "success";
    }

    @RequestMapping(value = "/**", method = RequestMethod.POST)
    @ResponseBody
    public String updateParrotEntity(@RequestBody Object entity,
                                     HttpServletRequest request,
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
                //Update using ID
                getService().update(entity);
            } else {
                //Throw exception, ID must be specified
            }
        } else {
            log.error("No resource match found");
            response.setStatus(404);
        }
        return "success";
    }

    @RequestMapping(value = "/**", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteParrotEntity(@RequestBody Object entity,
                                     HttpServletRequest request,
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
                //Delete using ID
                getService().delete(entity);
            } else {
                //Throw exception, ID must be specified
            }
        } else {
            log.error("No resource match found");
            response.setStatus(404);
        }
        return "success";
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

    private GenericService getService() {
        return new GenericService(entityManagerFactory);
    }
}
