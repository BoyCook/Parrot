package org.cccs.parrot.oxm;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import static org.cccs.parrot.util.ClassUtils.invokeReadMethod;
import static org.cccs.parrot.util.ContextUtils.getDescription;
import static org.cccs.parrot.util.ContextUtils.isInDomain;

/**
 * User: boycook
 * Date: 19/07/2012
 * Time: 11:35
 */
public class ParrotHtmlConverter implements Converter {

    @Override
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext context) {
        PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(o.getClass());
        writer.startNode("table");
        for (PropertyDescriptor descriptor : descriptors) {
            Method method = descriptor.getReadMethod();
            if (method.isAnnotationPresent(Column.class)) {
                String desc = getDescription(descriptor);
                Object result = invokeReadMethod(o, descriptor);
                writer.startNode("tr");
                writer.startNode("th");
                writer.setValue(desc);
                writer.endNode();
                writer.startNode("td");
                writer.setValue(result == null ? "" : result.toString());
                writer.endNode();
                writer.endNode();
            }
        }
        writer.endNode();
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        return null;
    }

    @Override
    public boolean canConvert(Class c) {
        return isInDomain(c);
    }
}
