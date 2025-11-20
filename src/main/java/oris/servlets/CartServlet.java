package oris.servlets;

import oris.other.ProductEntity;
import oris.other.ProductService;

import javax.servlet.ServletException;
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

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() {
        this.productService = (ProductService) getServletContext().getAttribute("productService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");

        if (cart == null) {
            cart = new HashMap<>();
        }

        List<ProductEntity> cartProducts = new ArrayList<>();
        double total = 0;

        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            ProductEntity product = productService.getProductById(entry.getKey());
            if (product != null) {
                cartProducts.add(product);
                total += product.getPrice() * entry.getValue();
            }
        }

        req.setAttribute("cartProducts", cartProducts);
        req.setAttribute("cart", cart);
        req.setAttribute("total", total);
        req.setAttribute("pageTitle", "Корзина - Клиника Вильдан");

        req.getRequestDispatcher("/WEB-INF/jsp/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}