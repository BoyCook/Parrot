package org.cccs.parrot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import static java.lang.String.format;

/**
 * User: boycook
 * Date: 30/06/2011
 * Time: 18:42
 */
public final class ClassUtils {

    private static final Logger log = LoggerFactory.getLogger(ClassUtils.class);

    //TODO handle exceptions
    public static Class getGenericType(Method method) {
        ParameterizedType stringListType = (ParameterizedType) method.getGenericReturnType();
        return (Class<?>) stringListType.getActualTypeArguments()[0];
    }

    public static Object getNewObject(Class c) {
        Object o = null;
        //Not for primitives
        if (!c.equals(Integer.TYPE) && !c.equals(Long.TYPE)) {
            try {
                o = c.getConstructor().newInstance();
            } catch (InvocationTargetException e) {
                log.error(format("Error creating new object [%s]", c.getName()), e);
            } catch (NoSuchMethodException e) {
                log.error(format("Error creating new object [%s]", c.getName()), e);
            } catch (InstantiationException e) {
                log.error(format("Error creating new object [%s]", c.getName()), e);
            } catch (IllegalAccessException e) {
                log.error(format("Error creating new object [%s]", c.getName()), e);
            } catch (IllegalArgumentException e) {
                log.error(format("Error creating new object [%s]", c.getName()), e);
            }
        }
        return o;
    }

    public static Object getNewObject(Class c, Class constructor, Object value) {
        Object o = null;
        //Not for primitives
        if (!c.equals(Integer.TYPE) && !c.equals(Long.TYPE)) {
            try {
                o = c.getConstructor(constructor).newInstance(value);
            } catch (InvocationTargetException e) {
                log.error(format("Error creating new object [%s]", c.getName()), e);
            } catch (NoSuchMethodException e) {
                log.error(format("Error creating new object [%s]", c.getName()), e);
            } catch (InstantiationException e) {
                log.error(format("Error creating new object [%s]", c.getName()), e);
            } catch (IllegalAccessException e) {
                log.error(format("Error creating new object [%s]", c.getName()), e);
            }
        }
        return o;
    }

    public static Object invokeReadMethod(Object o, PropertyDescriptor descriptor) {
        Object value = null;
        try {
            value = descriptor.getReadMethod().invoke(o);
        } catch (IllegalAccessException e) {
            log.error(format("Failed to get property [%s] value of [%s]", descriptor.getName(), o.getClass().getName()), e);
        } catch (InvocationTargetException e) {
            log.error(format("Failed to get property [%s] value of [%s]", descriptor.getName(), o.getClass().getName()), e);
        }
        return value;
    }

    public static void invokeWriteMethod(Object o, Object value, PropertyDescriptor descriptor) {
        try {
            descriptor.getWriteMethod().invoke(o, value);
        } catch (IllegalAccessException e) {
            log.error(format("Failed to write property [%s] value for [%s]", descriptor.getName(), o.getClass().getName()), e);
        } catch (InvocationTargetException e) {
            log.error(format("Failed to write property [%s] value for [%s]", descriptor.getName(), o.getClass().getName()), e);
        }
    }
}
