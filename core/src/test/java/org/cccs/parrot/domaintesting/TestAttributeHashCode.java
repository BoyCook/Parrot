package org.cccs.parrot.domaintesting;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * User: boycook
 * Date: 31/07/2012
 * Time: 14:39
 */
public class TestAttributeHashCode extends AttributeTesting {

    @Test
    public void hashCodeShouldBeTheSame() {
        assertThat(ATTRIBUTE.hashCode(), is(equalTo(COPY.hashCode())));
    }

    @Test
    public void hashCodeShouldBeTheSameForNullName() {
        assertThat(NULL_NAME.hashCode(), is(equalTo(NULL_NAME_COPY.hashCode())));
    }

    @Test
    public void hashCodeShouldBeTheSameForNullDescription() {
        assertThat(NULL_DESCRIPTION.hashCode(), is(equalTo(NULL_DESCRIPTION_COPY.hashCode())));
    }

    @Test
    public void hashCodeShouldBeTheSameForNullClass() {
        assertThat(NULL_CLASS.hashCode(), is(equalTo(NULL_CLASS_COPY.hashCode())));
    }
}
