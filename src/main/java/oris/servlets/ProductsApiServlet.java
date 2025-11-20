package oris.servlets;

import oris.other.ProductEntity;
import oris.other.ProductService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/products")
public class ProductsApiServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() {
        this.productService = (ProductService) getServletContext().getAttribute("productService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<ProductEntity> products = productService.getAllProducts();
        String json = convertProductsToJson(products);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    private String convertProductsToJson(List<ProductEntity> products) {
        StringBuilder json = new StringBuilder("[");

        for (int i = 0; i < products.size(); i++) {
            ProductEntity product = products.get(i);
            json.append("{")
                    .append("\"id\":").append(product.getId()).append(",")
                    .append("\"name\":\"").append(escapeJson(product.getName())).append("\",")
                    .append("\"price\":").append(product.getPrice()).append(",")
                    .append("\"description\":\"").append(escapeJson(product.getDescription())).append("\"")
                    .append("}");

            if (i < products.size() - 1) {
                json.append(",");
            }
        }

        json.append("]");
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