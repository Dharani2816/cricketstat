import java.sql.*;

public class TestConn {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/cricket_stats?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "root";
        String pass = "1234"; // your actual password
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);
            System.out.println("✅ Connected successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
