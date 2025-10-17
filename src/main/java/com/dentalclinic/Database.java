package com.dentalclinic;

import java.sql.*;
import java.util.*;

public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/dental_clinic";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // CREATE
    public static void addCourse(Course course) throws SQLException {
        String sql = "INSERT INTO courses (name, price) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, course.getName());
            stmt.setInt(2, course.getPrice());
            stmt.executeUpdate();
        }
    }

    // READ - все курсы
    public static List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                courses.add(new Course(rs.getInt("id"), rs.getString("name"), rs.getInt("price")));
            }
        }
        return courses;
    }

    // READ - один курс
    public static Course getCourseById(int id) throws SQLException {
        String sql = "SELECT * FROM courses WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Course(rs.getInt("id"), rs.getString("name"), rs.getInt("price"));
            }
        }
        return null;
    }

    // UPDATE
    public static void updateCourse(Course course) throws SQLException {
        String sql = "UPDATE courses SET name = ?, price = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, course.getName());
            stmt.setInt(2, course.getPrice());
            stmt.setInt(3, course.getId());
            stmt.executeUpdate();
        }
    }

    // DELETE
    public static void deleteCourse(int id) throws SQLException {
        String sql = "DELETE FROM courses WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}