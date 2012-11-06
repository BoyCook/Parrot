package org.cccs.parrot.web;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: boycook
 * Date: 25/09/2012
 * Time: 15:28
 */
public class TestRenderErrorToResponseExceptionResolver {

    private RenderErrorToResponseExceptionResolver resolver;
    private Map<Class<? extends Throwable>, Integer> mappings;

    @Before
    public void setup() {
        mappings = new HashMap<Class<? extends Throwable>, Integer>();
        mappings.put(NoResultException.class, 404);
        mappings.put(EntityNotFoundException.class, 404);
        mappings.put(ResourceNotFoundException.class, 404);
        mappings.put(ResourceConflictException.class, 409);
        mappings.put(ConstraintViolationException.class, 422);
        mappings.put(IllegalArgumentException.class, 422);
        mappings.put(ValidationException.class, 422);
        mappings.put(PersistenceException.class, 500);
        resolver = new RenderErrorToResponseExceptionResolver();
        resolver.setStatusCodeMappings(mappings);
    }

    @Test
    public void extractMappedExceptionIfPresent() {

    }

}
