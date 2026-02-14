/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DbUtils;

/**
 *
 * @author Admin
 */
public class UserDAO {

    public UserDAO() {
    }

    /**
     * Get user by user name - preLogin
     */
    public UserDTO getUserByUserName(String username) {
        String sql = "SELECT * FROM tblUsers WHERE username = ?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String userName = rs.getString("username");
                String name = rs.getString("Name");
                String password = rs.getString("password");
                String role = rs.getString("Role");
                return new UserDTO(userName, name, password, role);
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Login
     */
    public boolean login(String username, String pasword) {
        try {
            UserDTO user = new UserDTO();
            user = getUserByUserName(username);
            if (user != null) {
                if (user.getPassword().equals(pasword)) {
                    return true;
                }
            }
        } catch (Exception e) {
        }

        return false;
    }
}
