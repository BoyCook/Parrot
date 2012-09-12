package org.cccs.parrot.generator;

/**
 * User: boycook
 * Date: 31/07/2012
 * Time: 17:03
 */
public class H extends DOMElement {

    public H(String size, String value) {
        super("Header", "h" + size);
        setValue(value);
    }
}
