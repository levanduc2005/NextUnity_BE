-- 1. Seed Roles
INSERT INTO roles (id, name, description, created_at, updated_at)
VALUES
    ('a0000000-0000-0000-0000-000000000001', 'ADMIN', 'Quản trị viên hệ thống', NOW(), NOW()),
    ('a0000000-0000-0000-0000-000000000003', 'STUDENT', 'Sinh viên', NOW(), NOW())
    ON CONFLICT (id) DO NOTHING;

-- 2. Seed Users (Mật khẩu băm Bcrypt của '123456')
INSERT INTO users (
    id, created_at, updated_at, deleted_at, role_id, email, password_hash,
    full_name, university, student_id, avatar_url, points, streak, gender, date_of_birth, cohort, major, bio,
    subscription_tier, premium_until
)
VALUES
    -- Admin (khớp admin@nextunity.com & phongnx)
    ('b0000000-0000-0000-0000-000000000001', NOW(), NOW(), NULL, 'a0000000-0000-0000-0000-000000000001', 'admin@nextunity.com', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.',
     'Dr. Nguyen Xuan Phong', 'FPT Can Tho', 'GV000001', 'https://api.dicebear.com/7.x/avataaars/svg?seed=phongnx', 5000, 30, 'MALE', '1990-07-25', 0, 'Computer Science', 'Senior Lecturer with 10+ years of experience.',
     'FREE', NULL),

    -- Member 1: Tran Minh Khoa (STUDENT - PLUS subscriber)
    ('b0000000-0000-0000-0000-000000000002', NOW(), NOW(), NULL, 'a0000000-0000-0000-0000-000000000003', 'khoatm@fpt.edu.vn', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.',
     'Tran Minh Khoa', 'FPT HCM', 'SE150001', 'https://api.dicebear.com/7.x/avataaars/svg?seed=khoatm', 2450, 15, 'MALE', '2002-05-14', 17, 'Software Engineering', 'Passionate about building scalable applications and learning new tech.',
     'PLUS', NOW() + INTERVAL '30 days'),

    -- Member 2: Le Thi Hong (STUDENT - FREE)
    ('b0000000-0000-0000-0000-000000000003', NOW(), NOW(), NULL, 'a0000000-0000-0000-0000-000000000003', 'honglt@fpt.edu.vn', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.',
     'Le Thi Hong', 'FPT HN', 'HE150002', 'https://api.dicebear.com/7.x/avataaars/svg?seed=honglt', 2180, 12, 'FEMALE', '2003-08-22', 18, 'Information Systems', 'Data enthusiast, always curious about databases and analytics.',
     'FREE', NULL),

    -- Member 3: Pham Duc Anh (STUDENT - FREE)
    ('b0000000-0000-0000-0000-000000000004', NOW(), NOW(), NULL, 'a0000000-0000-0000-0000-000000000003', 'anhpd@fpt.edu.vn', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.',
     'Pham Duc Anh', 'FPT DN', 'DE150003', 'https://api.dicebear.com/7.x/avataaars/svg?seed=anhpd', 1920, 10, 'MALE', '2003-11-05', 18, 'Artificial Intelligence', 'AI researcher in the making.',
     'FREE', NULL),

    -- Member 4: Nguyen Van A (STUDENT - FREE)
    ('b0000000-0000-0000-0000-000000000005', NOW(), NOW(), NULL, 'a0000000-0000-0000-0000-000000000003', 'anv@fpt.edu.vn', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.',
     'Nguyen Van A', 'FPT HCM', 'SE150004', 'https://api.dicebear.com/7.x/avataaars/svg?seed=anv', 1750, 7, 'MALE', '2004-02-10', 19, 'Software Engineering', 'Freshman exploring the world of code.',
     'FREE', NULL)
    ON CONFLICT (id) DO NOTHING;
