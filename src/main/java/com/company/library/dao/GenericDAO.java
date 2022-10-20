package com.company.library.dao;

import com.company.library.configs.HibernateConfigurer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class GenericDAO<T, ID> implements BaseDAO {

    protected SessionFactory sessionFactory = HibernateConfigurer.getSessionFactory();

    private final Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public GenericDAO() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    public T save(T entity) {
        Session currentSession = getSession();
        currentSession.beginTransaction();
        currentSession.persist(entity);
        currentSession.getTransaction().commit();
        currentSession.close();
        return entity;
    }

    public void deleteById(ID id) throws SQLException {
        T entity = findById(id);
        if (Objects.isNull(entity)) {
            throw new SQLException("Object not found by given id '%s'".formatted(id));
        }

        Session session = getSession();
        session.beginTransaction();
        session.remove(entity);

        session.close();
    }

    public void update(T entity) {
        Session session = getSession();
        session.beginTransaction();
        session.merge(entity);
        session.getTransaction().commit();
        session.close();

    }

    public T findById(ID id) {
        Session session = getSession();
        session.beginTransaction();
        T t = session.get(persistentClass, id);
        session.close();
        return t;
    }

    public List<T> findAll() {
        Session session = getSession();
        session.beginTransaction();
        List<T> resultList = session.createQuery("from " + persistentClass.getSimpleName(), persistentClass)
                .getResultList();
        session.close();
        return resultList;
    }

    protected Session getSession() {
        if (Objects.isNull(sessionFactory) || sessionFactory.isClosed()) {
            sessionFactory = HibernateConfigurer.getSessionFactory();
        }
        return sessionFactory.getCurrentSession();
    }

}
