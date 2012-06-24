package org.cccs.parrot.finder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

/**
 * User: boycook
 * Date: 24/04/2012
 * Time: 13:16
 */
public class GenericFinder {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String ERROR_MESSAGE = "Query returns no results, input params may be invalid";
    private EntityManagerFactory entityManagerFactory;

    public GenericFinder(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public <T> T executeNative(final String sql, final Map<String, Object> params) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createNativeQuery(sql);

        for (String key : params.keySet()) {
            query.setParameter(key, params.get(key));
        }

        T result;

        try {
            result = (T) query.getSingleResult();
        } catch (NoResultException e) {
            log.error(ERROR_MESSAGE, e);
            throw e;
        } finally {
            if (entityManager.isOpen()) {
                log.debug("Closing entityManager");
                entityManager.close();
            }
        }

        return result;
    }

    public <T> T find(final Class<T> c, long id) {
        EntityManager entityManager = getEntityManager();
        T result = null;

        try {
            result = entityManager.find(c, id);
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
            throw new EntityNotFoundException(ERROR_MESSAGE);
        }
        return result;
    }

    public <T> T find(final Class<T> c, String key, Object value) {
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
            throw new EntityNotFoundException(ERROR_MESSAGE);
        }
        return result;
    }

    public <T> List<T> all(final Class<T> c) {
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

    public <T> List<T> query(final Class<T> c, String key, Object value) {
        EntityManager entityManager = getEntityManager();
        List<T> results = null;

        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(c);
            Root<T> root = query.from(c);
            query.select(root);
            query.where(builder.equal(root.get(key), value));
            results = entityManager.createQuery(query).getResultList();
        } catch (Exception e) {
            //TODO: work out specific exception
            log.error("Error finding object", e);
        } finally {
            if (entityManager.isOpen()) {
                log.debug("Closing entityManager");
                entityManager.close();
            }
        }

        return results;
    }

    protected EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
