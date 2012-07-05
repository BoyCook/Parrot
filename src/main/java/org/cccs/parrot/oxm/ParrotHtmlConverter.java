package org.cccs.parrot.oxm;

import org.apache.commons.io.IOUtils;
import org.cccs.parrot.context.ContextBuilder;
import org.cccs.parrot.domain.Attribute;
import org.cccs.parrot.domain.Entity;
import org.springframework.beans.BeanUtils;

import javax.persistence.Column;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import static java.lang.String.format;
import static org.cccs.parrot.context.ContextBuilder.getDescription;
import static org.cccs.parrot.util.Utils.readFile;

/**
 * User: boycook
 * Date: 04/07/2012
 * Time: 21:56
 */
public class ParrotHtmlConverter {

    private static final String HTML_OBJECT_ROW = "<tr><th>%s</th><td>%s</td></tr>";
    private static final String HTML_ANCHOR = "<a href='/service/%s/%s' target='_blank'>%s</a>";
    private static final String HTML_TEMPLATE_FILE = "/html/template.html";
    private static String HTML_TEMPLATE = null;

    public void writeHtml(OutputStream out, Object o) throws IOException, InvocationTargetException, IllegalAccessException {
        if (Collection.class.isAssignableFrom(o.getClass())) {
            Collection items = (Collection) o;
            writeObjects(out, items);
        } else {
            writeObject(out, o);
        }
    }

    private void writeObjects(OutputStream out, Collection items) throws IOException, InvocationTargetException, IllegalAccessException {
        if (items != null && items.size() > 0) {
            StringBuilder header = new StringBuilder();
            StringBuilder html = new StringBuilder();
            StringBuilder body = new StringBuilder();
            Object item1 = items.toArray()[0];
            Entity entity = ContextBuilder.getContext().getModel().get(item1.getClass());

            header.append("<tr>\n");
            for (Attribute attribute : entity.getAttributes()) {
                if (attribute.isColumn()) {
                    header.append("<th>");
                    header.append(attribute.getDescription());
                    header.append("</th>");
                }
            }
            header.append("</tr>");

            for (Object item : items) {
                body.append("<tr>\n");
                for (Attribute attribute : entity.getAttributes()) {
                    if (attribute.isColumn()) {
                        PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(item1.getClass(), attribute.getName());
                        Object result = descriptor.getReadMethod().invoke(item);
                        body.append("<td>");

                        String value = result == null ? "" : result.toString();
                        if (attribute.isIdentity()) {
                            body.append(format(HTML_ANCHOR, entity.getName(), value, value));
                        } else {
                            body.append(value);
                        }
                        body.append("</td>");
                    }
                }
                body.append("</tr>");
            }

            html.append("<table>\n");
            html.append(header.toString());
            html.append(body.toString());
            html.append("</table>");

            IOUtils.write(format(getHtmlTemplate(), item1.getClass().getSimpleName(), html.toString()), out);
        }
    }

    private void writeObject(OutputStream out, Object o) throws IOException, InvocationTargetException, IllegalAccessException {
        StringBuilder html = new StringBuilder();
        PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(o.getClass());
        html.append("<table>\n");

        for (PropertyDescriptor descriptor : descriptors) {
            Method method = descriptor.getReadMethod();
//            if (method.isAnnotationPresent(OneToOne.class) ||
//                    method.isAnnotationPresent(ManyToOne.class) ||
//                    method.isAnnotationPresent(OneToMany.class) ||
//                    method.isAnnotationPresent(ManyToMany.class)) {
//
//            } else
            if (method.isAnnotationPresent(Column.class)) {
                String desc = getDescription(descriptor);
                Object result = descriptor.getReadMethod().invoke(o);
                html.append(format(HTML_OBJECT_ROW, desc, result == null ? "" : result.toString()));
            }
        }

        html.append("</table>");
        IOUtils.write(format(getHtmlTemplate(), o.getClass().getSimpleName(), html.toString()), out);
    }

    private String getHtmlTemplate() {
        if (HTML_TEMPLATE == null) {
            HTML_TEMPLATE = readFile(HTML_TEMPLATE_FILE);
        }
        return HTML_TEMPLATE;
    }
}
