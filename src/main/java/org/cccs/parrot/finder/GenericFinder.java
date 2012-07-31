package org.cccs.parrot.finder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

/**
 * User: boycook
 * Date: 24/04/2012
 * Time: 13:16
 */
public class GenericFinder {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String NOT_FOUND_MESSAGE = "Unable to find entity [%s] with [%s] as [%s]";
    private static final String ERROR_MESSAGE = "Query returns no results, input params may be invalid";
    private EntityManagerFactory entityManagerFactory;

    public GenericFinder(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public <T> List<T> all(final Class<T> c) {
        log.debug(format("Finding all [%s]", c.getSimpleName()));
        EntityManager entityManager = getEntityManager();
        List<T> results;

        try {
            results = entityManager.createQuery("from " + c.getSimpleName(), c).getResultList();
        } catch (NoResultException e) {
            log.error(ERROR_MESSAGE, e);
            throw e;
        } finally {
            if (entityManager.isOpen()) {
                log.debug("Closing entityManager");
                entityManager.close();
            }
        }

        return results;
    }

    public <T> T find(final Class<T> c, long id) {
        log.debug(format("Finding [%s] as [%d]", c.getSimpleName(), id));
        EntityManager entityManager = getEntityManager();
        T result = null;

        try {
            result = entityManager.find(c, id);
        } catch (Exception e) {
            //TODO: catch specific (non generic) exception
            log.error("Error finding object", e);
        } finally {
            if (entityManager.isOpen()) {
                log.debug("Closing entityManager");
                entityManager.close();
            }
        }

        if (result == null) {
            throw new EntityNotFoundException(format(NOT_FOUND_MESSAGE, c.getSimpleName(), "id", id));
        }
        return result;
    }

    public <T> T find(final Class<T> c, String key, Object value) {
        log.debug(format("Finding [%s] as [%s] = [%s]", c.getSimpleName(), key, value));
        EntityManager entityManager = getEntityManager();
        T result = null;

        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(c);
            Root<T> root = query.from(c);
            query.select(root);
            query.where(builder.equal(root.get(key), value));
            result = entityManager.createQuery(query).getSingleResult();
        } catch (Exception e) {
            //TODO: work out specific exception
            log.error("Error finding object", e);
        } finally {
            if (entityManager.isOpen()) {
                log.debug("Closing entityManager");
                entityManager.close();
            }
        }

        if (result == null) {
            throw new EntityNotFoundException(format(NOT_FOUND_MESSAGE, c.getSimpleName(), key, value));
        }
        return result;
    }

    protected EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
