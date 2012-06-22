package org.cccs.parrot.context;

import org.cccs.parrot.util.ClassUtils;
import org.cccs.parrot.util.ContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;

/**
 * User: boycook
 * Date: 22/06/2012
 * Time: 10:35
 */
public class ContextBuilder {

    protected static final Logger log = LoggerFactory.getLogger(ContextBuilder.class);
    private static ParrotContext context;

    public static void init(final String packageName) {
        setContext(build(packageName));
    }

    public static ParrotContext build(final String packageName) {
        ContextBuilder builder = new ContextBuilder();
        return builder.create(packageName);
    }

    public ParrotContext create(final String packageName) {
        final Set<Class> tops = new HashSet<Class>();
        final Map<String, Class> requestMappings = new HashMap<String, Class>();
        final ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Table.class));

        for (BeanDefinition bd : scanner.findCandidateComponents(packageName)) {
            Class clazz = ContextUtils.getClassByName(bd.getBeanClassName());
            if (ContextUtils.isTop(clazz)) {
                tops.add(clazz);
            }
        }

        log.debug(format("Found [%d] top level resources", tops.size()));
        for (Class top : tops) {
            log.debug(format("Building resource path for [%s]", top.getName()));
            buildResourcePath(requestMappings, top, "");
        }

        return new ParrotContext(packageName, requestMappings);
    }

    public void buildResourcePath(final Map<String, Class> requestMappings, final Class c, String path) {
        String thisPath = ("/" + c.getSimpleName() + "/{" + c.getSimpleName() + "Id" + "}").toLowerCase();
        path += thisPath;

        log.debug(format("Adding mapping [%s] [%s]", path.toLowerCase(), c));
        requestMappings.put(path.toLowerCase(), c);

        for (Method method : c.getMethods()) {
            if (method.isAnnotationPresent(OneToOne.class)) {
                String newPath = ("/" + method.getReturnType().getSimpleName() + "/{" + method.getReturnType().getSimpleName() + "Id}").toLowerCase();
                if (path.contains(newPath)) {
                    log.debug(format("Adding mapping [%s] [%s]", newPath, method.getReturnType()));
                    requestMappings.put(path + newPath, method.getReturnType());
                } else {
                    log.debug(format("Looking down into resource [%s]", method.getReturnType().getSimpleName()));
                    buildResourcePath(requestMappings, method.getReturnType(), path);
                }
            } else if (method.isAnnotationPresent(OneToMany.class)) {
                Class type = ClassUtils.getGenericType(method);
                String newPath = ("/" + type.getSimpleName() + "/{" + type.getSimpleName() + "Id}").toLowerCase();
                if (path.contains(newPath)) {
                    log.debug(format("Adding mapping [%s] [%s]", newPath, type));
                    requestMappings.put(path + newPath, type);
                } else {
                    log.debug(format("Looking down into resource [%s]", type.getSimpleName()));
                    buildResourcePath(requestMappings, ClassUtils.getGenericType(method), path);
                }
            } else if (method.isAnnotationPresent(ManyToMany.class)) {
                Class type = ClassUtils.getGenericType(method);
                String newPath = ("/" + type.getSimpleName() + "/{" + type.getSimpleName() + "Id}").toLowerCase();
                log.debug(format("Adding mapping [%s] [%s]", newPath, type));
                requestMappings.put(path + newPath, type);
            }
        }
    }

    public static ParrotContext getContext() {
        return context;
    }

    public static void setContext(ParrotContext context) {
        ContextBuilder.context = context;
    }
}
