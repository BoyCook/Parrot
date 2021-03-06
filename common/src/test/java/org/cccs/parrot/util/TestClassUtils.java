package org.cccs.parrot.util;

import org.cccs.parrot.Description;
import org.cccs.parrot.domain.Person;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.cccs.parrot.ParrotTestUtils.getCraig;
import static org.cccs.parrot.util.ClassUtils.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * User: boycook
 * Date: 19/07/2012
 * Time: 14:32
 */
//@RunWith(PowerMockRunner.class)
//@PrepareForTest(ClassUtils.class)
public class TestClassUtils {

    @Test
    public void getNewObjectShouldWork() {
        assertNotNull(getNewObject(Object.class));
    }

    @Test
    public void getNewObjectShouldReturnNullForNoSuchMethodException() {
        assertNull(getNewObject(Description.class));
    }

    @Ignore
    @Test
    public void getNewObjectShouldReturnNullForInvocationTargetException() {
        assertNull(getNewObject(Description.class));
    }

    @Ignore
    @Test
    public void getNewObjectShouldReturnNullForIllegalAccessException() {
        assertNull(getNewObject(Description.class));
    }

    @Test
    public void getNewObjectShouldReturnNullForInstantiationException() {
        assertNull(getNewObject(SomeAbstractClass.class));
    }

    @Test
    public void getNewObjectShouldReturnNullForInteger() {
        assertNull(getNewObject(Integer.class));
    }

    @Test
    public void getNewObjectShouldReturnNullForIntegerWithConstructorValue() {
        assertNull(getNewObject(Integer.class, null, null));
    }

    @Test
    public void getNewObjectShouldReturnNullForLong() {
        assertNull(getNewObject(Long.class));
    }

    @Test
    public void getNewObjectShouldReturnNullForLongWithConstructorValue() {
        assertNull(getNewObject(Long.class, null, null));
    }

    @Test
    public void invokeReadMethodShouldWork() {
        Person person = getCraig();
        PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(Person.class, "id");
        assertNotNull(invokeReadMethod(person, descriptor));
    }

    @Test
    public void invokeReadMethodShouldThrowIllegalAccessException() throws InvocationTargetException, IllegalAccessException {
//        Person person = new Person();
//        Method method = mock(Method.class);
//
//        PowerMockito.mockStatic(ClassUtils.class);
//        when(ClassUtils.invokeReadMethod(person, method)).thenThrow(IllegalAccessException.class);
//
//        ClassUtils.invokeReadMethod(person, method);
//        ClassUtils.getIdValue(person);
    }

    @Test
    public void invokeReadMethodShouldReturnNullForInvocationTargetException() {
        PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(SomeObject.class, "name");
        assertNull(ClassUtils.invokeReadMethod(new SomeObject(), descriptor));
    }

    @Test
    public void getIdValueShouldWork() {
        assertThat(getIdValue(getCraig()).toString(), is(equalTo("1")));
    }

    @Test
    public void mediaTypeListContainsShouldWork() {
        List<MediaType> types = new ArrayList<MediaType>();
        types.add(MediaType.APPLICATION_XML);
        types.add(MediaType.APPLICATION_JSON);
        types.add(MediaType.TEXT_HTML);

        assertTrue(types.contains(MediaType.TEXT_HTML));
    }
}
