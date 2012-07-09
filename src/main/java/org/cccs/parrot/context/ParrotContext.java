package org.cccs.parrot.context;

import org.cccs.parrot.domain.Entity;

import java.util.Collections;
import java.util.Map;

/**
 * User: boycook
 * Date: 22/06/2012
 * Time: 09:48
 */
public class ParrotContext {

    private final String packageName;
    private final Map<String, Entity> model;
    private final Map<String, Class> requestMappings;

    public ParrotContext(final String packageName, final Map<String, Entity> model, final Map<String, Class> mappings) {
        this.packageName = packageName;
        this.model = model;
        this.requestMappings = mappings;
    }

    public String getPackageName() {
        return packageName;
    }

    public Map<String, Class> getRequestMappings() {
        return Collections.unmodifiableMap(requestMappings);
    }

    public Map<String, Entity> getModel() {
        return Collections.unmodifiableMap(model);
    }
}
