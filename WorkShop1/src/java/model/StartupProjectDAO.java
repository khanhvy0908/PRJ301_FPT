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
public class StartupProjectDAO {

    public boolean update(StartupProjectDTO project) {
        boolean success = false;
        String sql = "UPDATE tblStartupProjects SET project_id = ?, Description = ?, Status = ?, estimated_launch = ? WHERE project_name = ?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, project.getProjectId());
            ps.setString(2, project.getDescription());
            ps.setString(3, project.getStatus());
            ps.setDate(4, project.getEstimatedLaunch());
            ps.setString(5, project.getProjectName());
            int rowAffected = ps.executeUpdate();
            success = (rowAffected > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public boolean updateStatus(String projectName, String status) {
        StartupProjectDTO project = getProjectByName(projectName);
        if (project != null) {
            project.setStatus(status);
            return update(project);
        } else {
            return false;

        }
    }

    public List<StartupProjectDTO> getAllProjects() {
        List<StartupProjectDTO> projectList = new ArrayList<>();
        String sql = "SELECT * FROM tblStartupProjects";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                StartupProjectDTO project = new StartupProjectDTO();
                project.setProjectId(rs.getInt("project_id"));
                project.setProjectName(rs.getString("project_name"));
                project.setDescription(rs.getString("Description"));
                project.setStatus(rs.getString("Status"));
                project.setEstimatedLaunch(rs.getDate("estimated_launch"));
                projectList.add(project);
            }
            return projectList;
        } catch (Exception e) {
            System.err.println("Error in getAll(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }
        return projectList;
    }

    public List<StartupProjectDTO> searchByName(String projectName) {
        List<StartupProjectDTO> searchList = new ArrayList<>();
        String sql = "SELECT * FROM tblStartupProjects WHERE project_name like ?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + projectName + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                StartupProjectDTO project = new StartupProjectDTO();
                project.setProjectId(rs.getInt("project_id"));
                project.setProjectName(rs.getString("project_name"));
                project.setDescription(rs.getString("Description"));
                project.setStatus(rs.getString("Status"));
                project.setEstimatedLaunch(rs.getDate("estimated_launch"));
                searchList.add(project);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchList;
    }

    public StartupProjectDTO getProjectByID(int id) {
        StartupProjectDTO project = null;
        String sql = "SELECT * FROM tblStartupProjects WHERE project_id = ?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                project = new StartupProjectDTO();
                project.setProjectId(rs.getInt("project_id"));
                project.setProjectName(rs.getString("project_name"));
                project.setDescription(rs.getString("Description"));
                project.setStatus(rs.getString("Status"));
                project.setEstimatedLaunch(rs.getDate("estimated_launch"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return project;
    }

    public StartupProjectDTO getProjectByName(String name) {
        StartupProjectDTO project = null;
        String sql = "SELECT * FROM tblStartupProjects WHERE project_name = ?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                project = new StartupProjectDTO();
                project.setProjectId(rs.getInt("project_id"));
                project.setProjectName(rs.getString("project_name"));
                project.setDescription(rs.getString("Description"));
                project.setStatus(rs.getString("Status"));
                project.setEstimatedLaunch(rs.getDate("estimated_launch"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return project;
    }

    public boolean isProjectExists(String name) {
        return getProjectByName(name) != null;
    }

    public boolean isProjectExists(int id) {
        return getProjectByID(id) != null;
    }

    public boolean create(StartupProjectDTO project) {
        boolean success = false;
        String sql = "INSERT INTO tblStartupProjects (project_id, project_name, Description, Status, estimated_launch) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setInt(1, project.getProjectId());
            ps.setString(2, project.getProjectName());
            ps.setString(3, project.getDescription());
            ps.setString(4, project.getStatus());
            ps.setDate(5, project.getEstimatedLaunch());

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
