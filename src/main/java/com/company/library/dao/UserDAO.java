package com.company.library.dao;

import com.company.library.domains.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.Optional;

/**
 * @author "Otajonov Dilshodbek
 * @since 7/13/22 12:21 PM (Wednesday)
 * libraryEE/IntelliJ IDEA
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDAO extends GenericDAO<User, Long> {

    private static UserDAO userDAO;

    public static UserDAO getInstance() {
        if (userDAO == null) {
            userDAO = new UserDAO();
        }
        return userDAO;
    }

    public Optional<User> findByEmail(String email) {
        Session session = getSession();
        session.beginTransaction();

        User result = session.createQuery("select t from User t where t.email=:email ", User.class)
                .setParameter("email", email)
                .getSingleResultOrNull();

        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(result);
    }
}
