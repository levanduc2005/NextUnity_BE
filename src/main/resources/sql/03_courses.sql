-- ==============================================================================
-- 03_courses.sql
-- Seed Data for Courses Entity
-- ==============================================================================

INSERT INTO courses (
    id, created_at, updated_at, deleted_at,
    title, description, category, difficulty, thumbnail_url, is_premium, total_lessons, creator_id
)
VALUES
    ('e0000000-0000-0000-0000-000000000001', NOW(), NOW(), NULL, 'AI Fundamentals', 'Understand core concepts of Artificial Intelligence and how it powers modern applications.', 'FPT Courses', 'BEGINNER', 'https://images.unsplash.com/photo-1677442136019-21780ecad995?auto=format&fit=crop&w=800&q=80', FALSE, 8, 'b0000000-0000-0000-0000-000000000001'),
    ('e0000000-0000-0000-0000-000000000002', NOW(), NOW(), NULL, 'Database Systems', 'Learn to design, implement and query relational databases with SQL and normalization.', 'FPT Courses', 'INTERMEDIATE', 'https://images.unsplash.com/photo-1544383835-bda2bc66a55d?auto=format&fit=crop&w=800&q=80', FALSE, 7, 'b0000000-0000-0000-0000-000000000001'),
    ('e0000000-0000-0000-0000-000000000003', NOW(), NOW(), NULL, 'Data Structures', 'Master foundational data structures and algorithms (Array, Linked List, Stack, Queue, Tree) in Java.', 'FPT Courses', 'INTERMEDIATE', 'https://images.unsplash.com/photo-1516116211227-bbc804fb5f42?auto=format&fit=crop&w=800&q=80', FALSE, 6, 'b0000000-0000-0000-0000-000000000001'),
    ('e0000000-0000-0000-0000-000000000004', NOW(), NOW(), NULL, 'Discrete Mathematics', 'Learn propositional logic, set theory, graph theory, combinatorics and formal reasoning.', 'FPT Courses', 'ADVANCED', 'https://images.unsplash.com/photo-1635070041078-e363dbe005cb?auto=format&fit=crop&w=800&q=80', FALSE, 7, 'b0000000-0000-0000-0000-000000000001')
ON CONFLICT (id) DO NOTHING;
