package dao.implement;

import dao.connection.Connector;
import dao.intefaces.GenericDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.OrderStatus;

public class OrderStatusDao extends Connector implements GenericDao<OrderStatus> {

    @Override
    public List<OrderStatus> getAll() {
        List<OrderStatus> orderStatuses = new ArrayList<>();
        String query = "SELECT * FROM OrderStatus";

        try (Connection conn = getConnect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                OrderStatus orderStatus = new OrderStatus();
                orderStatus.setId(rs.getInt("OrderStatusID"));
                orderStatus.setFullName(rs.getString("FullName"));
                
                orderStatuses.add(orderStatus);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching order statuses: " + e.getMessage());
        }

        return orderStatuses;
    }

    @Override
    public OrderStatus getById(int id) {
        OrderStatus orderStatus = null;
        String query = "SELECT * FROM OrderStatus WHERE OrderStatusID = ?";

        try (Connection conn = getConnect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                orderStatus = new OrderStatus();
                orderStatus.setId(rs.getInt("OrderStatusID"));
                orderStatus.setFullName(rs.getString("FullName"));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching order status with ID " + id + ": " + e.getMessage());
        }

        return orderStatus;
    }

    @Override
    public void insert(OrderStatus orderStatus) {
        String query = "INSERT INTO OrderStatus (FullName) VALUES (?)";

        try (Connection conn = getConnect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, orderStatus.getFullName());
            stmt.executeUpdate();

            System.out.println("OrderStatus inserted successfully.");

        } catch (SQLException e) {
            System.out.println("Error inserting order status: " + e.getMessage());
        }
    }

    @Override
    public void update(OrderStatus orderStatus) {
        String query = "UPDATE OrderStatus SET FullName = ? WHERE OrderStatusID = ?";

        try (Connection conn = getConnect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, orderStatus.getFullName());
            stmt.setInt(2, orderStatus.getId());
            stmt.executeUpdate();

            System.out.println("OrderStatus updated successfully.");

        } catch (SQLException e) {
            System.out.println("Error updating order status with ID " + orderStatus.getId() + ": " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM OrderStatus WHERE OrderStatusID = ?";

        try (Connection conn = getConnect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("OrderStatus deleted successfully.");

        } catch (SQLException e) {
            System.out.println("Error deleting order status with ID " + id + ": " + e.getMessage());
        }
    }
}