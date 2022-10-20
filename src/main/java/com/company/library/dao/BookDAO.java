package com.company.library.dao;

import com.company.library.domains.Book;
import com.company.library.enums.BookStatus;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

/**
 * @author "Otajonov Dilshodbek
 * @since 7/13/22 12:26 PM (Wednesday)
 * libraryEE/IntelliJ IDEA
 */
public class BookDAO extends GenericDAO<Book, Long> {
    private static BookDAO bookDAO;

    public static BookDAO getInstance() {
        if (bookDAO == null) {
            bookDAO = new BookDAO();
        }
        return bookDAO;
    }

    public Optional<List<Book>> findAll(String searchQuery, Integer page, Integer limit) {
        Session session = getSession();
        session.beginTransaction();
        Integer skip = (page - 1) * limit;

//        List<Book> result = session.createQuery("select t from Book t where lower(t.name) like lower (:searchQuery) or lower(t.author) like lower(:searchQuery) ", Book.class)
//                .setParameter("searchQuery", searchQuery)
//                .setFirstResult(skip)
//                .setMaxResults(limit)
//                .getResultList();
        List<Book> resultList = session.createNativeQuery("select t.* from book t  where t.status='ACTIVE' and (t.name ilike '%'||:searchQuery||'%' or t.author ilike '%'||:searchQuery||'%') order by t.id desc offset :skip limit :limit", Book.class)
                .setParameter("searchQuery", searchQuery)
                .setParameter("skip", skip)
                .setParameter("limit", limit)
                .getResultList();
        session.getTransaction().commit();
        session.close();

        return Optional.ofNullable(resultList);
    }

    public Optional<List<Book>> findAll(Book.Genre searchQuery, Integer page, Integer limit) {
        Session session = getSession();
        session.beginTransaction();
        Integer skip = (page - 1) * limit;

        List<Book> resultList = session.createQuery("select t from Book t  where t.status=com.company.library.enums.BookStatus.ACTIVE and t.genre=:genre order by t.id desc", Book.class)
                .setParameter("genre", searchQuery)
                .setFirstResult(skip)
                .setMaxResults(limit)
                .getResultList();
        session.getTransaction().commit();
        session.close();

        return Optional.ofNullable(resultList);
    }

    public Integer findNumberOfElementBySearch(String search) {
        Session session = getSession();
        session.beginTransaction();
        List<Book> resultList = session.createNativeQuery("select t.* from book t where t.status='ACTIVE' and (t.name ilike '%'||:searchQuery||'%' or t.author ilike '%'||:searchQuery||'%')", Book.class)
                .setParameter("searchQuery", search)
                .getResultList();
        session.getTransaction().commit();
        session.close();

        return resultList.size();
    }

    public List<Book> findAllByStatus(BookStatus pending) {
        Session session = getSession();
        session.beginTransaction();

        List<Book> resultList = session.createQuery("select t from Book t where t.status=com.company.library.enums.BookStatus.PENDING", Book.class).getResultList();

        session.getTransaction().commit();
        session.close();
        return resultList;
    }

    public Integer findNumberOfElementByGenre(Book.Genre genre) {
        Session session = getSession();
        session.beginTransaction();
        List<Book> resultList = session.createQuery("select t from Book t where t.status=com.company.library.enums.BookStatus.ACTIVE and t.genre=:genre", Book.class)
                .setParameter("genre", genre)
                .getResultList();
        session.getTransaction().commit();
        session.close();

        return resultList.size();
    }

    public Book findByUploadFileId(Long uploadFileId) {
        Session session = getSession();
        session.beginTransaction();
        Book book = session.createQuery("select t from Book t where t.file.id=:uploadFileId", Book.class)
                .setParameter("uploadFileId", uploadFileId)
                .getSingleResultOrNull();
        session.getTransaction().commit();
        session.close();
        return book;
    }
}
