-- ==============================================================================
-- UPDATE SCRIPT: NANG CAP DU LIEU COMMUNITY CHUYEN NGHIEP & UY TIN (FPTU ECOSYSTEM)
-- Target Database: PostgreSQL (nextunity_db)
-- Bao gom: Xoa sach bai cu, Users mo rong, Tags chuan FPT, Posts chat luong cao, Comments & Reactions
-- ==============================================================================

BEGIN;

-- 0. DỌN DẸP SẠCH BÀI VIẾT CŨ & CÁC DỮ LIỆU LIÊN QUAN (Tránh lỗi khóa ngoại Foreign Key)
DELETE FROM reactions WHERE post_id IS NOT NULL OR comment_id IS NOT NULL;
DELETE FROM comments;
DELETE FROM post_tags;
DELETE FROM posts;

-- 1. Bổ sung các tài khoản Người dùng (Sinh viên & Mentor FPT các cơ sở)
INSERT INTO users (
    id, created_at, updated_at, deleted_at, role_id, email, password_hash,
    full_name, university, student_id, avatar_url, streak, gender, date_of_birth, cohort, major, bio,
    subscription_tier, premium_until
)
VALUES
    ('b0000000-0000-0000-0000-000000000006', NOW() - INTERVAL '30 days', NOW(), NULL, 'a0000000-0000-0000-0000-000000000003', 'baohq@fpt.edu.vn', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.',
     'Hoang Quoc Bao', 'FPT HN', 'HE160010', 'https://api.dicebear.com/7.x/avataaars/svg?seed=baohq', 25, 'MALE', '2001-04-12', 16, 'Software Engineering', 'Alumni K16 SE FPT Hanoi. Software Engineer @ FPT Software, Mentor SWP391.',
     'PLUS', NOW() + INTERVAL '60 days'),
    ('b0000000-0000-0000-0000-000000000007', NOW() - INTERVAL '30 days', NOW(), NULL, 'a0000000-0000-0000-0000-000000000003', 'chivtm@fpt.edu.vn', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.',
     'Vu Thi Mai Chi', 'FPT HCM', 'SE170025', 'https://api.dicebear.com/7.x/avataaars/svg?seed=chivtm', 20, 'FEMALE', '2002-09-18', 17, 'Information Assurance', 'K17 IA FPT HCM. DSA enthusiast, ICPC competitor, TA CSD201.',
     'PLUS', NOW() + INTERVAL '60 days'),
    ('b0000000-0000-0000-0000-000000000008', NOW() - INTERVAL '30 days', NOW(), NULL, 'a0000000-0000-0000-0000-000000000003', 'sondt@fpt.edu.vn', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.',
     'Dang Thai Son', 'FPT Quy Nhon', 'QE180015', 'https://api.dicebear.com/7.x/avataaars/svg?seed=sondt', 18, 'MALE', '2003-03-22', 18, 'Artificial Intelligence', 'K18 AI FPT Quy Nhon. Machine Learning & Python passionate.',
     'FREE', NULL),
    ('b0000000-0000-0000-0000-000000000009', NOW() - INTERVAL '30 days', NOW(), NULL, 'a0000000-0000-0000-0000-000000000003', 'namnh@fpt.edu.vn', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.',
     'Nguyen Hoang Nam', 'FPT Can Tho', 'CE170030', 'https://api.dicebear.com/7.x/avataaars/svg?seed=namnh', 14, 'MALE', '2002-12-05', 17, 'Software Engineering', 'K17 SE FPT Can Tho. Full-stack Web & Database developer.',
     'FREE', NULL),
    ('b0000000-0000-0000-0000-000000000010', NOW() - INTERVAL '30 days', NOW(), NULL, 'a0000000-0000-0000-0000-000000000003', 'ngoctb@fpt.edu.vn', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.',
     'Tran Bao Ngoc', 'FPT HCM', 'SE180050', 'https://api.dicebear.com/7.x/avataaars/svg?seed=ngoctb', 22, 'FEMALE', '2003-07-14', 18, 'Software Engineering', 'K18 SE FPT HCM. UI/UX design and Frontend lover.',
     'PLUS', NOW() + INTERVAL '60 days')
ON CONFLICT (id) DO UPDATE SET
    full_name = EXCLUDED.full_name,
    university = EXCLUDED.university,
    bio = EXCLUDED.bio,
    avatar_url = EXCLUDED.avatar_url;

-- 2. Cập nhật bảng Tags đầy đủ các môn học và chủ đề FPT
INSERT INTO tags (id, created_at, updated_at, name, description)
VALUES
    ('f1000000-0000-0000-0000-000000000001', NOW(), NOW(), 'PRN211', 'Lập trình C# & .NET FPT'),
    ('f1000000-0000-0000-0000-000000000002', NOW(), NOW(), 'PE', 'Kỳ thi Practical Exam FPT'),
    ('f1000000-0000-0000-0000-000000000003', NOW(), NOW(), 'SWP391', 'Đồ án kỹ thuật phần mềm'),
    ('f1000000-0000-0000-0000-000000000004', NOW(), NOW(), 'PRO192', 'Lập trình hướng đối tượng Java'),
    ('f1000000-0000-0000-0000-000000000005', NOW(), NOW(), 'PRF192', 'Lập trình C cơ bản & Thuật toán'),
    ('f1000000-0000-0000-0000-000000000006', NOW(), NOW(), 'CSD201', 'Cấu trúc dữ liệu & Giải thuật'),
    ('f1000000-0000-0000-0000-000000000007', NOW(), NOW(), 'DBI202', 'Hệ quản trị CSDL SQL Server'),
    ('f1000000-0000-0000-0000-000000000008', NOW(), NOW(), 'PRJ301', 'Lập trình Java Web Servlet/JSP'),
    ('f1000000-0000-0000-0000-000000000009', NOW(), NOW(), 'OJT_INTERNSHIP', 'Thực tập doanh nghiệp & FPT Software'),
    ('f1000000-0000-0000-0000-000000000010', NOW(), NOW(), 'TAI_LIEU', 'Chia sẻ tài liệu & Đề thi thử')
ON CONFLICT (id) DO UPDATE SET
    name = EXCLUDED.name,
    description = EXCLUDED.description;

-- 3. Cập nhật & Thêm mới Posts chuyên môn sâu
INSERT INTO posts (id, created_at, updated_at, deleted_at, title, content, author_id)
VALUES
    ('f2000000-0000-0000-0000-000000000001', NOW() - INTERVAL '2 hours', NOW(), NULL,
     '[Góc Giảng Viên] Hướng dẫn phương pháp ôn luyện & 5 cạm bẫy dễ mất điểm nhất trong kỳ thi PE FPTU',
     'Chào các bạn sinh viên FPTU,

Kỳ thi Practical Exam (PE) luôn là thử thách quyết định để qua môn đối với khối ngành Công nghệ thông tin. Thầy thấy nhiều bạn học lực rất tốt trên lớp nhưng khi thi PE vẫn bị điểm dưới trung bình vì những lỗi rất đáng tiếc. Thầy tổng hợp lại 5 cạm bẫy lớn nhất để các bạn lưu ý:

1. Không tuân thủ đúng định dạng Output: Hệ thống chấm PE chấm tự động từng ký tự. Nếu đề yêu cầu in "Result: 15" mà bạn in "result: 15" hoặc thừa khoảng trắng ở cuối dòng thì test case đó sẽ bị tính là SAI hoàn toàn.
2. Quên giải phóng con trỏ (Memory Leak) trong C (PRF192): Đối với các câu thao tác mảng động hoặc danh sách liên kết, quên free() có thể dẫn đến việc trình chấm báo Time Limit Exceeded hoặc Crash.
3. Không kiểm tra trường hợp danh sách rỗng (NullPointerException): Trong Java (PRO192, CSD201), luôn phải kiểm tra if (head == null) hoặc if (list.isEmpty()) trước khi duyệt.
4. Lỗi chuỗi kết nối Database trong PRJ301 / DBI202: Quên đổi tên database, sai cổng 1433 hoặc viết hard-coded mật khẩu dẫn đến không kết nối được DB trên máy phòng thi.
5. Không commit bài thi đúng thời hạn: Dành quá nhiều thời gian cho 1 câu hóc búa (thường là câu 2 điểm cuối) mà bỏ quên việc kiểm tra và nộp các câu cơ bản đã hoàn thành.

Chúc các bạn ôn tập thật kỹ, giữ tâm lý bình tĩnh và đạt điểm số tối đa trong kỳ thi PE sắp tới!',
     'b0000000-0000-0000-0000-000000000001')
ON CONFLICT (id) DO UPDATE SET
    title = EXCLUDED.title,
    content = EXCLUDED.content,
    author_id = EXCLUDED.author_id,
    created_at = EXCLUDED.created_at;

INSERT INTO posts (id, created_at, updated_at, deleted_at, title, content, author_id)
VALUES
    ('f2000000-0000-0000-0000-000000000002', NOW() - INTERVAL '5 hours', NOW(), NULL,
     'Bí kíp "phá đảo" PE CSD201: Cây nhị phân tìm kiếm BST & Thuật toán đồ thị Dijkstra',
     'Chào mọi người, kỳ trước mình may mắn đạt 10.0 PE môn CSD201 nên hôm nay muốn chia sẻ lại toàn bộ chiến thuật ôn thi cho các bạn đang chuẩn bị vượt qua môn này.

Đề thi PE CSD201 thường cố định cấu trúc 3 câu lớn:
- Câu 1 (3.5đ): Singly Linked List (Danh sách liên kết đơn). Thường yêu cầu thêm vào đầu/cuối, xóa node có giá trị lớn thứ hai, hoặc đảo ngược danh sách.
- Câu 2 (3.5đ): Binary Search Tree (Cây nhị phân tìm kiếm BST). Đề hay yêu cầu duyệt theo thứ tự Breadth-First Traversal (dùng Queue), tìm chiều cao cây, hoặc xóa node bằng kỹ thuật Copy / Merge.
- Câu 3 (3.0đ): Graph & Dijkstra Algorithm. Yêu cầu biểu diễn ma trận kề, duyệt đồ thị theo BFS/DFS hoặc tìm đường đi ngắn nhất giữa 2 đỉnh bằng thuật toán Dijkstra.

Kinh nghiệm xương máu: Các bạn hãy chuẩn bị sẵn một bộ khung template chuẩn cho Linked List và BST trong đầu. Khi vào phòng thi, chỉ mất 15 phút đầu tiên để gõ xong phần khung cơ bản, sau đó dành trọn vẹn thời gian để viết hàm xử lý logic theo đúng yêu cầu của đề.

Mình có đính kèm bộ 10 đề thi mẫu các kỳ gần nhất đã giải chi tiết, bạn nào cần thì để lại email ở bình luận nhé!',
     'b0000000-0000-0000-0000-000000000007')
ON CONFLICT (id) DO UPDATE SET
    title = EXCLUDED.title,
    content = EXCLUDED.content,
    author_id = EXCLUDED.author_id,
    created_at = EXCLUDED.created_at;

INSERT INTO posts (id, created_at, updated_at, deleted_at, title, content, author_id)
VALUES
    ('f2000000-0000-0000-0000-000000000003', NOW() - INTERVAL '12 hours', NOW(), NULL,
     'Cẩm nang sống sót qua kỳ đồ án SWP391: Từ chọn ý tưởng, phân chia WBS đến Demo bảo vệ trước Hội đồng',
     'Chào các bạn sinh viên K17, K18 sắp bước vào kỳ đồ án SWP391!

Là một cựu sinh viên K16 đã từng trải qua SWP391 và hiện tại đang làm Mentor cho các nhóm đồ án, mình muốn chia sẻ một vài kinh nghiệm thực tế giúp nhóm bạn không bị "toang" giữa kỳ:

1. Cách chọn ý tưởng đề tài:
Đừng chọn đề tài quá vĩ mô như "Mạng xã hội tích hợp AI", "Sàn thương mại điện tử xuyên biên giới". Hãy chọn đề tài thực tế, nghiệp vụ rõ ràng như: Hệ thống quản lý tiệm giặt ủi, Nền tảng đặt lịch phòng khám nha khoa, Hệ thống cho thuê xe tự lái. Hội đồng chấm đánh giá cao tính hoàn thiện và độ sạch của code hơn là tính năng vẽ vời mà bị lỗi.

2. Phân chia công việc theo WBS và Agile Scrum:
- Cử một bạn làm Scrum Master có trách nhiệm theo dõi tiến độ hàng ngày (Daily stand-up 10 phút).
- Tách rõ ràng API Contract giữa Frontend (Flutter/React) và Backend (Spring Boot/NestJS) bằng Swagger ngay từ tuần thứ 2. Đừng đợi code xong mới ráp!

3. Kỷ luật Git và Code Review:
Tuyệt đối không push thẳng lên nhánh main. Mọi pull request phải có ít nhất 2 thành viên review và pass bộ test tự động.

4. Chuẩn bị buổi Demo bảo vệ (Final Defense):
Chuẩn bị sẵn dữ liệu mẫu thực tế, đẹp mắt. Đừng để khi thầy cô yêu cầu test lại tạo tài khoản tên là "test 123", "aaaa". Diễn tập thuyết trình ít nhất 3 lần trước ngày bảo vệ.

Chúc các nhóm làm việc đoàn kết và giành điểm 9+ trước Hội đồng nhé!',
     'b0000000-0000-0000-0000-000000000006')
ON CONFLICT (id) DO UPDATE SET
    title = EXCLUDED.title,
    content = EXCLUDED.content,
    author_id = EXCLUDED.author_id,
    created_at = EXCLUDED.created_at;

INSERT INTO posts (id, created_at, updated_at, deleted_at, title, content, author_id)
VALUES
    ('f2000000-0000-0000-0000-000000000004', NOW() - INTERVAL '24 hours', NOW(), NULL,
     'Kinh nghiệm ôn thi PE PRN211 (C# WPF & Entity Framework) đạt 9.5+',
     'Chào các bạn, kỳ vừa rồi mình vừa qua môn PRN211 với 9.5 điểm PE. Mình đúc kết một số điểm mấu chốt để các bạn đạt điểm cao:

1. Nắm chắc cấu trúc dự án chuẩn N-Tier (GUI, BLL, DAL).
2. Luyện kỹ Binding dữ liệu hai chiều trên WPF (INotifyPropertyChanged và DataContext).
3. Sử dụng Entity Framework Core thành thạo: AsNoTracking() khi chỉ đọc dữ liệu, Include() để load bảng liên kết, và viết câu truy vấn LINQ thuần thục.
4. Xử lý validation dữ liệu đầu vào: Bắt ngoại lệ try-catch ở tầng giao diện, hiển thị thông báo lỗi rõ ràng bằng MessageBox.

Bạn nào đang gặp khúc mắc về phần gán SelectedItem trên DataGrid thì xem lại bài viết trước của mình nhé!',
     'b0000000-0000-0000-0000-000000000002')
ON CONFLICT (id) DO UPDATE SET
    title = EXCLUDED.title,
    content = EXCLUDED.content,
    author_id = EXCLUDED.author_id,
    created_at = EXCLUDED.created_at;

INSERT INTO posts (id, created_at, updated_at, deleted_at, title, content, author_id)
VALUES
    ('f2000000-0000-0000-0000-000000000005', NOW() - INTERVAL '36 hours', NOW(), NULL,
     'Luyện đề PE PRJ301 (Java Web MVC): Hướng dẫn viết Servlet, Session Filter và JDBC Pagination sạch đẹp',
     'Môn PRJ301 là môn học bản lề giúp các bạn sinh viên FPT hiểu rõ bản chất của giao thức HTTP, Session, Cookie và vòng đời của Servlet.

Để làm tốt đề thi PE môn này trong 90 phút, các bạn cần chuẩn bị kỹ:
- Base DAO kết nối SQL Server thông qua javax.sql.DataSource hoặc DriverManager.
- Mô hình MVC2: Controller nhận HttpServletRequest, gọi Service/DAO xử lý dữ liệu, sau đó setAttribute và forward sang file JSP tương ứng.
- Viết Filter phân quyền: Kiểm tra session.getAttribute("LOGIN_USER") để chặn người dùng chưa đăng nhập truy cập vào các trang quản trị.
- Kỹ thuật phân trang (Pagination): Câu truy vấn OFFSET-FETCH trong SQL Server kết hợp truyền param pageIndex và pageSize.

Lưu ý: Tuyệt đối không viết code logic hoặc câu truy vấn SQL trực tiếp trong file JSP (Scriptlet <% %>). Thầy cô chấm bài sẽ trừ điểm rất nặng lỗi này!',
     'b0000000-0000-0000-0000-000000000009')
ON CONFLICT (id) DO UPDATE SET
    title = EXCLUDED.title,
    content = EXCLUDED.content,
    author_id = EXCLUDED.author_id,
    created_at = EXCLUDED.created_at;

INSERT INTO posts (id, created_at, updated_at, deleted_at, title, content, author_id)
VALUES
    ('f2000000-0000-0000-0000-000000000006', NOW() - INTERVAL '48 hours', NOW(), NULL,
     'Review kỳ thực tập OJT tại FPT Software Đà Nẵng: Sinh viên năm 3 cần chuẩn bị những gì?',
     'Mình vừa hoàn thành 4 tháng thực tập OJT tại F-Complex Đà Nẵng và chính thức được ký hợp đồng Fresher. Mình muốn gửi tới các bạn chuẩn bị đi OJT vài lời khuyên thực tế:

1. Về phỏng vấn đầu vào:
Kiến thức cốt lõi là OOP (Java/C#), SQL cơ bản, và tư duy cấu trúc dữ liệu. Người phỏng vấn rất thích các ứng viên hiểu sâu về bản chất (ví dụ: tại sao dùng HashMap thay vì List, transaction trong database hoạt động thế nào) hơn là học vẹt framework.

2. Tiếng Anh cực kỳ quan trọng:
Làm việc với khách hàng Nhật Bản, Singapore hay Mỹ thì khả năng đọc tài liệu SRS và viết mail tiếng Anh là bắt buộc. Nếu bạn có bằng TOEIC 700+ thì cơ hội vào các dự án lớn rất cao.

3. Thái độ hơn trình độ:
Khi gặp vấn đề không giải quyết được trong 30 phút, hãy chủ động hỏi Senior hoặc Mentor. Đừng giấu dốt hoặc ngồi im cả ngày vì sẽ ảnh hưởng đến tiến độ của cả Sprint.

Chúc các bạn có một kỳ OJT thật nhiều trải nghiệm và sớm trở thành kỹ sư phần mềm chính thức!',
     'b0000000-0000-0000-0000-000000000004')
ON CONFLICT (id) DO UPDATE SET
    title = EXCLUDED.title,
    content = EXCLUDED.content,
    author_id = EXCLUDED.author_id,
    created_at = EXCLUDED.created_at;

INSERT INTO posts (id, created_at, updated_at, deleted_at, title, content, author_id)
VALUES
    ('f2000000-0000-0000-0000-000000000007', NOW() - INTERVAL '60 hours', NOW(), NULL,
     'Lộ trình tự học Tư duy lập trình C (PRF192) từ con số 0 cho tân sinh viên K19',
     'Gửi các bạn tân sinh viên K19 chuẩn bị học kỳ đầu tiên tại FPT!

Học môn lập trình C (PRF192) thường khiến nhiều bạn bỡ ngỡ, đặc biệt là những bạn cấp 3 học khối chuyên ban xã hội. Nhưng đừng lo lắng, C tuy chặt chẽ nhưng logic rất tường minh. Lộ trình gợi ý cho bạn:

- Tuần 1-2: Kiểu dữ liệu, toán tử, câu lệnh điều kiện if-else và vòng lặp for, while.
- Tuần 3-4: Hàm (Function) và tầm vực biến (Scope). Luyện viết code chia module nhỏ.
- Tuần 5-6: Mảng 1 chiều và chuỗi ký tự (String/char[]). Nắm chắc các hàm trong <string.h> như strlen, strcpy, strcmp.
- Tuần 7-8: Con trỏ (Pointers) và Cấp phát động (malloc/free). Đây là "nỗi sợ" của 80% sinh viên nhưng lại là chìa khóa để đạt điểm 10 PE.

Mỗi ngày dành ít nhất 1 giờ gõ code thực tế trên máy tính, tự tay fix lỗi compiler thay vì chỉ đọc tài liệu giấy nhé!',
     'b0000000-0000-0000-0000-000000000008')
ON CONFLICT (id) DO UPDATE SET
    title = EXCLUDED.title,
    content = EXCLUDED.content,
    author_id = EXCLUDED.author_id,
    created_at = EXCLUDED.created_at;

INSERT INTO posts (id, created_at, updated_at, deleted_at, title, content, author_id)
VALUES
    ('f2000000-0000-0000-0000-000000000008', NOW() - INTERVAL '72 hours', NOW(), NULL,
     'Bộ Cheat Sheet SQL Server môn DBI202: Mẫu câu lệnh GROUP BY HAVING, Subquery và Trigger hay thi PE',
     'Mình tổng hợp lại bảng Cheat Sheet các câu lệnh SQL Server hay gặp nhất trong đề thi PE DBI202:

- Phân biệt WHERE và HAVING: WHERE lọc từng dòng trước khi gom nhóm; HAVING lọc các nhóm sau khi đã gom bằng GROUP BY.
- Subquery trong SELECT và FROM: Nhớ đặt Alias cho bảng dẫn xuất (Derived table).
- Các hàm phân hạng nâng cao: ROW_NUMBER(), DENSE_RANK() OVER (PARTITION BY ... ORDER BY ...).
- Trigger kiểm tra nghiệp vụ: Sử dụng bảng ảo INSERTED và DELETED để so sánh giá trị cũ và mới, kết hợp ROLLBACK TRANSACTION.

Bộ tài liệu được tóm tắt trong 3 trang PDF ngắn gọn, bạn nào đang ôn thi thì tải về in ra học rất tiện!',
     'b0000000-0000-0000-0000-000000000010')
ON CONFLICT (id) DO UPDATE SET
    title = EXCLUDED.title,
    content = EXCLUDED.content,
    author_id = EXCLUDED.author_id,
    created_at = EXCLUDED.created_at;

INSERT INTO posts (id, created_at, updated_at, deleted_at, title, content, author_id)
VALUES
    ('f2000000-0000-0000-0000-000000000009', NOW() - INTERVAL '84 hours', NOW(), NULL,
     'Tổng hợp 150 câu trắc nghiệm Java OOP môn PRO192 ôn thi Final FE kèm giải thích chi tiết',
     'Mình vừa cập nhật lại file tổng hợp 150 câu trắc nghiệm Java OOP môn PRO192 có đáp án và giải thích từng phương án sai. 

Các chủ đề trọng tâm trong đề thi FE:
1. Tính kế thừa và tính đa hình (Dynamic Binding vs Static Binding).
2. Xử lý ngoại lệ (Checked Exception vs Unchecked Exception).
3. Cơ chế thu gom rác tự động (Garbage Collection & System.gc()).
4. Giao diện Collection (Set, List, Map) và cách triển khai Comparable / Comparator.

Tài liệu hoàn toàn miễn phí cho cộng đồng NextUnity, chúc các bạn qua môn điểm cao!',
     'b0000000-0000-0000-0000-000000000003')
ON CONFLICT (id) DO UPDATE SET
    title = EXCLUDED.title,
    content = EXCLUDED.content,
    author_id = EXCLUDED.author_id,
    created_at = EXCLUDED.created_at;

-- 4. Liên kết Post Tags
DELETE FROM post_tags WHERE post_id IN ('f2000000-0000-0000-0000-000000000001', 'f2000000-0000-0000-0000-000000000002', 'f2000000-0000-0000-0000-000000000003', 'f2000000-0000-0000-0000-000000000004', 'f2000000-0000-0000-0000-000000000005', 'f2000000-0000-0000-0000-000000000006', 'f2000000-0000-0000-0000-000000000007', 'f2000000-0000-0000-0000-000000000008', 'f2000000-0000-0000-0000-000000000009');

INSERT INTO post_tags (post_id, tag_id)
VALUES
    ('f2000000-0000-0000-0000-000000000001', 'f1000000-0000-0000-0000-000000000002'),
    ('f2000000-0000-0000-0000-000000000001', 'f1000000-0000-0000-0000-000000000005'),
    ('f2000000-0000-0000-0000-000000000001', 'f1000000-0000-0000-0000-000000000004'),
    ('f2000000-0000-0000-0000-000000000002', 'f1000000-0000-0000-0000-000000000006'),
    ('f2000000-0000-0000-0000-000000000002', 'f1000000-0000-0000-0000-000000000002'),
    ('f2000000-0000-0000-0000-000000000002', 'f1000000-0000-0000-0000-000000000010'),
    ('f2000000-0000-0000-0000-000000000003', 'f1000000-0000-0000-0000-000000000003'),
    ('f2000000-0000-0000-0000-000000000003', 'f1000000-0000-0000-0000-000000000010'),
    ('f2000000-0000-0000-0000-000000000004', 'f1000000-0000-0000-0000-000000000001'),
    ('f2000000-0000-0000-0000-000000000004', 'f1000000-0000-0000-0000-000000000002'),
    ('f2000000-0000-0000-0000-000000000005', 'f1000000-0000-0000-0000-000000000008'),
    ('f2000000-0000-0000-0000-000000000005', 'f1000000-0000-0000-0000-000000000002'),
    ('f2000000-0000-0000-0000-000000000006', 'f1000000-0000-0000-0000-000000000009'),
    ('f2000000-0000-0000-0000-000000000006', 'f1000000-0000-0000-0000-000000000003'),
    ('f2000000-0000-0000-0000-000000000007', 'f1000000-0000-0000-0000-000000000005'),
    ('f2000000-0000-0000-0000-000000000007', 'f1000000-0000-0000-0000-000000000010'),
    ('f2000000-0000-0000-0000-000000000008', 'f1000000-0000-0000-0000-000000000007'),
    ('f2000000-0000-0000-0000-000000000008', 'f1000000-0000-0000-0000-000000000002'),
    ('f2000000-0000-0000-0000-000000000008', 'f1000000-0000-0000-0000-000000000010'),
    ('f2000000-0000-0000-0000-000000000009', 'f1000000-0000-0000-0000-000000000004'),
    ('f2000000-0000-0000-0000-000000000009', 'f1000000-0000-0000-0000-000000000010');

-- 5. Cập nhật Thảo luận & Bình luận (Comments)
INSERT INTO comments (id, created_at, updated_at, deleted_at, content, post_id, author_id, parent_id)
VALUES
    ('f3000000-0000-0000-0000-000000000001', NOW() - INTERVAL '100 minutes', NOW(), NULL,
     'Cảm ơn Thầy Phong đã chia sẻ bài viết rất bổ ích ạ! Kỳ trước em suýt bị 0 điểm câu 2 PRF192 cũng vì tội in thừa dấu cách ở cuối dòng.',
     'f2000000-0000-0000-0000-000000000001', 'b0000000-0000-0000-0000-000000000002', NULL)
ON CONFLICT (id) DO UPDATE SET
    content = EXCLUDED.content,
    parent_id = EXCLUDED.parent_id;

INSERT INTO comments (id, created_at, updated_at, deleted_at, content, post_id, author_id, parent_id)
VALUES
    ('f3000000-0000-0000-0000-000000000002', NOW() - INTERVAL '85 minutes', NOW(), NULL,
     'Chúc mừng Khoa đã rút kinh nghiệm kịp thời. Các bạn sinh viên khóa sau nhớ chú ý kỹ chi tiết này nhé!',
     'f2000000-0000-0000-0000-000000000001', 'b0000000-0000-0000-0000-000000000001', 'f3000000-0000-0000-0000-000000000001')
ON CONFLICT (id) DO UPDATE SET
    content = EXCLUDED.content,
    parent_id = EXCLUDED.parent_id;

INSERT INTO comments (id, created_at, updated_at, deleted_at, content, post_id, author_id, parent_id)
VALUES
    ('f3000000-0000-0000-0000-000000000003', NOW() - INTERVAL '60 minutes', NOW(), NULL,
     'Thầy cho em hỏi trong phòng thi PE có được mang tài liệu giấy vào không ạ?',
     'f2000000-0000-0000-0000-000000000001', 'b0000000-0000-0000-0000-000000000005', NULL)
ON CONFLICT (id) DO UPDATE SET
    content = EXCLUDED.content,
    parent_id = EXCLUDED.parent_id;

INSERT INTO comments (id, created_at, updated_at, deleted_at, content, post_id, author_id, parent_id)
VALUES
    ('f3000000-0000-0000-0000-000000000004', NOW() - INTERVAL '40 minutes', NOW(), NULL,
     'Quy chế thi PE là Closed-book hoàn toàn em nhé. Em chỉ được sử dụng máy tính của phòng thi và tài liệu offline có sẵn trong bộ đề thi.',
     'f2000000-0000-0000-0000-000000000001', 'b0000000-0000-0000-0000-000000000001', 'f3000000-0000-0000-0000-000000000003')
ON CONFLICT (id) DO UPDATE SET
    content = EXCLUDED.content,
    parent_id = EXCLUDED.parent_id;

INSERT INTO comments (id, created_at, updated_at, deleted_at, content, post_id, author_id, parent_id)
VALUES
    ('f3000000-0000-0000-0000-000000000005', NOW() - INTERVAL '240 minutes', NOW(), NULL,
     'Bài viết đỉnh quá chị ơi! Cho em xin tài liệu vào email: anhpd@fpt.edu.vn với ạ, em cảm ơn chị nhiều!',
     'f2000000-0000-0000-0000-000000000002', 'b0000000-0000-0000-0000-000000000004', NULL)
ON CONFLICT (id) DO UPDATE SET
    content = EXCLUDED.content,
    parent_id = EXCLUDED.parent_id;

INSERT INTO comments (id, created_at, updated_at, deleted_at, content, post_id, author_id, parent_id)
VALUES
    ('f3000000-0000-0000-0000-000000000006', NOW() - INTERVAL '210 minutes', NOW(), NULL,
     'Chị vừa gửi qua email trường cho em rồi nhé. Check hòm thư kiểm tra nha!',
     'f2000000-0000-0000-0000-000000000002', 'b0000000-0000-0000-0000-000000000007', 'f3000000-0000-0000-0000-000000000005')
ON CONFLICT (id) DO UPDATE SET
    content = EXCLUDED.content,
    parent_id = EXCLUDED.parent_id;

INSERT INTO comments (id, created_at, updated_at, deleted_at, content, post_id, author_id, parent_id)
VALUES
    ('f3000000-0000-0000-0000-000000000007', NOW() - INTERVAL '180 minutes', NOW(), NULL,
     'Phần Dijkstra thuật toán khó nhớ quá chị, chị có mẹo nào code nhanh không ạ?',
     'f2000000-0000-0000-0000-000000000002', 'b0000000-0000-0000-0000-000000000008', NULL)
ON CONFLICT (id) DO UPDATE SET
    content = EXCLUDED.content,
    parent_id = EXCLUDED.parent_id;

INSERT INTO comments (id, created_at, updated_at, deleted_at, content, post_id, author_id, parent_id)
VALUES
    ('f3000000-0000-0000-0000-000000000008', NOW() - INTERVAL '150 minutes', NOW(), NULL,
     'Em hãy viết một class DijkstraHelper riêng, dùng 3 mảng: d[] lưu khoảng cách, p[] lưu đỉnh trước đó, và selected[] đánh dấu. Chỉ cần nhớ vòng lặp tìm min là code trong 10 phút thôi em.',
     'f2000000-0000-0000-0000-000000000002', 'b0000000-0000-0000-0000-000000000007', 'f3000000-0000-0000-0000-000000000007')
ON CONFLICT (id) DO UPDATE SET
    content = EXCLUDED.content,
    parent_id = EXCLUDED.parent_id;

INSERT INTO comments (id, created_at, updated_at, deleted_at, content, post_id, author_id, parent_id)
VALUES
    ('f3000000-0000-0000-0000-000000000009', NOW() - INTERVAL '600 minutes', NOW(), NULL,
     'Anh Bảo cho em hỏi nếu nhóm em dùng Spring Boot 3 + Flutter làm ứng dụng di động cho SWP391 thì có được Mentor duyệt không anh?',
     'f2000000-0000-0000-0000-000000000003', 'b0000000-0000-0000-0000-000000000009', NULL)
ON CONFLICT (id) DO UPDATE SET
    content = EXCLUDED.content,
    parent_id = EXCLUDED.parent_id;

INSERT INTO comments (id, created_at, updated_at, deleted_at, content, post_id, author_id, parent_id)
VALUES
    ('f3000000-0000-0000-0000-000000000010', NOW() - INTERVAL '550 minutes', NOW(), NULL,
     'Stack đó quá chuẩn luôn em ơi! Điểm cộng lớn nếu làm mobile app mượt mà và có bộ RESTful API tài liệu Swagger đầy đủ. Cứ tự tin bảo vệ đề tài với Mentor nhé.',
     'f2000000-0000-0000-0000-000000000003', 'b0000000-0000-0000-0000-000000000006', 'f3000000-0000-0000-0000-000000000009')
ON CONFLICT (id) DO UPDATE SET
    content = EXCLUDED.content,
    parent_id = EXCLUDED.parent_id;

INSERT INTO comments (id, created_at, updated_at, deleted_at, content, post_id, author_id, parent_id)
VALUES
    ('f3000000-0000-0000-0000-000000000011', NOW() - INTERVAL '450 minutes', NOW(), NULL,
     'Bài viết tâm huyết quá anh, lưu lại ngay cho kỳ sau nhóm em dùng!',
     'f2000000-0000-0000-0000-000000000003', 'b0000000-0000-0000-0000-000000000010', NULL)
ON CONFLICT (id) DO UPDATE SET
    content = EXCLUDED.content,
    parent_id = EXCLUDED.parent_id;

INSERT INTO comments (id, created_at, updated_at, deleted_at, content, post_id, author_id, parent_id)
VALUES
    ('f3000000-0000-0000-0000-000000000012', NOW() - INTERVAL '1200 minutes', NOW(), NULL,
     'Anh Nam ơi, cho em hỏi phần phân trang nếu có thêm điều kiện Search theo tên thì truyền param trên URL thế nào để không bị mất khi chuyển trang ạ?',
     'f2000000-0000-0000-0000-000000000005', 'b0000000-0000-0000-0000-000000000003', NULL)
ON CONFLICT (id) DO UPDATE SET
    content = EXCLUDED.content,
    parent_id = EXCLUDED.parent_id;

INSERT INTO comments (id, created_at, updated_at, deleted_at, content, post_id, author_id, parent_id)
VALUES
    ('f3000000-0000-0000-0000-000000000013', NOW() - INTERVAL '1100 minutes', NOW(), NULL,
     'Em ghép chuỗi href="MainController?action=search&keyword=${param.keyword}&page=${i}" trong thẻ a phân trang ở JSP là giữ nguyên được từ khóa nhé!',
     'f2000000-0000-0000-0000-000000000005', 'b0000000-0000-0000-0000-000000000009', 'f3000000-0000-0000-0000-000000000012')
ON CONFLICT (id) DO UPDATE SET
    content = EXCLUDED.content,
    parent_id = EXCLUDED.parent_id;

INSERT INTO comments (id, created_at, updated_at, deleted_at, content, post_id, author_id, parent_id)
VALUES
    ('f3000000-0000-0000-0000-000000000014', NOW() - INTERVAL '1500 minutes', NOW(), NULL,
     'Đọc bài review của anh thấy có thêm động lực học tập hẳn. Cảm ơn anh đã truyền lửa cho đàn em K19!',
     'f2000000-0000-0000-0000-000000000006', 'b0000000-0000-0000-0000-000000000005', NULL)
ON CONFLICT (id) DO UPDATE SET
    content = EXCLUDED.content,
    parent_id = EXCLUDED.parent_id;

-- 6. Thêm tương tác Cảm xúc (Reactions) tạo độ uy tín & sôi nổi
DELETE FROM reactions WHERE post_id IN ('f2000000-0000-0000-0000-000000000001', 'f2000000-0000-0000-0000-000000000002', 'f2000000-0000-0000-0000-000000000003', 'f2000000-0000-0000-0000-000000000004', 'f2000000-0000-0000-0000-000000000005', 'f2000000-0000-0000-0000-000000000006', 'f2000000-0000-0000-0000-000000000007', 'f2000000-0000-0000-0000-000000000008', 'f2000000-0000-0000-0000-000000000009');

INSERT INTO reactions (id, created_at, updated_at, reaction_type, user_id, post_id, comment_id)
VALUES
    ('f4000000-0000-0000-0000-000000000001', NOW() - INTERVAL '120 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000001', 'f2000000-0000-0000-0000-000000000001', NULL),
    ('f4000000-0000-0000-0000-000000000002', NOW() - INTERVAL '105 minutes', NOW(), 'LOVE', 'b0000000-0000-0000-0000-000000000002', 'f2000000-0000-0000-0000-000000000001', NULL),
    ('f4000000-0000-0000-0000-000000000003', NOW() - INTERVAL '90 minutes', NOW(), 'WOW', 'b0000000-0000-0000-0000-000000000003', 'f2000000-0000-0000-0000-000000000001', NULL),
    ('f4000000-0000-0000-0000-000000000004', NOW() - INTERVAL '75 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000004', 'f2000000-0000-0000-0000-000000000001', NULL),
    ('f4000000-0000-0000-0000-000000000005', NOW() - INTERVAL '60 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000005', 'f2000000-0000-0000-0000-000000000001', NULL),
    ('f4000000-0000-0000-0000-000000000006', NOW() - INTERVAL '45 minutes', NOW(), 'LOVE', 'b0000000-0000-0000-0000-000000000006', 'f2000000-0000-0000-0000-000000000001', NULL),
    ('f4000000-0000-0000-0000-000000000007', NOW() - INTERVAL '300 minutes', NOW(), 'LOVE', 'b0000000-0000-0000-0000-000000000001', 'f2000000-0000-0000-0000-000000000002', NULL),
    ('f4000000-0000-0000-0000-000000000008', NOW() - INTERVAL '285 minutes', NOW(), 'WOW', 'b0000000-0000-0000-0000-000000000002', 'f2000000-0000-0000-0000-000000000002', NULL),
    ('f4000000-0000-0000-0000-000000000009', NOW() - INTERVAL '270 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000003', 'f2000000-0000-0000-0000-000000000002', NULL),
    ('f4000000-0000-0000-0000-000000000010', NOW() - INTERVAL '255 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000004', 'f2000000-0000-0000-0000-000000000002', NULL),
    ('f4000000-0000-0000-0000-000000000011', NOW() - INTERVAL '240 minutes', NOW(), 'LOVE', 'b0000000-0000-0000-0000-000000000005', 'f2000000-0000-0000-0000-000000000002', NULL),
    ('f4000000-0000-0000-0000-000000000012', NOW() - INTERVAL '225 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000006', 'f2000000-0000-0000-0000-000000000002', NULL),
    ('f4000000-0000-0000-0000-000000000013', NOW() - INTERVAL '210 minutes', NOW(), 'LOVE', 'b0000000-0000-0000-0000-000000000007', 'f2000000-0000-0000-0000-000000000002', NULL),
    ('f4000000-0000-0000-0000-000000000014', NOW() - INTERVAL '720 minutes', NOW(), 'WOW', 'b0000000-0000-0000-0000-000000000001', 'f2000000-0000-0000-0000-000000000003', NULL),
    ('f4000000-0000-0000-0000-000000000015', NOW() - INTERVAL '705 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000002', 'f2000000-0000-0000-0000-000000000003', NULL),
    ('f4000000-0000-0000-0000-000000000016', NOW() - INTERVAL '690 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000003', 'f2000000-0000-0000-0000-000000000003', NULL),
    ('f4000000-0000-0000-0000-000000000017', NOW() - INTERVAL '675 minutes', NOW(), 'LOVE', 'b0000000-0000-0000-0000-000000000004', 'f2000000-0000-0000-0000-000000000003', NULL),
    ('f4000000-0000-0000-0000-000000000018', NOW() - INTERVAL '660 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000005', 'f2000000-0000-0000-0000-000000000003', NULL),
    ('f4000000-0000-0000-0000-000000000019', NOW() - INTERVAL '645 minutes', NOW(), 'LOVE', 'b0000000-0000-0000-0000-000000000006', 'f2000000-0000-0000-0000-000000000003', NULL),
    ('f4000000-0000-0000-0000-000000000020', NOW() - INTERVAL '630 minutes', NOW(), 'WOW', 'b0000000-0000-0000-0000-000000000007', 'f2000000-0000-0000-0000-000000000003', NULL),
    ('f4000000-0000-0000-0000-000000000021', NOW() - INTERVAL '615 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000008', 'f2000000-0000-0000-0000-000000000003', NULL),
    ('f4000000-0000-0000-0000-000000000022', NOW() - INTERVAL '1440 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000001', 'f2000000-0000-0000-0000-000000000004', NULL),
    ('f4000000-0000-0000-0000-000000000023', NOW() - INTERVAL '1425 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000002', 'f2000000-0000-0000-0000-000000000004', NULL),
    ('f4000000-0000-0000-0000-000000000024', NOW() - INTERVAL '1410 minutes', NOW(), 'LOVE', 'b0000000-0000-0000-0000-000000000003', 'f2000000-0000-0000-0000-000000000004', NULL),
    ('f4000000-0000-0000-0000-000000000025', NOW() - INTERVAL '1395 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000004', 'f2000000-0000-0000-0000-000000000004', NULL),
    ('f4000000-0000-0000-0000-000000000026', NOW() - INTERVAL '1380 minutes', NOW(), 'LOVE', 'b0000000-0000-0000-0000-000000000005', 'f2000000-0000-0000-0000-000000000004', NULL),
    ('f4000000-0000-0000-0000-000000000027', NOW() - INTERVAL '1365 minutes', NOW(), 'WOW', 'b0000000-0000-0000-0000-000000000006', 'f2000000-0000-0000-0000-000000000004', NULL),
    ('f4000000-0000-0000-0000-000000000028', NOW() - INTERVAL '1350 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000007', 'f2000000-0000-0000-0000-000000000004', NULL),
    ('f4000000-0000-0000-0000-000000000029', NOW() - INTERVAL '1335 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000008', 'f2000000-0000-0000-0000-000000000004', NULL),
    ('f4000000-0000-0000-0000-000000000030', NOW() - INTERVAL '1320 minutes', NOW(), 'LOVE', 'b0000000-0000-0000-0000-000000000009', 'f2000000-0000-0000-0000-000000000004', NULL),
    ('f4000000-0000-0000-0000-000000000031', NOW() - INTERVAL '2160 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000001', 'f2000000-0000-0000-0000-000000000005', NULL),
    ('f4000000-0000-0000-0000-000000000032', NOW() - INTERVAL '2145 minutes', NOW(), 'LOVE', 'b0000000-0000-0000-0000-000000000002', 'f2000000-0000-0000-0000-000000000005', NULL),
    ('f4000000-0000-0000-0000-000000000033', NOW() - INTERVAL '2130 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000003', 'f2000000-0000-0000-0000-000000000005', NULL),
    ('f4000000-0000-0000-0000-000000000034', NOW() - INTERVAL '2115 minutes', NOW(), 'LOVE', 'b0000000-0000-0000-0000-000000000004', 'f2000000-0000-0000-0000-000000000005', NULL),
    ('f4000000-0000-0000-0000-000000000035', NOW() - INTERVAL '2100 minutes', NOW(), 'WOW', 'b0000000-0000-0000-0000-000000000005', 'f2000000-0000-0000-0000-000000000005', NULL),
    ('f4000000-0000-0000-0000-000000000036', NOW() - INTERVAL '2085 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000006', 'f2000000-0000-0000-0000-000000000005', NULL),
    ('f4000000-0000-0000-0000-000000000037', NOW() - INTERVAL '2880 minutes', NOW(), 'LOVE', 'b0000000-0000-0000-0000-000000000001', 'f2000000-0000-0000-0000-000000000006', NULL),
    ('f4000000-0000-0000-0000-000000000038', NOW() - INTERVAL '2865 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000002', 'f2000000-0000-0000-0000-000000000006', NULL),
    ('f4000000-0000-0000-0000-000000000039', NOW() - INTERVAL '2850 minutes', NOW(), 'LOVE', 'b0000000-0000-0000-0000-000000000003', 'f2000000-0000-0000-0000-000000000006', NULL),
    ('f4000000-0000-0000-0000-000000000040', NOW() - INTERVAL '2835 minutes', NOW(), 'WOW', 'b0000000-0000-0000-0000-000000000004', 'f2000000-0000-0000-0000-000000000006', NULL),
    ('f4000000-0000-0000-0000-000000000041', NOW() - INTERVAL '2820 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000005', 'f2000000-0000-0000-0000-000000000006', NULL),
    ('f4000000-0000-0000-0000-000000000042', NOW() - INTERVAL '2805 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000006', 'f2000000-0000-0000-0000-000000000006', NULL),
    ('f4000000-0000-0000-0000-000000000043', NOW() - INTERVAL '2790 minutes', NOW(), 'LOVE', 'b0000000-0000-0000-0000-000000000007', 'f2000000-0000-0000-0000-000000000006', NULL),
    ('f4000000-0000-0000-0000-000000000044', NOW() - INTERVAL '3600 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000001', 'f2000000-0000-0000-0000-000000000007', NULL),
    ('f4000000-0000-0000-0000-000000000045', NOW() - INTERVAL '3585 minutes', NOW(), 'LOVE', 'b0000000-0000-0000-0000-000000000002', 'f2000000-0000-0000-0000-000000000007', NULL),
    ('f4000000-0000-0000-0000-000000000046', NOW() - INTERVAL '3570 minutes', NOW(), 'WOW', 'b0000000-0000-0000-0000-000000000003', 'f2000000-0000-0000-0000-000000000007', NULL),
    ('f4000000-0000-0000-0000-000000000047', NOW() - INTERVAL '3555 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000004', 'f2000000-0000-0000-0000-000000000007', NULL),
    ('f4000000-0000-0000-0000-000000000048', NOW() - INTERVAL '3540 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000005', 'f2000000-0000-0000-0000-000000000007', NULL),
    ('f4000000-0000-0000-0000-000000000049', NOW() - INTERVAL '3525 minutes', NOW(), 'LOVE', 'b0000000-0000-0000-0000-000000000006', 'f2000000-0000-0000-0000-000000000007', NULL),
    ('f4000000-0000-0000-0000-000000000050', NOW() - INTERVAL '3510 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000007', 'f2000000-0000-0000-0000-000000000007', NULL),
    ('f4000000-0000-0000-0000-000000000051', NOW() - INTERVAL '3495 minutes', NOW(), 'LOVE', 'b0000000-0000-0000-0000-000000000008', 'f2000000-0000-0000-0000-000000000007', NULL),
    ('f4000000-0000-0000-0000-000000000052', NOW() - INTERVAL '4320 minutes', NOW(), 'LOVE', 'b0000000-0000-0000-0000-000000000001', 'f2000000-0000-0000-0000-000000000008', NULL),
    ('f4000000-0000-0000-0000-000000000053', NOW() - INTERVAL '4305 minutes', NOW(), 'WOW', 'b0000000-0000-0000-0000-000000000002', 'f2000000-0000-0000-0000-000000000008', NULL),
    ('f4000000-0000-0000-0000-000000000054', NOW() - INTERVAL '4290 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000003', 'f2000000-0000-0000-0000-000000000008', NULL),
    ('f4000000-0000-0000-0000-000000000055', NOW() - INTERVAL '4275 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000004', 'f2000000-0000-0000-0000-000000000008', NULL),
    ('f4000000-0000-0000-0000-000000000056', NOW() - INTERVAL '4260 minutes', NOW(), 'LOVE', 'b0000000-0000-0000-0000-000000000005', 'f2000000-0000-0000-0000-000000000008', NULL),
    ('f4000000-0000-0000-0000-000000000057', NOW() - INTERVAL '4245 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000006', 'f2000000-0000-0000-0000-000000000008', NULL),
    ('f4000000-0000-0000-0000-000000000058', NOW() - INTERVAL '4230 minutes', NOW(), 'LOVE', 'b0000000-0000-0000-0000-000000000007', 'f2000000-0000-0000-0000-000000000008', NULL),
    ('f4000000-0000-0000-0000-000000000059', NOW() - INTERVAL '4215 minutes', NOW(), 'WOW', 'b0000000-0000-0000-0000-000000000008', 'f2000000-0000-0000-0000-000000000008', NULL),
    ('f4000000-0000-0000-0000-000000000060', NOW() - INTERVAL '4200 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000009', 'f2000000-0000-0000-0000-000000000008', NULL),
    ('f4000000-0000-0000-0000-000000000061', NOW() - INTERVAL '5040 minutes', NOW(), 'WOW', 'b0000000-0000-0000-0000-000000000001', 'f2000000-0000-0000-0000-000000000009', NULL),
    ('f4000000-0000-0000-0000-000000000062', NOW() - INTERVAL '5025 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000002', 'f2000000-0000-0000-0000-000000000009', NULL),
    ('f4000000-0000-0000-0000-000000000063', NOW() - INTERVAL '5010 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000003', 'f2000000-0000-0000-0000-000000000009', NULL),
    ('f4000000-0000-0000-0000-000000000064', NOW() - INTERVAL '4995 minutes', NOW(), 'LOVE', 'b0000000-0000-0000-0000-000000000004', 'f2000000-0000-0000-0000-000000000009', NULL),
    ('f4000000-0000-0000-0000-000000000065', NOW() - INTERVAL '4980 minutes', NOW(), 'LIKE', 'b0000000-0000-0000-0000-000000000005', 'f2000000-0000-0000-0000-000000000009', NULL),
    ('f4000000-0000-0000-0000-000000000066', NOW() - INTERVAL '4965 minutes', NOW(), 'LOVE', 'b0000000-0000-0000-0000-000000000006', 'f2000000-0000-0000-0000-000000000009', NULL);

COMMIT;

-- Kiem tra tong so Posts, Comments, Reactions vua cap nhat
SELECT 
    (SELECT COUNT(*) FROM posts) AS total_posts,
    (SELECT COUNT(*) FROM comments) AS total_comments,
    (SELECT COUNT(*) FROM reactions WHERE post_id IS NOT NULL) AS total_post_reactions,
    (SELECT COUNT(*) FROM tags) AS total_tags,
    (SELECT COUNT(*) FROM users) AS total_users;
