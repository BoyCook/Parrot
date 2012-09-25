package org.cccs.parrot.oxm;

import org.cccs.parrot.context.ContextBuilder;
import org.cccs.parrot.domain.Attribute;
import org.cccs.parrot.domain.Entity;
import org.cccs.parrot.generator.*;
import org.cccs.parrot.util.ClassUtils;
import org.cccs.parrot.util.ContextUtils;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * User: boycook
 * Date: 04/07/2012
 * Time: 21:56
 */
public class ParrotHtmlGenerator {

    public DOMElement convert(Object o) {
        PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(o.getClass());
        Entity entity = ContextBuilder.getContext().getModel().get(o.getClass().getSimpleName());

        Div main = new Div();
        main.setId("main");
        Ul list = new Ul();
        main.append(list);
        Div entityDiv = new Div();
        entityDiv.setId("entity");
        Table table = new Table();
        entityDiv.append(table);
        main.append(entityDiv);

        list.append(new Li().append(new A(o.getClass().getSimpleName(), "#entity")));

        for (PropertyDescriptor descriptor : descriptors) {
            Method method = descriptor.getReadMethod();
            if (method.isAnnotationPresent(Column.class)) {
                Object result = ClassUtils.invokeReadMethod(o, descriptor);
                String value = result == null ? "" : result.toString();
                Tr row = new Tr();
                table.append(row);
                row.append(new Th(ContextUtils.getDescription(descriptor)));
                Attribute attribute = entity.getAttribute(descriptor.getName());
                if (!attribute.isEditable() || attribute.isSystemManaged()) {
                    row.append(new Td(value));
                } else {
                    Td td = new Td();
                    Text text = new Text("txt" + descriptor.getName(), value);
                    row.append(td.append(text));
                }
            } else if (method.isAnnotationPresent(OneToMany.class) ||
                    method.isAnnotationPresent(ManyToMany.class)) {
                Collection value = (Collection) ClassUtils.invokeReadMethod(o, descriptor);
                list.append(new Li().append(new A(ContextUtils.getDescription(descriptor), "#" + descriptor.getName())));
                Div div = new Div();
                div.setId(descriptor.getName());
                main.append(div);
                if (value != null) {
                    div.append(convert(value));
                }
            }
        }

        return main;
    }

    public DOMElement convert(Collection items) {
        Table table = new Table();

        if (items != null && items.size() > 0) {
            //TODO: this isn't very eloquent
            Object item1 = items.toArray()[0];
            Entity entity = ContextBuilder.getContext().getModel().get(item1.getClass().getSimpleName());

            Tr headRow = new Tr();
            table.append(headRow);

            for (Attribute attribute : entity.getAttributes()) {
                if (attribute.isColumn()) {
                    headRow.append(new Th(attribute.getDescription()));
                }
            }

            for (Object item : items) {
                Tr row = new Tr();
                table.append(row);

                for (Attribute attribute : entity.getAttributes()) {
                    if (attribute.isColumn()) {
                        PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(item1.getClass(), attribute.getName());
                        Object result = ClassUtils.invokeReadMethod(item, descriptor);
                        String value = result == null ? "" : result.toString();

                        if (attribute.isIdentity()) {
                            row.append(new Td().append(new A(value, String.format("/service/%s/%s", entity.getName(), value), "_blank")));
                        } else {
                            row.append(new Td(value));
                        }
                    }
                }
            }
        }
        return table;
    }

}
