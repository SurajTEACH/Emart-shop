
package emart.dao;

import emart.dbutil.DBConnection;
import emart.pojo.ProductsPojo;
import emart.pojo.userProfile;
import java.sql.*;
import java.util.ArrayList;

public class OrderDAO {
    public static String getNextOrderId() throws SQLException {
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT MAX(order_id) FROM orders")) {
            rs.next();
            String ordId = rs.getString(1);
            if (ordId == null)
                return "O-101";
            int ordno = Integer.parseInt(ordId.substring(2)) + 1;
            return "O-" + ordno;
        }
    }

    public static boolean addOrder(ArrayList<ProductsPojo> al, String ordId) throws SQLException {
        String query = "INSERT INTO orders VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            int count = 0;
            for (ProductsPojo p : al) {
                ps.setString(1, ordId);
                ps.setString(2, p.getProductId());
                ps.setInt(3, p.getQuantity());
                ps.setString(4, userProfile.getUserid());
                count += ps.executeUpdate();
            }
            return count == al.size();
        }
    }

    
    public static ArrayList<ProductsPojo> viewOrder(String orderId) throws SQLException {
    String query = 
        "SELECT p.p_id, p.p_name, p.p_companyname, p.p_price, p.our_price, o.quantity, p.p_tax " +
        "FROM orders o " +
        "JOIN products p ON o.p_id = p.p_id " +
        "WHERE o.order_id = ?";
    
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setString(1, orderId);
        ResultSet rs = ps.executeQuery();

        ArrayList<ProductsPojo> orderList = new ArrayList<>();
        while (rs.next()) {
            ProductsPojo product = new ProductsPojo();
            product.setProductId(rs.getString("p_id"));
            product.setProductName(rs.getString("p_name"));
            product.setProductCompany(rs.getString("p_companyname"));
            product.setProductPrice(rs.getDouble("p_price"));
            product.setOurPrice(rs.getDouble("our_price"));
            product.setQuantity(rs.getInt("quantity"));
            product.setTax(rs.getInt("p_tax"));
            orderList.add(product);
        }
        return orderList;
    }
}


    public static ArrayList<String> getAllOrderIds() throws SQLException {
        String query = "SELECT DISTINCT order_id FROM orders";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            ArrayList<String> orderIds = new ArrayList<>();
            while (rs.next()) {
                orderIds.add(rs.getString(1));
            }
            return orderIds;
        }
    }
}
