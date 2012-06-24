package org.cccs.parrot.web;

import org.cccs.parrot.context.ContextBuilder;
import org.cccs.parrot.context.ParrotContext;
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

    public static PathMatcher getMatcher() {
        return new PathMatcher();
    }

    public String match(String urlPath) {
        urlPath = urlPath.toLowerCase();
        // Pattern match?
        List<String> matchingPatterns = new ArrayList<String>();
        for (String registeredPattern : ContextBuilder.getContext().getRequestMappings().keySet()) {
            registeredPattern = registeredPattern.toLowerCase();
            if (getPathMatcher().match(registeredPattern, urlPath)) {
                System.out.println(format("Matched [%s] with [%s]", urlPath, registeredPattern));
                matchingPatterns.add(registeredPattern);
            }
        }
        String bestPatternMatch = null;
        Comparator<String> patternComparator = getPathMatcher().getPatternComparator(urlPath);
        if (!matchingPatterns.isEmpty()) {
            Collections.sort(matchingPatterns, patternComparator);
            bestPatternMatch = matchingPatterns.get(0);
        }

        return bestPatternMatch;
    }

    private org.springframework.util.PathMatcher getPathMatcher() {
        return new AntPathMatcher();
    }
}
