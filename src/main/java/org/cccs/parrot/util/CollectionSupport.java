package org.cccs.parrot.util;

import java.util.ArrayList;
import java.util.List;

public final class CollectionSupport {

    public static <T> List<T> asList(T... items) {
        List<T> results = new ArrayList<T>();
        for (T item : items) {
            results.add(item);
        }
        return results;
    }
}
