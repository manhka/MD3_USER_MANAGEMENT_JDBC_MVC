package user_management.com.controller;

import user_management.com.dao.IUserDAO;
import user_management.com.dao.UserDAOImpl;
import user_management.com.model.User;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/users")
public class UserServlet extends HttpServlet {
    IUserDAO userDAO = new UserDAOImpl();
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "sort":
                try {
                    sortByName(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "search":
                try {
                    showListUser(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "create":
                createForm(request, response);
                break;
            case "edit":
                editForm(request, response);
                break;
            case "delete":
                try {
                    deleteUser(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                try {
                    showListUser(request, response);
                } catch (ServletException | SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    private void sortByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException, IOException {
        List<User> users = new ArrayList<>();
        users = userDAO.sortByName();
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("user/list.jsp");
        request.setAttribute("userList", users);
        requestDispatcher.forward(request, response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userDAO.deleteById(id);
        showListUser(request, response);
    }

    private void editForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("user/update.jsp");
        requestDispatcher.forward(request, response);
    }

    private void createForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("user/create.jsp");
        requestDispatcher.forward(request, response);
    }

    private void showListUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String country = request.getParameter("country");
        List<User> users = new ArrayList<>();
        if (country != null && country != "") {
            users = userDAO.searchByCountry(country);
        } else {
            users = userDAO.findAll();
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("user/list.jsp");
        request.setAttribute("userList", users);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                try {
                    createNewUser(req, resp);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "edit":
                try {
                    editUser(req, resp);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void editUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, SQLException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String country = req.getParameter("country");
        int age = Integer.parseInt(req.getParameter("age"));
        userDAO.update(new User(id, name, country, age));
        resp.sendRedirect("users");
    }

    private void createNewUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String name = req.getParameter("name");
        String country = req.getParameter("country");
        int age = Integer.parseInt(req.getParameter("age"));
        userDAO.create(new User(name, country, age));
        showListUser(req, resp);
    }

    public void destroy() {
    }
}