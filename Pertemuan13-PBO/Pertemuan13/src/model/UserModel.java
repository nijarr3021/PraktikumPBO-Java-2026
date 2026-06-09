package model;

import koneksi.Koneksi;
import java.sql.*;

public class UserModel {

    private int id;
    private String username;
    private String password;
    private String role;

    // Constructor
    public UserModel(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    // —————— QUERY ——————
    /**
     * Cari user berdasarkan username dan password.
     * @return objek UserModel jika ditemukan, null jika tidak cocok.
     */
    public static UserModel cariUser(String username, String password) {
        UserModel user = null;
        String sql = "SELECT * FROM tb_user WHERE username = ? AND password = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new UserModel(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error cariUser: " + e.getMessage());
        }

        return user;
    }
}
