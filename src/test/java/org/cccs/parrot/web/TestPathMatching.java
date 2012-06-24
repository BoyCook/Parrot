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
    private PathMatcher matcher;

    @Before
    public void setup() {
        ContextBuilder.init(PACKAGE_NAME);
        assertThat(ContextBuilder.getContext().getRequestMappings().size(), is(equalTo(24)));
        matcher = new PathMatcher();
    }

    @Test
    public void matchPathsShouldWork() {
        assertThat(matcher.match("/person"), is(equalTo("/person")));
        assertThat(matcher.match("/person/123"), is(equalTo("/person/{personid}")));
        assertThat(matcher.match("/person/123/SomeAttribute"), is(equalTo("/person/{personid}/{attribute}")));
        assertThat(matcher.match("/person/123/cat"), is(equalTo("/person/{personid}/cat")));
        assertThat(matcher.match("/person/123/cat/123"), is(equalTo("/person/{personid}/cat/{catid}")));
        assertThat(matcher.match("/person/123/cat/123/SomeAttribute"), is(equalTo("/person/{personid}/cat/{catid}/{attribute}")));
        assertThat(matcher.match("/person/123/dog"), is(equalTo("/person/{personid}/dog")));
        assertThat(matcher.match("/person/123/dog/123"), is(equalTo("/person/{personid}/dog/{dogid}")));
        assertThat(matcher.match("/person/123/dog/123/SomeAttribute"), is(equalTo("/person/{personid}/dog/{dogid}/{attribute}")));
        assertThat(matcher.match("/person/123/cat/123/country"), is(equalTo("/person/{personid}/cat/{catid}/country")));
        assertThat(matcher.match("/person/123/cat/123/country/123"), is(equalTo("/person/{personid}/cat/{catid}/country/{countryid}")));
        assertThat(matcher.match("/person/123/cat/123/country/123/SomeAttribute"), is(equalTo("/person/{personid}/cat/{catid}/country/{countryid}/{attribute}")));
        assertThat(matcher.match("/person/123/dog/123/country"), is(equalTo("/person/{personid}/dog/{dogid}/country")));
        assertThat(matcher.match("/person/123/dog/123/country/123"), is(equalTo("/person/{personid}/dog/{dogid}/country/{countryid}")));
        assertThat(matcher.match("/person/123/dog/123/country/123/SomeAttribute"), is(equalTo("/person/{personid}/dog/{dogid}/country/{countryid}/{attribute}")));
        assertThat(matcher.match("/country"), is(equalTo("/country")));
        assertThat(matcher.match("/country/123"), is(equalTo("/country/{countryid}")));
        assertThat(matcher.match("/country/123/SomeAttribute"), is(equalTo("/country/{countryid}/{attribute}")));
        assertThat(matcher.match("/country/123/cat"), is(equalTo("/country/{countryid}/cat")));
        assertThat(matcher.match("/country/123/cat/123"), is(equalTo("/country/{countryid}/cat/{catid}")));
        assertThat(matcher.match("/country/123/cat/123/SomeAttribute"), is(equalTo("/country/{countryid}/cat/{catid}/{attribute}")));
        assertThat(matcher.match("/country/123/dog"), is(equalTo("/country/{countryid}/dog")));
        assertThat(matcher.match("/country/123/dog/123"), is(equalTo("/country/{countryid}/dog/{dogid}")));
        assertThat(matcher.match("/country/123/dog/123/SomeAttribute"), is(equalTo("/country/{countryid}/dog/{dogid}/{attribute}")));
    }

    @Test
    public void matchPathsUpperCaseShouldWork() {
        assertThat(matcher.match("/PERSON"), is(equalTo("/person")));
        assertThat(matcher.match("/PERSON/123"), is(equalTo("/person/{personid}")));
        assertThat(matcher.match("/PERSON/123/SomeAttribute"), is(equalTo("/person/{personid}/{attribute}")));
        assertThat(matcher.match("/PERSON/123/CAT"), is(equalTo("/person/{personid}/cat")));
        assertThat(matcher.match("/PERSON/123/CAT/123"), is(equalTo("/person/{personid}/cat/{catid}")));
        assertThat(matcher.match("/PERSON/123/CAT/123/SomeAttribute"), is(equalTo("/person/{personid}/cat/{catid}/{attribute}")));
        assertThat(matcher.match("/PERSON/123/DOG"), is(equalTo("/person/{personid}/dog")));
        assertThat(matcher.match("/PERSON/123/DOG/123"), is(equalTo("/person/{personid}/dog/{dogid}")));
        assertThat(matcher.match("/PERSON/123/DOG/123/SomeAttribute"), is(equalTo("/person/{personid}/dog/{dogid}/{attribute}")));
        assertThat(matcher.match("/PERSON/123/CAT/123/COUNTRY"), is(equalTo("/person/{personid}/cat/{catid}/country")));
        assertThat(matcher.match("/PERSON/123/CAT/123/COUNTRY/123"), is(equalTo("/person/{personid}/cat/{catid}/country/{countryid}")));
        assertThat(matcher.match("/PERSON/123/CAT/123/COUNTRY/123/SomeAttribute"), is(equalTo("/person/{personid}/cat/{catid}/country/{countryid}/{attribute}")));
        assertThat(matcher.match("/PERSON/123/DOG/123/COUNTRY"), is(equalTo("/person/{personid}/dog/{dogid}/country")));
        assertThat(matcher.match("/PERSON/123/DOG/123/COUNTRY/123"), is(equalTo("/person/{personid}/dog/{dogid}/country/{countryid}")));
        assertThat(matcher.match("/PERSON/123/DOG/123/COUNTRY/123/SomeAttribute"), is(equalTo("/person/{personid}/dog/{dogid}/country/{countryid}/{attribute}")));
        assertThat(matcher.match("/COUNTRY"), is(equalTo("/country")));
        assertThat(matcher.match("/COUNTRY/123"), is(equalTo("/country/{countryid}")));
        assertThat(matcher.match("/COUNTRY/123/SomeAttribute"), is(equalTo("/country/{countryid}/{attribute}")));
        assertThat(matcher.match("/COUNTRY/123/CAT"), is(equalTo("/country/{countryid}/cat")));
        assertThat(matcher.match("/COUNTRY/123/CAT/123"), is(equalTo("/country/{countryid}/cat/{catid}")));
        assertThat(matcher.match("/COUNTRY/123/CAT/123/SomeAttribute"), is(equalTo("/country/{countryid}/cat/{catid}/{attribute}")));
        assertThat(matcher.match("/COUNTRY/123/DOG"), is(equalTo("/country/{countryid}/dog")));
        assertThat(matcher.match("/COUNTRY/123/DOG/123"), is(equalTo("/country/{countryid}/dog/{dogid}")));
        assertThat(matcher.match("/COUNTRY/123/DOG/123/SomeAttribute"), is(equalTo("/country/{countryid}/dog/{dogid}/{attribute}")));
    }

    @Test
    public void matchInvalidPathShouldReturnNull() {
        assertNull(matcher.match("/foo"));
        assertNull(matcher.match("/foo/bar"));
        assertNull(matcher.match("/foo/bar/foo"));
        assertNull(matcher.match("/foo/bar/foo/bar"));
        assertNull(matcher.match("/foo/bar/foo/bar/foo"));
        assertNull(matcher.match("/foo/bar/foo/bar/foo/bar"));
    }
}
