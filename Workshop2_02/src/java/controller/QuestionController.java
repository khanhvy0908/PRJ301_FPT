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
import model.ExamDAO;
import model.ExamDTO;
import model.QuestionDAO;
import model.QuestionDTO;
import utils.AuthUtils;

/**
 *
 * @author Admin
 */
@WebServlet(name = "QuestionController", urlPatterns = {"/QuestionController"})
public class QuestionController extends HttpServlet {
    QuestionDAO questionDAO = new QuestionDAO();
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
        String url = "error.jsp";
        try {
            String action = request.getParameter("action");
            if (action != null && action.equals("listQuestion")) {
                url = handleQuestionListing(request, response);
            }else if (action != null && action.equals("addQuestion")) {
                url = handleQuestionAdding(request, response);
            } else {
                request.setAttribute("errorMessage", "Invalid action " + action);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    private String handleQuestionListing(HttpServletRequest request, HttpServletResponse response) {
        List<QuestionDTO> listQuestion = questionDAO.getAllQuestion();
        request.setAttribute("listQuestion", listQuestion);
        return "welcome.jsp";
    }

    private String handleQuestionAdding(HttpServletRequest request, HttpServletResponse response) {
        String checkError = "";
        String message = "";
        String questionIdError = "";
        String questionTextError = "";
        if(AuthUtils.isInstructor(request)){
            // lay thong tin
            String questionId = request.getParameter("questionId");
            String examId = request.getParameter("examId");
            String questionText = request.getParameter("questionText");
            String optionA = request.getParameter("optionA");
            String optionB = request.getParameter("optionB");
            String optionC = request.getParameter("optionC");
            String optionD = request.getParameter("optionD");
            String correctOption = request.getParameter("correctOption");
        
            // TRY CATCH
            int questionId_value = 0;
            int examId_value = 0;
            try {
                questionId_value = Integer.parseInt(questionId);
                examId_value = Integer.parseInt(examId);
            } catch (Exception e) {
            }
            
             char correctOption_value = ' ';
             try {
                correctOption_value = correctOption.charAt(0);
            } catch (Exception e) {
            }
        
            // kiem tra loi cho questionid va questiontext 
            if(questionDAO.isQuestionExisting(questionId_value)){
                request.setAttribute("questionIdError", "The question ID is duplicated!");
            }
            if(questionDAO.isQuestionExisting(questionText)){
                request.setAttribute("questionTextError", "The question text is duplicated!");
            }
             
            QuestionDTO question = new QuestionDTO(questionId_value, examId_value, questionText, optionA, optionB, optionC, optionD, correctOption_value);
            if(!questionDAO.create(question)){
                checkError += "<br/> Can not add question!";
            }
            request.setAttribute("question", question);
        }
        
        //
        if(checkError.isEmpty()){
            message = "Add question successfully!";
        }
        request.setAttribute("checkError", checkError);
        request.setAttribute("message", message);
        
        // gui lai listExamForQuestionAdding
        ExamDAO examDAO = new ExamDAO();
        List<ExamDTO> listExamForQuestionAdding = examDAO.getAllExam();
        request.setAttribute("listExamForQuestionAdding", listExamForQuestionAdding);
        return "addQuestion.jsp";
    }

}
