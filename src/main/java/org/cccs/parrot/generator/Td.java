package org.cccs.parrot.generator;

/**
 * User: boycook
 * Date: 21/07/2012
 * Time: 00:20
 */
public class Td extends DOMElement {

    public Td() {
        super("Td", "td");
    }

    public Td(String value) {
        super("Td", "td");
        setValue(value);
    }
}
