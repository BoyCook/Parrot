package org.cccs.parrot.context;

import java.util.Map;
import java.util.Set;

/**
 * User: boycook
 * Date: 22/06/2012
 * Time: 09:48
 */
public class ParrotContext {

    private final String packageName;
    private final Map<String, Class> requestMappings;

    public ParrotContext(final String packageName, final Map<String, Class> mappings) {
        this.packageName = packageName;
        this.requestMappings = mappings;
    }
    public String getPackageName() {
        return packageName;
    }

    public Map<String, Class> getRequestMappings() {
        return requestMappings;
    }
}
