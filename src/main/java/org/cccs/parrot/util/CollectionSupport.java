package org.cccs.parrot.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CollectionSupport {

    private static final List EMPTY_LIST = new ArrayList();

    private CollectionSupport() {
    }

    public static <T> List<T> asList(final Collection<T> iter) {
        return new ArrayList<T>(iter);
    }

    public static <T> List<T> asList(T... items) {
        List<T> results = new ArrayList<T>();
        for (T item : items) {
            results.add(item);
        }
        return results;
    }
}
