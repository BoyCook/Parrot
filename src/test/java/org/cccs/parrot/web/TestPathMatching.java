package org.cccs.parrot.web;

import org.cccs.parrot.context.ContextBuilder;
import org.junit.Before;
import org.junit.Test;

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

    @Before
    public void setup() {
        ContextBuilder.init(PACKAGE_NAME);
    }

    @Test
    public void matchPathCasInsensitiveShouldWork() {
        PathMatcher matcher = new PathMatcher();

        assertThat(ContextBuilder.getContext().getRequestMappings().size(), is(equalTo(8)));
        assertNull(matcher.match("/foo/bar"));
        assertThat(matcher.match("/person/123"), is(equalTo("/person/{personid}")));
        assertThat(matcher.match("/person/123/cat/123"), is(equalTo("/person/{personid}/cat/{catid}")));
        assertThat(matcher.match("/person/123/dog/123"), is(equalTo("/person/{personid}/dog/{dogid}")));
        assertThat(matcher.match("/person/123/cat/123/country/123"), is(equalTo("/person/{personid}/cat/{catid}/country/{countryid}")));
        assertThat(matcher.match("/person/123/dog/123/country/123"), is(equalTo("/person/{personid}/dog/{dogid}/country/{countryid}")));
        assertThat(matcher.match("/country/123"), is(equalTo("/country/{countryid}")));
        assertThat(matcher.match("/country/123/cat/123"), is(equalTo("/country/{countryid}/cat/{catid}")));
        assertThat(matcher.match("/country/123/dog/123"), is(equalTo("/country/{countryid}/dog/{dogid}")));

        assertThat(matcher.match("/PERSON/123"), is(equalTo("/person/{personid}")));
        assertThat(matcher.match("/PERSON/123/CAT/123"), is(equalTo("/person/{personid}/cat/{catid}")));
        assertThat(matcher.match("/PERSON/123/DOG/123"), is(equalTo("/person/{personid}/dog/{dogid}")));
        assertThat(matcher.match("/PERSON/123/CAT/123/COUNTRY/123"), is(equalTo("/person/{personid}/cat/{catid}/country/{countryid}")));
        assertThat(matcher.match("/PERSON/123/DOG/123/COUNTRY/123"), is(equalTo("/person/{personid}/dog/{dogid}/country/{countryid}")));
        assertThat(matcher.match("/COUNTRY/123"), is(equalTo("/country/{countryid}")));
        assertThat(matcher.match("/COUNTRY/123/CAT/123"), is(equalTo("/country/{countryid}/cat/{catid}")));
        assertThat(matcher.match("/COUNTRY/123/DOG/123"), is(equalTo("/country/{countryid}/dog/{dogid}")));
    }
}
