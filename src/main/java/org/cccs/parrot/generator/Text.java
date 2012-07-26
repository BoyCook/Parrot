package org.cccs.parrot.generator;

/**
 * User: boycook
 * Date: 25/07/2012
 * Time: 18:28
 */
public class Text extends Input {

    public Text(String id) {
        this(id, null);
    }

    public Text(String id, String value) {
        super("text", value);
        setId(id);
    }
}
