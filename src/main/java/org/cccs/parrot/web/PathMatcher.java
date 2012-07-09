package org.cccs.parrot.web;

import org.cccs.parrot.context.ContextBuilder;
import org.cccs.parrot.context.ParrotContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;

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

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    public static PathMatcher getMatcher() {
        return new PathMatcher();
    }

    public String match(String urlPath) {
        urlPath = urlPath.toLowerCase();
        List<String> matchingPatterns = new ArrayList<String>();
        for (String registeredPattern : ContextBuilder.getContext().getRequestMappings().keySet()) {
            registeredPattern = registeredPattern.toLowerCase();
            if (getPathMatcher().match(registeredPattern, urlPath)) {
                log.debug(format("Matched [%s] with [%s]", urlPath, registeredPattern));
                matchingPatterns.add(registeredPattern);
            }
        }
        String bestPatternMatch = null;
        Comparator<String> patternComparator = getPathMatcher().getPatternComparator(urlPath);
        if (!matchingPatterns.isEmpty()) {
            Collections.sort(matchingPatterns, patternComparator);
            bestPatternMatch = matchingPatterns.get(0);
        }

        if (bestPatternMatch == null) {
            throw new ResourceNotFoundException(format("Cannot match path [%s]", urlPath));
        }

        return bestPatternMatch;
    }

    private org.springframework.util.PathMatcher getPathMatcher() {
        return new AntPathMatcher();
    }
}
