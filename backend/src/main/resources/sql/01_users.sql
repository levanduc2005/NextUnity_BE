-- 1. Seed Roles
INSERT INTO roles (id, name, description, created_at, updated_at)
VALUES
    ('a0000000-0000-0000-0000-000000000001', 'ADMIN', 'Quản trị viên hệ thống', NOW(), NOW()),
    ('a0000000-0000-0000-0000-000000000002', 'USER', 'Người dùng thông thường', NOW(), NOW())
    ON CONFLICT (id) DO NOTHING;

-- 2. Seed Users (Mật khẩu băm Bcrypt của '123456' giống trong file mẫu của bạn)
INSERT INTO users (id, created_at, updated_at, deleted_at, role_id, email, password_hash, username)
VALUES
    ('b0000000-0000-0000-0000-000000000001', NOW(), NOW(), NULL, 'a0000000-0000-0000-0000-000000000001', 'admin@nextunity.com', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.', 'Administrator'),
    ('b0000000-0000-0000-0000-000000000002', NOW(), NOW(), NULL, 'a0000000-0000-0000-0000-000000000002', 'member1@nextunity.com', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.', 'Nguyen Van A'),
    ('b0000000-0000-0000-0000-000000000003', NOW(), NOW(), NULL, 'a0000000-0000-0000-0000-000000000002', 'member2@nextunity.com', '$2a$12$RBcyryCgZEe7ta5MMeX4huiO8V8txscrw/FunPgjW9bnFtRcTF4H.', 'Tran Thi B')
    ON CONFLICT (id) DO NOTHING;
