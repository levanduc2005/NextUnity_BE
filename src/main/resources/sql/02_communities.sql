-- 1. Seed Communities
INSERT INTO communities (id, name, description, creator_id, created_at, updated_at, deleted_at)
VALUES
    ('c0000000-0000-0000-0000-000000000001', 'NextUnity Official Support', 'Cộng đồng hỗ trợ chính thức và giải đáp thắc mắc về nền tảng NextUnity.', 'b0000000-0000-0000-0000-000000000001', NOW(), NOW(), NULL),
    ('c0000000-0000-0000-0000-000000000002', 'Java Developers Vietnam', 'Nơi chia sẻ kiến thức, kinh nghiệm và cơ hội việc làm về ngôn ngữ lập trình Java.', 'b0000000-0000-0000-0000-000000000002', NOW(), NOW(), NULL)
    ON CONFLICT (id) DO NOTHING;

-- 2. Seed Community Members
INSERT INTO community_members (id, community_id, user_id, role, created_at, updated_at)
VALUES
    -- Gán Creator com1 làm ADMIN của nhóm com1
    ('d0000000-0000-0000-0000-000000000001', 'c0000000-0000-0000-0000-000000000001', 'b0000000-0000-0000-0000-000000000001', 'ADMIN', NOW(), NOW()),

    -- Gán Creator com2 làm ADMIN của nhóm com2
    ('d0000000-0000-0000-0000-000000000002', 'c0000000-0000-0000-0000-000000000002', 'b0000000-0000-0000-0000-000000000002', 'ADMIN', NOW(), NOW()),

    -- Member 2 tham gia nhóm com1 làm MEMBER
    ('d0000000-0000-0000-0000-000000000003', 'c0000000-0000-0000-0000-000000000001', 'b0000000-0000-0000-0000-000000000003', 'MEMBER', NOW(), NOW()),

    -- Member 2 tham gia nhóm com2 làm MEMBER
    ('d0000000-0000-0000-0000-000000000004', 'c0000000-0000-0000-0000-000000000002', 'b0000000-0000-0000-0000-000000000003', 'MEMBER', NOW(), NOW()),

    -- Member 1 tham gia nhóm com1 làm MODERATOR
    ('d0000000-0000-0000-0000-000000000005', 'c0000000-0000-0000-0000-000000000001', 'b0000000-0000-0000-0000-000000000002', 'MODERATOR', NOW(), NOW())
    ON CONFLICT (community_id, user_id) DO NOTHING;

-- 3. Seed Tags
INSERT INTO tags (id, created_at, updated_at, name, description)
VALUES
    ('f1000000-0000-0000-0000-000000000001', NOW(), NOW(), 'PRN211', 'Lập trình C# & .NET FPT'),
    ('f1000000-0000-0000-0000-000000000002', NOW(), NOW(), 'PE', 'Kỳ thi Practical Exam FPT'),
    ('f1000000-0000-0000-0000-000000000003', NOW(), NOW(), 'SWP391', 'Đồ án kỹ thuật phần mềm'),
    ('f1000000-0000-0000-0000-000000000004', NOW(), NOW(), 'PRO192', 'Lập trình hướng đối tượng Java')
    ON CONFLICT (id) DO NOTHING;

-- 4. Seed Posts
INSERT INTO posts (id, created_at, updated_at, deleted_at, title, content, author_id, community_id)
VALUES
    ('f2000000-0000-0000-0000-000000000001', NOW() - INTERVAL '2 hours', NOW(), NULL,
     'Kinh nghiệm ôn thi PE PRN211 (C# WPF & Entity Framework) đạt 9.5+',
     'Chào các bạn, kỳ vừa rồi mình vừa qua môn PRN211 với 9.5 điểm PE. Điểm mấu chốt là nắm chắc LINQ, DBContext và DataBinding WPF. Các bạn nên luyện kỹ phần CRUD và xử lý transaction nhé!',
     'b0000000-0000-0000-0000-000000000002', 'c0000000-0000-0000-0000-000000000001'),

    ('f2000000-0000-0000-0000-000000000002', NOW() - INTERVAL '5 hours', NOW(), NULL,
     'Tổng hợp bộ câu hỏi lý thuyết trắc nghiệm PRO192 hay gặp nhất',
     'Mình vừa tổng hợp lại hơn 150 câu trắc nghiệm Java OOP môn PRO192 có giải thích chi tiết, bạn nào sắp thi Final thì lưu lại ôn tập nhé.',
     'b0000000-0000-0000-0000-000000000003', 'c0000000-0000-0000-0000-000000000002'),

    ('f2000000-0000-0000-0000-000000000003', NOW() - INTERVAL '1 day', NOW(), NULL,
     'Xin kinh nghiệm chia task và quản lý tiến độ đồ án SWP391 cho nhóm 5 người',
     'Nhóm mình chuẩn bị làm SWP391 kỳ tới, anh chị đi trước cho mình hỏi nên chọn stack Spring Boot + Next.js hay .NET Core? Cách viết SRS và WBS thế nào để Mentor duyệt nhanh?',
     'b0000000-0000-0000-0000-000000000004', 'c0000000-0000-0000-0000-000000000001')
    ON CONFLICT (id) DO NOTHING;

-- 5. Seed Post Tags
INSERT INTO post_tags (post_id, tag_id)
VALUES
    ('f2000000-0000-0000-0000-000000000001', 'f1000000-0000-0000-0000-000000000001'),
    ('f2000000-0000-0000-0000-000000000001', 'f1000000-0000-0000-0000-000000000002'),
    ('f2000000-0000-0000-0000-000000000002', 'f1000000-0000-0000-0000-000000000004'),
    ('f2000000-0000-0000-0000-000000000003', 'f1000000-0000-0000-0000-000000000003')
    ON CONFLICT (post_id, tag_id) DO NOTHING;

