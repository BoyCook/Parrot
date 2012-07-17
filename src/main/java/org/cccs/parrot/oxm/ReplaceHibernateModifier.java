package org.cccs.parrot.oxm;

import org.cccs.parrot.context.ContextBuilder;
import org.cccs.parrot.util.ObjectReplacement;
import org.hibernate.collection.internal.PersistentSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * User: boycook
 * Date: 17/07/2012
 * Time: 12:04
 */
public class ReplaceHibernateModifier implements ObjectModifier {

    @Override
    public void modify(Object o) {
        Map<Class, Class> mappings = new HashMap<Class, Class>();
        mappings.put(PersistentSet.class, HashSet.class);
        ObjectReplacement replacement = new ObjectReplacement(mappings, ContextBuilder.getContext().getPackageName());
        replacement.findAndReplace(o);
    }
}
