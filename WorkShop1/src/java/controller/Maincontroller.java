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

/**
 *
 * @author Admin
 */
@WebServlet(name = "Maincontroller", urlPatterns = {"/","/Maincontroller"})
public class Maincontroller extends HttpServlet {

    private static final String LOGIN_PAGE = "login.jsp";
    
    private boolean isUserAction(String action){
        return "login".equals(action)
                || "logout".equals(action)
                || "register".equals(action)
                || "updateProfile".equals(action)
                || "viewProfilel".equals(action)
                || "changePassword".equals(action);
    }
    
    private boolean isProjectAction(String action){
        return "listProjects".equals(action)
                || "addProject".equals(action)
                || "updateProjectStatus".equals(action)
                || "searchProject".equals(action);
    }
    
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
       String url = LOGIN_PAGE;
        try {
            String action = request.getParameter("action");
            System.out.println("Action received: " + action); // Debug log
            
            // xu ly cac action cua user
            if(action != null && isUserAction(action)){
                url = "/UserController";
            }else if(action != null && isProjectAction(action)){
                url = "/ProjectController";
            } else if(action == null) {
                // Nếu không có action, chuyển về welcome page
                url = "welcome.jsp";
            } else {
                // Nếu action không hợp lệ, chuyển về error
                url = "error.jsp";
                request.setAttribute("errorMessage", "Invalid action: " + action);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "MainController error: " + e.getMessage());
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

}
