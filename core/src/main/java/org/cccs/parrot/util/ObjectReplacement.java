package org.cccs.parrot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;
import static org.cccs.parrot.util.ClassUtils.getNewObject;
import static org.cccs.parrot.util.ClassUtils.invokeReadMethod;
import static org.cccs.parrot.util.ClassUtils.invokeWriteMethod;

/**
 * User: boycook
 * Date: 13/07/2012
 * Time: 12:29
 */
public class ObjectReplacement {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private Map<Class, Class> mappings;
    private Set<Object> stack;
    private String packageName;

    public ObjectReplacement(Map<Class, Class> mappings, String packageName) {
        this.mappings = mappings;
        this.packageName = packageName;
        this.stack = new HashSet<Object>();
    }

    public void findAndReplace(Object o) {
        if (Collection.class.isAssignableFrom(o.getClass())) {
//            o = getNewCollection((Collection) o);
            Collection items = (Collection) o;
            for (Object item : items) {
                findAndReplace(item);
            }
        } else {
            scanObject(o);
        }
    }

    private void scanObject(Object o) {
        if (isInDomain(o.getClass())) {
            stack.add(o);
            log.debug(format("Checking object [%s] as [%s]", o.getClass().getName(), o.toString()));
            PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(o.getClass());
            for (PropertyDescriptor descriptor : descriptors) {
                Class<?> type = descriptor.getReadMethod().getReturnType();
                Object value = invokeReadMethod(o, descriptor);
                if (Collection.class.isAssignableFrom(type) && mappings.containsKey(value.getClass())) {
                    replaceCollection(o, descriptor);
//                } else if (Map.class.isAssignableFrom(type)) {
//                    //TODO: handle HashMap
                } else if (isInDomain(type) &&
                        value != null &&
                        !stack.contains(value)) {
                    findAndReplace(value);
                }
            }
        }
    }

    private void replaceCollection(Object o, PropertyDescriptor descriptor) {
        Collection value = (Collection) invokeReadMethod(o, descriptor);
        Collection replaced = getNewCollection(value, mappings.get(value.getClass()));
        invokeWriteMethod(o, replaced, descriptor);
        log.debug(format("Replaced property [%s] as [%s] with [%s]", descriptor.getName(), value.getClass().getName(), replaced.getClass().getName()));
    }

    private Collection getNewCollection(Collection items, Class replaceType) {
        for (Object item : items) {
            findAndReplace(item);
        }
        return (Collection) getNewObject(replaceType, Collection.class, items);
    }

    private boolean isInDomain(Class c) {
        //TODO use ContextUtils.isInDomain
        return c.getPackage().getName().equalsIgnoreCase(packageName);
    }
}
