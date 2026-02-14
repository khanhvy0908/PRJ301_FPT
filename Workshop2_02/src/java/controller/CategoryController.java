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
import java.util.List;
import model.ExamCategoryDAO;
import model.ExamCategoryDTO;

/**
 *
 * @author Admin
 */
@WebServlet(name = "CategoryController", urlPatterns = {"/CategoryController"})
public class CategoryController extends HttpServlet {

    ExamCategoryDAO categoryDAO = new ExamCategoryDAO();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // declare url
        String url = "error.jsp";
        // try catch finnally
        try {
            // lay action
            String action = request.getParameter("action");

            //-----------Xu ly action ----------------
            if (action != null && action.equals("listExamCategory")) {
                url = handleCategoryListing(response, request);
            } else if (action != null && action.equals("showAddExamForm")) {
                List<ExamCategoryDTO> listCategory = categoryDAO.getAllCategories();
                request.setAttribute("listCategory", listCategory);
                url = "addExam.jsp"; // chuyển tiếp sang form
            }else {
                request.setAttribute("errorMessage", "Invalid action: " + action);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // chuyen tiep
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

    private String handleCategoryListing(HttpServletResponse response, HttpServletRequest request) {
        List<ExamCategoryDTO> listCategory = categoryDAO.getAllCategories();
        request.setAttribute("listCategory", listCategory);
        return "welcome.jsp";
    }

}
