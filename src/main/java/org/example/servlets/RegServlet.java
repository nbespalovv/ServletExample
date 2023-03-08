package org.example.servlets;

import org.example.domain.UserProfile;
import org.example.services.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("signup")
public class RegServlet extends HttpServlet {

    private AccountService service = AccountService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserProfile user = service.getUserByLogin(req.getParameter("login"));

        if (user != null) {
            resp.getWriter().println("User with such login already exists");
            return;
        }

        user = new UserProfile(req.getParameter("login"),
                req.getParameter("password"),
                req.getParameter("email"));

        service.addNewUser(user);
        resp.sendRedirect("login");
    }
}
