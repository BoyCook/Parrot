package org.cccs.parrot.generator;

import static java.lang.String.format;

/**
 * User: boycook
 * Date: 25/07/2012
 * Time: 18:27
 */
public abstract class Input extends DOMElement {

    private final String type;

    public Input(String type) {
        super("Input", "input");
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getStartTag() {
        return format("<%s type=\"%s\" id=\"%s\"/>", getTagName(), getType(), getId());
    }

    @Override
    public String toString() {
        return getStartTag();
    }
}
