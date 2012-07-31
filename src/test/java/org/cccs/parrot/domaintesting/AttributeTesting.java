package org.cccs.parrot.domaintesting;

import org.cccs.parrot.domain.Attribute;

/**
 * User: boycook
 * Date: 31/07/2012
 * Time: 14:54
 */
public class AttributeTesting {

    public static final Attribute ATTRIBUTE = new Attribute("att1", "Att 1", Object.class);
    public static final Attribute COPY = new Attribute("att1", "Att 1", Object.class);
    public static final Attribute NULL_NAME = new Attribute(null, "Att 1", Object.class);
    public static final Attribute NULL_DESCRIPTION = new Attribute("att1", null, Object.class);
    public static final Attribute NULL_CLASS = new Attribute("att1", "Att 1", null);
    public static final Attribute NULL_NAME_COPY = new Attribute(null, "Att 1", Object.class);
    public static final Attribute NULL_DESCRIPTION_COPY = new Attribute("att1", null, Object.class);
    public static final Attribute NULL_CLASS_COPY = new Attribute("att1", "Att 1", null);
    public static final Attribute DIFFERENT_NAME = new Attribute("DIFFERENT", "Att 1", Object.class);
    public static final Attribute DIFFERENT_DESC = new Attribute("att1", "DIFFERENT", Object.class);
    public static final Attribute DIFFERENT_CLASS = new Attribute("att1", "Att 1", String.class);

}
