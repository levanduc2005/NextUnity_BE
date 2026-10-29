-- ==============================================================================
-- 03_courses.sql
-- Seed Data for Courses Entity
-- ==============================================================================

INSERT INTO courses (
    id, created_at, updated_at, deleted_at,
    code, title, description, category, difficulty, thumbnail_url, is_premium, total_lessons, creator_id
)
VALUES
    ('e0000000-0000-0000-0000-000000000001', NOW(), NOW(), NULL, 'AIF101', 'AI Fundamentals', 'Understand core concepts of Artificial Intelligence and how it powers modern applications.', 'FPT_COURSES', 'BEGINNER', 'https://images.unsplash.com/photo-1677442136019-21780ecad995?auto=format&fit=crop&w=800&q=80', FALSE, 8, 'b0000000-0000-0000-0000-000000000001'),
    ('e0000000-0000-0000-0000-000000000002', NOW(), NOW(), NULL, 'DBI202', 'Database Systems', 'Learn to design, implement and query relational databases with SQL and normalization.', 'FPT_COURSES', 'INTERMEDIATE', 'https://images.unsplash.com/photo-1544383835-bda2bc66a55d?auto=format&fit=crop&w=800&q=80', FALSE, 7, 'b0000000-0000-0000-0000-000000000001'),
    ('e0000000-0000-0000-0000-000000000003', NOW(), NOW(), NULL, 'CSD201', 'Data Structures', 'Master foundational data structures and algorithms (Array, Linked List, Stack, Queue, Tree) in Java.', 'FPT_COURSES', 'INTERMEDIATE', 'https://images.unsplash.com/photo-1504639725590-34d0984388bd?auto=format&fit=crop&w=800&q=80', FALSE, 6, 'b0000000-0000-0000-0000-000000000001'),
    ('e0000000-0000-0000-0000-000000000004', NOW(), NOW(), NULL, 'MAD101', 'Discrete Mathematics', 'Learn propositional logic, set theory, graph theory, combinatorics and formal reasoning.', 'FPT_COURSES', 'ADVANCED', 'https://images.unsplash.com/photo-1635070041078-e363dbe005cb?auto=format&fit=crop&w=800&q=80', TRUE, 7, 'b0000000-0000-0000-0000-000000000001'),
    ('e0000000-0000-0000-0000-000000000005', NOW(), NOW(), NULL, 'PRF192', 'Lập trình C cơ bản (PRF192)', 'Làm chủ tư duy thuật toán, cú pháp C, con trỏ (pointers), quản lý bộ nhớ động và kỹ thuật lập trình hàm theo chuẩn FPTU.', 'FPT_COURSES', 'BEGINNER', 'https://images.unsplash.com/photo-1515879218367-8466d910aaa4?auto=format&fit=crop&w=800&q=80', FALSE, 7, 'b0000000-0000-0000-0000-000000000001'),
    ('e0000000-0000-0000-0000-000000000006', NOW(), NOW(), NULL, 'PRO192', 'Lập trình Hướng đối tượng với Java (PRO192)', 'Nắm vững 4 tính chất OOP (Đóng gói, Kế thừa, Đa hình, Trừu tượng), Java Collections và xử lý ngoại lệ chuẩn đề thi Practical Exam.', 'FPT_COURSES', 'INTERMEDIATE', 'https://images.unsplash.com/photo-1517694712202-14dd9538aa97?auto=format&fit=crop&w=800&q=80', FALSE, 7, 'b0000000-0000-0000-0000-000000000001'),
    ('e0000000-0000-0000-0000-000000000007', NOW(), NOW(), NULL, 'PRJ301', 'Lập trình Ứng dụng Web với Java (PRJ301)', 'Phát triển ứng dụng Web hoàn chỉnh theo mô hình MVC sử dụng Servlet, JSP, JSTL, kết nối SQL Server qua JDBC, Cookie, Session và Filter.', 'FPT_COURSES', 'ADVANCED', 'https://images.unsplash.com/photo-1555066931-4365d14bab8c?auto=format&fit=crop&w=800&q=80', FALSE, 7, 'b0000000-0000-0000-0000-000000000001')
ON CONFLICT (id) DO NOTHING;
