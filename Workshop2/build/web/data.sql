-- Tạo database
CREATE DATABASE Workshop2;
USE Workshop2;

-- Bảng người dùng
CREATE TABLE tblUsers (
    username VARCHAR(50) PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    Role VARCHAR(20) NOT NULL CHECK (Role IN ('Instructor', 'Student'))
);

-- Bảng danh mục kỳ thi
CREATE TABLE tblExamCategories (
    category_id INT PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL,
    description TEXT
);

-- Bảng kỳ thi
CREATE TABLE tblExams (
    exam_id INT PRIMARY KEY,
    exam_title VARCHAR(100) NOT NULL,
    Subject VARCHAR(50) NOT NULL,
    category_id INT NOT NULL,
    total_marks INT NOT NULL,
    Duration INT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES tblExamCategories(category_id)
);

-- Bảng câu hỏi
CREATE TABLE tblQuestions (
    question_id INT PRIMARY KEY,
    exam_id INT NOT NULL,
    question_text TEXT NOT NULL,
    option_a VARCHAR(100) NOT NULL,
    option_b VARCHAR(100) NOT NULL,
    option_c VARCHAR(100) NOT NULL,
    option_d VARCHAR(100) NOT NULL,
    correct_option CHAR(1) NOT NULL CHECK (correct_option IN ('A', 'B', 'C', 'D')),
    FOREIGN KEY (exam_id) REFERENCES tblExams(exam_id)
);
