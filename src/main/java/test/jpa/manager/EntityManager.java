package test.jpa.manager;

import test.jpa.repository.Repository;

import javax.transaction.Transactional;
import java.util.List;

public abstract class EntityManager<T> {

    @Transactional
    public void create(T entity) {
        getRepository().create(entity);
    }

    @Transactional
    public T read(long id) {
        return getRepository().read(id);
    }

    @Transactional
    public T update(T entity) {
        return getRepository().update(entity);
    }

    @Transactional
    public void delete(T entity) {
        getRepository().delete(entity);
    }

    @Transactional
    public List<T> list() {
        return getRepository().getAll();
    }

    protected abstract Repository<T> getRepository();
}
