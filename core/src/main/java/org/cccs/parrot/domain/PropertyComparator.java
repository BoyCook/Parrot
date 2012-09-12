package org.cccs.parrot.domain;

import java.util.Comparator;

/**
 * User: boycook
 * Date: 26/07/2012
 * Time: 20:55
 */
public class PropertyComparator implements Comparator<String> {
    @Override
    public int compare(String s1, String s2) {
        return s1.compareTo(s2);
    }
}
