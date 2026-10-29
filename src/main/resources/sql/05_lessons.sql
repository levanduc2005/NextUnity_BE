-- ==============================================================================
-- 05_lessons.sql
-- Seed Data for Lessons Entity (Compact & Readable)
-- ==============================================================================

INSERT INTO lessons (
    id, created_at, updated_at, deleted_at,
    module_id, title, content, order_index, type, estimated_minutes, video_url
)
VALUES
    -- Course 1, Module 1 (Introduction to AI)
    ('e2000000-0000-0000-0000-000000000001', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000001', 'What is AI?', 'Artificial intelligence is the simulation of human intelligence processes by machines, especially computer systems.', 1, 'VIDEO', 10, 'https://www.youtube.com/watch?v=qYNweeDHiyU'),
    ('e2000000-0000-0000-0000-000000000002', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000001', 'History of AI', 'The history of AI dates back to antiquity, with modern AI founded at Dartmouth College in 1956.', 2, 'TEXT', 15, NULL),
    ('e2000000-0000-0000-0000-000000000003', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000001', 'Types of AI', 'Explore Narrow AI (Weak AI), General AI (Strong AI), and Superintelligent AI.', 3, 'QUIZ', 20, NULL),
    ('e2000000-0000-0000-0000-000000000004', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000001', 'AI in Everyday Life', 'Recommendation engines on Netflix, navigation in autonomous vehicles, and virtual assistants.', 4, 'TEXT', 12, NULL),
    ('e2000000-0000-0000-0000-000000000005', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000001', 'Ethical Considerations in AI', 'Addressing bias in machine learning models, accountability, and automated decision-making ethics.', 5, 'TEXT', 18, NULL),

    -- Course 1, Module 2 (Machine Learning Basics)
    ('e2000000-0000-0000-0000-000000000006', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000002', 'Supervised vs Unsupervised Learning', 'Supervised learning trains on labeled datasets; unsupervised learning discovers hidden patterns in unlabeled data.', 1, 'TEXT', 25, NULL),
    ('e2000000-0000-0000-0000-000000000007', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000002', 'Linear Regression & Classification', 'Predicting continuous targets with linear regression, and classifying categorical outcomes.', 2, 'TEXT', 20, NULL),

    -- Course 1, Module 3 (Neural Networks)
    ('e2000000-0000-0000-0000-000000000008', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000003', 'Introduction to Artificial Neurons', 'Perceptron structure: inputs, weights, bias, activation functions (ReLU, Sigmoid), and forward pass.', 1, 'TEXT', 30, NULL),

    -- Course 2, Module 1 (Introduction to Databases)
    ('e2000000-0000-0000-0000-000000000009', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000004', 'What is a Database?', 'Organized collection of structured information stored electronically and managed by a DBMS.', 1, 'VIDEO', 12, 'https://www.youtube.com/watch?v=hRulZhTtUTg'),
    ('e2000000-0000-0000-0000-000000000010', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000004', 'File Systems vs DBMS', 'Comparing concurrency control, ACID transaction isolation, crash recovery, and redundancy.', 2, 'TEXT', 15, NULL),
    ('e2000000-0000-0000-0000-000000000011', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000004', 'Relational Model', 'Structuring data using tables, tuples, primary keys, foreign keys and integrity constraints.', 3, 'QUIZ', 20, NULL),
    ('e2000000-0000-0000-0000-000000000012', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000004', 'NoSQL Databases', 'Document stores, key-value caches, and graph databases for high scalability.', 4, 'TEXT', 15, NULL),

    -- Course 2, Module 2 (SQL Fundamentals)
    ('e2000000-0000-0000-0000-000000000013', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000005', 'DDL and DML Queries', 'Data Definition (CREATE, ALTER) and Data Manipulation (SELECT, INSERT, UPDATE, DELETE).', 1, 'TEXT', 25, NULL),
    ('e2000000-0000-0000-0000-000000000014', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000005', 'JOIN Operations', 'INNER JOIN, LEFT JOIN, RIGHT JOIN, and FULL OUTER JOIN with query optimization.', 2, 'TEXT', 25, NULL),

    -- Course 2, Module 3 (Database Design)
    ('e2000000-0000-0000-0000-000000000015', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000006', 'Database Normalization (1NF - BCNF)', 'Eliminating redundancy and update anomalies via 1NF, 2NF, 3NF, and Boyce-Codd Normal Form.', 1, 'TEXT', 35, NULL),

    -- Course 3, Module 1 (Introduction to Data Structures)
    ('e2000000-0000-0000-0000-000000000016', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000007', 'What is a Data Structure?', 'Memory organization concepts and choosing suitable structures for optimal algorithms.', 1, 'VIDEO', 15, 'https://www.youtube.com/watch?v=RBSGKlAvoiM'),
    ('e2000000-0000-0000-0000-000000000017', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000007', 'Arrays vs Linked Lists', 'Core differences between contiguous memory allocations vs pointer-linked heap nodes.', 2, 'QUIZ', 20, NULL),
    ('e2000000-0000-0000-0000-000000000018', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000007', 'Time Complexity (Big O)', 'Asymptotic runtime bounds: O(1), O(log N), O(N), O(N log N), and O(N^2).', 3, 'TEXT', 25, NULL),
    ('e2000000-0000-0000-0000-000000000019', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000007', 'Space Complexity', 'Understanding auxiliary memory footprint and stack overhead in recursion.', 4, 'TEXT', 15, NULL),

    -- Course 3, Module 2 (Stacks & Queues)
    ('e2000000-0000-0000-0000-000000000020', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000008', 'Stack Operations & Applications', 'Push, pop, peek implementation. Use-cases: parenthesis matching, undo history, evaluation.', 1, 'TEXT', 20, NULL),

    -- Course 3, Module 3 (Trees & Graphs)
    ('e2000000-0000-0000-0000-000000000021', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000009', 'Binary Search Trees (BST)', 'BST properties, insertion, deletion, and tree traversals (inorder, preorder, postorder).', 1, 'TEXT', 30, NULL),

    -- Course 4, Module 1 (Logic & Proofs)
    ('e2000000-0000-0000-0000-000000000022', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000010', 'Propositional Logic', 'Logical propositions, truth tables, negation, conjunction, disjunction, and implication.', 1, 'VIDEO', 15, 'https://www.youtube.com/watch?v=itrXYg41-V0'),
    ('e2000000-0000-0000-0000-000000000023', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000010', 'Logical Equivalences', 'De Morgan laws, contrapositive identities, and simplifying boolean expressions.', 2, 'TEXT', 20, NULL),
    ('e2000000-0000-0000-0000-000000000024', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000010', 'Predicates & Quantifiers', 'Universal (∀) and existential (∃) quantifiers, domain of discourse, and negations.', 3, 'TEXT', 20, NULL),
    ('e2000000-0000-0000-0000-000000000025', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000010', 'Rules of Inference', 'Modus Ponens, Modus Tollens, Hypothetical Syllogism, and constructing formal proofs.', 4, 'TEXT', 25, NULL),
    ('e2000000-0000-0000-0000-000000000026', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000010', 'Introduction to Proofs', 'Direct proof, proof by contraposition, and proof by contradiction.', 5, 'TEXT', 25, NULL),

    -- Course 4, Module 2 (Set Theory)
    ('e2000000-0000-0000-0000-000000000027', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000011', 'Sets and Set Operations', 'Power sets, Cartesian products, union, intersection, set differences, and Venn diagrams.', 1, 'TEXT', 20, NULL),

    -- Course 4, Module 3 (Graph Theory)
    ('e2000000-0000-0000-0000-000000000028', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000012', 'Graph Representation & Paths', 'Adjacency matrices, Handshaking Theorem, Euler paths, and Hamilton cycles.', 1, 'TEXT', 25, NULL),

    -- Course 5: PRF192, Module 1 (C Basics & Control Flow)
    ('e2000000-0000-0000-0000-000000000029', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000013', 'Làm quen với C & Cấu trúc chương trình chuẩn FPT', 'Tổng quan về quá trình biên dịch mã nguồn C, hàm main(), thư viện stdio.h và cách viết mã nguồn chuẩn phong cách lập trình.', 1, 'VIDEO', 15, 'https://www.youtube.com/watch?v=KJgsSFOSQv0'),
    ('e2000000-0000-0000-0000-000000000030', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000013', 'Kiểu dữ liệu, Toán tử & Vào ra chuẩn printf/scanf', 'Nắm vững các kiểu số nguyên, số thực, ký tự, ép kiểu, định dạng %d, %f, %c và kỹ thuật xử lý dấu cách khi nhập liệu.', 2, 'TEXT', 20, NULL),
    ('e2000000-0000-0000-0000-000000000031', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000013', 'Cấu trúc điều khiển rẽ nhánh và Vòng lặp', 'Cú pháp if-else lồng nhau, toán tử 3 ngôi, lệnh switch-case và các vòng lặp for, while, do-while giải quyết bài toán lặp.', 3, 'TEXT', 25, NULL),

    -- Course 5: PRF192, Module 2 (Functions, Arrays & Strings)
    ('e2000000-0000-0000-0000-000000000032', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000014', 'Hàm trong C & Kỹ thuật truyền tham số', 'Khai báo nguyên mẫu hàm (prototype), định nghĩa hàm, phạm vi biến cục bộ/toàn cục và cơ chế truyền tham số.', 1, 'TEXT', 20, NULL),
    ('e2000000-0000-0000-0000-000000000033', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000014', 'Mảng 1D, 2D & Thuật toán tìm kiếm, sắp xếp', 'Khai báo và duyệt mảng, tìm phần tử lớn nhất/nhỏ nhất, thuật toán Bubble Sort và Selection Sort chuẩn đề thi Practical Exam.', 2, 'TEXT', 30, NULL),
    ('e2000000-0000-0000-0000-000000000034', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000014', 'Chuỗi ký tự trong C & Kỹ thuật xử lý xâu chuẩn PE', 'Ký tự kết thúc chuỗi \0, các hàm strlen, strcpy, strcmp, strcat trong thư viện string.h và bẫy lỗi trôi lệnh khi nhập chuỗi.', 3, 'TEXT', 25, NULL),

    -- Course 5: PRF192, Module 3 (Pointers & Dynamic Memory)
    ('e2000000-0000-0000-0000-000000000035', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000015', 'Bản chất con trỏ (Pointer), toán tử * và &', 'Tìm hiểu kiến trúc ô nhớ, địa chỉ biến, con trỏ NULL, phép toán số học trên con trỏ và mối quan hệ giữa con trỏ và mảng.', 1, 'VIDEO', 20, 'https://www.youtube.com/watch?v=zuegQmMdy8M'),
    ('e2000000-0000-0000-0000-000000000036', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000015', 'Cấp phát bộ nhớ động & Kiểu cấu trúc Struct', 'Sử dụng malloc, calloc, realloc, hàm giải phóng bộ nhớ free() để chống rò rỉ bộ nhớ, định nghĩa struct quản lý sinh viên.', 2, 'TEXT', 30, NULL),
    ('e2000000-0000-0000-0000-000000000037', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000015', 'Quiz: Kiểm tra trọng tâm Con trỏ & Bộ nhớ trong C (Ôn thi FE)', 'Bài kiểm tra trắc nghiệm tổng hợp đánh giá hiểu biết sâu về con trỏ, toán tử tham chiếu, cấp phát động và thao tác mảng.', 3, 'QUIZ', 20, NULL),

    -- Course 6: PRO192, Module 1 (Java Fundamentals & Encapsulation)
    ('e2000000-0000-0000-0000-000000000038', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000016', 'Tổng quan Java, JDK & Lớp - Đối tượng', 'Hiểu về máy ảo JVM, Bytecode, viết class đầu tiên, khai báo trường (field), phương thức (method) và tạo instance bằng từ khóa new.', 1, 'VIDEO', 18, 'https://www.youtube.com/watch?v=xk4_1vDrzzo'),
    ('e2000000-0000-0000-0000-000000000039', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000016', 'Tính đóng gói (Encapsulation), Access Modifiers & Constructor', 'Bảo vệ dữ liệu với private/protected/public, kỹ thuật viết Constructor mặc định và có tham số, sử dụng từ khóa this và Getter/Setter.', 2, 'TEXT', 25, NULL),

    -- Course 6: PRO192, Module 2 (Inheritance, Polymorphism & Interface)
    ('e2000000-0000-0000-0000-000000000040', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000017', 'Tính kế thừa (Inheritance) & Từ khóa super', 'Tái sử dụng mã nguồn với extends, gọi constructor của lớp cha bằng super(), quan hệ IS-A trong thiết kế phần mềm hướng đối tượng.', 1, 'VIDEO', 20, 'https://www.youtube.com/watch?v=aFZ-0bbSLi8'),
    ('e2000000-0000-0000-0000-000000000041', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000017', 'Tính đa hình (Polymorphism), Override & Abstract Class, Interface', 'Ghi đè phương thức (@Override), nạp chồng phương thức (Overload), phân biệt Abstract Class và Interface chuẩn yêu cầu bài thi PE FPT.', 2, 'TEXT', 30, NULL),
    ('e2000000-0000-0000-0000-000000000042', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000017', 'Quiz: Kiểm tra 4 tính chất OOP & Interface trong Java (Ôn thi FE)', 'Bài kiểm tra trắc nghiệm đánh giá kiến thức về tính kế thừa, tính đa hình, cơ chế liên kết động (dynamic binding) và Interface.', 3, 'QUIZ', 20, NULL),

    -- Course 6: PRO192, Module 3 (Java Collections & Exceptions)
    ('e2000000-0000-0000-0000-000000000043', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000018', 'Java Collections Framework: ArrayList & Sắp xếp dữ liệu', 'Thực hành ArrayList, duyệt danh sách với Iterator / for-each, sắp xếp danh sách đối tượng bằng Comparable (compareTo) và Comparator.', 1, 'TEXT', 30, NULL),
    ('e2000000-0000-0000-0000-000000000044', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000018', 'Cơ chế xử lý ngoại lệ (Exception Handling) trong Java', 'Phân biệt Checked Exception và Unchecked Exception, cấu trúc try-catch-finally, từ khóa throw và throws khi lập trình an toàn.', 2, 'TEXT', 20, NULL),

    -- Course 7: PRJ301, Module 1 (Web Architecture & Servlet Lifecycle)
    ('e2000000-0000-0000-0000-000000000045', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000019', 'Kiến trúc Web Client-Server & Tổng quan về Java Servlet', 'Giao thức HTTP, chu kỳ Request-Response, cài đặt Apache Tomcat và cấu hình Servlet đầu tiên qua file web.xml và @WebServlet.', 1, 'VIDEO', 20, 'https://www.youtube.com/watch?v=OuBUUkQfBYM'),
    ('e2000000-0000-0000-0000-000000000046', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000019', 'Vòng đời Servlet (Lifecycle) & Chuyển tiếp RequestDispatcher', 'Các giai đoạn init(), service(), destroy(), đọc tham số với getParameter(), chuyển hướng redirect (sendRedirect) vs forward.', 2, 'TEXT', 25, NULL),

    -- Course 7: PRJ301, Module 2 (JSP, JSTL & MVC Pattern)
    ('e2000000-0000-0000-0000-000000000020', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000020', 'JSP cơ bản, Expression Language (EL) & Thư viện thẻ JSTL Core', 'Cú pháp JSP, dùng EL (${user.name}) loại bỏ mã Java trong view, thực hành các thẻ c:if, c:forEach, c:choose để hiển thị bảng dữ liệu.', 1, 'TEXT', 25, NULL),
    ('e2000000-0000-0000-0000-000000000048', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000020', 'Mô hình kiến trúc MVC Model 2 trong ứng dụng Java Web', 'Phân tách trách nhiệm: Model (JavaBeans, DAO), View (JSP), Controller (Servlet). Luồng dữ liệu chuẩn từ người dùng đến cơ sở dữ liệu.', 2, 'TEXT', 30, NULL),

    -- Course 7: PRJ301, Module 3 (JDBC, Session & Authentication)
    ('e2000000-0000-0000-0000-000000000049', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000021', 'Kết nối CSDL SQL Server với JDBC & Mẫu thiết kế DAO', 'Đăng ký Driver, mở Connection, thực thi PreparedStatement để chống SQL Injection, ánh xạ ResultSet sang đối tượng Java Model.', 1, 'VIDEO', 25, 'https://www.youtube.com/watch?v=y_YxwyYRJek'),
    ('e2000000-0000-0000-0000-000000000050', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000021', 'Quản lý phiên làm việc: Cookie, HttpSession & Filter bảo mật', 'Cơ chế lưu trạng thái người dùng với Cookie, HttpSession (setAttribute, getAttribute, invalidate), và tạo Filter kiểm tra quyền truy cập.', 2, 'TEXT', 30, NULL),
    ('e2000000-0000-0000-0000-000000000051', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000021', 'Quiz: Kiểm tra kiến trúc Servlet, JSP Lifecycle & Quản lý Session (Ôn thi FE)', 'Bài kiểm tra trắc nghiệm trọng tâm đánh giá hiểu biết về vòng đời Servlet, phạm vi biến trong JSP/Servlet (Request, Session, Application) và Filter.', 3, 'QUIZ', 20, NULL)
ON CONFLICT (id) DO NOTHING;
