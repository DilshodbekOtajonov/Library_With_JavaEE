package com.company.library.servlets.forFiles;

import com.company.library.configs.ApplicationContextHolder;
import com.company.library.domains.Book;
import com.company.library.dto.BookCreateDTO;
import com.company.library.dto.UploadsDTO;
import com.company.library.enums.Language;
import com.company.library.services.book.BookServiceImpl;
import com.company.library.services.file.FileStorageServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.*;

/**
 * @author "Otajonov Dilshodbek
 * @since 7/14/22 7:49 AM (Thursday)
 * libraryEE/IntelliJ IDEA
 */

@WebServlet("/uploads/*")
@MultipartConfig
public class FileStorageServlet extends HttpServlet {
    private final BookServiceImpl bookService = ApplicationContextHolder.getBean(BookServiceImpl.class);
    private final FileStorageServiceImpl fileStorageService = ApplicationContextHolder.getBean(FileStorageServiceImpl.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String author = req.getParameter("author");
        String description = req.getParameter("description");
        Book.Genre genre = Enum.valueOf(Book.Genre.class, req.getParameter("genre"));
        Language language = Enum.valueOf(Language.class, req.getParameter("language"));
        Integer pageCount = Integer.parseInt(req.getParameter("pageCount"));
        BookCreateDTO bookCreateDTO = BookCreateDTO.builder()
                .name(name)
                .author(author)
                .genre(genre)
                .language(language)
                .pageCount(pageCount)
                .description(description)
                .build();

        Part file = req.getPart("file");
        bookService.create(bookCreateDTO, file);
        resp.sendRedirect("/");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestedFile = req.getParameter("path");
        UploadsDTO uploadsDTO = fileStorageService.getByPath(requestedFile);
        CompletableFuture.runAsync(() -> {
            if (uploadsDTO.getContentType().equals("application/pdf")) {
                bookService.updateDownloadCount(uploadsDTO.getId());
            }
        });
        resp.setContentType(uploadsDTO.getContentType());
        resp.setHeader("Content-disposition", "attachment; filename=" + uploadsDTO.getOriginalName());
        Path path = Paths.get("/home/dilshodbek/Uploads", uploadsDTO.getPath());
        ServletOutputStream outputStream = resp.getOutputStream();
        Files.copy(path, outputStream);
    }


}
