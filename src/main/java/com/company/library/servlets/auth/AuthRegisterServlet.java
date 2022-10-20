package com.company.library.servlets.auth;

import com.company.library.configs.ApplicationContextHolder;
import com.company.library.configs.PasswordConfigurer;
import com.company.library.dto.auth.UserCreateDTO;
import com.company.library.dto.auth.UserDTO;
import com.company.library.services.auth.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/register")
public class AuthRegisterServlet extends HttpServlet {

    private final UserServiceImpl userService = ApplicationContextHolder.getBean(UserServiceImpl.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("views/auth/register.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        UserCreateDTO userCreateDTO = UserCreateDTO.builder()
                .email(email)
                .password(password)
                .surname(surname)
                .name(name)
                .build();


        UserDTO userDTO = userService.create(userCreateDTO);
        Cookie cookie = new Cookie("session_user", userDTO.getEmail());
        resp.addCookie(cookie);
        resp.sendRedirect("/");
    }
}
