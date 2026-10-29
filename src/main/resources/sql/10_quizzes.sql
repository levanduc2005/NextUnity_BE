-- ==============================================================================
-- 10_quizzes.sql
-- Seed Data for Quizzes, Questions, and Question Options
-- ==============================================================================

-- 1. SEED QUIZZES
INSERT INTO quizzes (
    id, created_at, updated_at,
    lesson_id, title, description, passing_score, duration_minutes, status
)
VALUES
    (
        'e7000000-0000-0000-0000-000000000001', NOW(), NOW(),
        'e2000000-0000-0000-0000-000000000001',
        'Quiz: Tổng quan về Trí tuệ Nhân tạo (AI)',
        'Bài trắc nghiệm đánh giá hiểu biết cơ bản về định nghĩa, lịch sử và ứng dụng thực tiễn của AI.',
        70, 15, 'PUBLISHED'
    ),
    (
        'e7000000-0000-0000-0000-000000000003', NOW(), NOW(),
        'e2000000-0000-0000-0000-000000000003',
        'Quiz: Phân loại các cấp độ Trí tuệ Nhân tạo',
        'Đánh giá hiểu biết sâu về Narrow AI (ANI), General AI (AGI) và Super AI (ASI).',
        80, 15, 'PUBLISHED'
    ),
    (
        'e7000000-0000-0000-0000-000000000011', NOW(), NOW(),
        'e2000000-0000-0000-0000-000000000011',
        'Quiz: Mô hình Dữ liệu Quan hệ & Ràng buộc toàn vẹn',
        'Đánh giá kiến thức về Primary Key, Foreign Key và tính toàn vẹn tham chiếu trong cơ sở dữ liệu quan hệ.',
        75, 20, 'PUBLISHED'
    ),
    (
        'e7000000-0000-0000-0000-000000000017', NOW(), NOW(),
        'e2000000-0000-0000-0000-000000000017',
        'Quiz: So sánh Mảng (Array) và Danh sách liên kết (Linked List)',
        'Đánh giá hiểu biết về cấu trúc bộ nhớ, độ phức tạp thuật toán và trường hợp sử dụng phù hợp.',
        70, 15, 'PUBLISHED'
    ),
    (
        'e7000000-0000-0000-0000-000000000037', NOW(), NOW(),
        'e2000000-0000-0000-0000-000000000037',
        'Quiz: Kiểm tra trọng tâm Con trỏ & Bộ nhớ trong C (Ôn thi FE)',
        'Đánh giá hiểu biết cốt lõi về con trỏ, toán tử giải tham chiếu, cấp phát bộ nhớ động malloc/free và thao tác mảng.',
        70, 15, 'PUBLISHED'
    ),
    (
        'e7000000-0000-0000-0000-000000000042', NOW(), NOW(),
        'e2000000-0000-0000-0000-000000000042',
        'Quiz: Kiểm tra 4 tính chất OOP & Interface trong Java (Ôn thi FE)',
        'Đánh giá kiến thức về tính kế thừa, tính đa hình, cơ chế liên kết động (dynamic binding) và Interface trong Java.',
        75, 15, 'PUBLISHED'
    ),
    (
        'e7000000-0000-0000-0000-000000000051', NOW(), NOW(),
        'e2000000-0000-0000-0000-000000000051',
        'Quiz: Kiểm tra kiến trúc Servlet, JSP Lifecycle & Quản lý Session (Ôn thi FE)',
        'Đánh giá hiểu biết về vòng đời Servlet, phân biệt forward và sendRedirect, kiến trúc MVC 2 và quản lý phiên người dùng.',
        75, 15, 'PUBLISHED'
    )
ON CONFLICT (id) DO NOTHING;


-- 2. SEED QUESTIONS
INSERT INTO questions (
    id, created_at, updated_at,
    quiz_id, content, type, points, order_index, explanation
)
VALUES
    -- Quiz 1 Questions (AI Overview)
    (
        'e8000000-0000-0000-0000-000000000001', NOW(), NOW(),
        'e7000000-0000-0000-0000-000000000001',
        'Thuật ngữ "Artificial Intelligence" (Trí tuệ nhân tạo) được chính thức đưa ra tại sự kiện khoa học nào?',
        'SINGLE_CHOICE', 10.0, 1,
        'Hội thảo Dartmouth năm 1956 do John McCarthy và các cộng sự tổ chức được coi là cái nôi khai sinh thuật ngữ AI.'
    ),
    (
        'e8000000-0000-0000-0000-000000000002', NOW(), NOW(),
        'e7000000-0000-0000-0000-000000000001',
        'Những ứng dụng nào sau đây áp dụng công nghệ Trí tuệ Nhân tạo trong đời sống? (Chọn tất cả đáp án đúng)',
        'MULTIPLE_CHOICE', 10.0, 2,
        'Hệ thống gợi ý phim, trợ lý ảo giọng nói và thuật toán tự hành đều sử dụng học máy và AI.'
    ),
    (
        'e8000000-0000-0000-0000-000000000003', NOW(), NOW(),
        'e7000000-0000-0000-0000-000000000001',
        'Phép thử Turing (Turing Test) được đề xuất nhằm kiểm tra khả năng một cỗ máy có thể biểu hiện trí tuệ tương đương con người.',
        'SINGLE_CHOICE', 10.0, 3,
        'Alan Turing đề xuất phép thử này năm 1950 trong bài báo nổi tiếng Computing Machinery and Intelligence.'
    ),

    -- Quiz 2 Questions (AI Types)
    (
        'e8000000-0000-0000-0000-000000000004', NOW(), NOW(),
        'e7000000-0000-0000-0000-000000000003',
        'Đặc điểm nổi bật của Trí tuệ nhân tạo hẹp (Narrow AI / Weak AI) là gì?',
        'SINGLE_CHOICE', 10.0, 1,
        'Narrow AI được thiết kế và huấn luyện chuyên sâu để thực hiện một tác vụ cụ thể vượt trội (như chơi cờ, nhận diện khuôn mặt).'
    ),
    (
        'e8000000-0000-0000-0000-000000000005', NOW(), NOW(),
        'e7000000-0000-0000-0000-000000000003',
        'Trí tuệ nhân tạo tổng quát (AGI - Artificial General Intelligence) hiện nay đã được hiện thực hóa và ứng dụng phổ biến trên toàn cầu.',
        'SINGLE_CHOICE', 10.0, 2,
        'AGI vẫn đang trong giai đoạn nghiên cứu lý thuyết và phát triển học thuật, chưa cỗ máy nào đạt được trí tuệ ngang tầm con người trên mọi lĩnh vực.'
    ),

    -- Quiz 3 Questions (Relational Model)
    (
        'e8000000-0000-0000-0000-000000000006', NOW(), NOW(),
        'e7000000-0000-0000-0000-000000000011',
        'Đặc điểm nào sau đây là BẮT BUỘC đối với Khóa chính (Primary Key) trong cơ sở dữ liệu quan hệ?',
        'SINGLE_CHOICE', 10.0, 1,
        'Khóa chính dùng để định danh duy nhất mỗi bản ghi, do đó bắt buộc phải có giá trị độc nhất (Unique) và không được phép chứa giá trị NULL.'
    ),
    (
        'e8000000-0000-0000-0000-000000000007', NOW(), NOW(),
        'e7000000-0000-0000-0000-000000000011',
        'Những khẳng định nào sau đây là ĐÚNG về Khóa ngoại (Foreign Key)? (Chọn tất cả đáp án đúng)',
        'MULTIPLE_CHOICE', 10.0, 2,
        'Khóa ngoại dùng để tạo mối liên kết giữa 2 bảng và duy trì ràng buộc toàn vẹn tham chiếu (Referential Integrity).'
    ),
    (
        'e8000000-0000-0000-0000-000000000008', NOW(), NOW(),
        'e7000000-0000-0000-0000-000000000011',
        'Theo chuẩn 1NF (Chuẩn 1), một ô thuộc tính trong bảng có thể chứa một mảng gồm nhiều số điện thoại của sinh viên.',
        'SINGLE_CHOICE', 10.0, 3,
        'Sai. Chuẩn 1NF yêu cầu mọi thuộc tính phải mang giá trị nguyên tố (Atomic), không được chứa danh sách lặp lại hay mảng con.'
    ),

    -- Quiz 4 Questions (Arrays vs Linked Lists)
    (
        'e8000000-0000-0000-0000-000000000009', NOW(), NOW(),
        'e7000000-0000-0000-0000-000000000017',
        'Độ phức tạp thời gian để truy cập ngẫu nhiên phần tử thứ k trong Mảng (Array) là bao nhiêu?',
        'SINGLE_CHOICE', 10.0, 1,
        'Do mảng cấp phát bộ nhớ liên tục nên vị trí phần tử thứ k được tính toán trực tiếp qua công thức toán học O(1).'
    ),
    (
        'e8000000-0000-0000-0000-000000000010', NOW(), NOW(),
        'e7000000-0000-0000-0000-000000000017',
        'Thao tác chèn phần tử mới vào đầu Danh sách liên kết đơn (Singly Linked List) mất độ phức tạp thời gian là:',
        'SINGLE_CHOICE', 10.0, 2,
        'Chèn vào đầu danh sách liên kết chỉ cần trỏ next của node mới vào head cũ và gán head mới, luôn đạt O(1).'
    ),

    -- Quiz 5 Questions (PRF192 - Con trỏ & Bộ nhớ trong C)
    (
        'e8000000-0000-0000-0000-000000000011', NOW(), NOW(),
        'e7000000-0000-0000-0000-000000000037',
        'Trong ngôn ngữ C, cho đoạn mã: int a = 10; int *p = &a; *p = 20; Giá trị của biến a sau các câu lệnh trên là bao nhiêu?',
        'SINGLE_CHOICE', 10.0, 1,
        'Con trỏ p trỏ tới địa chỉ của biến a. Phép gán *p = 20 thông qua toán tử giải tham chiếu sẽ thay đổi trực tiếp giá trị của a thành 20.'
    ),
    (
        'e8000000-0000-0000-0000-000000000012', NOW(), NOW(),
        'e7000000-0000-0000-0000-000000000037',
        'Hàm nào trong thư viện stdlib.h được sử dụng để giải phóng vùng nhớ đã cấp phát động nhằm tránh rò rỉ bộ nhớ (Memory Leak)?',
        'SINGLE_CHOICE', 10.0, 2,
        'Hàm free(ptr) dùng để hoàn trả vùng nhớ động được cấp phát bởi malloc, calloc hoặc realloc về cho hệ điều hành.'
    ),
    (
        'e8000000-0000-0000-0000-000000000013', NOW(), NOW(),
        'e7000000-0000-0000-0000-000000000037',
        'Những phát biểu nào sau đây là ĐÚNG về con trỏ và mảng trong ngôn ngữ C? (Chọn tất cả đáp án đúng)',
        'MULTIPLE_CHOICE', 10.0, 3,
        'Tên mảng đại diện cho địa chỉ phần tử đầu tiên (&arr[0]). Phép truy xuất *(arr + i) hoàn toàn tương đương với arr[i].'
    ),

    -- Quiz 6 Questions (PRO192 - OOP trong Java)
    (
        'e8000000-0000-0000-0000-000000000014', NOW(), NOW(),
        'e7000000-0000-0000-0000-000000000042',
        'Từ khóa nào trong Java được sử dụng để một lớp con kế thừa lại các thuộc tính và phương thức từ một lớp cha?',
        'SINGLE_CHOICE', 10.0, 1,
        'Java sử dụng từ khóa extends để kế thừa lớp cha và implements để thực thi Interface.'
    ),
    (
        'e8000000-0000-0000-0000-000000000015', NOW(), NOW(),
        'e7000000-0000-0000-0000-000000000042',
        'Một Interface trong Java (từ phiên bản Java 8 trở đi) có thể chứa những thành phần nào sau đây? (Chọn tất cả đáp án đúng)',
        'MULTIPLE_CHOICE', 10.0, 2,
        'Từ Java 8, Interface có thể chứa phương thức trừu tượng, hằng số public static final, và phương thức có phần thân mặc định với từ khóa default hoặc static.'
    ),
    (
        'e8000000-0000-0000-0000-000000000016', NOW(), NOW(),
        'e7000000-0000-0000-0000-000000000042',
        'Trong Java, một lớp có thể kế thừa trực tiếp (extends) từ bao nhiêu lớp cha?',
        'SINGLE_CHOICE', 10.0, 3,
        'Java chỉ hỗ trợ đơn kế thừa giữa các lớp (Duy nhất 1 lớp cha) nhằm tránh xung đột đa kế thừa (Diamond Problem).'
    ),

    -- Quiz 7 Questions (PRJ301 - Servlet, JSP & MVC)
    (
        'e8000000-0000-0000-0000-000000000017', NOW(), NOW(),
        'e7000000-0000-0000-0000-000000000051',
        'Trong vòng đời (Lifecycle) của Java Servlet, phương thức init() được gọi bao nhiêu lần?',
        'SINGLE_CHOICE', 10.0, 1,
        'Phương thức init() chỉ được gọi duy nhất 1 lần khi Servlet được nạp và khởi tạo lần đầu bởi Servlet Container.'
    ),
    (
        'e8000000-0000-0000-0000-000000000018', NOW(), NOW(),
        'e7000000-0000-0000-0000-000000000051',
        'Sự khác biệt cốt lõi giữa RequestDispatcher.forward() và HttpServletResponse.sendRedirect() là gì?',
        'SINGLE_CHOICE', 10.0, 2,
        'forward() chuyển tiếp nội bộ trên Server nên giữ nguyên Request và URL không đổi; sendRedirect() gửi mã chuyển hướng về Browser yêu cầu gửi Request mới nên URL thay đổi.'
    ),
    (
        'e8000000-0000-0000-0000-000000000019', NOW(), NOW(),
        'e7000000-0000-0000-0000-000000000051',
        'Trong mô hình kiến trúc MVC Model 2 của Java Web, thành phần nào giữ vai trò là "Controller" tiếp nhận và điều phối luồng xử lý?',
        'SINGLE_CHOICE', 10.0, 3,
        'Trong Java Web MVC, Servlet đóng vai trò Controller tiếp nhận request, gọi Model/DAO lấy dữ liệu và chuyển tiếp sang JSP (View) để hiển thị giao diện.'
    )
ON CONFLICT (id) DO NOTHING;


-- 3. SEED QUESTION OPTIONS
INSERT INTO question_options (
    id, created_at, updated_at,
    question_id, content, is_correct, order_index
)
VALUES
    -- Options for Question 1 (AI Event)
    ('e9000000-0000-0000-0000-000000000001', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000001', 'Hội thảo Dartmouth năm 1956', true, 1),
    ('e9000000-0000-0000-0000-000000000002', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000001', 'Hội nghị Turing năm 1950', false, 2),
    ('e9000000-0000-0000-0000-000000000003', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000001', 'Lễ khánh thành MIT AI Lab năm 1965', false, 3),
    ('e9000000-0000-0000-0000-000000000004', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000001', 'Hội nghị Diễn đàn Kinh tế Thế giới Davos', false, 4),

    -- Options for Question 2 (AI Applications)
    ('e9000000-0000-0000-0000-000000000005', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000002', 'Hệ thống gợi ý phim cá nhân hóa của Netflix', true, 1),
    ('e9000000-0000-0000-0000-000000000006', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000002', 'Hệ thống tự lái (Autopilot) của xe hơi thông minh', true, 2),
    ('e9000000-0000-0000-0000-000000000007', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000002', 'Đồng hồ bấm giờ cơ học truyền thống', false, 3),
    ('e9000000-0000-0000-0000-000000000008', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000002', 'Trợ lý ảo giọng nói (Apple Siri, Google Assistant)', true, 4),

    -- Options for Question 3 (Turing Test)
    ('e9000000-0000-0000-0000-000000000009', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000003', 'Đúng', true, 1),
    ('e9000000-0000-0000-0000-000000000010', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000003', 'Sai', false, 2),

    -- Options for Question 4 (Narrow AI)
    ('e9000000-0000-0000-0000-000000000011', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000004', 'Được tối ưu hóa xuất sắc cho một nhiệm vụ cụ thể', true, 1),
    ('e9000000-0000-0000-0000-000000000012', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000004', 'Có nhận thức và cảm xúc giống hệt con người', false, 2),
    ('e9000000-0000-0000-0000-000000000013', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000004', 'Tự động giải quyết mọi bài toán chưa từng được học', false, 3),

    -- Options for Question 5 (AGI)
    ('e9000000-0000-0000-0000-000000000014', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000005', 'Đúng', false, 1),
    ('e9000000-0000-0000-0000-000000000015', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000005', 'Sai', true, 2),

    -- Options for Question 6 (Primary Key)
    ('e9000000-0000-0000-0000-000000000016', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000006', 'Phải có giá trị duy nhất (Unique) và không được phép chứa giá trị NULL', true, 1),
    ('e9000000-0000-0000-0000-000000000017', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000006', 'Có thể nhận giá trị NULL nếu bảng chưa có dữ liệu', false, 2),
    ('e9000000-0000-0000-0000-000000000018', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000006', 'Bắt buộc phải là kiểu số nguyên tự tăng (Auto-increment)', false, 3),

    -- Options for Question 7 (Foreign Key)
    ('e9000000-0000-0000-0000-000000000019', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000007', 'Dùng để thiết lập liên kết quan hệ giữa các bảng', true, 1),
    ('e9000000-0000-0000-0000-000000000020', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000007', 'Giá trị phải tham chiếu đến khóa chính hợp lệ hoặc mang giá trị NULL', true, 2),
    ('e9000000-0000-0000-0000-000000000021', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000007', 'Một bảng chỉ được phép có tối đa 1 khóa ngoại', false, 3),
    ('e9000000-0000-0000-0000-000000000022', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000007', 'Đảm bảo tính toàn vẹn tham chiếu (Referential Integrity)', true, 4),

    -- Options for Question 8 (1NF)
    ('e9000000-0000-0000-0000-000000000023', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000008', 'Đúng', false, 1),
    ('e9000000-0000-0000-0000-000000000024', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000008', 'Sai', true, 2),

    -- Options for Question 9 (Array random access)
    ('e9000000-0000-0000-0000-000000000025', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000009', 'O(1)', true, 1),
    ('e9000000-0000-0000-0000-000000000026', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000009', 'O(N)', false, 2),
    ('e9000000-0000-0000-0000-000000000027', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000009', 'O(log N)', false, 3),

    -- Options for Question 10 (Linked List Insert Head)
    ('e9000000-0000-0000-0000-000000000028', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000010', 'O(1)', true, 1),
    ('e9000000-0000-0000-0000-000000000029', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000010', 'O(N)', false, 2),
    ('e9000000-0000-0000-0000-000000000030', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000010', 'O(N^2)', false, 3),

    -- Options for Question 11 (PRF192 - Con trỏ a=10, *p=20)
    ('e9000000-0000-0000-0000-000000000031', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000011', '20', true, 1),
    ('e9000000-0000-0000-0000-000000000032', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000011', '10', false, 2),
    ('e9000000-0000-0000-0000-000000000033', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000011', 'Địa chỉ ô nhớ của biến a', false, 3),
    ('e9000000-0000-0000-0000-000000000034', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000011', 'Lỗi biên dịch mã nguồn', false, 4),

    -- Options for Question 12 (PRF192 - Hàm giải phóng bộ nhớ)
    ('e9000000-0000-0000-0000-000000000035', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000012', 'free()', true, 1),
    ('e9000000-0000-0000-0000-000000000036', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000012', 'delete()', false, 2),
    ('e9000000-0000-0000-0000-000000000037', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000012', 'clear()', false, 3),
    ('e9000000-0000-0000-0000-000000000038', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000012', 'release()', false, 4),

    -- Options for Question 13 (PRF192 - Con trỏ và Mảng)
    ('e9000000-0000-0000-0000-000000000039', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000013', 'Tên mảng đại diện cho địa chỉ của phần tử đầu tiên (&arr[0])', true, 1),
    ('e9000000-0000-0000-0000-000000000040', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000013', 'Biểu thức *(arr + i) tương đương hoàn toàn với arr[i]', true, 2),
    ('e9000000-0000-0000-0000-000000000041', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000013', 'Có thể gán arr = ptr để thay đổi địa chỉ của cả mảng', false, 3),
    ('e9000000-0000-0000-0000-000000000042', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000013', 'Toán tử sizeof của biến con trỏ trên hệ thống 64-bit thường trả về 8 bytes', true, 4),

    -- Options for Question 14 (PRO192 - Kế thừa trong Java)
    ('e9000000-0000-0000-0000-000000000043', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000014', 'extends', true, 1),
    ('e9000000-0000-0000-0000-000000000044', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000014', 'implements', false, 2),
    ('e9000000-0000-0000-0000-000000000045', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000014', 'inherits', false, 3),
    ('e9000000-0000-0000-0000-000000000046', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000014', 'super', false, 4),

    -- Options for Question 15 (PRO192 - Interface Java 8+)
    ('e9000000-0000-0000-0000-000000000047', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000015', 'Các phương thức trừu tượng (Abstract methods)', true, 1),
    ('e9000000-0000-0000-0000-000000000048', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000015', 'Các hằng số mặc định public static final', true, 2),
    ('e9000000-0000-0000-0000-000000000049', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000015', 'Phương thức mặc định có thân hàm với từ khóa default', true, 3),
    ('e9000000-0000-0000-0000-000000000050', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000015', 'Hàm khởi tạo Constructor để tạo instance trực tiếp', false, 4),

    -- Options for Question 16 (PRO192 - Số lượng lớp cha)
    ('e9000000-0000-0000-0000-000000000051', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000016', 'Duy nhất 1 lớp cha trực tiếp', true, 1),
    ('e9000000-0000-0000-0000-000000000052', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000016', 'Không giới hạn số lượng lớp cha', false, 2),
    ('e9000000-0000-0000-0000-000000000053', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000016', 'Tối đa 2 lớp cha', false, 3),
    ('e9000000-0000-0000-0000-000000000054', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000016', 'Tùy thuộc vào phiên bản JDK sử dụng', false, 4),

    -- Options for Question 17 (PRJ301 - init() lifecycle)
    ('e9000000-0000-0000-0000-000000000055', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000017', 'Duy nhất 1 lần khi Servlet được khởi tạo', true, 1),
    ('e9000000-0000-0000-0000-000000000056', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000017', 'Mỗi khi có HTTP Request mới gửi đến', false, 2),
    ('e9000000-0000-0000-0000-000000000057', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000017', 'Mỗi khi người dùng Refresh lại trình duyệt', false, 3),
    ('e9000000-0000-0000-0000-000000000058', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000017', 'Được gọi định kỳ theo chu kỳ của Web Container', false, 4),

    -- Options for Question 18 (PRJ301 - forward vs sendRedirect)
    ('e9000000-0000-0000-0000-000000000059', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000018', 'forward() chuyển tiếp nội bộ trên Server (URL không đổi), sendRedirect() yêu cầu Browser gửi Request mới (URL thay đổi)', true, 1),
    ('e9000000-0000-0000-0000-000000000060', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000018', 'forward() làm thay đổi URL trên thanh địa chỉ, sendRedirect() giữ nguyên URL', false, 2),
    ('e9000000-0000-0000-0000-000000000061', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000018', 'Cả hai phương thức đều chia sẻ chung request attributes', false, 3),
    ('e9000000-0000-0000-0000-000000000062', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000018', 'sendRedirect() chỉ dùng được cho phương thức HTTP POST', false, 4),

    -- Options for Question 19 (PRJ301 - Controller trong MVC)
    ('e9000000-0000-0000-0000-000000000063', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000019', 'Java Servlet', true, 1),
    ('e9000000-0000-0000-0000-000000000064', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000019', 'Trang JSP (JavaServer Pages)', false, 2),
    ('e9000000-0000-0000-0000-000000000065', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000019', 'Lớp DAO (Data Access Object)', false, 3),
    ('e9000000-0000-0000-0000-000000000066', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000019', 'Tập tin cấu hình deployment descriptor (web.xml)', false, 4)
ON CONFLICT (id) DO NOTHING;
