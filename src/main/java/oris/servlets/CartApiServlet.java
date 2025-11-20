package oris.servlets;

import oris.other.ProductEntity;
import oris.other.ProductService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/api/cart")
public class CartApiServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() {
        this.productService = (ProductService) getServletContext().getAttribute("productService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");

        if (cart == null) {
            cart = new HashMap<>();
        }

        List<Map<String, Object>> cartData = new ArrayList<>();
        double total = 0;

        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            ProductEntity product = productService.getProductById(entry.getKey());
            if (product != null) {
                Map<String, Object> item = new HashMap<>();
                item.put("product", product);
                item.put("quantity", entry.getValue());
                item.put("itemTotal", product.getPrice() * entry.getValue());
                cartData.add(item);
                total += product.getPrice() * entry.getValue();
            }
        }

        String json = convertCartToJson(cartData, total);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        Long productId = Long.parseLong(req.getParameter("productId"));

        HttpSession session = req.getSession();
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");

        if (cart == null) {
            cart = new HashMap<>();
        }

        if ("add".equals(action)) {
            cart.put(productId, cart.getOrDefault(productId, 0) + 1);
        } else if ("remove".equals(action)) {
            cart.remove(productId);
        }

        session.setAttribute("cart", cart);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private String convertCartToJson(List<Map<String, Object>> cartData, double total) {
        StringBuilder json = new StringBuilder("{");
        json.append("\"items\":[");

        for (int i = 0; i < cartData.size(); i++) {
            Map<String, Object> item = cartData.get(i);
            ProductEntity product = (ProductEntity) item.get("product");
            int quantity = (int) item.get("quantity");
            double itemTotal = (double) item.get("itemTotal");

            json.append("{")
                    .append("\"product\":{")
                    .append("\"id\":").append(product.getId()).append(",")
                    .append("\"name\":\"").append(escapeJson(product.getName())).append("\",")
                    .append("\"price\":").append(product.getPrice()).append(",")
                    .append("\"description\":\"").append(escapeJson(product.getDescription())).append("\"")
                    .append("},")
                    .append("\"quantity\":").append(quantity).append(",")
                    .append("\"itemTotal\":").append(itemTotal)
                    .append("}");

            if (i < cartData.size() - 1) {
                json.append(",");
            }
        }

        json.append("],\"total\":").append(total).append("}");
        return json.toString();
    }

    private String escapeJson(String text) {
        if (text == null) return "";
        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}