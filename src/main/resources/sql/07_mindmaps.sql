-- ==============================================================================
-- 07_mindmaps.sql
-- Seed Data for Mindmaps Entity
-- ==============================================================================

INSERT INTO mindmaps (
    id, created_at, updated_at,
    lesson_id, title, data
)
VALUES
    (
        'e4000000-0000-0000-0000-000000000001', NOW(), NOW(),
        'e2000000-0000-0000-0000-000000000003',
        'Sơ đồ phân loại trí tuệ nhân tạo (AI Types)',
        '{"root":{"id":"root","topic":"Artificial Intelligence","children":[{"id":"c1","topic":"Narrow AI (ANI)","children":[{"id":"c1_1","topic":"Voice Assistants (Siri, Alexa)"},{"id":"c1_2","topic":"Recommendation Systems"}]},{"id":"c2","topic":"General AI (AGI)","children":[{"id":"c2_1","topic":"Human-level Reasoning"},{"id":"c2_2","topic":"Multimodal Transfer Learning"}]},{"id":"c3","topic":"Super AI (ASI)","children":[{"id":"c3_1","topic":"Autonomous Scientific Discovery"},{"id":"c3_2","topic":"Theoretical Frontier"}]}]}}'
    ),
    (
        'e4000000-0000-0000-0000-000000000002', NOW(), NOW(),
        'e2000000-0000-0000-0000-000000000011',
        'Kiến trúc mô hình quan hệ (Relational Model)',
        '{"root":{"id":"root","topic":"Relational Model","children":[{"id":"db1","topic":"Tables (Relations)","children":[{"id":"db1_1","topic":"Tuples (Rows)"},{"id":"db1_2","topic":"Attributes (Columns)"}]},{"id":"db2","topic":"Keys","children":[{"id":"db2_1","topic":"Primary Key"},{"id":"db2_2","topic":"Foreign Key"}]},{"id":"db3","topic":"Integrity Rules","children":[{"id":"db3_1","topic":"Entity Integrity"},{"id":"db3_2","topic":"Referential Integrity"}]}]}}'
    )
ON CONFLICT (id) DO NOTHING;
