-- ==============================================================================
-- 08_slides.sql
-- Seed Data for SlideUploads Entity
-- ==============================================================================

INSERT INTO slide_uploads (
    id, created_at, updated_at,
    lesson_id, uploader_id, file_name, file_url, status, rejection_reason
)
VALUES
    (
        'e5000000-0000-0000-0000-000000000001', NOW(), NOW(),
        'e2000000-0000-0000-0000-000000000001',
        'b0000000-0000-0000-0000-000000000001', -- Dr. Phong (Admin)
        'Slide_01_AI_Introduction_Official.pdf',
        'https://storage.nextunity.com/slides/ai_intro_official.pdf',
        'PUBLIC', NULL
    ),
    (
        'e5000000-0000-0000-0000-000000000002', NOW(), NOW(),
        'e2000000-0000-0000-0000-000000000003',
        'b0000000-0000-0000-0000-000000000002', -- Tran Minh Khoa (Student)
        'AI_Types_Summary_Notes_By_Khoa.pdf',
        'https://storage.nextunity.com/slides/ai_types_notes.pdf',
        'PUBLIC', NULL
    ),
    (
        'e5000000-0000-0000-0000-000000000003', NOW(), NOW(),
        'e2000000-0000-0000-0000-000000000011',
        'b0000000-0000-0000-0000-000000000003', -- Le Thi Hong (Student)
        'HongLT_Personal_Database_Draft.pdf',
        'https://storage.nextunity.com/slides/hong_db_draft.pdf',
        'PRIVATE', NULL
    )
ON CONFLICT (id) DO NOTHING;
