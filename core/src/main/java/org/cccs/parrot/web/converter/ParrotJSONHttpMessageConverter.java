package org.cccs.parrot.web.converter;

import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;
import org.cccs.parrot.http.PathReader;
import org.cccs.parrot.oxm.ObjectModifier;
import org.cccs.parrot.web.PathMatcher;
import org.cccs.parrot.web.ResourceNotFoundException;
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
            Class convertClazz = PathMatcher.getResourceClass(inboundPath);
            return readFromStream(convertClazz, inputMessage.getBody());
        } catch (ResourceNotFoundException e) {
            return readFromStream(clazz, inputMessage.getBody());
        } catch (IOException e) {
            throw new HttpMessageNotReadableException("Unable to read JSON", e);
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
        if (inputStream != null) {
            JsonReader jr = new JsonReader(inputStream);
            return (T) jr.readObject();
        }
        return null;
    }

    public PathReader getReader() {
        return reader;
    }

    public ObjectModifier getModifier() {
        return modifier;
    }
}
