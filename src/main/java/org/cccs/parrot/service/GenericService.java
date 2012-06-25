package org.cccs.parrot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import static java.lang.String.format;

/**
 * User: boycook
 * Date: 23/05/2012
 * Time: 15:07
 */
@Transactional(readOnly = true)
public class GenericService {
    //TODO: get the Transactional stuff to work automatically

    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final String UNIQUE_MESSAGE = "[%s] [%s] already exists";
    private static final String NOT_FOUND_MESSAGE = "Cannot find related entity to persist [%s] as [%s]";
    private static final String CREATE_MESSAGE = "Creating [%s] as [%s]";
    private static final String UPDATE_MESSAGE = "Updating [%s] as [%s]";
    private static final String ERROR_MESSAGE = "Error persisting [%s] as [%s]";
    private EntityManagerFactory entityManagerFactory;

    public GenericService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void create(final Object entity) {
//        validate(entity);
        log.debug(format(CREATE_MESSAGE, entity.getClass().getSimpleName(), entity.toString()));
        EntityManager entityManager = getEntityManager();
        EntityTransaction txn = entityManager.getTransaction();
        try {
            txn.begin();
            entityManager.persist(entity);
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
    public void update(final Object entity) {
//        validate(entity);
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
//        validate(entity);
        log.debug(format(UPDATE_MESSAGE, entity.getClass().getSimpleName(), entity.toString()));
        EntityManager entityManager = getEntityManager();
        EntityTransaction txn = entityManager.getTransaction();
        try {
            txn.begin();
            entityManager.remove(entity);
            txn.commit();
        } catch (PersistenceException e) {
            handleException(entity, e);
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }

    protected EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    private void handleException(final Object entity, final RuntimeException e) {
        logError(entity, e);
//        Throwable exception = findNestedException(e, SQLIntegrityConstraintViolationException.class);
//        if (exception != null) {
//            Table table = entity.getClass().getAnnotation(Table.class);
//            if (table != null) {
//                for (UniqueConstraint constraint : table.uniqueConstraints()) {
//                    if (isNotEmpty(constraint.name()) && exception.getMessage().contains(constraint.name())) {
//                        final String msg = format(UNIQUE_MESSAGE, entity.getClass().getSimpleName(), entity.toString());
//                        log.error(msg, e);
//                        throw new ResourceConflictException(msg, exception);
//                    }
//                }
//            }
//            for (Method method : entity.getClass().getMethods()) {
//                ForeignKey foreignKey = method.getAnnotation(ForeignKey.class);
//                if (foreignKey != null && isNotEmpty(foreignKey.name()) && exception.getMessage().contains(foreignKey.name())) {
//                    final String msg = format(NOT_FOUND_MESSAGE, entity.getClass().getSimpleName(), entity.toString());
//                    log.error(msg, e);
//                    throw new EntityNotFoundException(msg);
//                }
//            }
//        }
        throw e;
    }

    private void logError(final Object entity, final Exception e) {
        final String msg = format(ERROR_MESSAGE, entity.getClass().getSimpleName(), entity.toString());
        log.error(msg, e);
        log.error(e.getMessage());
    }
}
