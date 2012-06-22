package org.cccs.parrot.util;

import org.junit.Test;

import static org.cccs.parrot.util.Utils.extractParameter;
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
}

