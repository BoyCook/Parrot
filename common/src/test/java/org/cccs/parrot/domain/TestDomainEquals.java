package org.cccs.parrot.domain;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * User: boycook
 * Date: 22/09/2012
 * Time: 13:50
 */
public class TestDomainEquals {

    private static final Attribute ATT_1 = new Attribute("NAME_1", "DESC_1", Object.class);
    private static final Attribute ATT_1_B = new Attribute("NAME_1", "DESC_1", Object.class);
    private static final Attribute ATT_2 = new Attribute("NAME_2", "DESC_2", Object.class);

    @Test
    public void attributeEqualsShouldWork() {
        assertTrue(ATT_1.equals(ATT_1));
        assertTrue(ATT_1.equals(ATT_1_B));
        assertFalse(ATT_1.equals(ATT_2));
    }
}
