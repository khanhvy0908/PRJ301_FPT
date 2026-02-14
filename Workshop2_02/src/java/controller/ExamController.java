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
import java.util.ArrayList;
import java.util.List;
import model.ExamCategoryDAO;
import model.ExamCategoryDTO;
import model.ExamDAO;
import model.ExamDTO;
import model.QuestionDAO;
import model.QuestionDTO;
import utils.AuthUtils;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ExamController", urlPatterns = {"/ExamController"})
public class ExamController extends HttpServlet {

    ExamDAO examDAO = new ExamDAO();
    QuestionDAO dao = new QuestionDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = "error.jsp";
        try {
            String action = request.getParameter("action");
            if (action != null && action.equals("listExam")) {
                url = handleExamListing(request, response);
            } else if (action != null && action.equals("addExam")) {
                url = handleExamAdding(request, response);
            } else if (action != null && action.equals("ListExamForQuestionAdding")) {
                List<ExamDTO> listExamForQuestionAdding = examDAO.getAllExam();
                request.setAttribute("listExamForQuestionAdding", listExamForQuestionAdding);
                url = "addQuestion.jsp";
            } else if (action != null && action.equals("startExam")) {
                url = handleExamStart(request, response);
            } else if (action != null && action.equals("submitExam")) {
                url = handleExamSubmit(request, response);
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

    private String handleExamListing(HttpServletRequest request, HttpServletResponse response) {
        // lay keyword - category tu form
        String keyword = request.getParameter("strKeyword");
        // lay category tu categoryName duoc nhap
        ExamCategoryDAO categoryDAO = new ExamCategoryDAO();
        ExamCategoryDTO category = categoryDAO.getCategoryByCategoryName(keyword);

        List<ExamDTO> listExam = new ArrayList<>();
        // Xu ly neu category tra ve la null
        if (category != null) {
            int categoryID = category.getCategoryId();
            listExam = examDAO.getExamByCategoryID(categoryID);
        }

        // gui listExam va keyword di
        request.setAttribute("listExam", listExam);
        request.setAttribute("keyword", keyword);

        return "welcome.jsp";
    }

    private String handleExamAdding(HttpServletRequest request, HttpServletResponse response) {
        String checkError = "";
        String message = "";
        String idError = "";
        String titleError = "";
        if (AuthUtils.isInstructor(request)) {
            String examId = request.getParameter("examId");
            String examTitle = request.getParameter("examTitle");
            String subject = request.getParameter("subject");
            String categoryId = request.getParameter("categoryId");
            String totalMarkb = request.getParameter("totalMarks");
            String duration = request.getParameter("duration");

            //--- Try catch ---
            int examId_value = 0;
            int categoryId_value = 0;
            int totalMarkb_value = 0;
            int duration_value = 0;
            try {
                examId_value = Integer.parseInt(examId);
                categoryId_value = Integer.parseInt(categoryId);
                totalMarkb_value = Integer.parseInt(totalMarkb);
                duration_value = Integer.parseInt(duration);
            } catch (Exception e) {
            }

            // --- Kiem tra loi ---
            if (examDAO.isExamExisting(examId_value)) {
                request.setAttribute("idError", "This exam ID already exists!");
            }
            if (examDAO.isExamExisting(examTitle)) {
                request.setAttribute("titleError", "This exam Title already exist!");
            }

            ExamDTO exam = new ExamDTO(examId_value, examTitle, subject, categoryId_value, totalMarkb_value, duration_value);
            if (!examDAO.create(exam)) {
                checkError += "<br/> Can not add exam!";
            }

            request.setAttribute("exam", exam);
        }

        // 
        if (checkError.isEmpty()) {
            message = "Add exam successfully!";
        }
        request.setAttribute("checkError", checkError);
        request.setAttribute("message", message);

        // gui lai listCategory
        ExamCategoryDAO categoryDAO = new ExamCategoryDAO();
        List<ExamCategoryDTO> listCategory = categoryDAO.getAllCategories();
        request.setAttribute("listCategory", listCategory);
        return "addExam.jsp";
    }

    private String handleExamStart(HttpServletRequest request, HttpServletResponse response) {
        int examId = Integer.parseInt(request.getParameter("examId"));
        String examTitle = request.getParameter("examTitle");

        List<QuestionDTO> questionList = dao.getQuestionByExamID(examId);

        request.setAttribute("questionList", questionList);
        request.setAttribute("examId", examId);
        request.setAttribute("examTitle", examTitle);
        return "takeExam.jsp";
    }

    private String handleExamSubmit(HttpServletRequest request, HttpServletResponse response) {
        // lay danh sach question xuong
        int examId = Integer.parseInt(request.getParameter("examId"));
        String examTitle = request.getParameter("examTitle");

        List<QuestionDTO> questionList = dao.getQuestionByExamID(examId);

        int total = questionList.size();
        int correct = 0;

        for (QuestionDTO q : questionList) {
            String userAnswer = request.getParameter("answer_" + q.getQuestionId());
            if (userAnswer != null && userAnswer.charAt(0) == q.getCorrectOption()) {
                correct++;
            }
        }

        // xu ly diem
        double score_temp = ((double) correct / total) * 100;
        int score = (int) score_temp;

        request.setAttribute("score", score);
        request.setAttribute("total", total);
        request.setAttribute("correct", correct);
        return "examResult.jsp";
    }

}
