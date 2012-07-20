package org.cccs.parrot.generator;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * User: boycook
 * Date: 21/07/2012
 * Time: 00:07
 */
public class TestGeneration {

    @Test
    public void generateDivShouldWork() {
        Div div = new Div("Div Content");
        assertThat(div.toString(), is(equalTo("<div>Div Content</div>")));
    }

    @Test
    public void generateTableShouldWork() {
        Table table = new Table();
        Tr tr = new Tr();
        table.append(tr);
        tr.append(new Th("ID")).append(new Td("123"));

        assertThat(table.toString(), is(equalTo("<table><tr><th>ID</th><td>123</td></tr></table>")));
    }
}
