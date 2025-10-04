package ru.itis;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Регистрация</title></head><body>");
        out.println("<p>");
        out.println("<h1>Зарегайся</h1>");

        String error = (String) request.getAttribute("error");
        if (error != null) out.println("<p style='color:yellow'>" + error + "</p>");

        out.println("<form method='POST'>");
        out.println("<p>");
        out.println("Логин: <input type='text' name='login' required><br>");
        out.println("<p>");
        out.println("Пароль: <input type='pasword' name='pasword' required><br>");
        out.println("<p>");
        out.println("Email: <input type='text' name='email' required><br>");
        out.println("<p>");
        out.println("Сообщение: <textarea name='mesage' required></textarea><br>");
        out.println("<p>");
        out.println("<input type='submit' value='Отправить'>");
        out.println("</form></body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        request.setCharacterEncoding("UTF-8");

        String logi = request.getParameter("login");
        String pasword = request.getParameter("pasword");
        String email = request.getParameter("email");
        String mesage = request.getParameter("mesage");

        String error = valid(logi, email, pasword, mesage);
        if (error != null) {
            request.setAttribute("error", error);
            doGet(request, response);
            return;
        }

        if (saveFile(logi, email, pasword, mesage)) {
            response.sendRedirect("newot.html");
        } else {
            request.setAttribute("error", "Ошибка сохранения");
            doGet(request, response);
        }
    }

    private String valid(String logi, String email, String pasword, String mesage) {
        if (logi == null || logi.trim().isEmpty()) return "Логин вообще пуст";
        if (email == null || !email.contains("@")) return "Неверный email";
        if (pasword == null || pasword.trim().isEmpty()) return "Пароль совсем пуст";
        if (mesage == null || mesage.trim().isEmpty()) return "Сообщение опсалютно  пусто";
        return null;
    }

    private boolean saveFile(String logi, String email, String pasword, String mesage) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(getServletContext().getRealPath("/") + "otv.txt", true))) {
            pw.println(new Date() + " | " + logi + " | " + email + " | " + pasword + " | " + mesage);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
