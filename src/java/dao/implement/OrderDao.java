package dao.implement;

import dao.connection.Connector;
import dao.intefaces.GenericDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Order;

public class OrderDao extends Connector implements GenericDao<Order> {

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM [Order]";

        try (Connection conn = getConnect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("OrderID"));
                order.setUserID(rs.getInt("UserID"));
                order.setOrderDate(rs.getDate("OrderDate"));
                order.setExpectedDate(rs.getDate("ExpectedDate"));
                order.setOrderStatusID(rs.getInt("OrderStatusID"));
                order.setTotalCost(rs.getDouble("TotalCost"));

                orders.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching orders: " + e.getMessage());
        }

        return orders;
    }

    @Override
    public Order getById(int id) {
        Order order = null;
        String query = "SELECT * FROM [Order] WHERE OrderID = ?";

        try (Connection conn = getConnect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                order = new Order();
                order.setId(rs.getInt("OrderID"));
                order.setUserID(rs.getInt("UserID"));
                order.setOrderDate(rs.getDate("OrderDate"));
                order.setExpectedDate(rs.getDate("ExpectedDate"));
                order.setOrderStatusID(rs.getInt("OrderStatusID"));
                order.setTotalCost(rs.getDouble("TotalCost"));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching order with ID " + id + ": " + e.getMessage());
        }

        return order;
    }

    @Override
    public void insert(Order order) {
        String query = "INSERT INTO [Order] (UserID, OrderDate, ExpectedDate, OrderStatusID, TotalCost) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, order.getUserID());
            stmt.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            stmt.setDate(3, new java.sql.Date(order.getExpectedDate().getTime()));
            stmt.setInt(4, order.getOrderStatusID());
            stmt.setDouble(5, order.getTotalCost());
            stmt.executeUpdate();

            System.out.println("Order inserted successfully.");

        } catch (SQLException e) {
            System.out.println("Error inserting order: " + e.getMessage());
        }
    }

    @Override
    public void update(Order order) {
        String query = "UPDATE [Order] SET UserID = ?, OrderDate = ?, ExpectedDate = ?, OrderStatusID = ?, TotalCost = ? WHERE OrderID = ?";

        try (Connection conn = getConnect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, order.getUserID());
            stmt.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            stmt.setDate(3, new java.sql.Date(order.getExpectedDate().getTime()));
            stmt.setInt(4, order.getOrderStatusID());
            stmt.setDouble(5, order.getTotalCost());
            stmt.setInt(6, order.getId());
            stmt.executeUpdate();

            System.out.println("Order updated successfully.");

        } catch (SQLException e) {
            System.out.println("Error updating order with ID " + order.getId() + ": " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM [Order] WHERE OrderID = ?";

        try (Connection conn = getConnect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Order deleted successfully.");

        } catch (SQLException e) {
            System.out.println("Error deleting order with ID " + id + ": " + e.getMessage());
        }
    }
}