package org.cccs.parrot.oxm;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import static java.lang.String.format;
import static org.cccs.parrot.web.PathMatcher.getResourceClass;

/**
 * User: boycook
 * Date: 11/07/2012
 * Time: 15:44
 */
public class ParrotJSONHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private PathReader reader;
    private ObjectModifier modifier;

    public ParrotJSONHttpMessageConverter(PathReader reader, ObjectModifier modifier) {
        super(new MediaType("application", "json", DEFAULT_CHARSET));
        this.reader = reader;
        this.modifier = modifier;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        //TODO: match class to model
        return true;
    }

    @Override
    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        try {
            final String inboundPath = getReader().getPath(inputMessage);
            Class convertClazz = getResourceClass(inboundPath);
            return readFromStream(convertClazz, inputMessage.getBody());
        } catch (IOException e) {
            throw new HttpMessageNotReadableException("Unable to read JSON");
        }
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        logger.debug(format("Writing entity [%s] as [%s] to output stream",
                o.getClass().getName(),
                o.toString()));
        getModifier().modify(o);
        JsonWriter jw = new JsonWriter(outputMessage.getBody());
        jw.write(o);
    }

    private <T> T readFromStream(Class<T> type, InputStream inputStream) throws IOException {
        JsonReader jr = new JsonReader(inputStream);
        return (T) jr.readObject();
    }

    public PathReader getReader() {
        return reader;
    }

    public ObjectModifier getModifier() {
        return modifier;
    }
}
