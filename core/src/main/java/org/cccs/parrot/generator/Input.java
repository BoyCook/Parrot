package org.cccs.parrot.generator;

import static java.lang.String.format;

/**
 * User: boycook
 * Date: 25/07/2012
 * Time: 18:27
 */
public abstract class Input extends DOMElement {

    private final String type;
    private String inputValue;

    public Input(String type) {
        this(type, null);
    }

    public Input(String type, String inputValue) {
        super("Input", "input");
        this.type = type;
        this.inputValue = inputValue;
    }

    public String getType() {
        return type;
    }

    public String getInputValue() {
        return inputValue;
    }

    @Override
    public String toString() {
        if (getInputValue() != null) {
            return format("<%s type=\"%s\" id=\"%s\" value=\"%s\" />", getTagName(), getType(), getId(), getInputValue());
        } else {
            return format("<%s type=\"%s\" id=\"%s\"/>", getTagName(), getType(), getId());
        }
    }
}
