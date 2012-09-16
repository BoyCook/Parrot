package org.cccs.parrot.util;

import org.cccs.parrot.ParrotTestUtils;
import org.cccs.parrot.context.ContextBuilder;
import org.hibernate.collection.internal.PersistentSet;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * User: boycook
 * Date: 13/07/2012
 * Time: 12:30
 */
public class TestObjectReplacement {

    private ObjectReplacement replacement;

    @Before
    public void beforeEach() throws Exception {
        Map<Class, Class> mappings = new HashMap<Class, Class>();
        mappings.put(PersistentSet.class, HashSet.class);
        replacement = new ObjectReplacement(mappings, ContextBuilder.getContext().getPackageName());
    }

    @Test
    public void replaceCollectionsShouldWork() {
        replacement.findAndReplace(ParrotTestUtils.getCraig());
    }
}
