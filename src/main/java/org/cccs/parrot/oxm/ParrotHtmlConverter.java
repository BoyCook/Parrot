package org.cccs.parrot.oxm;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.lang.String.format;
import static org.cccs.parrot.context.ContextBuilder.getDescription;
import static org.cccs.parrot.util.Utils.readFile;

/**
 * User: boycook
 * Date: 04/07/2012
 * Time: 21:56
 */
public class ParrotHtmlConverter {

    private static final String HTML_TEMPLATE_FILE = "/html/template.html";
    private static String HTML_TEMPLATE = null;

    public void writeHtml(OutputStream out, Object o) throws IOException, InvocationTargetException, IllegalAccessException {
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

                html.append("<tr>\n");
                html.append("<td>");
                html.append(desc);
                html.append("</td>\n");
                html.append("<td>");
                html.append(result == null ? "" : result.toString());
                html.append("</td>\n");
                html.append("</tr>\n");
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
