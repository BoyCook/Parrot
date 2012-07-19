package org.cccs.parrot.context;

import org.cccs.parrot.domain.Attribute;
import org.cccs.parrot.domain.Entity;
import org.cccs.parrot.util.ClassUtils;
import org.cccs.parrot.util.ContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.persistence.*;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static org.cccs.parrot.util.ContextUtils.*;

/**
 * User: boycook
 * Date: 22/06/2012
 * Time: 10:35
 */
public class ContextBuilder {

    protected static final Logger log = LoggerFactory.getLogger(ContextBuilder.class);
    private static ParrotContext context;

    public ContextBuilder() {
    }

    public ContextBuilder(final String packageName) {
        setContext(build(packageName));
    }

    public static void init(final String packageName) {
        setContext(build(packageName));
    }

    public static ParrotContext build(final String packageName) {
        ContextBuilder builder = new ContextBuilder();
        return builder.create(packageName);
    }

    public ParrotContext create(final String packageName) {
        final Map<String, Class> rootMappings = new HashMap<String, Class>();
        final Map<String, Class> requestMappings = new HashMap<String, Class>();
        final Map<String, Entity> model = new HashMap<String, Entity>();

        final ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Table.class));

        for (BeanDefinition bd : scanner.findCandidateComponents(packageName)) {
            Class clazz = ContextUtils.getClassByName(bd.getBeanClassName());
            if (ContextUtils.isTop(clazz)) {
                rootMappings.put(getResourcePath(clazz), clazz);
            }
        }

        log.debug(format("Found [%d] top level resources", rootMappings.size()));
        for (Class top : rootMappings.values()) {
            log.debug(format("Building resource path for [%s]", top.getName()));
            buildResourcePath(requestMappings, top, "");
        }

        for (Class clazz : requestMappings.values()) {
            if (!model.containsKey(clazz.getSimpleName())) {
                model.put(clazz.getSimpleName(), buildEntity(clazz));
            }
        }

        return new ParrotContext(packageName, model, requestMappings);
    }

    public void buildResourcePath(final Map<String, Class> requestMappings, final Class c, String path) {
        addResource(requestMappings, c, path);
        path += getResourcePath(c) + getUniquePath(c);

        for (Method method : c.getMethods()) {
            if (method.isAnnotationPresent(OneToOne.class)) {
                if (path.contains(getResourcePath(method.getReturnType()))) {
                    addResource(requestMappings, method.getReturnType(), path);
                } else {
                    log.debug(format("Looking down into resource [%s]", method.getReturnType().getSimpleName()));
                    buildResourcePath(requestMappings, method.getReturnType(), path);
                }
            } else if (method.isAnnotationPresent(OneToMany.class)) {
                Class type = ClassUtils.getGenericType(method);
                if (path.contains(getResourcePath(type))) {
                    addResource(requestMappings, type, path);
                } else {
                    log.debug(format("Looking down into resource [%s]", type.getSimpleName()));
                    buildResourcePath(requestMappings, ClassUtils.getGenericType(method), path);
                }
            } else if (method.isAnnotationPresent(ManyToMany.class)) {
                Class type = ClassUtils.getGenericType(method);
                addResource(requestMappings, type, path);
            }
        }
    }

    public void addResource(final Map<String, Class> requestMappings, final Class c, String path) {
        String resourcePath = getResourcePath(c);
        String uniquePath = resourcePath + getUniquePath(c);
        String uniqueAttribute = resourcePath + getUniquePath(c) + "/{attribute}";
        String fullResourcePath = path + resourcePath;
        String fullUniquePath = path + uniquePath;
        String fullUniqueAttribute = path + uniqueAttribute;

        //Adding root path for non-root entities
        if (!requestMappings.containsKey(resourcePath)) {
            addMapping(requestMappings, c, resourcePath);
            addMapping(requestMappings, c, uniquePath);
            addMapping(requestMappings, c, uniqueAttribute);
        }

        addMapping(requestMappings, c, fullResourcePath);
        addMapping(requestMappings, c, fullUniquePath);
        addMapping(requestMappings, c, fullUniqueAttribute);
    }

    private void addMapping(final Map<String, Class> requestMappings, final Class c, String path) {
        log.debug(format("Adding mapping [%s] [%s]", path, c));
        requestMappings.put(path, c);
    }

    public Entity buildEntity(final Class c) {
        String description = getDescription(c);

        if (description == null) {
            description = c.getSimpleName();
        }

        Entity entity = new Entity(c.getSimpleName(), description, c);
        PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(c);

        for (PropertyDescriptor descriptor : descriptors) {
            Method method = descriptor.getReadMethod();
            if (method.isAnnotationPresent(OneToOne.class) ||
                method.isAnnotationPresent(ManyToOne.class) ||
                method.isAnnotationPresent(OneToMany.class) ||
                method.isAnnotationPresent(ManyToMany.class)) {
                Attribute attribute = new Attribute(descriptor.getName(), getDescription(descriptor), method.getReturnType());
                entity.addAttribute(attribute);
            } else if (method.isAnnotationPresent(Column.class)) {
                Attribute attribute = new Attribute(descriptor.getName(), getDescription(descriptor), method.getReturnType());
                attribute.setColumn(true);
                if (method.isAnnotationPresent(Id.class)) {
                    attribute.setSystemManaged(true);
                    attribute.setIdentity(true);
                }
                entity.addAttribute(attribute);
            }
        }
        return entity;
    }

    public static ParrotContext getContext() {
        return context;
    }

    public static void setContext(ParrotContext context) {
        ContextBuilder.context = context;
    }
}
