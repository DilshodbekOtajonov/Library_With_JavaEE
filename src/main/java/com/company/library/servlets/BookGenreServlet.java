package com.company.library.servlets;

import com.company.library.configs.ApplicationContextHolder;
import com.company.library.domains.Book;
import com.company.library.domains.PageInfo;
import com.company.library.enums.Language;
import com.company.library.exceptions.NotFoundException;
import com.company.library.services.auth.UserServiceImpl;
import com.company.library.services.book.BookServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author "Otajonov Dilshodbek
 * @since 7/16/22 3:26 PM (Saturday)
 * libraryEE/IntelliJ IDEA
 */

@WebServlet("/genre/*")
public class BookGenreServlet extends HttpServlet {
    BookServiceImpl bookService = ApplicationContextHolder.getBean(BookServiceImpl.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String[] pathVariables = req.getPathInfo().split("/");
        String genreKey = pathVariables[pathVariables.length - 1];
        Book.Genre searchGenre = Arrays.stream(Book.Genre.values())
                .filter(item -> item.getKey().equals(genreKey))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Genre not found"));


        Integer page = Integer.parseInt(Optional.ofNullable(req.getParameter("page")).orElse("1"));
        Integer limit = Integer.parseInt(Optional.ofNullable(req.getParameter("limit")).orElse("12"));
        String search = Optional.ofNullable(req.getParameter("search")).orElse("");

        req.setAttribute("books", bookService.getAllByGenre(searchGenre, page, limit));
        req.setAttribute("pageInfo", PageInfo.builder()
                .hasPrevious(bookService.hasPrevious(page))
                .hasNext(bookService.hasNext(searchGenre, page, limit))
                .number(page)
                .totalPages(bookService.totalPage(searchGenre, limit))
                .build());
        req.setAttribute("search", search);
        req.setAttribute("genres", Book.Genre.values());
        req.setAttribute("languages", Language.values());


        RequestDispatcher dispatcher = req.getRequestDispatcher("/views/BookListByGenre.jsp");
        dispatcher.forward(req, resp);

    }
}
