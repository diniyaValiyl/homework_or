package oris.other;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ApplicationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        DataClass dataClass = new DataClass();
        UserToUserEntityConverter userConverter = new UserToUserEntityConverter();
        UserService userService = new UserServiceImpl(dataClass, userConverter);

        ProductDao productDao = new ProductDao();
        ProductService productService = new ProductServiceImpl(productDao);

        context.setAttribute("userService", userService);
        context.setAttribute("productService", productService);

        initializeDatabase(dataClass, productDao);
    }

    private void initializeDatabase(DataClass dataClass, ProductDao productDao) {
        try {
            dataClass.createUserTable();
            productDao.createProductTable();

            if (productDao.findAll().isEmpty()) {
                productDao.save(Product.builder()
                        .name("Курс ортодонтии")
                        .price(80000.0)
                        .description("Профессиональный курс по ортодонтии")
                        .build());

                productDao.save(Product.builder()
                        .name("Курс хирургии")
                        .price(150000.0)
                        .description("Продвинутый курс по хирургической стоматологии")
                        .build());

                productDao.save(Product.builder()
                        .name("Курс ортопедии")
                        .price(200000.0)
                        .description("Комплексный курс по ортопедической стоматологии")
                        .build());
            }
        } catch (Exception e) {
            System.out.println("Ошибка инициализации БД: " + e.getMessage());
        }
    }
}