package org.example.servlets;

import org.example.domain.UserProfile;
import org.example.services.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("login")
public class LoginServlet extends HttpServlet {

    private AccountService service = AccountService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserProfile user = service.getUserByLogin(req.getParameter("login"));
        if(user == null) {
            resp.getWriter().println("User not found");
            return;
        }
        if (!user.getPass().equals(req.getParameter("password"))) {
            resp.getWriter().println("Incorrect password");
            return;
        }

        String sessionId = req.getSession().getId();
        service.addSession(sessionId, user);
        resp.sendRedirect("./");
    }
}
