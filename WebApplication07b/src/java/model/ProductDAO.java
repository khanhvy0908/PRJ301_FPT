/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;

/**
 *
 * @author Admin
 */
public class ProductDAO {
    
    // SQL Queries
    private static final String GET_ALL_PRODUCTS ="SELECT * FROM tblProducts";
    private static final String GET_PRODUCT_BY_ID ="SELECT * FROM tblProducts WHERE id = ?";
    private static final String CREATE_PRODUCT ="INSERT INTO tblProducts VALUES(?, ?, ?, ?, ?, ?))";
    private static final String UPDATE_PRODUCT ="UPDATE tblProducts SET name = ?, description = ?, price = ?, size = ?, status = ? WHERE id = ?";
    private static final String DELETE_PRODUCT ="DELETE FROM tblProducts WHERE id = ?";
    
    // closeResources
    private void closeResources(Connection conn, PreparedStatement ps, ResultSet rs){
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
    
    public List<ProductDTO> getAll(){
        List<ProductDTO> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ProductDTO product = null;
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL_PRODUCTS);
            rs = ps.executeQuery();
            
            if(rs.next()){
                 product = new ProductDTO();
                product.setId(rs.getString("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setSize(rs.getString("size"));
                product.setStatus(rs.getBoolean("status"));
            }
        } catch (Exception e) {
            System.err.println("Error in getAll(): " + e.getMessage());
            e.printStackTrace();
        } finally {
           closeResources(conn, ps, rs);
        }
        // 
        return products;
    }

/**
 * Get product by ID
 */
    public ProductDTO getProductByID(String id){
        Connection conn = null;
        ProductDTO product = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_PRODUCT_BY_ID);
            ps.setString(1, id);
            rs = ps.executeQuery();
        } catch (Exception e) {
            System.err.println("Error in getProductByID(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return product;
    }

    /**
     * Create new product
     */
    public boolean create(ProductDTO product){
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;
       
         try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(CREATE_PRODUCT);

            ps.setString(1, product.getId());
            ps.setString(2, product.getName());
            ps.setString(3, product.getDescription());
            ps.setDouble(4, product.getPrice());
            ps.setString(5, product.getSize());
            ps.setBoolean(6, product.isStatus());

            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);

        } catch (Exception e) {
            System.err.println("Error in create(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }

        return success;
        
        
    }
    
    /**
     * Update existing product
     */
    public boolean update(ProductDTO product){
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(UPDATE_PRODUCT);
            
             ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setString(4, product.getSize());
            ps.setBoolean(5, product.isStatus());
            ps.setString(6, product.getId()); // WHERE condition
            
            int rowAffected =ps.executeUpdate();
            success = (rowAffected > 0);
            
        } catch (Exception e) {
           System.err.println("Error in update(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }
        
        return success;
    }
    
    /**
     * Delete id product ID to delete
     */
    public boolean delete(String id){
        boolean success = false;
        Connection conn= null;
        PreparedStatement ps = null;
        
        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(DELETE_PRODUCT);
            ps.setString(1, id);
            
            int rowAffected = ps.executeUpdate();
            success= (rowAffected > 0);
            
        } catch (Exception e) {
            System.err.println("Error in delete(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }
        
        return success;
    }
    
    
    /**
     * Check if product exist by ID
     */
    public boolean isProductExists(String id){
        return getProductByID(id) != null;
    }
    
    /**
     * Get products by status
     */
    public List<ProductDTO> getProductByStatus(boolean status){
        List<ProductDTO> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // query
        String query = GET_ALL_PRODUCTS + " WHERE status = ?";
        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(query);
            ps.setBoolean(1, status);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                ProductDTO product = new ProductDTO();
                product.setId(rs.getString("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setSize(rs.getString("size"));
                product.setStatus(rs.getBoolean("status"));

                products.add(product);
            }
            
        } catch (Exception e) {
            System.err.println("Error in getProductsByStatus(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        
        return products;
    }
}
