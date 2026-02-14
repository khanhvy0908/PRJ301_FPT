-- Tạo database
CREATE DATABASE Workshop2;
USE Workshop2;

DROP TABLE IF EXISTS tblUsers
DROP TABLE IF EXISTS tblExamCategories;


-- Bảng người dùng
CREATE TABLE tblUsers (
    username VARCHAR(50) PRIMARY KEY,
    Name NVARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    Role VARCHAR(20) NOT NULL CHECK (Role IN ('Instructor', 'Student'))
);

-- Bảng danh mục kỳ thi
CREATE TABLE tblExamCategories (
    category_id INT PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

-- Bảng kỳ thi
CREATE TABLE tblExams (
    exam_id INT PRIMARY KEY,
    exam_title VARCHAR(100) NOT NULL UNIQUE,
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


-- Thêm người dùng
INSERT INTO tblUsers (username, Name, password, Role) VALUES
('teacher001',  N'Lê Minh Tuấn', '123', 'Instructor'),
('teacher002',  N'Vũ Hoàng Nam', '12345', 'Instructor'),
('student001',  N'Nguyễn Thị Hạnh', '67890', 'Student'),
('student002',  N'Phạm Thu Trang', '13579', 'Student');

INSERT INTO tblExamCategories (category_id, category_name, description) VALUES
(1, 'Quiz', 'Quick check on knowledge'),
(2, 'Midterm', 'Middle of course assessment'),
(3, 'Final', 'End of course exam');

INSERT INTO tblExams (exam_id, exam_title, Subject, category_id, total_marks, Duration) VALUES
(101, 'SQL Basics Quiz', 'Database Systems', 1, 10, 15),
(102, 'OOP Midterm Exam', 'Object-Oriented Programming', 2, 50, 60),
(103, 'Final Java Exam', 'Programming with Java', 3, 100, 90);

INSERT INTO tblQuestions (question_id, exam_id, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES
(1001, 101, 'What does SQL stand for?', 'Structured Query Language', 'Strong Question Logic', 'Simple Query Link', 'None of the above', 'A'),
(1002, 101, 'Which command is used to select data?', 'GET', 'SELECT', 'FETCH', 'SHOW', 'B'),
(1003, 102, 'What is encapsulation in OOP?', 'Binding data and functions', 'Making data public', 'Using XML files', 'Looping over data', 'A'),
(1004, 103, 'Which keyword is used to inherit a class in Java?', 'inherits', 'extends', 'implements', 'super', 'B');
