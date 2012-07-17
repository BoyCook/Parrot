package org.cccs.parrot.web;

import org.cccs.parrot.context.ContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.String.format;

/**
 * User: boycook
 * Date: 22/06/2012
 * Time: 16:47
 */
public class PathMatcher {

    private static final Logger log = LoggerFactory.getLogger(PathMatcher.class);
    private static final UrlPathHelper urlPathHelper = new UrlPathHelper();
    public static final String SERVICE_PATH = "/service";

    public static PathMatcher getMatcher() {
        return new PathMatcher();
    }

    public String match(String urlPath) {
        urlPath = urlPath.toLowerCase();
        List<String> matchingPatterns = new ArrayList<String>();
        for (String registeredPattern : ContextBuilder.getContext().getRequestMappings().keySet()) {
            registeredPattern = registeredPattern.toLowerCase();
            if (getPathMatcher().match(registeredPattern, urlPath)) {
                log.debug(format("Matched [%s] with [%s]", registeredPattern, urlPath));
                matchingPatterns.add(registeredPattern);
            }
        }
        String bestPatternMatch = null;
        Comparator<String> patternComparator = getPathMatcher().getPatternComparator(urlPath);
        if (!matchingPatterns.isEmpty()) {
            Collections.sort(matchingPatterns, patternComparator);
            bestPatternMatch = matchingPatterns.get(0);
            log.debug(format("Using match [%s] for [%s]", bestPatternMatch, urlPath));
        }

        if (bestPatternMatch == null) {
            throw new ResourceNotFoundException(format("Cannot match path [%s]", urlPath));
        }

        return bestPatternMatch;
    }

    public static String getInboundPath(HttpServletRequest request) {
        String inboundPath = urlPathHelper.getPathWithinApplication(request);
        inboundPath = inboundPath.substring(SERVICE_PATH.length());
        log.debug("Inbound URL: " + inboundPath);
        return inboundPath;
    }

    public static Class getResourceClass(HttpServletRequest request) {
        return getResourceClass(PathMatcher.getMatcher().match(getInboundPath(request)));
    }

    public static Class getResourceClass(String path) {
        final String matchedPath = PathMatcher.getMatcher().match(path);
        Class clazz = ContextBuilder.getContext().getRequestMappings().get(matchedPath);
        log.debug(format("Found resource match [%s] as [%s]", matchedPath, clazz.getSimpleName()));
        return clazz;
    }

    private org.springframework.util.PathMatcher getPathMatcher() {
        return new AntPathMatcher();
    }
}
