package oris.other;

import java.sql.*;

public class DataClass {
    private static final String GET_USER_BY_CREDS = "select * from usr where username = ? and hash_password = ?";
    private static final String SAVE_NEW_USER = "insert into usr (username, hash_password) values (?, ?)";

    public void createUserTable() throws SQLException {
        getStatement().executeUpdate("""
                create table if not exists usr (
                id bigserial primary key,
                username varchar(255) not null unique,
                hash_password varchar(255) not null);""");
    }

    public UserEntity getUser(String username, String password) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(GET_USER_BY_CREDS)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, HashUtil.hashPassword(password));
            return convertFromResultSet(preparedStatement.executeQuery());
        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }
    }

    public void saveNewUser(UserEntity entity) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(SAVE_NEW_USER)) {
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getHashPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    private Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/oris",
                "postgres", "Fadc766e!");
    }

    private UserEntity convertFromResultSet(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return UserEntity.builder()
                    .id(rs.getLong("id"))
                    .username(rs.getString("username"))
                    .hashPassword(rs.getString("hash_password"))
                    .build();
        }
        return null;
    }

    private Statement getStatement() {
        try {
            return getConnection().createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
}