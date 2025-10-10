package ru;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.Date;

@WebServlet("/")
public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Регистрация</title></head><body>");
        out.println("<h1>Зарегайся</h1>");

        String error = (String) request.getAttribute("error");
        if (error != null) out.println("<p style='color:red'>" + error + "</p>");

        String success = (String) request.getAttribute("success");
        if (success != null) out.println("<p style='color:green'>" + success + "</p>");

        out.println("<form method='POST'>");
        out.println("<p>Логин: <input type='text' name='login' required></p>");
        out.println("<p>Пароль: <input type='password' name='password' required></p>");
        out.println("<p>Email: <input type='text' name='email' required></p>");
        out.println("<p>Сообщение: <textarea name='message' required></textarea></p>");
        out.println("<p><input type='submit' value='Отправить'></p>");
        out.println("</form></body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String message = request.getParameter("message");

        String error = valid(login, email, password, message);
        if (error != null) {
            request.setAttribute("error", error);
            doGet(request, response);
            return;
        }

        if (saveFile(login, email, password, message)) {
            request.setAttribute("success", "Регистрация прошла успешно! Данные сохранены.");
            doGet(request, response);
        } else {
            request.setAttribute("error", "Ошибка сохранения данных");
            doGet(request, response);
        }
    }

    private String valid(String login, String email, String password, String message) {
        if (login == null || login.trim().isEmpty()) return "Логин вообще пуст";
        if (email == null || !email.contains("@")) return "Неверный email";
        if (password == null || password.trim().isEmpty()) return "Пароль совсем пуст";
        if (message == null || message.trim().isEmpty()) return "Сообщение абсолютно пусто";
        return null;
    }

    private boolean saveFile(String login, String email, String password, String message) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(getServletContext().getRealPath("/") + "otv.txt", true))) {
            pw.println(new Date() + " | " + login + " | " + email + " | " + password + " | " + message);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}