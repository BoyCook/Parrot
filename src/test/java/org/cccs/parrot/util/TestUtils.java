package org.cccs.parrot.util;

import org.cccs.parrot.domain.Person;
import org.junit.Test;

import static org.cccs.parrot.util.Utils.extractParameter;
import static org.cccs.parrot.util.Utils.extractParameterFromEnd;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TestUtils {

    private final String PATH = "/org/cccs/boycook/parrot";

    @Test
    public void extractParameterShouldWork() {
        assertThat(extractParameter(PATH, 1), is(equalTo("org")));
        assertThat(extractParameter(PATH, 2), is(equalTo("cccs")));
        assertThat(extractParameter(PATH, 3), is(equalTo("boycook")));
        assertThat(extractParameter(PATH, 4), is(equalTo("parrot")));
    }

    @Test
    public void extractParameterFromEndShouldWork() {
        assertThat(extractParameterFromEnd(PATH, 1), is(equalTo("parrot")));
        assertThat(extractParameterFromEnd(PATH, 2), is(equalTo("boycook")));
        assertThat(extractParameterFromEnd(PATH, 3), is(equalTo("cccs")));
        assertThat(extractParameterFromEnd(PATH, 4), is(equalTo("org")));
    }

    public static Person getPerson() {
        return new Person("Craig Cook", "craig@cook.com", "012341234");
    }
}

