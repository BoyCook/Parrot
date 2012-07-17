package org.cccs.parrot.util;

import org.hibernate.annotations.ForeignKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

/**
 * User: boycook
 * Date: 30/06/2011
 * Time: 18:42
 */
public final class ClassUtils {

    private static final Logger log = LoggerFactory.getLogger(ClassUtils.class);
    private static final String GET = "get";
    private static final String IS = "is";
    private static final String HAS = "has";

    protected static boolean isSelectableColumn(AccessibleObject object) {
        Column column = object.getAnnotation(Column.class);
        Id id = object.getAnnotation(Id.class); //YES
        OneToOne oneToOne = object.getAnnotation(OneToOne.class); //YES
        OneToMany oneToMany = object.getAnnotation(OneToMany.class); //NO
        ManyToOne manyToOne = object.getAnnotation(ManyToOne.class); //NO
        ManyToMany manyToMany = object.getAnnotation(ManyToMany.class); //NO
        return column != null && !(id != null || oneToOne != null || oneToMany != null || manyToOne != null || manyToMany != null);
    }

    //TODO: cache
    @SuppressWarnings({"unchecked"})
    public static boolean hasRelations(final Class c, final Class cardinality) {
        for (Field field : c.getFields()) {
            if (field.getAnnotation(cardinality) != null) {
                return true;
            }
        }
        for (Method method : c.getMethods()) {
            if (method.getAnnotation(cardinality) != null) {
                return true;
            }
        }
        return false;
    }

    public static String getColumnName(Column column, Member member) {
        return column != null && isNotEmpty(column.name()) ? column.name() : member.getName();
    }

    public static String getColumnType(Class type) {
        if (Number.class.isAssignableFrom(type) || type.equals(Long.TYPE) || type.equals(Integer.TYPE)) {
            return "INTEGER";
        } else if (String.class.isAssignableFrom(type)) {
            return "VARCHAR";
        } else if (Boolean.class.isAssignableFrom(type)) {
            return "BOOLEAN";
        }
        return null;
    }

    //TODO handle exceptions
    public static Class getGenericType(Field field) {
        try {
            ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
            return (Class<?>) stringListType.getActualTypeArguments()[0];
        } catch (ClassCastException e) {
            return field.getType();
        }
    }

    //TODO handle exceptions
    public static Class getGenericType(Method method) {
        try {
            ParameterizedType stringListType = (ParameterizedType) method.getGenericReturnType();
            return (Class<?>) stringListType.getActualTypeArguments()[0];
        } catch (ClassCastException e) {
            return method.getReturnType();
        }
    }

    @SuppressWarnings({"unchecked"})
    public static String getTableName(Class c) {
        Table table = (Table) c.getAnnotation(Table.class);
        return table != null && isNotEmpty(table.name()) ? table.name() : c.getSimpleName();
    }

    public static String stripName(final String name) {
        if (name.indexOf(GET) == 0) {
            return lowerFirst(name.substring(name.indexOf(GET) + GET.length()));
        } else {
            if (name.indexOf(IS) == 0) {
                return lowerFirst(name.substring(name.indexOf(IS) + IS.length()));
            } else {
                if (name.indexOf(HAS) == 0) {
                    return lowerFirst(name.substring(name.indexOf(HAS) + HAS.length()));
                }
            }
        }
        return name;
    }

    private UniqueConstraint getUniqueConstraint(Class c, String constraintName) {
        Table table = (Table) c.getAnnotation(Table.class);
        UniqueConstraint found = null;
        if (table != null) {
            for (UniqueConstraint constraint : table.uniqueConstraints()) {
                if (isNotEmpty(constraint.name()) && constraint.name().equalsIgnoreCase(constraintName)) {
                    found = constraint;
                }
            }
        }
        return found;
    }

    private ForeignKey getForeignKey(Class c, String constraintName) {
        ForeignKey found = null;
        for (Method method : c.getMethods()) {
            ForeignKey foreignKey = method.getAnnotation(ForeignKey.class);
            if (foreignKey != null && isNotEmpty(foreignKey.name()) && foreignKey.name().equalsIgnoreCase(constraintName)) {
                found = foreignKey;
            }
        }
        return found;
    }

    public static String lowerFirst(String word) {
        return word.substring(0, 1).toLowerCase() + word.substring(1);
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
