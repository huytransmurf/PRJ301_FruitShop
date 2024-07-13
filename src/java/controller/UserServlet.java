package controller;

import dao.implement.UserDao;
import model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = {"/UserServlet"})
public class UserServlet extends HttpServlet {

    private static final int PAGE_SIZE = 10; // Số lượng người dùng mỗi trang

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "list-users-admin":
                getListUsers(request, response);
                break;
            case "detail":
                getUserById(request, response);
                break;
            default:
                // Redirect to a default action or error page
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "add":
                createNewUser(request, response);
                break;
            case "delete":
                deleteUserById(request, response);
                break;
            case "update":
                updateUserById(request, response);
                break;
            default:
                // Redirect to a default action or error page
                break;
        }
    }

    private void createNewUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String address = request.getParameter("address");
        String role = request.getParameter("role");
        String avatarURL = request.getParameter("avatarURL");

        User newUser = new User(0, firstName, lastName, address, role, avatarURL);

        UserDao userDao = new UserDao();
        userDao.insert(newUser);

        response.sendRedirect("UserServlet?action=list-users-admin");
    }

    private void deleteUserById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String userIdToDelete = request.getParameter("UserId");
            int userId = Integer.parseInt(userIdToDelete);
            UserDao userDao = new UserDao();
            userDao.delete(userId);
            response.sendRedirect("UserServlet?action=list");
        } catch (NumberFormatException e) {
            // Xử lý lỗi số liệu không hợp lệ
            response.sendRedirect("error.jsp");
        }
    }

    private void updateUserById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String address = request.getParameter("address");
        String role = request.getParameter("role");
        String avatarURL = request.getParameter("avatarURL");

        User user = new User(id, firstName, lastName, address, role, avatarURL);
        UserDao userDao = new UserDao();

        userDao.update(user);

        response.sendRedirect("UserServlet?action=list-users-admin");
    }

    private void getListUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao userDao = new UserDao();
        int totalUsers = userDao.getTotalUserCount();

        int currentPage = 1;
        try {
            currentPage = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
            // Default to page 1 if invalid or missing
        }

        int offset = (currentPage - 1) * PAGE_SIZE;
        List<User> users = userDao.getPaginatedUsers(offset, PAGE_SIZE);

        int totalPages = (int) Math.ceil((double) totalUsers / PAGE_SIZE);

            request.setAttribute("users", users);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/admin/user/list-user.jsp");
        dispatcher.forward(request, response);
    }

    private void getUserById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        UserDao userDao = new UserDao();
        User user = userDao.getById(id);

        request.setAttribute("user", user);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user_detail.jsp");
        dispatcher.forward(request, response);
    }
}
