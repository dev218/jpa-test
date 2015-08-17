package test.jpa.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public abstract class Repository<T> {

    private final Class<T> entityClass;

    @PersistenceContext
    EntityManager entityManager;

    public Repository() {
        this.entityClass = getEntityClass();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public abstract Class<T> getEntityClass();

    public void create(T entity) {
        entityManager.persist(entity);
    }

    public T read(long id) {
        return entityManager.find(entityClass, id);
    }

    public T update(T entity) {
        return entityManager.merge(entity);
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }

    public List<T> getAll() {
        Query query = entityManager.createQuery("select e from " + entityClass.getSimpleName() + " e order by e.id");
        List resultList = query.getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            return resultList;
        } else {
            return new ArrayList<>();
        }
    }
}
