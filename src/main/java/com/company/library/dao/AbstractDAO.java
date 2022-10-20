package com.company.library.dao;

/**
 * @author "Otajonov Dilshodbek
 * @since 7/13/22 12:19 PM (Wednesday)
 * libraryEE/IntelliJ IDEA
 */
public class AbstractDAO<D extends BaseDAO> {
    protected final D dao;

    public AbstractDAO(D dao) {
        this.dao = dao;
    }
}
