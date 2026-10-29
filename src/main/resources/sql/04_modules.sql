-- ==============================================================================
-- 04_modules.sql
-- Seed Data for Modules Entity
-- ==============================================================================

INSERT INTO modules (
    id, created_at, updated_at, deleted_at,
    course_id, title, description, order_index
)
VALUES
    -- Modules for Course 1: AI Fundamentals
    ('e1000000-0000-0000-0000-000000000001', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000001', 'Introduction to AI', 'Explore foundational concepts and history of AI.', 1),
    ('e1000000-0000-0000-0000-000000000002', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000001', 'Machine Learning Basics', 'Core concepts of supervised and unsupervised learning.', 2),
    ('e1000000-0000-0000-0000-000000000003', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000001', 'Neural Networks', 'Introduction to deep architectures and perception.', 3),

    -- Modules for Course 2: Database Systems
    ('e1000000-0000-0000-0000-000000000004', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000002', 'Introduction to Databases', 'Relational model, DBMS vs File Systems.', 1),
    ('e1000000-0000-0000-0000-000000000005', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000002', 'SQL Fundamentals', 'DDL, DML and advanced JOIN techniques.', 2),
    ('e1000000-0000-0000-0000-000000000006', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000002', 'Database Design', 'ERD, Normalization (1NF to BCNF).', 3),

    -- Modules for Course 3: Data Structures
    ('e1000000-0000-0000-0000-000000000007', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000003', 'Introduction to Data Structures', 'Complexity analysis and linear memory.', 1),
    ('e1000000-0000-0000-0000-000000000008', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000003', 'Stacks & Queues', 'LIFO, FIFO principles and buffer implementations.', 2),
    ('e1000000-0000-0000-0000-000000000009', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000003', 'Trees & Graphs', 'Hierarchical structures, BFS, DFS and BST.', 3),

    -- Modules for Course 4: Discrete Mathematics
    ('e1000000-0000-0000-0000-000000000010', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000004', 'Logic & Proofs', 'Propositional logic, inference rules and direct proofs.', 1),
    ('e1000000-0000-0000-0000-000000000011', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000004', 'Set Theory', 'Sets, functions, sequences and relations.', 2),
    ('e1000000-0000-0000-0000-000000000012', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000004', 'Graph Theory', 'Euler paths, Hamilton cycles and trees.', 3),

    -- Modules for Course 5: PRF192 (Programming Fundamentals with C)
    ('e1000000-0000-0000-0000-000000000013', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000005', 'Cơ bản về C & Cấu trúc rẽ nhánh, vòng lặp', 'Biến, kiểu dữ liệu, toán tử, scanf/printf, cấu trúc if-else, switch-case, vòng lặp for/while.', 1),
    ('e1000000-0000-0000-0000-000000000014', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000005', 'Hàm, Mảng & Xử lý Chuỗi (String)', 'Kỹ thuật viết hàm mô-đun, mảng một chiều, mảng hai chiều và các hàm thư viện string.h chuẩn PE.', 2),
    ('e1000000-0000-0000-0000-000000000015', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000005', 'Con trỏ & Quản lý Bộ nhớ Động', 'Bản chất con trỏ, toán tử *, &, cấp phát bộ nhớ động malloc/calloc/free và kiểu cấu trúc Struct.', 3),

    -- Modules for Course 6: PRO192 (Object-Oriented Programming with Java)
    ('e1000000-0000-0000-0000-000000000016', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000006', 'Cơ sở Java & Tính đóng gói (Encapsulation)', 'Lớp (Class), Đối tượng (Object), Constructor, từ khóa this, Access Modifiers và Getter/Setter.', 1),
    ('e1000000-0000-0000-0000-000000000017', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000006', 'Kế thừa, Đa hình & Interface', 'Từ khóa extends, super, ghi đè phương thức (Override), Abstract Class và Interface.', 2),
    ('e1000000-0000-0000-0000-000000000018', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000006', 'Java Collections Framework & Ngoại lệ', 'Sử dụng ArrayList, sắp xếp với Comparable/Comparator, cấu trúc Try-Catch-Finally và ném ngoại lệ.', 3),

    -- Modules for Course 7: PRJ301 (Java Web Application Development)
    ('e1000000-0000-0000-0000-000000000019', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000007', 'Kiến trúc Web & Vòng đời Servlet', 'Mô hình Client-Server HTTP, phương thức GET/POST, vòng đời init-service-destroy, RequestDispatcher.', 1),
    ('e1000000-0000-0000-0000-000000000020', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000007', 'JSP, JSTL & Mô hình MVC 2', 'Vòng đời trang JSP, Expression Language (EL), bộ thẻ JSTL core (c:if, c:forEach) và cấu trúc MVC.', 2),
    ('e1000000-0000-0000-0000-000000000021', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000007', 'JDBC, Session & Xác thực người dùng', 'Kết nối cơ sở dữ liệu qua JDBC, mẫu thiết kế DAO, Cookie, HttpSession đăng nhập/đăng xuất và Filter.', 3)
ON CONFLICT (id) DO NOTHING;
