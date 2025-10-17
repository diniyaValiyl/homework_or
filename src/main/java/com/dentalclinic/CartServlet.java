package com.dentalclinic;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.*;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");

        if (cart == null) {
            cart = new HashMap<>();
        }

        String courseId = request.getParameter("courseId");
        String action = request.getParameter("action");

        if ("add".equals(action) && courseId != null) {
            cart.put(courseId, cart.getOrDefault(courseId, 0) + 1);
            session.setAttribute("cart", cart);
            response.getWriter().print("{\"status\":\"added\"}");
        } else if ("remove".equals(action) && courseId != null) {
            cart.remove(courseId);
            session.setAttribute("cart", cart);
            response.getWriter().print("{\"status\":\"removed\"}");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");

        if (cart == null) {
            cart = new HashMap<>();
        }

        StringBuilder json = new StringBuilder("{");
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            json.append("\"").append(entry.getKey()).append("\":").append(entry.getValue()).append(",");
        }
        if (cart.size() > 0) {
            json.deleteCharAt(json.length() - 1);
        }
        json.append("}");

        response.getWriter().print(json.toString());
    }
}