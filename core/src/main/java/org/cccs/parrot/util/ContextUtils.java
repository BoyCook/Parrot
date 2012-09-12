package org.cccs.parrot.util;

import org.cccs.parrot.Description;
import org.cccs.parrot.context.ContextBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * User: boycook
 * Date: 22/06/2012
 * Time: 10:02
 */
public final class ContextUtils {

    private ContextUtils() {}

    public static Class getClassByName(final String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static boolean isTop(final Class c) {
        if (!c.isAnnotationPresent(Table.class)) {
            return false;
        }
        for (Method method : c.getMethods()) {
            if (method.isAnnotationPresent(ManyToOne.class)) {
                return false;
            }
        }
        return true;
    }

    public static String getDescription(Class c) {
        String description = c.getSimpleName();
        if (c.isAnnotationPresent(Description.class)) {
            Description desc = (Description) c.getAnnotation(Description.class);
            description = desc.value();
        }
        return description;
    }

    public static String getDescription(PropertyDescriptor descriptor) {
        String description = descriptor.getName();
        Method method = descriptor.getReadMethod();
        if (method.isAnnotationPresent(Description.class)) {
            Description desc = method.getAnnotation(Description.class);
            description = desc.value();
        }
        return description;
    }

    public static String getResourcePath(final Class c) {
        return ("/" + c.getSimpleName()).toLowerCase();
    }

    public static String getUniquePath(final Class c) {
        return ("/{" + c.getSimpleName() + "Id" + "}").toLowerCase();
    }

    public static boolean isInDomain(Class c) {
        return (c.isAnnotationPresent(Entity.class) &&
                c.getPackage().getName().equalsIgnoreCase(ContextBuilder.getContext().getPackageName()));
    }
}
