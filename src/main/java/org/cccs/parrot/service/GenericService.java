package org.cccs.parrot.service;

import org.cccs.parrot.finder.GenericFinder;
import org.cccs.parrot.web.ResourceConflictException;
import org.cccs.parrot.web.ResourceNotFoundException;
import org.hibernate.TransientPropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import java.beans.PropertyDescriptor;

import static java.lang.String.format;
import static org.cccs.parrot.service.Validation.findNestedException;
import static org.cccs.parrot.util.ClassUtils.getIdValue;

/**
 * User: boycook
 * Date: 23/05/2012
 * Time: 15:07
 */
@Transactional(readOnly = true)
public class GenericService {
    //TODO: get the Transactional stuff to work automatically

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String UNIQUE_CONSTRAINT_ERROR_MESSAGE = "integrity constraint violation: unique constraint or index violation;";
    private static final String NOT_NULL_CONSTRAINT_ERROR_MESSAGE = "integrity constraint violation: NOT NULL check constraint;";
    private static final String UNIQUE_MESSAGE = "[%s] [%s] already exists";
    private static final String NOT_FOUND_MESSAGE = "Cannot find related entity [%s] [%s] to persist [%s] as [%s]";
    private static final String CREATE_MESSAGE = "Creating [%s] as [%s]";
    private static final String UPDATE_MESSAGE = "Updating [%s] as [%s]";
    private static final String ERROR_MESSAGE = "Error persisting [%s] as [%s]";
    private EntityManagerFactory entityManagerFactory;
    private GenericFinder finder;

    public GenericService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.finder = new GenericFinder(entityManagerFactory);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void create(final Object entity) {
        Validation.validate(entity);
        log.debug(format(CREATE_MESSAGE, entity.getClass().getSimpleName(), entity.toString()));
        EntityManager entityManager = getEntityManager();
        EntityTransaction txn = entityManager.getTransaction();
        try {
            txn.begin();
            entityManager.persist(entity);
            txn.commit();
        } catch (PersistenceException e) {
            handleException(entity, e);
        } catch (IllegalStateException e) {
            handleException(entity, e);
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void update(final Object entity) {
        Validation.validate(entity);
        log.debug(format(UPDATE_MESSAGE, entity.getClass().getSimpleName(), entity.toString()));
        EntityManager entityManager = getEntityManager();
        EntityTransaction txn = entityManager.getTransaction();
        try {
            txn.begin();
            entityManager.merge(entity);
            txn.commit();
        } catch (PersistenceException e) {
            handleException(entity, e);
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void delete(final Object entity) {
        //TODO: delete validation - there must be a jpaId
        log.debug(format(UPDATE_MESSAGE, entity.getClass().getSimpleName(), entity.toString()));
        EntityManager entityManager = getEntityManager();
        EntityTransaction txn = entityManager.getTransaction();
        try {
            txn.begin();
            if (!entityManager.contains(entity)) {
                Object persisted = getFinder().find(entity.getClass(), Long.valueOf(getIdValue(entity).toString()));
                entityManager.remove(entityManager.merge(persisted));
            } else {
                entityManager.remove(entity);
            }
            txn.commit();
        } catch (PersistenceException e) {
            handleException(entity, e);
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    //TODO: move to ExceptionUtils
    private void handleException(final Object entity, final RuntimeException e) {
        logError(entity, e);
        ConstraintViolationException constrainException = findNestedException(e, ConstraintViolationException.class);
        TransientPropertyValueException transientException = findNestedException(e, TransientPropertyValueException.class);

        if (constrainException != null && constrainException.getMessage().contains(UNIQUE_CONSTRAINT_ERROR_MESSAGE)) {
            final String msg = format(UNIQUE_MESSAGE, entity.getClass().getSimpleName(), entity.toString());
            log.error(msg, e);
            throw new ResourceConflictException(msg, constrainException);
        } else if (transientException != null) {
            PropertyDescriptor missing = BeanUtils.getPropertyDescriptor(entity.getClass(), transientException.getPropertyName());
            final String msg = format(NOT_FOUND_MESSAGE,
                    transientException.getPropertyName(),
                    missing.getPropertyType().getName(),
                    entity.getClass().getName(),
                    entity.toString());
            log.error(msg, e);
            throw new ResourceNotFoundException(msg, transientException);
        } else if (constrainException != null) {
            throw constrainException;
        }
        throw e;
    }

    private void logError(final Object entity, final Exception e) {
        final String msg = format(ERROR_MESSAGE, entity.getClass().getSimpleName(), entity.toString());
        log.error(msg, e);
        log.error(e.getMessage());
    }

    public GenericFinder getFinder() {
        return finder;
    }
}
