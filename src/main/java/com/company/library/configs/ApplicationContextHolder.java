package com.company.library.configs;


import com.company.library.dao.BookDAO;
import com.company.library.dao.UploadDAO;
import com.company.library.dao.UserDAO;
import com.company.library.services.auth.UserServiceImpl;
import com.company.library.services.book.BookServiceImpl;
import com.company.library.services.file.FileStorageServiceImpl;
import com.company.library.utils.BaseUtils;

public class ApplicationContextHolder {

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        return switch (beanName) {
            case "BaseUtils" -> (T) BaseUtils.getInstance();
            case "UserServiceImpl" -> (T) UserServiceImpl.getInstance();
            case "BookServiceImpl" -> (T) BookServiceImpl.getInstance();
            case "FileStorageServiceImpl" -> (T) FileStorageServiceImpl.getInstance();
            case "UserDAO" -> (T) UserDAO.getInstance();
            case "BookDAO" -> (T) BookDAO.getInstance();
            case "UploadDAO" -> (T) UploadDAO.getInstance();
            default -> throw new RuntimeException("Bean Not Found %s".formatted(beanName));
        };
    }

    public static <T> T getBean(Class<T> clazz) {
        String beanName = clazz.getSimpleName();
        return getBean(beanName);

    }

}
