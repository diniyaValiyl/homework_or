package com.dentalclinic;

import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/courses")
public class CourseServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String idParam = request.getParameter("id");
            if (idParam != null) {
                // Получить один курс
                Course course = Database.getCourseById(Integer.parseInt(idParam));
                if (course != null) {
                    response.getWriter().print("{\"id\":" + course.getId() +
                            ",\"name\":\"" + course.getName() +
                            "\",\"price\":" + course.getPrice() + "}");
                }
            } else {
                // Получить все курсы
                List<Course> courses = Database.getAllCourses();
                StringBuilder json = new StringBuilder("[");
                for (Course course : courses) {
                    json.append("{\"id\":").append(course.getId())
                            .append(",\"name\":\"").append(course.getName())
                            .append("\",\"price\":").append(course.getPrice())
                            .append("},");
                }
                if (courses.size() > 0) {
                    json.deleteCharAt(json.length() - 1);
                }
                json.append("]");
                response.getWriter().print(json.toString());
            }
        } catch (Exception e) {
            response.getWriter().print("{\"status\":\"error\"}");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");

        try {
            int price = Integer.parseInt(priceStr);
            Course course = new Course(0, name, price);
            Database.addCourse(course);
            response.getWriter().print("{\"status\":\"created\"}");
        } catch (Exception e) {
            response.getWriter().print("{\"status\":\"error\"}");
        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String priceStr = request.getParameter("price");

        try {
            Course course = Database.getCourseById(Integer.parseInt(id));
            if (course != null) {
                if (name != null) course.setName(name);
                if (priceStr != null) course.setPrice(Integer.parseInt(priceStr));
                Database.updateCourse(course);
                response.getWriter().print("{\"status\":\"updated\"}");
            }
        } catch (Exception e) {
            response.getWriter().print("{\"status\":\"error\"}");
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");

        try {
            Database.deleteCourse(Integer.parseInt(id));
            response.getWriter().print("{\"status\":\"deleted\"}");
        } catch (Exception e) {
            response.getWriter().print("{\"status\":\"error\"}");
        }
    }
}