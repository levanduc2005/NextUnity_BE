-- 1. Seed Roles
INSERT INTO roles (id, name, description, created_at, updated_at)
VALUES
    ('a0000000-0000-0000-0000-000000000001', 'ADMIN', 'Quản trị viên hệ thống', NOW(), NOW()),
    ('a0000000-0000-0000-0000-000000000003', 'STUDENT', 'Sinh viên', NOW(), NOW()),
    ('a0000000-0000-0000-0000-000000000004', 'INSTRUCTOR', 'Giảng viên', NOW(), NOW())
    ON CONFLICT (id) DO NOTHING;

-- 2. Seed Users (Mật khẩu băm Bcrypt của '123456')
INSERT INTO users (
    id, created_at, updated_at, deleted_at, role_id, email, password_hash, username,
    name, university, student_id, avatar_url, points, streak, gender, date_of_birth, cohort, major, bio
)
VALUES
    -- Admin / Giảng viên (khớp admin@nextunity.com & phongnx)
    ('b0000000-0000-0000-0000-000000000001', NOW(), NOW(), NULL, 'a0000000-0000-0000-0000-000000000001', 'admin@nextunity.com', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.', 'phongnx',
     'Dr. Nguyen Xuan Phong', 'FPT Can Tho', 'GV000001', 'https://api.dicebear.com/7.x/avataaars/svg?seed=phongnx', 5000, 30, 'male', '1990-07-25', 0, 'Computer Science', 'Senior Lecturer with 10+ years of experience.'),

    -- Member 1: Tran Minh Khoa (STUDENT)
    ('b0000000-0000-0000-0000-000000000002', NOW(), NOW(), NULL, 'a0000000-0000-0000-0000-000000000003', 'khoatm@fpt.edu.vn', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.', 'khoatm',
     'Tran Minh Khoa', 'FPT HCM', 'SE150001', 'https://api.dicebear.com/7.x/avataaars/svg?seed=khoatm', 2450, 15, 'male', '2002-05-14', 17, 'Software Engineering', 'Passionate about building scalable applications and learning new tech.'),

    -- Member 2: Le Thi Hong (STUDENT)
    ('b0000000-0000-0000-0000-000000000003', NOW(), NOW(), NULL, 'a0000000-0000-0000-0000-000000000003', 'honglt@fpt.edu.vn', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.', 'honglt',
     'Le Thi Hong', 'FPT HN', 'HE150002', 'https://api.dicebear.com/7.x/avataaars/svg?seed=honglt', 2180, 12, 'female', '2003-08-22', 18, 'Information Systems', 'Data enthusiast, always curious about databases and analytics.'),

    -- Member 3: Pham Duc Anh (STUDENT)
    ('b0000000-0000-0000-0000-000000000004', NOW(), NOW(), NULL, 'a0000000-0000-0000-0000-000000000003', 'anhpd@fpt.edu.vn', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.', 'anhpd',
     'Pham Duc Anh', 'FPT DN', 'DE150003', 'https://api.dicebear.com/7.x/avataaars/svg?seed=anhpd', 1920, 10, 'male', '2003-11-05', 18, 'Artificial Intelligence', 'AI researcher in the making.'),

    -- Member 4: Nguyen Van A (STUDENT)
    ('b0000000-0000-0000-0000-000000000005', NOW(), NOW(), NULL, 'a0000000-0000-0000-0000-000000000003', 'anv@fpt.edu.vn', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.', 'anv',
     'Nguyen Van A', 'FPT HCM', 'SE150004', 'https://api.dicebear.com/7.x/avataaars/svg?seed=anv', 1750, 7, 'male', '2004-02-10', 19, 'Software Engineering', 'Freshman exploring the world of code.')
    ON CONFLICT (id) DO NOTHING;

