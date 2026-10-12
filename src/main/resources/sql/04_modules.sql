-- ==============================================================================
-- 04_modules.sql
-- Seed Data for Modules Entity
-- ==============================================================================

INSERT INTO modules (
    id, created_at, updated_at, deleted_at,
    course_id, title, description, order_index
)
VALUES
    -- Modules for Course 1: AI Fundamentals
    ('e1000000-0000-0000-0000-000000000001', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000001', 'Introduction to AI', 'Explore foundational concepts and history of AI.', 1),
    ('e1000000-0000-0000-0000-000000000002', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000001', 'Machine Learning Basics', 'Core concepts of supervised and unsupervised learning.', 2),
    ('e1000000-0000-0000-0000-000000000003', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000001', 'Neural Networks', 'Introduction to deep architectures and perception.', 3),

    -- Modules for Course 2: Database Systems
    ('e1000000-0000-0000-0000-000000000004', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000002', 'Introduction to Databases', 'Relational model, DBMS vs File Systems.', 1),
    ('e1000000-0000-0000-0000-000000000005', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000002', 'SQL Fundamentals', 'DDL, DML and advanced JOIN techniques.', 2),
    ('e1000000-0000-0000-0000-000000000006', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000002', 'Database Design', 'ERD, Normalization (1NF to BCNF).', 3),

    -- Modules for Course 3: Data Structures
    ('e1000000-0000-0000-0000-000000000007', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000003', 'Introduction to Data Structures', 'Complexity analysis and linear memory.', 1),
    ('e1000000-0000-0000-0000-000000000008', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000003', 'Stacks & Queues', 'LIFO, FIFO principles and buffer implementations.', 2),
    ('e1000000-0000-0000-0000-000000000009', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000003', 'Trees & Graphs', 'Hierarchical structures, BFS, DFS and BST.', 3),

    -- Modules for Course 4: Discrete Mathematics
    ('e1000000-0000-0000-0000-000000000010', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000004', 'Logic & Proofs', 'Propositional logic, inference rules and direct proofs.', 1),
    ('e1000000-0000-0000-0000-000000000011', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000004', 'Set Theory', 'Sets, functions, sequences and relations.', 2),
    ('e1000000-0000-0000-0000-000000000012', NOW(), NOW(), NULL, 'e0000000-0000-0000-0000-000000000004', 'Graph Theory', 'Euler paths, Hamilton cycles and trees.', 3)
ON CONFLICT (id) DO NOTHING;
