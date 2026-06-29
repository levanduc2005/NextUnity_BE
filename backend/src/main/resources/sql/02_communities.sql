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
