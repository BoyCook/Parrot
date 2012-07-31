package org.cccs.parrot;

import org.cccs.parrot.domain.Attribute;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: boycook
 * Date: 31/07/2012
 * Time: 14:20
 */
public class TestAttributeEqualsObjects {

    private Attribute attribute = new Attribute("att1", "Att 1", Object.class);

    @Test
    public void attributeEqualsShouldWork() {
        assertTrue(attribute.equals(new Attribute("att1", "Att 1", Object.class)));
    }

    @Test
    public void attributeEqualsShouldBeFalseForNull() {
        assertFalse(attribute.equals(null));
    }

    @Test
    public void attributeEqualsShouldBeFalseForNullClass() {
        assertFalse(attribute.equals(new Attribute("att1", "Att 1", null)));
    }

    @Test
    public void attributeEqualsShouldBeFalseForNullDescription() {
        assertFalse(attribute.equals(new Attribute("att1", null, Object.class)));
    }

    @Test
    public void attributeEqualsShouldBeFalseForNullName() {
        assertFalse(attribute.equals(new Attribute(null, "Att 1", Object.class)));
    }

    @Test
    public void attributeEqualsShouldBeFalseForNullClassInReverse() {
        assertFalse(new Attribute("att1", "Att 1", null).equals(attribute));
    }

    @Test
    public void attributeEqualsShouldBeFalseForNullDescriptionInReverse() {
        assertFalse(new Attribute("att1", null, Object.class).equals(attribute));
    }

    @Test
    public void attributeEqualsShouldBeFalseForNullNameInReverse() {
        assertFalse(new Attribute(null, "Att 1", Object.class).equals(attribute));
    }

    @Test
    public void attributeEqualsShouldBeFalseForDifferentClass() {
        assertFalse(attribute.equals(new Attribute("att1", "Att 1", String.class)));
    }

    @Test
    public void attributeEqualsShouldBeFalseForDifferentDescription() {
        assertFalse(attribute.equals(new Attribute("att1", "DIFFERENT", Object.class)));
    }

    @Test
    public void attributeEqualsShouldBeFalseForDifferentName() {
        assertFalse(attribute.equals(new Attribute("DIFFERENT", "Att 1", Object.class)));
    }

    @Test
    public void attributeEqualsShouldBeFalseForDifferentClassInReverse() {
        assertFalse(new Attribute("att1", "Att 1", String.class).equals(attribute));
    }

    @Test
    public void attributeEqualsShouldBeFalseForDifferentDescriptionInReverse() {
        assertFalse(new Attribute("att1", "DIFFERENT", Object.class).equals(attribute));
    }

    @Test
    public void attributeEqualsShouldBeFalseForDifferentNameInReverse() {
        assertFalse(new Attribute("DIFFERENT", "Att 1", Object.class).equals(attribute));
    }
}
