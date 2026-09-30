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
    ('e9000000-0000-0000-0000-000000000030', NOW(), NOW(), 'e8000000-0000-0000-0000-000000000010', 'O(N^2)', false, 3)
ON CONFLICT (id) DO NOTHING;
