package org.cccs.parrot.web.converter;

import org.cccs.parrot.generator.*;

import static java.lang.String.format;

/**
 * User: boycook
 * Date: 30/07/2012
 * Time: 20:56
 */
public class ParrotHtmlExceptionGenerator {

    public DOMElement convert(Exception e, int code) {
        Div main = new Div();
        main.append(new H("1", format("Error (%d)", code)));

        Table table = new Table();
        main.append(table);

        addRow(table, "Exception", e.getClass().getName());
        addRow(table, "Message", e.getMessage());

        if (e.getCause() != null) {
            addRow(table, "Cause", e.getCause().toString());
        }

        return main;
    }

    private void addRow(Table table, String header, String value) {
        Tr row = new Tr();
        table.append(row);
        row.append(new Th(header));
        row.append(new Td(value));
    }
}
