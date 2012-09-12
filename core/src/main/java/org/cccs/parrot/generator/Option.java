package org.cccs.parrot.generator;

import static java.lang.String.format;

/**
 * User: boycook
 * Date: 26/07/2012
 * Time: 12:53
 */
public class Option extends DOMElement {

    private String optionValue;

    public Option() {
        this(null, null);
    }

    public Option(String value, String optionValue) {
        super("Option", "option");
        this.optionValue = optionValue;
        setValue(value);
    }

    public String getOptionValue() {
        return optionValue;
    }

    @Override
    public String getStartTag() {
        if (getOptionValue() != null) {
            return format("<%s value=\"%s\">", getTagName(), getOptionValue());
        } else {
            return super.getStartTag();
        }
    }
}
