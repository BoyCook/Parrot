package org.cccs.parrot.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.cccs.parrot.util.CollectionSupport.asList;

/**
 * User: boycook
 * Date: 22/06/2012
 * Time: 12:14
 */
public final class Utils {

    private static final Logger LOG = LoggerFactory.getLogger(Utils.class);

    private Utils() {}

    public static String extractParameter(String path, int position) {
        List<String> stringList = asList(path.split("/"));
        return stringList.get(position);
    }

    public static String extractParameterFromEnd(String path, int position) {
        List<String> stringList = asList(path.split("/"));
        return stringList.get(stringList.size() - position);
    }

    public static String readFile(String fileName) {
        Validate.notEmpty(fileName);
        String file = null;
        try {
            file = Utils.readFileToString(fileName);
        } catch (IOException e) {
            LOG.error("There was an error reading in file: " + fileName, e);
        }
        return file;
    }

    public static String readFileToString(final String filename) throws IOException {
        final InputStream stream = Utils.class.getResourceAsStream(filename);
        return IOUtils.toString(stream);
    }
}
