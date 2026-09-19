-- ==============================================================================
-- 05_lessons.sql
-- Seed Data for Lessons Entity (Compact & Readable)
-- ==============================================================================

INSERT INTO lessons (
    id, created_at, updated_at, deleted_at,
    module_id, title, content, order_index, type, video_url
)
VALUES
    -- Course 1, Module 1 (Introduction to AI)
    ('e2000000-0000-0000-0000-000000000001', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000001', 'What is AI?', 'Artificial intelligence is the simulation of human intelligence processes by machines, especially computer systems.', 1, 'VIDEO', 'https://www.youtube.com/watch?v=JMUxmLyrhSk'),
    ('e2000000-0000-0000-0000-000000000002', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000001', 'History of AI', 'The history of AI dates back to antiquity, with modern AI founded at Dartmouth College in 1956.', 2, 'TEXT', NULL),
    ('e2000000-0000-0000-0000-000000000003', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000001', 'Types of AI', 'Explore Narrow AI (Weak AI), General AI (Strong AI), and Superintelligent AI.', 3, 'INTERACTIVE', NULL),
    ('e2000000-0000-0000-0000-000000000004', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000001', 'AI in Everyday Life', 'Recommendation engines on Netflix, navigation in autonomous vehicles, and virtual assistants.', 4, 'TEXT', NULL),
    ('e2000000-0000-0000-0000-000000000005', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000001', 'Ethical Considerations in AI', 'Addressing bias in machine learning models, accountability, and automated decision-making ethics.', 5, 'TEXT', NULL),

    -- Course 1, Module 2 (Machine Learning Basics)
    ('e2000000-0000-0000-0000-000000000006', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000002', 'Supervised vs Unsupervised Learning', 'Supervised learning trains on labeled datasets; unsupervised learning discovers hidden patterns in unlabeled data.', 1, 'TEXT', NULL),
    ('e2000000-0000-0000-0000-000000000007', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000002', 'Linear Regression & Classification', 'Predicting continuous targets with linear regression, and classifying categorical outcomes.', 2, 'TEXT', NULL),

    -- Course 1, Module 3 (Neural Networks)
    ('e2000000-0000-0000-0000-000000000008', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000003', 'Introduction to Artificial Neurons', 'Perceptron structure: inputs, weights, bias, activation functions (ReLU, Sigmoid), and forward pass.', 1, 'TEXT', NULL),

    -- Course 2, Module 1 (Introduction to Databases)
    ('e2000000-0000-0000-0000-000000000009', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000004', 'What is a Database?', 'Organized collection of structured information stored electronically and managed by a DBMS.', 1, 'VIDEO', 'https://www.youtube.com/watch?v=wR0jg0eQsZA'),
    ('e2000000-0000-0000-0000-000000000010', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000004', 'File Systems vs DBMS', 'Comparing concurrency control, ACID transaction isolation, crash recovery, and redundancy.', 2, 'TEXT', NULL),
    ('e2000000-0000-0000-0000-000000000011', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000004', 'Relational Model', 'Structuring data using tables, tuples, primary keys, foreign keys and integrity constraints.', 3, 'INTERACTIVE', NULL),
    ('e2000000-0000-0000-0000-000000000012', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000004', 'NoSQL Databases', 'Document stores, key-value caches, and graph databases for high scalability.', 4, 'TEXT', NULL),

    -- Course 2, Module 2 (SQL Fundamentals)
    ('e2000000-0000-0000-0000-000000000013', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000005', 'DDL and DML Queries', 'Data Definition (CREATE, ALTER) and Data Manipulation (SELECT, INSERT, UPDATE, DELETE).', 1, 'TEXT', NULL),
    ('e2000000-0000-0000-0000-000000000014', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000005', 'JOIN Operations', 'INNER JOIN, LEFT JOIN, RIGHT JOIN, and FULL OUTER JOIN with query optimization.', 2, 'TEXT', NULL),

    -- Course 2, Module 3 (Database Design)
    ('e2000000-0000-0000-0000-000000000015', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000006', 'Database Normalization (1NF - BCNF)', 'Eliminating redundancy and update anomalies via 1NF, 2NF, 3NF, and Boyce-Codd Normal Form.', 1, 'TEXT', NULL),

    -- Course 3, Module 1 (Introduction to Data Structures)
    ('e2000000-0000-0000-0000-000000000016', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000007', 'What is a Data Structure?', 'Memory organization concepts and choosing suitable structures for optimal algorithms.', 1, 'VIDEO', 'https://www.youtube.com/watch?v=RBSGKlAvoiM'),
    ('e2000000-0000-0000-0000-000000000017', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000007', 'Arrays vs Linked Lists', 'Core differences between contiguous memory allocations vs pointer-linked heap nodes.', 2, 'INTERACTIVE', NULL),
    ('e2000000-0000-0000-0000-000000000018', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000007', 'Time Complexity (Big O)', 'Asymptotic runtime bounds: O(1), O(log N), O(N), O(N log N), and O(N^2).', 3, 'TEXT', NULL),
    ('e2000000-0000-0000-0000-000000000019', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000007', 'Space Complexity', 'Understanding auxiliary memory footprint and stack overhead in recursion.', 4, 'TEXT', NULL),

    -- Course 3, Module 2 (Stacks & Queues)
    ('e2000000-0000-0000-0000-000000000020', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000008', 'Stack Operations & Applications', 'Push, pop, peek implementation. Use-cases: parenthesis matching, undo history, evaluation.', 1, 'TEXT', NULL),

    -- Course 3, Module 3 (Trees & Graphs)
    ('e2000000-0000-0000-0000-000000000021', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000009', 'Binary Search Trees (BST)', 'BST properties, insertion, deletion, and tree traversals (inorder, preorder, postorder).', 1, 'TEXT', NULL),

    -- Course 4, Module 1 (Logic & Proofs)
    ('e2000000-0000-0000-0000-000000000022', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000010', 'Propositional Logic', 'Logical propositions, truth tables, negation, conjunction, disjunction, and implication.', 1, 'TEXT', NULL),
    ('e2000000-0000-0000-0000-000000000023', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000010', 'Logical Equivalences', 'De Morgan laws, contrapositive identities, and simplifying boolean expressions.', 2, 'TEXT', NULL),
    ('e2000000-0000-0000-0000-000000000024', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000010', 'Predicates & Quantifiers', 'Universal (∀) and existential (∃) quantifiers, domain of discourse, and negations.', 3, 'TEXT', NULL),
    ('e2000000-0000-0000-0000-000000000025', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000010', 'Rules of Inference', 'Modus Ponens, Modus Tollens, Hypothetical Syllogism, and constructing formal proofs.', 4, 'INTERACTIVE', NULL),
    ('e2000000-0000-0000-0000-000000000026', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000010', 'Introduction to Proofs', 'Direct proof, proof by contraposition, and proof by contradiction.', 5, 'TEXT', NULL),

    -- Course 4, Module 2 (Set Theory)
    ('e2000000-0000-0000-0000-000000000027', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000011', 'Sets and Set Operations', 'Power sets, Cartesian products, union, intersection, set differences, and Venn diagrams.', 1, 'TEXT', NULL),

    -- Course 4, Module 3 (Graph Theory)
    ('e2000000-0000-0000-0000-000000000028', NOW(), NOW(), NULL, 'e1000000-0000-0000-0000-000000000012', 'Graph Representation & Paths', 'Adjacency matrices, Handshaking Theorem, Euler paths, and Hamilton cycles.', 1, 'TEXT', NULL)
ON CONFLICT (id) DO NOTHING;
