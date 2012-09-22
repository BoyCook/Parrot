package org.cccs.parrot.oxm;

import org.cccs.parrot.context.ContextBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * User: boycook
 * Date: 22/09/2012
 * Time: 14:17
 */
public class TestReplaceHibernateModifier {

    private static final String PACKAGE_NAME = "org.cccs.parrot.domain";
    private ReplaceHibernateModifier modifier;

    @Before
    public void setup() {
        ContextBuilder.init(PACKAGE_NAME);
        modifier = new ReplaceHibernateModifier();
    }

    @Test
    public void modifierShouldWork() {
        modifier.modify(new Object());
    }
}
