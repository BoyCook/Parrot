package org.cccs.parrot.web;

import org.apache.commons.lang.math.NumberUtils;
import org.cccs.parrot.context.ContextBuilder;
import org.cccs.parrot.context.ParrotContext;
import org.cccs.parrot.domain.Entity;
import org.cccs.parrot.finder.GenericFinder;
import org.cccs.parrot.service.GenericService;
import org.cccs.parrot.util.ClassUtils;
import org.cccs.parrot.util.EntityFactory;
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

import static java.lang.String.format;
import static org.cccs.parrot.util.ContextUtils.getUniquePath;
import static org.cccs.parrot.web.PathMatcher.getInboundPath;

/**
 * User: boycook
 * Date: 22/06/2012
 * Time: 11:41
 */
@Controller
@Scope("session")
public class ParrotController {

    private final Logger log = LoggerFactory.getLogger(ParrotController.class);
    private final UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Autowired
    public EntityManagerFactory entityManagerFactory;

    @RequestMapping(value = "/context", method = RequestMethod.GET)
    @ResponseBody
    public ParrotContext getParrotContext() {
        return ContextBuilder.getContext();
    }

    @RequestMapping(value = "/model", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Entity> getModel() {
        return ContextBuilder.getContext().getModel();
    }

    @RequestMapping(value = "/resources", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Class> getResources() {
        return ContextBuilder.getContext().getRequestMappings();
    }

    @RequestMapping(value = "/example/{entityType}", method = RequestMethod.GET)
    @ResponseBody
    public Object getExampleEntity(@PathVariable("entityType") String entityType) {
        log.debug(format("Looking for example entityType [%s]", entityType));
        Collection<Class> classes = ContextBuilder.getContext().getRequestMappings().values();
        for (Class clazz : classes) {
            if (clazz.getSimpleName().equalsIgnoreCase(entityType)) {
                Object newObject = ClassUtils.getNewObject(clazz);
                log.debug(format("Created new example object [%s] as [%s]", newObject.getClass().getSimpleName(), newObject.toString()));
                return newObject;
            }
        }
        return null;
    }

    @RequestMapping(value = "/**", method = RequestMethod.GET)
    @ResponseBody
    public Object getParrotEntity(HttpServletRequest request) {
        final String matchedPath = PathMatcher.getMatcher().match(getInboundPath(request));
        Class clazz = ContextBuilder.getContext().getRequestMappings().get(matchedPath);

        //TODO: build query from FULL path hierarchy, not just last entity
        if (matchedPath.endsWith(getUniquePath(clazz))) {
            String key = extractParameterFromEnd(request, 1);
            return getFinder().find(clazz, NumberUtils.toLong(key));
        } else {
            return getFinder().all(clazz);
        }
    }

    @RequestMapping(value = "/{entityType}", method = RequestMethod.PUT)
    @ResponseBody
    public String createParrotEntity(@RequestBody Object entity,
                                     @PathVariable("entityType") String entityType,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
        return createParrotEntityWithId(entity, entityType, null, request);
    }

    @RequestMapping(value = "/{entityType}/{entityId}", method = RequestMethod.PUT)
    @ResponseBody
    public String createParrotEntityWithId(@RequestBody Object entity,
                                           @PathVariable("entityType") String entityType,
                                           @PathVariable("entityId") String entityId,
                                           HttpServletRequest request) {
        PathMatcher.getMatcher().match(getInboundPath(request));
        getService().create(entity);
        return "success";
    }

    @RequestMapping(value = "/{entityType}/{entityId}", method = RequestMethod.POST)
    @ResponseBody
    public String updateParrotEntity(@RequestBody Object entity,
                                     @PathVariable("entityType") String entityType,
                                     @PathVariable("entityId") String entityId,
                                     HttpServletRequest request) {
        PathMatcher.getMatcher().match(getInboundPath(request));
        getService().update(entity);
        return "success";
    }

    @RequestMapping(value = "/{entityType}/{entityId}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteParrotEntity(@PathVariable("entityType") String entityType,
                                     @PathVariable("entityId") String entityId,
                                     HttpServletRequest request) {
        PathMatcher.getMatcher().match(getInboundPath(request));
        Object entity = EntityFactory.get(entityType, Long.valueOf(entityId));
        getService().delete(entity);
        return "success";
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
