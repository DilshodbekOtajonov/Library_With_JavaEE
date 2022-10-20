package com.company.library.servlets;

import com.company.library.configs.ApplicationContextHolder;
import com.company.library.domains.Book;
import com.company.library.domains.PageInfo;
import com.company.library.enums.Language;
import com.company.library.services.auth.UserServiceImpl;
import com.company.library.services.book.BookServiceImpl;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.Optional;


@WebServlet("")
public class HomeServlet extends HttpServlet {
    BookServiceImpl bookService = ApplicationContextHolder.getBean(BookServiceImpl.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = Integer.parseInt(Optional.ofNullable(req.getParameter("page")).orElse("1"));
        int limit = Integer.parseInt(Optional.ofNullable(req.getParameter("limit")).orElse("12"));
        String search = Optional.ofNullable(req.getParameter("search")).orElse("");

        req.setAttribute("books", bookService.getAll(search, page, limit));
        req.setAttribute("pageInfo", PageInfo.builder()
                .hasPrevious(bookService.hasPrevious(page))
                .hasNext(bookService.hasNext(search, page, limit))
                .number(page)
                .totalPages(bookService.totalPage(search, limit))
                .build());
        req.setAttribute("search", search);
        req.setAttribute("genres", Book.Genre.values());
        req.setAttribute("languages", Language.values());
        RequestDispatcher dispatcher = req.getRequestDispatcher("views/main.jsp");
        dispatcher.forward(req, resp);

    }
}