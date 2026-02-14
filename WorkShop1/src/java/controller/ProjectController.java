/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import model.StartupProjectDAO;
import model.StartupProjectDTO;
import utils.AuthUtils;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ProjectController", urlPatterns = {"/ProjectController"})
public class ProjectController extends HttpServlet {

    // -----------------------------------------------------
    StartupProjectDAO pdao = new StartupProjectDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = "error.jsp";
        try {
            String action = request.getParameter("action");
            System.out.println("ProjectController - Action received: " + action); // Debug log

            //---------Xu ly cac action cua project -----------
            if (action != null && action.equals("addProject")) {
                url = handleProjectAdding(request, response);
            } else if (action != null && action.equals("searchProject")) {
                url = handleProjectSearching(request, response);
            } else if (action != null && action.equals("listProjects")) {
                url = handleProjectListing(request, response);
            } else if (action != null && action.equals("updateProjectStatus")) {
                url = handleStatusChanging(request, response);
            } else {
                // Nếu action không hợp lệ, chuyển về error
                request.setAttribute("errorMessage", "Invalid action: " + action);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
        } finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String handleProjectAdding(HttpServletRequest request, HttpServletResponse response) {
        String checkError = "";
        String message = "";
        String idError = "";
        String nameError = "";

        // check is Founder or not
        if (AuthUtils.isFounder(request)) {
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String status = request.getParameter("status");
            String launch = request.getParameter("launch");

            // Nếu không có dữ liệu từ form (lần đầu truy cập), chỉ hiển thị form
            if (id == null || name == null || status == null || launch == null) {
                return "add.jsp";
            }

            Date launchDate;
            try {
                launchDate = Date.valueOf(launch);
                Date today = Date.valueOf(LocalDate.now());

                if (launchDate.before(today)) {
                    request.setAttribute("launchError", "Launch date must be in the future!");
                    StartupProjectDTO project = new StartupProjectDTO(0, name, description, status, null);
                    request.setAttribute("project", project);
                    return "add.jsp";
                }
            } catch (Exception e) {
                request.setAttribute("launchError", "Invalid date format!");
                // Tạo project object với dữ liệu đã nhập để giữ lại trong form
                StartupProjectDTO project = new StartupProjectDTO(0, name, description, status, null);
                request.setAttribute("project", project);
                return "add.jsp";
            }

            int id_value;
            try {
                id_value = Integer.parseInt(id);
            } catch (Exception e) {
                idError = "ID must be a valid number!";
                request.setAttribute("idError", idError);
                // Tạo project object với dữ liệu đã nhập để giữ lại trong form
                StartupProjectDTO project = new StartupProjectDTO(0, name, description, status, null);
                request.setAttribute("project", project);
                return "add.jsp";
            }

            // check duplicate id
            if (pdao.isProjectExists(id_value)) {
                request.setAttribute("idError", "This product ID already exists");
                // Tạo project object với dữ liệu đã nhập để giữ lại trong form
                StartupProjectDTO project = new StartupProjectDTO(id_value, name, description, status, launchDate);
                request.setAttribute("project", project);
                return "add.jsp";
            }
            // check duplicate name
            if (pdao.isProjectExists(name)) {
                request.setAttribute("nameError", "This product Name already exists");
                // Tạo project object với dữ liệu đã nhập để giữ lại trong form
                StartupProjectDTO project = new StartupProjectDTO(id_value, name, description, status, launchDate);
                request.setAttribute("project", project);
                return "add.jsp";
            }

            StartupProjectDTO project = new StartupProjectDTO(id_value, name, description, status, launchDate);
            if (!pdao.create(project)) {
                checkError = "Can not add product!";
            }
            request.setAttribute("project", project);
        } else {
            // Nếu không phải Founder, chuyển về error
            return "error.jsp";
        }

        if (checkError.isEmpty()) {
            message = "Add product successfully";
        }
        request.setAttribute("checkError", checkError);
        request.setAttribute("message", message);
        return "add.jsp";
    }

    private String handleProjectSearching(HttpServletRequest request, HttpServletResponse response) {
        String keyword = request.getParameter("strKeyword");
        List<StartupProjectDTO> listSearch = pdao.searchByName(keyword);
        request.setAttribute("keyword", keyword);
        request.setAttribute("listSearch", listSearch);
        return "welcome.jsp";
    }

    private String handleProjectListing(HttpServletRequest request, HttpServletResponse response) {
        List<StartupProjectDTO> list = pdao.getAllProjects();
        request.setAttribute("listAll", list);
        return "welcome.jsp";
    }

    private String handleStatusChanging(HttpServletRequest request, HttpServletResponse response) {
        if (AuthUtils.isFounder(request)) {
            String projectName = request.getParameter("projectName");
            String status = request.getParameter("status");
            //boolean check = pdao.updateStatus(projectName, status);
            if (pdao.updateStatus(projectName, status)) {
                request.setAttribute("projectName", projectName);
                request.setAttribute("message", "Update project successfully!");
            } else {
                request.setAttribute("projectName", projectName);
                request.setAttribute("error", "Failed to update project.");
            }
        }
        return handleProjectListing(request, response);
    }

}
