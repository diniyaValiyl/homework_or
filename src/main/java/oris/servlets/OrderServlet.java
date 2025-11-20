package oris.servlets;

import oris.other.ProductEntity;
import oris.other.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Map;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() {
        this.productService = (ProductService) getServletContext().getAttribute("productService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");

        if (cart != null && !cart.isEmpty()) {
            saveOrderToFile(cart);
            cart.clear();
            session.setAttribute("cart", cart);
            resp.sendRedirect("/products?order=success");
        } else {
            resp.sendRedirect("/cart?error=empty");
        }
    }

    private void saveOrderToFile(Map<Long, Integer> cart) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("orders.txt", true))) {
            writer.println("Заказ от: " + LocalDateTime.now());
            double total = 0;

            for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
                ProductEntity product = productService.getProductById(entry.getKey());
                if (product != null) {
                    double itemTotal = product.getPrice() * entry.getValue();
                    total += itemTotal;
                    writer.println(product.getName() + " x " + entry.getValue() + " = " + itemTotal + " руб.");
                }
            }

            writer.println("Итого: " + total + " руб.");
            writer.println("---");
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}