/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DbUtils;

/**
 *
 * @author Admin
 */
public class ExamCategoryDAO {

    /**
     * Get All Categories
     */
    public List<ExamCategoryDTO> getAllCategories() {
        List<ExamCategoryDTO> listCategory = new ArrayList<>();
        String sql = " SELECT * FROM tblExamCategories";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ExamCategoryDTO exam = new ExamCategoryDTO();
                exam.setCategoryId(rs.getInt("category_id"));
                exam.setCategoryName(rs.getString("category_name"));
                exam.setDescription(rs.getString("description"));
                listCategory.add(exam);
            }
            return listCategory;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCategory;
    }

    /**
     * Get Category by CategoryName
     */
    public ExamCategoryDTO getCategoryByCategoryName(String categoryName) {
        ExamCategoryDTO category = null;
        String sql = "SELECT * FROM tblExamCategories WHERE category_name = ?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, categoryName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                category = new ExamCategoryDTO();
                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("category_name"));
                category.setDescription(rs.getString("description"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return category;
    }

    /**
     * Close database resources safely
     *
     * @param conn Connection to close
     * @param ps PreparedStatement to close
     * @param rs ResultSet to close
     */
    private void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            System.err.println("Error closing resources: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
