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

    @Test
    public void generateListShouldWork() {
        Ul list = new Ul();
        Li item1 = new Li();
        item1.append(new A("FooBar", "#foo"));
        list.append(item1);

        Li item2 = new Li();
        item2.append(new A("BarFoo", "#bar"));
        list.append(item2);

        String expected = "<ul><li><a href=\"#foo\">FooBar</a></li><li><a href=\"#bar\">BarFoo</a></li></ul>";

        assertThat(list.toString(), is(equalTo(expected)));
    }
}