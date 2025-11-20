package oris.servlets;

import oris.other.UserEntity;
import oris.other.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        this.userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/html/login_page.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            resp.sendRedirect("/login?error=Username and password are required");
            return;
        }

        UserEntity user = userService.authenticateUser(username, password);

        if (user == null) {
            resp.sendRedirect("/login?error=Invalid credentials");
        } else {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect("/index");
        }
    }
}