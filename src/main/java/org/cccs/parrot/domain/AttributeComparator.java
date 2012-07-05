package org.cccs.parrot.domain;

import java.util.Comparator;

/**
 * User: boycook
 * Date: 05/07/2012
 * Time: 14:56
 */
public class AttributeComparator implements Comparator<Attribute> {
    @Override
    public int compare(Attribute att1, Attribute att2) {
        return att1.getName().compareTo(att2.getName());
    }
}
