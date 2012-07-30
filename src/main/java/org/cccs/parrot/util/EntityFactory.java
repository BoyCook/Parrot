package org.cccs.parrot.util;

import org.cccs.parrot.context.ContextBuilder;
import org.cccs.parrot.domain.Entity;

import java.beans.PropertyDescriptor;

import static org.cccs.parrot.util.ClassUtils.*;

/**
 * User: boycook
 * Date: 27/07/2012
 * Time: 12:47
 */
public final class EntityFactory {

    public static <T> T get(String type, long id) {
        Entity entity = ContextBuilder.getContext().getModel().get(type);
        T newObject = (T) getNewObject(entity.getClazz());
        PropertyDescriptor idProperty = getIdProperty(newObject.getClass());
        if (idProperty != null) {
            invokeWriteMethod(newObject, id, idProperty);
        }
        return newObject;
    }
}
