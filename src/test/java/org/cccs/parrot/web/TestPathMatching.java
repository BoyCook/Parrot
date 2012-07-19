package org.cccs.parrot.web;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * User: boycook
 * Date: 22/06/2012
 * Time: 14:31
 */
@ContextConfiguration(locations = "classpath:context/parrotContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestPathMatching {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private PathMatcher matcher;

    @Before
    public void setup() {
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
    public void matchInvalidPathShouldThrowException1() {
        assertInvalidPath("/foo");
    }

    @Test
    public void matchInvalidPathShouldThrowException2() {
        assertInvalidPath("/foo/bar");
    }

    @Test
    public void matchInvalidPathShouldThrowException3() {
        assertInvalidPath("/foo/bar/foo");
    }

    @Test
    public void matchInvalidPathShouldThrowException4() {
        assertInvalidPath("/foo/bar/foo/bar");
    }

    @Test
    public void matchInvalidPathShouldThrowException5() {
        assertInvalidPath("/foo/bar/foo/bar/foo");
    }

    @Test
    public void matchInvalidPathShouldThrowException6() {
        assertInvalidPath("/foo/bar/foo/bar/foo/bar");
    }

    private void assertInvalidPath(String path) {
        thrown.expect(ResourceNotFoundException.class);
        thrown.expectMessage(format("Cannot match path [%s]", path));
        matcher.match(path);
    }
}
