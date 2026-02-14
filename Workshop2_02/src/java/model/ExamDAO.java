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
public class ExamDAO {

    /**
     * Get all exam
     */
    public List<ExamDTO> getAllExam() {
        List<ExamDTO> exams = new ArrayList<>();
        String sql = "SELECT * FROM tblExams";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ExamDTO exam = new ExamDTO();
                exam.setExamId(rs.getInt("exam_id"));
                exam.setExamTitle(rs.getString("exam_title"));
                exam.setSubject(rs.getString("Subject"));
                exam.setCategoryId(rs.getInt("category_id"));
                exam.setTotalMarks(rs.getInt("total_marks"));
                exam.setDuration(rs.getInt("Duration"));
                exams.add(exam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exams;
    }
    
    /**
     * Get Exam By categoryID
     */
    public List<ExamDTO> getExamByCategoryID(int categoryID) {
        List<ExamDTO> exams = new ArrayList<>();
        String sql = "SELECT * FROM tblExams WHERE category_id = ?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, categoryID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ExamDTO exam = new ExamDTO();
                exam.setExamId(rs.getInt("exam_id"));
                exam.setExamTitle(rs.getString("exam_title"));
                exam.setSubject(rs.getString("Subject"));
                exam.setCategoryId(rs.getInt("category_id"));
                exam.setTotalMarks(rs.getInt("total_marks"));
                exam.setDuration(rs.getInt("Duration"));
                exams.add(exam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exams;
    }

    /**
     * Get Exam by id
     */
    public ExamDTO getExamByID(int examId) {
        ExamDTO exam = null;
        String sql = "SELECT * FROM tblExams WHERE exam_id = ?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, examId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                exam = new ExamDTO();
                exam.setExamId(rs.getInt("exam_id"));
                exam.setExamTitle(rs.getString("exam_title"));
                exam.setSubject(rs.getString("Subject"));
                exam.setCategoryId(rs.getInt("category_id"));
                exam.setTotalMarks(rs.getInt("total_marks"));
                exam.setDuration(rs.getInt("Duration"));
            }
            return exam;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exam;
    }

    /**
     * Get Exam by Name
     */
    public ExamDTO getExamByName(String examTitle) {
        ExamDTO exam = null;
        String sql = "SELECT * FROM tblExams WHERE exam_title = ?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, examTitle);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                exam = new ExamDTO();
                exam.setExamId(rs.getInt("exam_id"));
                exam.setExamTitle(rs.getString("exam_title"));
                exam.setSubject(rs.getString("Subject"));
                exam.setCategoryId(rs.getInt("category_id"));
                exam.setTotalMarks(rs.getInt("total_marks"));
                exam.setDuration(rs.getInt("Duration"));
            }
            return exam;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exam;
    }

    /**
     * check is title, id exam existing
     */
    public boolean isExamExisting(int id) {
        return getExamByID(id) != null;
    }

    public boolean isExamExisting(String title) {
        return getExamByName(title) != null;
    }

    /**
     * Create new exam
     */
    public boolean create(ExamDTO exam) {
        boolean success = false;
        String sql = "INSERT INTO tblExams (exam_id, exam_title, Subject, category_id, total_marks, Duration) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, exam.getExamId());
            ps.setString(2, exam.getExamTitle());
            ps.setString(3, exam.getSubject());
            ps.setInt(4, exam.getCategoryId());
            ps.setInt(5, exam.getTotalMarks());
            ps.setInt(6, exam.getDuration());
            
            int rowAffected = ps.executeUpdate();
            success = (rowAffected > 0);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }
}
