package com.company.library.servlets.auth;


import com.company.library.configs.ApplicationContextHolder;
import com.company.library.dto.auth.UserCreateDTO;
import com.company.library.dto.auth.UserDTO;
import com.company.library.dto.auth.UserLoginDTO;
import com.company.library.services.auth.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet("/login")
public class AuthLoginServlet extends HttpServlet {
    private final UserServiceImpl userService = ApplicationContextHolder.getBean(UserServiceImpl.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("views/auth/login.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        UserLoginDTO userLoginDTO = new UserLoginDTO(email, password);
        UserDTO user = userService.login(userLoginDTO);
        Cookie cookie=new Cookie("session_user",user.getEmail());
        cookie.setMaxAge(900);
        resp.addCookie(cookie);
        resp.sendRedirect("/");
    }
}
