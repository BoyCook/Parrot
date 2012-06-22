package org.cccs.parrot.web;

import org.cccs.parrot.context.ContextBuilder;
import org.cccs.parrot.context.ParrotContext;
import org.junit.Test;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.String.format;
import static junit.framework.Assert.assertNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * User: boycook
 * Date: 22/06/2012
 * Time: 14:31
 */
public class TestPathMatching {

    private static final String PACKAGE_NAME = "org.cccs.parrot.domain";
    private ParrotContext context = ContextBuilder.build(PACKAGE_NAME);

    @Test
    public void matchPathCasInsensitiveShouldWork() {
        assertThat(context.getRequestMappings().size(), is(equalTo(8)));
        assertNull(matchPath("/foo/bar"));
        assertThat(matchPath("/person/123"), is(equalTo("/person/{personid}")));
        assertThat(matchPath("/person/123/cat/123"), is(equalTo("/person/{personid}/cat/{catid}")));
        assertThat(matchPath("/person/123/dog/123"), is(equalTo("/person/{personid}/dog/{dogid}")));
        assertThat(matchPath("/person/123/cat/123/country/123"), is(equalTo("/person/{personid}/cat/{catid}/country/{countryid}")));
        assertThat(matchPath("/person/123/dog/123/country/123"), is(equalTo("/person/{personid}/dog/{dogid}/country/{countryid}")));
        assertThat(matchPath("/country/123"), is(equalTo("/country/{countryid}")));
        assertThat(matchPath("/country/123/cat/123"), is(equalTo("/country/{countryid}/cat/{catid}")));
        assertThat(matchPath("/country/123/dog/123"), is(equalTo("/country/{countryid}/dog/{dogid}")));

        assertThat(matchPath("/PERSON/123"), is(equalTo("/person/{personid}")));
        assertThat(matchPath("/PERSON/123/CAT/123"), is(equalTo("/person/{personid}/cat/{catid}")));
        assertThat(matchPath("/PERSON/123/DOG/123"), is(equalTo("/person/{personid}/dog/{dogid}")));
        assertThat(matchPath("/PERSON/123/CAT/123/COUNTRY/123"), is(equalTo("/person/{personid}/cat/{catid}/country/{countryid}")));
        assertThat(matchPath("/PERSON/123/DOG/123/COUNTRY/123"), is(equalTo("/person/{personid}/dog/{dogid}/country/{countryid}")));
        assertThat(matchPath("/COUNTRY/123"), is(equalTo("/country/{countryid}")));
        assertThat(matchPath("/COUNTRY/123/CAT/123"), is(equalTo("/country/{countryid}/cat/{catid}")));
        assertThat(matchPath("/COUNTRY/123/DOG/123"), is(equalTo("/country/{countryid}/dog/{dogid}")));
    }

    public String matchPath(String urlPath) {
        urlPath = urlPath.toLowerCase();
        // Pattern match?
        List<String> matchingPatterns = new ArrayList<String>();
        for (String registeredPattern : context.getRequestMappings().keySet()) {
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

    private PathMatcher getPathMatcher() {
        return new AntPathMatcher();
    }
}
