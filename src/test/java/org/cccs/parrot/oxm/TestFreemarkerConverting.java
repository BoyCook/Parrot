package org.cccs.parrot.oxm;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import static org.cccs.parrot.util.TestUtils.getCraig;

/**
 * User: boycook
 * Date: 04/07/2012
 * Time: 21:37
 */
public class TestFreemarkerConverting {

    private Configuration configuration;

    @Before
    public void setup() throws IOException {
        configuration = new Configuration();
        configuration.setDirectoryForTemplateLoading(new File("./src/test/resources/ftl/"));
    }

    @Test
    public void convertShouldWork() throws IOException, TemplateException {
        Template template = configuration.getTemplate("entity.ftl");
        StringWriter out = new StringWriter();
        template.process(getCraig(), out);
        System.out.println(out.toString());
    }
}
