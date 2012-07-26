package org.cccs.parrot.generator;

/**
 * User: boycook
 * Date: 26/07/2012
 * Time: 12:53
 */
public class Select extends DOMElement {

    public Select() {
        this(null);
    }

    public Select(String id) {
        super("Select", "select");
        setId(id);
    }
}
