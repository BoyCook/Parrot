package org.cccs.parrot.util;

import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.lang.reflect.Method;

/**
 * User: boycook
 * Date: 22/06/2012
 * Time: 10:02
 */
public final class ContextUtils {

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
}
