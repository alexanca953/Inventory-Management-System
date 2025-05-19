package DataAccess;

import Model.Bill;
import Connection.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * <p>Data Access Object (DAO) for Bill records.</p>
 * <p>Handles database operations related to bill insertion and retrieval.</p>
 */
public class BillDao {
    /**
     * <p>Inserts a new bill record into the database.</p>
     *
     * @param bill the Bill object to insert into the log table
     */
 public void insertBill(Bill bill) {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO log (id,orderId, clientName, totalPrice, date) VALUES (0,?, ?, ?, ?)")) {
            ps.setInt(1,bill.orderId() );
            ps.setString(2, bill.clientName());
            ps.setInt(3, bill.price());
            ps.setString(4, bill.date());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * <p>Retrieves all bill records from the database.</p>
     *
     * @return a list of all Bill objects found in the log table
     */
 public List<Bill> findAll() {
        List<Bill> bills = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM log")) {
            while (rs.next()) {
                bills.add(new Bill(
                        rs.getInt("id"),
                        rs.getInt("orderId"),
                        rs.getString("clientName"),
                        rs.getString("products"),
                        rs.getInt("price"),
                        rs.getString("date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }
}
