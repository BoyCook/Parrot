package org.cccs.parrot.util;

import java.util.List;

import static org.cccs.parrot.util.CollectionSupport.asList;

/**
 * User: boycook
 * Date: 22/06/2012
 * Time: 12:14
 */
public final class Utils {

    public static String extractParameter(String path, int position) {
        List<String> stringList = asList(path.split("/"));
        return stringList.get(position);
    }
}
