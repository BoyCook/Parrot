package org.cccs.parrot.oxm;

import org.cccs.parrot.generator.*;

/**
 * User: boycook
 * Date: 30/07/2012
 * Time: 20:56
 */
public class ParrotHtmlExceptionGenerator {

    public DOMElement convert(Exception e) {
        Div main = new Div();
        Table table = new Table();
        main.append(table);
        Tr row1 = new Tr();
        table.append(row1);
        row1.append(new Th("Exception"));
        row1.append(new Td(e.getClass().getName()));

        Tr row2 = new Tr();
        table.append(row2);
        row2.append(new Th("Message"));
        row2.append(new Td(e.getMessage()));

        if (e.getCause() != null) {
            Tr row3 = new Tr();
            table.append(row3);
            row3.append(new Th("Cause"));
            row3.append(new Td(e.getCause().toString()));
        }

        return main;
    }
}
