package org.cccs.parrot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.persistence.Id;
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

    private static final Logger LOG = LoggerFactory.getLogger(ClassUtils.class);
    private static final String NEW_OBJECT_ERROR_MSG_TEXT = "Error creating new object [%s]";
    private static final String READ_ERROR_MSG_TEXT = "Failed to get property [%s] value of [%s]";
    private static final String WRITE_ERROR_MSG_TEXT = "Failed to write property [%s] value for [%s]";

    private ClassUtils() {}

    //TODO handle exceptions
    public static Class getGenericType(Method method) {
        ParameterizedType stringListType = (ParameterizedType) method.getGenericReturnType();
        return (Class<?>) stringListType.getActualTypeArguments()[0];
    }

    public static <T> T getNewObject(Class<T> c) {
        T o = null;
        //Not for primitives
        if (!c.equals(Integer.TYPE) && !c.equals(Long.TYPE)) {
            try {
                o = c.getConstructor().newInstance();
            } catch (InvocationTargetException e) {
                LOG.error(format(NEW_OBJECT_ERROR_MSG_TEXT, c.getName()), e);
            } catch (NoSuchMethodException e) {
                LOG.error(format(NEW_OBJECT_ERROR_MSG_TEXT, c.getName()), e);
            } catch (InstantiationException e) {
                LOG.error(format(NEW_OBJECT_ERROR_MSG_TEXT, c.getName()), e);
            } catch (IllegalAccessException e) {
                LOG.error(format(NEW_OBJECT_ERROR_MSG_TEXT, c.getName()), e);
            } catch (IllegalArgumentException e) {
                LOG.error(format(NEW_OBJECT_ERROR_MSG_TEXT, c.getName()), e);
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
                LOG.error(format(NEW_OBJECT_ERROR_MSG_TEXT, c.getName()), e);
            } catch (NoSuchMethodException e) {
                LOG.error(format(NEW_OBJECT_ERROR_MSG_TEXT, c.getName()), e);
            } catch (InstantiationException e) {
                LOG.error(format(NEW_OBJECT_ERROR_MSG_TEXT, c.getName()), e);
            } catch (IllegalAccessException e) {
                LOG.error(format(NEW_OBJECT_ERROR_MSG_TEXT, c.getName()), e);
            }
        }
        return o;
    }

    public static Object invokeReadMethod(Object o, PropertyDescriptor descriptor) {
        return invokeReadMethod(o, descriptor.getReadMethod());
    }

    public static Object invokeReadMethod(Object o, Method method) {
        Object value = null;
        try {
            value = method.invoke(o);
        } catch (IllegalAccessException e) {
            LOG.error(format(READ_ERROR_MSG_TEXT, method.getName(), o.getClass().getName()), e);
        } catch (InvocationTargetException e) {
            LOG.error(format(READ_ERROR_MSG_TEXT, method.getName(), o.getClass().getName()), e);
        }
        return value;
    }

    public static void invokeWriteMethod(Object o, Object value, PropertyDescriptor descriptor) {
        try {
            descriptor.getWriteMethod().invoke(o, value);
        } catch (IllegalAccessException e) {
            LOG.error(format(WRITE_ERROR_MSG_TEXT, descriptor.getName(), o.getClass().getName()), e);
        } catch (InvocationTargetException e) {
            LOG.error(format(WRITE_ERROR_MSG_TEXT, descriptor.getName(), o.getClass().getName()), e);
        }
    }

    public static Object getIdValue(Object o) {
        Object value = null;
        for (Method method : o.getClass().getMethods()) {
            if (method.isAnnotationPresent(Id.class)) {
                value = invokeReadMethod(o, method);
            }
        }
        return value;
    }

    public static PropertyDescriptor getIdProperty(Class c) {
        PropertyDescriptor match = null;
        PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(c);
        for (PropertyDescriptor descriptor : descriptors) {
            if (descriptor.getReadMethod().isAnnotationPresent(Id.class)) {
                match = descriptor;
            }
        }
        return match;
    }

    public static Object getPropertyValue(Object object, String name) {
        PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(object.getClass(), name);
        return invokeReadMethod(object, descriptor.getReadMethod());
    }
}
