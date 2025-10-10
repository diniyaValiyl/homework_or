package com.shop;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.*;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (cart == null) {
            response.getWriter().print("{}");
        } else {
            response.getWriter().print(cart.toString().replace("=", ":"));
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productId = request.getParameter("id");
        String action = request.getParameter("action");

        HttpSession session = request.getSession();
        Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");

        if (cart == null) {
            cart = new HashMap<>();
        }

        if ("add".equals(action) && productId != null) {
            cart.put(productId, cart.getOrDefault(productId, 0) + 1);
        } else if ("remove".equals(action) && productId != null) {
            cart.remove(productId);
        } else if ("clear".equals(action)) {
            cart.clear();
        }

        session.setAttribute("cart", cart);
        response.getWriter().print("OK");
    }
}