-- ==============================================================================
-- 06_flashcards.sql
-- Seed Data for Flashcards Entity (Compact & Readable)
-- ==============================================================================

INSERT INTO flashcards (
    id, created_at, updated_at,
    lesson_id, front, back, order_index
)
VALUES
    -- Lesson 1.3 (AI Fundamentals - Types of AI)
    ('e3000000-0000-0000-0000-000000000001', NOW(), NOW(), 'e2000000-0000-0000-0000-000000000003', 'Human-level intelligence', 'Capable of performing any intellectual task that a human can do.', 1),
    ('e3000000-0000-0000-0000-000000000002', NOW(), NOW(), 'e2000000-0000-0000-0000-000000000003', 'Adaptability', 'Can transfer knowledge from one domain to another seamlessly.', 2),
    ('e3000000-0000-0000-0000-000000000003', NOW(), NOW(), 'e2000000-0000-0000-0000-000000000003', 'Still Theoretical', 'AGI does not exist yet. It remains an active and intensive area of research.', 3),
    ('e3000000-0000-0000-0000-000000000004', NOW(), NOW(), 'e2000000-0000-0000-0000-000000000003', 'What is Narrow AI (ANI)?', 'AI designed and programmed to excel at a single, specific task (e.g. facial recognition, chess bot).', 4),

    -- Lesson 1.3 (Database Systems - Relational Model)
    ('e3000000-0000-0000-0000-000000000005', NOW(), NOW(), 'e2000000-0000-0000-0000-000000000011', 'Primary Key (PK)', 'A unique identifier for each record in a table. It cannot be NULL.', 1),
    ('e3000000-0000-0000-0000-000000000006', NOW(), NOW(), 'e2000000-0000-0000-0000-000000000011', 'Foreign Key (FK)', 'A field in one table that uniquely identifies a row of another table, establishing a relationship.', 2),
    ('e3000000-0000-0000-0000-000000000007', NOW(), NOW(), 'e2000000-0000-0000-0000-000000000011', 'Integrity Constraint', 'Ensures that relationships between tables remain consistent (Referential Integrity).', 3),

    -- Lesson 1.2 (Data Structures - Arrays vs Linked Lists)
    ('e3000000-0000-0000-0000-000000000008', NOW(), NOW(), 'e2000000-0000-0000-0000-000000000017', 'Node', 'The basic building block of a linked list, containing data and reference pointer(s) to neighbor node(s).', 1),
    ('e3000000-0000-0000-0000-000000000009', NOW(), NOW(), 'e2000000-0000-0000-0000-000000000017', 'Dynamic Size', 'Unlike fixed-size arrays, linked lists grow and shrink dynamically during program execution.', 2),
    ('e3000000-0000-0000-0000-000000000010', NOW(), NOW(), 'e2000000-0000-0000-0000-000000000017', 'O(N) Sequential Access', 'To access element k, you must traverse through the first k-1 nodes starting from the head pointer.', 3),

    -- Lesson 1.4 (Discrete Math - Rules of Inference)
    ('e3000000-0000-0000-0000-000000000011', NOW(), NOW(), 'e2000000-0000-0000-0000-000000000025', 'Hypothetical Syllogism', 'Transitive rule of implication: Premises (p -> q, q -> r) entail the conclusion (p -> r).', 1),
    ('e3000000-0000-0000-0000-000000000012', NOW(), NOW(), 'e2000000-0000-0000-0000-000000000025', 'Modus Ponens', 'Law of detachment: If p is true, and p -> q is true, then q must be true.', 2),
    ('e3000000-0000-0000-0000-000000000013', NOW(), NOW(), 'e2000000-0000-0000-0000-000000000025', 'Modus Tollens', 'Denying the consequent: If p -> q is true, and not q is true, then not p must be true.', 3)
ON CONFLICT (id) DO NOTHING;
