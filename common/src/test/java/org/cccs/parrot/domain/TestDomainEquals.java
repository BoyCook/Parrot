package org.cccs.parrot.domain;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * User: boycook
 * Date: 22/09/2012
 * Time: 13:50
 */
public class TestDomainEquals {

    private static final Attribute ATTRIBUTE = new Attribute("NAME", "DESC", Object.class);
    private static final Attribute ATTRIBUTE_CLONE = new Attribute("NAME", "DESC", Object.class);
    private static final Attribute NULL_ATTRIBUTE = new Attribute(null, null, null);
    private static final Attribute DIFF_NAME = new Attribute("DIFF", "DESC", Object.class);
    private static final Attribute DIFF_DESC = new Attribute("NAME", "DIFF", Object.class);
    private static final Attribute DIFF_CLASS = new Attribute("NAME", "DESC", String.class);
    private static final Attribute NULL_NAME = new Attribute(null, "DESC", Object.class);
    private static final Attribute NULL_DESC = new Attribute("NAME", null, Object.class);
    private static final Attribute NULL_CLASS = new Attribute("NAME", "DESC", null);

    @Test
    public void attributeEqualsShouldWork() {
        assertTrue(ATTRIBUTE.equals(ATTRIBUTE));
        assertTrue(ATTRIBUTE.equals(ATTRIBUTE_CLONE));

        assertFalse(ATTRIBUTE.equals(null));
        assertFalse(ATTRIBUTE.equals(new String()));

        assertFalse(ATTRIBUTE.equals(DIFF_NAME));
        assertFalse(ATTRIBUTE.equals(DIFF_DESC));
        assertFalse(ATTRIBUTE.equals(DIFF_CLASS));
        assertFalse(ATTRIBUTE.equals(NULL_NAME));
        assertFalse(ATTRIBUTE.equals(NULL_DESC));
        assertFalse(ATTRIBUTE.equals(NULL_CLASS));

        assertFalse(DIFF_NAME.equals(ATTRIBUTE));
        assertFalse(DIFF_DESC.equals(ATTRIBUTE));
        assertFalse(DIFF_CLASS.equals(ATTRIBUTE));
        assertFalse(NULL_NAME.equals(ATTRIBUTE));
        assertFalse(NULL_DESC.equals(ATTRIBUTE));
        assertFalse(NULL_CLASS.equals(ATTRIBUTE));
    }

    @Test
    public void attributeHashCodeShouldWork() {
        assertThat(ATTRIBUTE.hashCode(), is(equalTo(ATTRIBUTE.hashCode())));
        assertThat(ATTRIBUTE.hashCode(), is(equalTo(ATTRIBUTE_CLONE.hashCode())));
        assertThat(ATTRIBUTE.hashCode(), is(not(equalTo(DIFF_NAME.hashCode()))));
        assertThat(NULL_ATTRIBUTE.hashCode(), is(equalTo(0)));
    }

    @Test
    public void keyValueShouldWork() {
        KeyValue kv = new KeyValue("Key", "Value");
        assertThat(kv.getKey(), is(equalTo("Key")));
        assertThat(kv.getValue(), is(equalTo("Value")));
    }
}
