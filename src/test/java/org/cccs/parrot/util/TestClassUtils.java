package org.cccs.parrot.util;

import org.cccs.parrot.Description;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.cccs.parrot.util.ClassUtils.getNewObject;

/**
 * User: boycook
 * Date: 19/07/2012
 * Time: 14:32
 */
public class TestClassUtils {

    @Test
    public void getNewObjectShouldWork() {
        assertNotNull(getNewObject(Object.class));
    }

    @Test
    public void getNewObjectShouldReturnNullForNoSuchMethodException(){
        assertNull(getNewObject(Description.class));
    }

    @Ignore
    @Test
    public void getNewObjectShouldReturnNullForInvocationTargetException(){
        assertNull(getNewObject(Description.class));
    }

    @Ignore
    @Test
    public void getNewObjectShouldReturnNullForIllegalAccessException(){
        assertNull(getNewObject(Description.class));
    }

    @Test
    public void getNewObjectShouldReturnNullForInstantiationException(){
        assertNull(getNewObject(SomeAbstractClass.class));
    }
}
