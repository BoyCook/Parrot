package org.cccs.parrot.domaintesting;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: boycook
 * Date: 31/07/2012
 * Time: 14:20
 */
public class TestAttributeEquals extends AttributeTesting {

    @Test
    public void attributeEqualsShouldWorkWhenEqual() {
        assertTrue(ATTRIBUTE.equals(COPY));
    }

    @Test
    public void attributeEqualsShouldWorkForTheSameObject() {
        assertTrue(ATTRIBUTE.equals(ATTRIBUTE));
    }

    @Test
    public void attributeEqualsShouldBeFalseForNull() {
        assertFalse(ATTRIBUTE.equals(null));
    }

    @Test
    public void attributeEqualsShouldBeFalseForNullClass() {
        assertFalse(ATTRIBUTE.equals(NULL_CLASS));
    }

    @Test
    public void attributeEqualsShouldBeFalseForNullDescription() {
        assertFalse(ATTRIBUTE.equals(NULL_DESCRIPTION));
    }

    @Test
    public void attributeEqualsShouldBeFalseForNullName() {
        assertFalse(ATTRIBUTE.equals(NULL_NAME));
    }

    @Test
    public void attributeEqualsShouldBeFalseForNullClassInReverse() {
        assertFalse(NULL_CLASS.equals(ATTRIBUTE));
    }

    @Test
    public void attributeEqualsShouldBeFalseForNullDescriptionInReverse() {
        assertFalse(NULL_DESCRIPTION.equals(ATTRIBUTE));
    }

    @Test
    public void attributeEqualsShouldBeFalseForNullNameInReverse() {
        assertFalse(NULL_NAME.equals(ATTRIBUTE));
    }

    @Test
    public void attributeEqualsShouldBeFalseForDifferentClass() {
        assertFalse(ATTRIBUTE.equals(DIFFERENT_CLASS));
    }

    @Test
    public void attributeEqualsShouldBeFalseForDifferentDescription() {
        assertFalse(ATTRIBUTE.equals(DIFFERENT_DESC));
    }

    @Test
    public void attributeEqualsShouldBeFalseForDifferentName() {
        assertFalse(ATTRIBUTE.equals(DIFFERENT_NAME));
    }

    @Test
    public void attributeEqualsShouldBeFalseForDifferentClassInReverse() {
        assertFalse(DIFFERENT_CLASS.equals(ATTRIBUTE));
    }

    @Test
    public void attributeEqualsShouldBeFalseForDifferentDescriptionInReverse() {
        assertFalse(DIFFERENT_DESC.equals(ATTRIBUTE));
    }

    @Test
    public void attributeEqualsShouldBeFalseForDifferentNameInReverse() {
        assertFalse(DIFFERENT_NAME.equals(ATTRIBUTE));
    }
}
