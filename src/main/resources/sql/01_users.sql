-- 1. Seed Roles
INSERT INTO roles (id, name, description, created_at, updated_at)
VALUES
    ('a0000000-0000-0000-0000-000000000001', 'ADMIN', 'Quản trị viên hệ thống', NOW(), NOW()),
    ('a0000000-0000-0000-0000-000000000003', 'STUDENT', 'Sinh viên', NOW(), NOW())
    ON CONFLICT (id) DO NOTHING;

-- 2. Seed Users (Mật khẩu băm Bcrypt của '123456')
INSERT INTO users (
    id, created_at, updated_at, deleted_at, role_id, email, password_hash,
    full_name, university, student_id, avatar_url, streak, gender, date_of_birth, cohort, major, bio,
    subscription_tier, premium_until
)
VALUES
    -- Admin (khớp admin@nextunity.com & phongnx)
    ('b0000000-0000-0000-0000-000000000001', NOW(), NOW(), NULL, 'a0000000-0000-0000-0000-000000000001', 'admin@nextunity.com', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.',
     'Dr. Nguyen Xuan Phong', 'FPT Can Tho', 'GV000001', 'https://api.dicebear.com/7.x/avataaars/svg?seed=phongnx', 30, 'MALE', '1990-07-25', 0, 'Computer Science', 'Senior Lecturer with 10+ years of experience.',
     'FREE', NULL),

    -- Member 1: Tran Minh Khoa (STUDENT - PLUS subscriber)
    ('b0000000-0000-0000-0000-000000000002', NOW(), NOW(), NULL, 'a0000000-0000-0000-0000-000000000003', 'khoatm@fpt.edu.vn', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.',
     'Tran Minh Khoa', 'FPT HCM', 'SE150001', 'https://api.dicebear.com/7.x/avataaars/svg?seed=khoatm', 15, 'MALE', '2002-05-14', 17, 'Software Engineering', 'Passionate about building scalable applications and learning new tech.',
     'PLUS', NOW() + INTERVAL '30 days'),

    -- Member 2: Le Thi Hong (STUDENT - FREE)
    ('b0000000-0000-0000-0000-000000000003', NOW(), NOW(), NULL, 'a0000000-0000-0000-0000-000000000003', 'honglt@fpt.edu.vn', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.',
     'Le Thi Hong', 'FPT HN', 'HE150002', 'https://api.dicebear.com/7.x/avataaars/svg?seed=honglt', 12, 'FEMALE', '2003-08-22', 18, 'Information Systems', 'Data enthusiast, always curious about databases and analytics.',
     'FREE', NULL),

    -- Member 3: Pham Duc Anh (STUDENT - FREE)
    ('b0000000-0000-0000-0000-000000000004', NOW(), NOW(), NULL, 'a0000000-0000-0000-0000-000000000003', 'anhpd@fpt.edu.vn', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.',
     'Pham Duc Anh', 'FPT DN', 'DE150003', 'https://api.dicebear.com/7.x/avataaars/svg?seed=anhpd', 10, 'MALE', '2003-11-05', 18, 'Artificial Intelligence', 'AI researcher in the making.',
     'FREE', NULL),

    -- Member 4: Nguyen Van A (STUDENT - FREE)
    ('b0000000-0000-0000-0000-000000000005', NOW(), NOW(), NULL, 'a0000000-0000-0000-0000-000000000003', 'anv@fpt.edu.vn', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.',
     'Nguyen Van A', 'FPT HCM', 'SE150004', 'https://api.dicebear.com/7.x/avataaars/svg?seed=anv', 7, 'MALE', '2004-02-10', 19, 'Software Engineering', 'Freshman exploring the world of code.',
     'FREE', NULL),

    -- Member 5: Hoang Quoc Bao (STUDENT - PLUS)
    ('b0000000-0000-0000-0000-000000000006', NOW() - INTERVAL '30 days', NOW(), NULL, 'a0000000-0000-0000-0000-000000000003', 'baohq@fpt.edu.vn', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.',
     'Hoang Quoc Bao', 'FPT HN', 'HE160010', 'https://api.dicebear.com/7.x/avataaars/svg?seed=baohq', 25, 'MALE', '2001-04-12', 16, 'Software Engineering', 'Alumni K16 SE FPT Hanoi. Software Engineer @ FPT Software, Mentor SWP391.',
     'PLUS', NOW() + INTERVAL '60 days'),

    -- Member 6: Vu Thi Mai Chi (STUDENT - PLUS)
    ('b0000000-0000-0000-0000-000000000007', NOW() - INTERVAL '30 days', NOW(), NULL, 'a0000000-0000-0000-0000-000000000003', 'chivtm@fpt.edu.vn', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.',
     'Vu Thi Mai Chi', 'FPT HCM', 'SE170025', 'https://api.dicebear.com/7.x/avataaars/svg?seed=chivtm', 20, 'FEMALE', '2002-09-18', 17, 'Information Assurance', 'K17 IA FPT HCM. DSA enthusiast, ICPC competitor, TA CSD201.',
     'PLUS', NOW() + INTERVAL '60 days'),

    -- Member 7: Dang Thai Son (STUDENT - FREE)
    ('b0000000-0000-0000-0000-000000000008', NOW() - INTERVAL '30 days', NOW(), NULL, 'a0000000-0000-0000-0000-000000000003', 'sondt@fpt.edu.vn', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.',
     'Dang Thai Son', 'FPT Quy Nhon', 'QE180015', 'https://api.dicebear.com/7.x/avataaars/svg?seed=sondt', 18, 'MALE', '2003-03-22', 18, 'Artificial Intelligence', 'K18 AI FPT Quy Nhon. Machine Learning & Python passionate.',
     'FREE', NULL),

    -- Member 8: Nguyen Hoang Nam (STUDENT - FREE)
    ('b0000000-0000-0000-0000-000000000009', NOW() - INTERVAL '30 days', NOW(), NULL, 'a0000000-0000-0000-0000-000000000003', 'namnh@fpt.edu.vn', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.',
     'Nguyen Hoang Nam', 'FPT Can Tho', 'CE170030', 'https://api.dicebear.com/7.x/avataaars/svg?seed=namnh', 14, 'MALE', '2002-12-05', 17, 'Software Engineering', 'K17 SE FPT Can Tho. Full-stack Web & Database developer.',
     'FREE', NULL),

    -- Member 9: Tran Bao Ngoc (STUDENT - PLUS)
    ('b0000000-0000-0000-0000-000000000010', NOW() - INTERVAL '30 days', NOW(), NULL, 'a0000000-0000-0000-0000-000000000003', 'ngoctb@fpt.edu.vn', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.',
     'Tran Bao Ngoc', 'FPT HCM', 'SE180050', 'https://api.dicebear.com/7.x/avataaars/svg?seed=ngoctb', 22, 'FEMALE', '2003-07-14', 18, 'Software Engineering', 'K18 SE FPT HCM. UI/UX design and Frontend lover.',
     'PLUS', NOW() + INTERVAL '60 days')
    ON CONFLICT (id) DO NOTHING;
