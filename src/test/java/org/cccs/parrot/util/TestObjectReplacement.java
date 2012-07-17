package org.cccs.parrot.util;

import org.cccs.parrot.DataDrivenTestEnvironment;
import org.cccs.parrot.context.ContextBuilder;
import org.cccs.parrot.domain.Person;
import org.cccs.parrot.finder.GenericFinder;
import org.hibernate.collection.internal.PersistentSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * User: boycook
 * Date: 13/07/2012
 * Time: 12:30
 */
@ContextConfiguration(locations = {"classpath:testApplicationContext.xml", "classpath:parrotContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestObjectReplacement extends DataDrivenTestEnvironment {

    private GenericFinder finder;
    private ObjectReplacement replacement;
    private Person person;

    @Before
    public void beforeEach() throws Exception {
        finder = new GenericFinder(entityManagerFactory);
        setDataFileNames(DEFAULT_TABLES);
        super.beforeEach();

        Map<Class, Class> mappings = new HashMap<Class, Class>();
        mappings.put(PersistentSet.class, HashSet.class);
        replacement = new ObjectReplacement(mappings, ContextBuilder.getContext().getPackageName());
        person = finder.find(Person.class, 1);
    }

    @Test
    public void replaceCollectionsShouldWork() {
        replacement.findAndReplace(person);
    }
}
