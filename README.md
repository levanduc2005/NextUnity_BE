# NextUnity Backend API

<p align="center">
  <img src="https://images.unsplash.com/photo-1516321318423-f06f85e504b3?auto=format&fit=crop&w=1200&q=80" alt="NextUnity Banner" width="100%" height="280" style="border-radius: 12px; object-fit: cover;" />
</p>

> **NextUnity Backend** là hệ thống RESTful API phục vụ nền tảng học tập trực tuyến (E-Learning) và Diễn đàn trao đổi học thuật (Community) chuyên sâu dành cho sinh viên và giảng viên khối ngành Công nghệ thông tin (Đại học FPT - FPTU).

---

## 🌟 Tính Năng Nổi Bật (Core Features)

### 1. Quản Lý Khóa Học & Bài Học Chuẩn FPT (Courses & Lessons)
- Quản lý danh mục giáo trình cốt lõi: **PRF192** (C cơ bản), **PRO192** (Java OOP), **CSD201** (Cấu trúc dữ liệu & Thuật toán), **DBI202** (SQL Server), **PRJ301** (Java Web), **PRN211** (.NET/WPF), **AIF101** (AI), **MAD101** (Toán rời rạc).
- Nội dung bài học đa phương tiện:
  - Video bài giảng YouTube nhúng chuẩn tỉ lệ điện ảnh 16:9.
  - Tài liệu lý thuyết Markdown học thuật chuyên sâu (không emoji, cấu trúc 5 phần chuẩn E-learning).
  - Tích hợp tài liệu đính kèm (Slide PDF).
- Theo dõi tiến độ học tập (Progress Tracking) và đánh dấu hoàn thành bài học.

### 2. Hệ Thống Đánh Giá & Trắc Nghiệm (Quizzes & Assessments)
- Bộ câu hỏi trắc nghiệm thực hành chuẩn cấu trúc đề thi FE (Final Exam).
- Chấm điểm tự động tức thì, phân loại Đạt/Trượt (Pass/Fail) và lưu vết lịch sử làm bài.

### 3. Diễn Đàn Cộng Đồng Sinh Viên FPT (Community & Discussion)
- Đăng bài viết chia sẻ kinh nghiệm ôn thi PE, cẩm nang đồ án SWP391 và review thực tập OJT.
- Hệ thống lọc bài viết theo thẻ môn học (Tags: `PE`, `PRF192`, `PRO192`, `CSD201`, `DBI202`, `PRJ301`, `SWP391`, `OJT_INTERNSHIP`, `TAI_LIEU`).
- Tương tác cảm xúc đa dạng: `LIKE`, `LOVE`, `HAHA`, `WOW`, `SAD`, `ANGRY`.
- Bình luận đa cấp (Nested Comments & Replies) hỗ trợ trao đổi giữa sinh viên và giảng viên.

### 4. Xác Thực & Bảo Mật (Authentication & Security)
- Xác thực Stateless thông qua **Spring Security** và **JWT (JSON Web Token)**.
- Đăng nhập tài khoản trường học FPT qua **Google OAuth2**.
- Phân quyền người dùng dựa trên vai trò: `ADMIN`, `LECTURER`, `STUDENT`.
- Cơ chế mã hóa mật khẩu một chiều an toàn bằng **BCrypt**.

### 5. Gói Nâng Cấp & Cổng Thanh Toán (Payment & Subscription)
- Quản lý gói thành viên: `FREE` và `PLUS` (truy cập trọn đời tài liệu VIP, giải đề PE có lời giải chi tiết).
- Tích hợp tạo mã thanh toán chuyển khoản ngân hàng tự động **VietQR** / **PayOS** kèm webhook xác nhận giao dịch tự động.

---

## 🛠️ Công Nghệ Sử Dụng (Tech Stack)

| Thành phần | Công nghệ / Thư viện |
| :--- | :--- |
| **Ngôn ngữ** | Java 17 / 21 / 23 |
| **Framework** | Spring Boot 3.x / 4.x (Spring Web, Spring Security, Spring Data JPA, Spring Validation) |
| **Cơ sở dữ liệu** | PostgreSQL 15+ (Hỗ trợ Docker và Cloud Database Neon/Supabase) |
| **Connection Pool** | HikariCP (Tối ưu connection timeout & idle limits) |
| **Mapping & Tiện ích** | Lombok, MapStruct, Jackson JSON |
| **Tài liệu API** | Springdoc OpenAPI 3 / Swagger UI |
| **Công cụ Build** | Apache Maven |

---

## 📁 Cấu Trúc Dự Án (Project Structure)

```text
src/main/java/project/
├── auth/                      # Module xác thực & JWT
│   ├── controller/            # AuthController, OAuth2Controller
│   ├── dto/                   # LoginRequest, RegisterRequest, AuthResponse
│   └── service/               # AuthService, JwtService, CustomUserDetailsService
├── course/                    # Module khóa học & giáo trình
│   ├── controller/            # CourseController, LessonController, ModuleController
│   ├── dto/                   # CourseResponse, LessonResponse, ProgressDto
│   ├── entity/                # Course, Module, Lesson, Enrollment, LessonProgress
│   └── service/               # CourseService, LessonService, EnrollmentService
├── community/                 # Module cộng đồng & bài viết
│   ├── controller/            # PostController, CommentController, TagController
│   ├── dto/                   # PostResponse, CommentResponse, CreatePostRequest
│   ├── entity/                # Post, Comment, Reaction, Tag
│   └── service/               # PostService, CommentService, ReactionService
├── quiz/                      # Module trắc nghiệm & câu hỏi
│   ├── controller/            # QuizController, SubmissionController
│   ├── entity/                # Quiz, Question, Option, QuizSubmission
│   └── service/               # QuizService, GradingService
├── payment/                   # Module thanh toán gói hội viên
│   ├── controller/            # PaymentController, VietQRController
│   ├── entity/                # Order, Transaction, Subscription
│   └── service/               # PaymentService, VietQRService
├── user/                      # Module hồ sơ người dùng
│   ├── controller/            # UserController, ProfileController
│   └── entity/                # User, Role, UserStreak
└── shared/                    # Các lớp dùng chung
    ├── common/                # BaseEntity, SoftDeleteEntity, ApiResponse
    ├── config/                # SecurityConfig, CorsConfig, OpenApiConfig
    └── exception/             # GlobalExceptionHandler, CustomExceptions
```

---

## 🚀 Hướng Dẫn Cài Đặt & Chạy Ứng Dụng (Getting Started)

### 1. Yêu Cầu Môi Trường (Prerequisites)
- **Java Development Kit (JDK)**: Phiên bản 17 hoặc 21+.
- **PostgreSQL**: Phiên bản 14 trở lên.
- **Maven**: Phiên bản 3.8+.

### 2. Cấu Hình Cơ Sở Dữ Liệu
Tạo cơ sở dữ liệu mới trong PostgreSQL:
```sql
CREATE DATABASE nextunity_db;
```

Kiểm tra và điều chỉnh thông số kết nối trong file [`src/main/resources/application.properties`](file:///e:/Workspace/Mon_Hoc_FPT/EXE/NextUnity_BE/src/main/resources/application.properties):
```properties
server.port=8000

spring.datasource.url=jdbc:postgresql://localhost:5432/nextunity_db
spring.datasource.username=postgres
spring.datasource.password=your_password
```

### 3. Nạp Dữ Liệu Mẫu (Seed Data)
Dự án cung cấp sẵn các tập lệnh SQL chất lượng cao tại thư mục `src/main/resources/sql/`

Bạn có thể chạy trực tiếp các file SQL trên vào PostgreSQL thông qua **DBeaver** hoặc **pgAdmin**.

### 4. Build và Khởi Động Server
Mở terminal tại thư mục dự án `NextUnity_BE` và thực hiện:

```bash
# Tải dependencies và biên dịch mã nguồn
mvn clean install -DskipTests

# Khởi chạy ứng dụng
mvn spring-boot:run
```

Sau khi khởi động thành công, server sẽ lắng nghe tại:
👉 `http://localhost:8000`

---

## 📖 Tài Liệu API (API Documentation)

Hệ thống tích hợp sẵn Swagger UI tự động sinh tài liệu:
- **Swagger UI Interactive Docs**: [http://localhost:8000/swagger-ui/index.html](http://localhost:8000/swagger-ui/index.html)
- **OpenAPI JSON Spec**: [http://localhost:8000/v3/api-docs](http://localhost:8000/v3/api-docs)

### Tài Khoản Mẫu Đăng Nhập (Default Credentials)

| Vai trò | Email | Mật khẩu | Quyền hạn |
| :--- | :--- | :--- | :--- |
| **Admin / Giảng viên** | `admin@nextunity.com` | `123456` | Quản trị toàn hệ thống, tạo môn học |
| **Sinh viên PLUS** | `khoatm@fpt.edu.vn` | `123456` | Học viên VIP (Full quyền xem đề thi & bài học) |
| **Sinh viên FREE** | `honglt@fpt.edu.vn` | `123456` | Học viên thông thường |

---

## 🛡️ Bản Quyền (License)
Dự án được phát triển phục vụ học tập và nghiên cứu trong môn học Đồ án Công nghệ phần mềm tại Đại học FPT (FPT University).
