INSERT INTO data.users(id, date_time_create, date_time_update, email, full_name, role, status, password)
VALUES (gen_random_uuid(),
        NOW(),
        NOW(),
        'admin@admin.com',
        'admin',
        'ADMIN',
        'ACTIVATED',
        '$2a$10$y8W1uCY4lbkHs7bHfACBqefZElsVmMkRpE0DmgeOzzBSrwh2pXHl6')
