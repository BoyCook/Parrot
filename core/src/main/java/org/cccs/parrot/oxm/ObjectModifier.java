package org.cccs.parrot.oxm;

/**
 * User: boycook
 * Date: 17/07/2012
 * Time: 12:03
 */
public interface ObjectModifier {

    /**
     * Performs any custom modifications to the object before marshalling
     *
     * @param o
     */
    void modify(Object o);

}
