package org.cccs.parrot.context;

import org.cccs.parrot.domain.Entity;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * User: boycook
 * Date: 22/06/2012
 * Time: 09:48
 */
public class ParrotContext {

    private final String packageName;
    private final Set<Entity> model;
    private final Map<String, Class> rootMappings;
    private final Map<String, Class> requestMappings;

    public ParrotContext(final String packageName, Set<Entity> model, final Map<String, Class> rootMappings, final Map<String, Class> mappings) {
        this.packageName = packageName;
        this.model = model;
        this.rootMappings = rootMappings;
        this.requestMappings = mappings;
    }

    public String getPackageName() {
        return packageName;
    }

    public Map<String, Class> getRootMappings() {
        return rootMappings;
    }

    public Map<String, Class> getRequestMappings() {
        return requestMappings;
    }

    public Set<Entity> getModel() {
        return model;
    }
}
