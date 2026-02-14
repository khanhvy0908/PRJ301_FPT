-- Tạo cơ sở dữ liệu
CREATE DATABASE WorkShop1;
GO

-- Sử dụng cơ sở dữ liệu
USE WorkShop1;
GO

-- Tạo bảng người dùng: tblUsers
CREATE TABLE tblUsers (
    Username VARCHAR(50) PRIMARY KEY,
    Name NVARCHAR(100) NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Role VARCHAR(20) NOT NULL CHECK (Role IN ('Founder', 'Member'))
);



-- Tạo bảng dự án khởi nghiệp: tblStartupProjects
CREATE TABLE tblStartupProjects (
    project_id INT PRIMARY KEY,
    project_name VARCHAR(100) NOT NULL,
    Description TEXT,
    Status VARCHAR(20) NOT NULL CHECK (Status IN ('Ideation', 'Development', 'Launch', 'Scaling')),
    estimated_launch DATE NOT NULL
);

-- Chèn dữ liệu mẫu vào tblUsers
  INSERT INTO tblUsers (Username, Name, Password, Role) VALUES
('founder', N'Lê Minh Tuấn', '123', 'Founder'),
('hanhnt22', N'Nguyễn Thị Hạnh', '456', 'Member'),
('trangpt1933', N'Phạm Thu Trang', 'securepwd', 'Member'),
('namvh2004', N'Vũ Hoàng Nam', 'nam2025', 'Member');


-- Chèn dữ liệu mẫu vào tblStartupProjects
INSERT INTO tblStartupProjects (project_id, project_name, Description, Status, estimated_launch) VALUES
(1, 'EcoMarket', 'An online platform for eco-friendly products.', 'Ideation', '2025-09-01'),
(2, 'HealthTracker', 'A mobile app to monitor personal health metrics.', 'Development', '2025-10-15'),
(3, 'EduAI', 'AI-powered tutoring platform for high school students.', 'Launch', '2025-07-01'),
(4, 'TravelBuddy', 'A smart assistant for travel planning.', 'Scaling', '2025-06-20');
