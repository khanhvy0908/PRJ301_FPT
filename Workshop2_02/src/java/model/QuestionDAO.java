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
public class QuestionDAO {

    /**
     * Get question by examId
     */
     public List<QuestionDTO> getQuestionByExamID(int examID) {
        List<QuestionDTO> questions = new ArrayList<>();
        String sql = "SELECT * FROM tblQuestions WHERE exam_id = ?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, examID);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                QuestionDTO question = new QuestionDTO();
                question.setQuestionId(rs.getInt("question_id"));
                question.setExamId(rs.getInt("exam_id"));
                question.setQuestionText(rs.getString("question_text"));
                question.setOptionA(rs.getString("option_a"));
                question.setOptionB(rs.getString("option_b"));
                question.setOptionC(rs.getString("option_c"));
                question.setOptionD(rs.getString("option_d"));
                question.setCorrectOption(rs.getString("correct_option").charAt(0));
                
                questions.add(question);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }
    
    /**
     * Get All Question
     */
    public List<QuestionDTO> getAllQuestion() {
        List<QuestionDTO> questions = new ArrayList<>();
        String sql = "SELECT * FROM tblQuestions";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                QuestionDTO question = new QuestionDTO();
                question.setQuestionId(rs.getInt("question_id"));
                question.setExamId(rs.getInt("exam_id"));
                question.setQuestionText(rs.getString("question_text"));
                question.setOptionA(rs.getString("option_a"));
                question.setOptionB(rs.getString("option_b"));
                question.setOptionC(rs.getString("option_c"));
                question.setOptionD(rs.getString("option_d"));

                String correct = rs.getString("correct_option");
                if (correct != null && !correct.isEmpty()) {
                    question.setCorrectOption(correct.charAt(0));
                }

                questions.add(question);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }

    /**
     * Create question
     */
    public boolean create(QuestionDTO question) {
        boolean success = false;
        String sql = "INSERT INTO tblQuestions (question_id, exam_id, question_text, option_a, option_b, option_c, option_d, correct_option)  VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, question.getQuestionId());
            ps.setInt(2, question.getExamId());
            ps.setString(3, question.getQuestionText());
            ps.setString(4, question.getOptionA());
            ps.setString(5, question.getOptionB());
            ps.setString(6, question.getOptionC());
            ps.setString(7, question.getOptionD());
            ps.setString(8, String.valueOf(question.getCorrectOption()));

            int rowAffected = ps.executeUpdate();
            success = (rowAffected > 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Get Question By id
     */
    public QuestionDTO getQuestionByID(int questionID) {
        QuestionDTO question = null;
        String sql = "SELECT * FROM tblQuestions WHERE question_id = ?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, questionID);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                question = new QuestionDTO();
                question.setQuestionId(rs.getInt("question_id"));
                question.setExamId(rs.getInt("exam_id"));
                question.setQuestionText(rs.getString("question_text"));
                question.setOptionA(rs.getString("option_a"));
                question.setOptionB(rs.getString("option_b"));
                question.setOptionC(rs.getString("option_c"));
                question.setOptionD(rs.getString("option_d"));
                question.setCorrectOption(rs.getString("correct_option").charAt(0));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return question;
    }

    /**
     * Get Question By Name
     */
    public QuestionDTO getQuestionByText(String questionText) {
        QuestionDTO question = null;
        String sql = "SELECT * FROM tblQuestions WHERE CAST(question_text AS varchar(MAX)) = ?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, questionText);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                question = new QuestionDTO();
                question.setQuestionId(rs.getInt("question_id"));
                question.setExamId(rs.getInt("exam_id"));
                question.setQuestionText(rs.getString("question_text"));
                question.setOptionA(rs.getString("option_a"));
                question.setOptionB(rs.getString("option_b"));
                question.setOptionC(rs.getString("option_c"));
                question.setOptionD(rs.getString("option_d"));

                String correct = rs.getString("correct_option");
                if (correct != null && !correct.isEmpty()) {
                    question.setCorrectOption(correct.charAt(0));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return question;
    }
    
    /**
     * is Question Existing
     */
    public boolean isQuestionExisting(int questionID){
        return getQuestionByID(questionID) != null;
    }
    public boolean isQuestionExisting(String questionText){
        return getQuestionByText(questionText) != null;
    }
}
