package com.shop;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Map<String, Integer> cart = (Map<String, Integer>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            response.getWriter().print("CART_EMPTY");
            return;
        }

        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String filename = "order_" + timestamp + ".txt";
        String filePath = getServletContext().getRealPath("/") + filename;

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("Заказ от: " + new Date());
            writer.println("Товары:");
            for (Map.Entry<String, Integer> entry : cart.entrySet()) {
                writer.println("ID: " + entry.getKey() + ", Количество: " + entry.getValue());
            }
        }

        cart.clear();
        session.setAttribute("cart", cart);

        response.getWriter().print("ORDER_SAVED");
    }
}