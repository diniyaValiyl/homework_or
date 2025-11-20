package oris.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello")
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();

        Integer visitCount = (Integer) session.getAttribute("visitCount");
        if (visitCount == null) {
            visitCount = 1;
        } else {
            visitCount++;
        }
        session.setAttribute("visitCount", visitCount);

        String email;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("email".equals(cookie.getName())) {
                    email = cookie.getValue();
                    System.out.println(email);
                }
            }
        }

        out.println("<h1>Привет, это мой первый сервлет!</h1>");
        out.println("<p>Текущее время: " + new java.util.Date() + "</p>");
        out.println("<p>Количество запросов из этой сессии: " + session.getAttribute("visitCount") + "</p>");
        out.println("<form method=\"post\" action=\"/hello\">");
        out.println("<input id=\"email\" name=\"email\" type=\"email\">");
        out.println("<input type=\"submit\">");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        System.out.println(email);
        response.sendRedirect("hello");
    }
}