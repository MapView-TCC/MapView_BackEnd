INSERT INTO Users (email, role, operative) VALUES
('user1@example.com', 'ADMIN', 1),
('user2@example.com', 'SUPPORT', 1),
('user3@example.com', 'USER', 1);

INSERT INTO access_history (id_user, login_datetime, logout_datetime) VALUES
(1, NOW(), NULL),
(2, NOW(), NULL),
(3, NOW(), NULL);

INSERT INTO classes (course_name, classes, id_user, creation_date, operative) VALUES
('ADMINISTRACAO', 'Class A', 1, '2024-01-01', 1),
('DIGITAL_SOLUTIONS', 'Class B', 2, '2024-01-01', 1);

INSERT INTO building (building_code, operative) VALUES
('B001', 1),
('B002', 1);

INSERT INTO area (area_code, area_name, operative) VALUES
('A001', 'Area 1', 1),
('A002', 'Area 2', 1);

INSERT INTO raspberry (raspberry_name, id_building, id_area, operative) VALUES
('Rasp1', 1, 1, 1),
('Rasp2', 2, 2, 1);

INSERT INTO environment (environment_name, id_raspberry, operative) VALUES
('Env1', 1, 1),
('Env2', 2, 1);

INSERT INTO post (post, operative) VALUES
('Post1', 1),
('Post2', 1);

INSERT INTO location (id_post, id_environment) VALUES
(1, 1),
(2, 2);

INSERT INTO cost_center (cost_center_name, operative) VALUES
('Cost1', 1),
('Cost2', 1);

INSERT INTO main_owner (id_owner, owner_name, id_cost_center, operative) VALUES
('Owner1', 'Owner 1', 1, 1),
('Owner2', 'Owner 2', 2, 1);

INSERT INTO equipment (id_equipment, name_equipment, rfid, type, model, validity, admin_rights, observation, id_location, id_owner, image, operative) VALUES
('Equip1', 'Equipment 1', 'RFID001', 'Type1', 'T16', '2025-01-01', 'Admin1', 'Observation 1', 1, 'Owner1', 'image1.png', 1),
('Equip2', 'Equipment 2', 'RFID002', 'Type2', 'T14', '2025-01-01', 'Admin2', 'Observation 2', 2, 'Owner2', 'image2.png', 1);

INSERT INTO tracking_history (id_equipment, id_environment, action, colors, dateTime) VALUES
('Equip1', 1, 'ENTER', 'RED', NOW()),
('Equip2', 2, 'OUT', 'GREEN', NOW());

INSERT INTO responsible (responsible_name, edv, id_classes, id_user, operative) VALUES
('Responsible 1', 'EDV001', 1, 1, 1),
('Responsible 2', 'EDV002', 2, 2, 1);

INSERT INTO equipment_responsible (id_equipment, id_responsible, start_usage, end_usage, operative) VALUES
('Equip1', 1, '2024-01-01', NULL, 1),
('Equip2', 2, '2024-01-01', NULL, 1);

INSERT INTO user_log (id_user, altered_table, id_altered, field, description, datetime, action) VALUES
(1, 'equipment', 'Equip1', 'type', 'Updated type', NOW(), 'UPDATE'),
(2, 'users', 'User2', 'email', 'Updated email', NOW(), 'UPDATE');
