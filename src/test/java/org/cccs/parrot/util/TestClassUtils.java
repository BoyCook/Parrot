package org.cccs.parrot.util;

import org.cccs.parrot.Description;
import org.cccs.parrot.domain.Person;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.cccs.parrot.ParrotTestUtils.getCraig;
import static org.cccs.parrot.util.ClassUtils.getNewObject;
import static org.cccs.parrot.util.ClassUtils.invokeReadMethod;

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

    @Test
    public void invokeReadMethodShouldWork() {
        Person person = getCraig();
        PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(Person.class, "id");
        assertNotNull(invokeReadMethod(person, descriptor));
    }

    @Ignore
    @Test
    public void invokeReadMethodShouldReturnNullForIllegalAccessException() {
        PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(Person.class, "id");
        assertNull(invokeReadMethod(new Object(), descriptor));
    }

    @Test
    public void invokeReadMethodShouldReturnNullForInvocationTargetException() {
        PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(SomeObject.class, "name");
        assertNull(invokeReadMethod(new SomeObject(), descriptor));
    }
}
