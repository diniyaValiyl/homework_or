package oris.servlets;

import oris.other.ProductEntity;
import oris.other.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ProductsServlet extends HttpServlet {
    private ProductService productService;
    private static final int DEFAULT_PAGE_SIZE = 3;

    @Override
    public void init() {
        this.productService = (ProductService) getServletContext().getAttribute("productService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = getIntParameter(req, "page", 1);
        int size = getIntParameter(req, "size", DEFAULT_PAGE_SIZE);

        List<ProductEntity> allProducts = productService.getAllProducts();

        int totalProducts = allProducts.size();
        int totalPages = (int) Math.ceil((double) totalProducts / size);
        int startIndex = (page - 1) * size;
        int endIndex = Math.min(startIndex + size, totalProducts);

        List<ProductEntity> pageProducts = allProducts.subList(startIndex, endIndex);

        req.setAttribute("products", pageProducts);
        req.setAttribute("currentPage", page);
        req.setAttribute("pageSize", size);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("totalProducts", totalProducts);
        req.setAttribute("pageTitle", "Курсы стоматологии - Клиника Вильдан");

        req.getRequestDispatcher("/WEB-INF/jsp/products.jsp").forward(req, resp);
    }

    private int getIntParameter(HttpServletRequest req, String paramName, int defaultValue) {
        String paramValue = req.getParameter(paramName);
        if (paramValue != null && !paramValue.trim().isEmpty()) {
            try {
                return Integer.parseInt(paramValue);
            } catch (NumberFormatException e) {
                // Используем значение по умолчанию
            }
        }
        return defaultValue;
    }
}