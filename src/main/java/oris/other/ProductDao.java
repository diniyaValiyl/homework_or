package oris.other;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private static final String FIND_ALL = "select * from product";
    private static final String FIND_BY_ID = "select * from product where id = ?";

    public void createProductTable() throws SQLException {
        getStatement().executeUpdate("""
                create table if not exists product (
                id bigserial primary key,
                name varchar(255) not null,
                price decimal not null,
                description text);""");
    }

    public List<ProductEntity> findAll() {
        try (ResultSet rs = getStatement().executeQuery(FIND_ALL)) {
            List<ProductEntity> products = new ArrayList<>();
            while (rs.next()) {
                products.add(convertFromResultSet(rs));
            }
            return products;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public ProductEntity findById(Long id) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            return convertFromResultSet(rs);
        } catch (SQLException e) {
            return null;
        }
    }

    public void save(Product product) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(
                "insert into product (name, price, description) values (?, ?, ?)")) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private ProductEntity convertFromResultSet(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return ProductEntity.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .price(rs.getDouble("price"))
                    .description(rs.getString("description"))
                    .build();
        }
        return null;
    }

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL Driver not found", e);
        }
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/oris",
                "postgres", "Fadc766e!");
    }

    private Statement getStatement() {
        try {
            return getConnection().createStatement();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}