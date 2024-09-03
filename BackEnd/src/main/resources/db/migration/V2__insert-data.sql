INSERT INTO Users (email, role, operative) VALUES
('admin@example.com', 'ADMIN', 1),
('support@example.com', 'SUPPORT', 1),
('user@example.com', 'USER', 1);

INSERT INTO access_history (id_user, login_datetime, logout_datetime) VALUES
(1, '2024-09-01 08:00:00', '2024-09-01 16:00:00'),
(2, '2024-09-01 09:00:00', '2024-09-01 17:00:00');

INSERT INTO classes (course_name, classes, id_user, creation_date, operative) VALUES
('ADMINISTRACAO', 'Class 1', 3, '2024-09-01', 1),
('DIGITAL_SOLUTIONS', 'Class 2', 3, '2024-09-01', 1);

INSERT INTO building (building_code, operative) VALUES
('B01', 1),
('B02', 1);

INSERT INTO area (area_code, area_name, operative) VALUES
('A01', 'Admin Area', 1),
('A02', 'Tech Area', 1);

INSERT INTO raspberry (raspberry_name, id_building, id_area, operative) VALUES
('Raspberry A', 1, 1, 1),
('Raspberry B', 2, 2, 1);

INSERT INTO environment (environment_name, id_raspberry, operative) VALUES
('Lab 1', 1, 1),
('Lab 2', 2, 1);

INSERT INTO post (post, operative) VALUES
('Post 1', 1),
('Post 2', 1);

INSERT INTO location (id_post, id_environment) VALUES
(1, 1),
(2, 2);

INSERT INTO cost_center (cost_center_name, operative) VALUES
('Cost Center 1', 1),
('Cost Center 2', 1);

INSERT INTO main_owner (id_owner, owner_name, id_cost_center, operative) VALUES
('OWNER1', 'Owner Name 1', 1, 1),
('OWNER2', 'Owner Name 2', 2, 1);

INSERT INTO equipment (id_equipment, rfid, type, model, validity, admin_rights, observation, id_location, id_owner, image, operative) VALUES
('EQUIP1', 'RFID123', 'Laptop', 'Model A', '2025-12-31', 'Admin A', 'Observation 1', 1, 'OWNER1', 'image1.jpg', 1),
('EQUIP2', 'RFID456', 'Projector', 'Model B', '2026-12-31', 'Admin B', 'Observation 2', 2, 'OWNER2', 'image2.jpg', 1);

INSERT INTO tracking_history (id_equipment, id_environment, action, dateTime) VALUES
('EQUIP1', 1, 'ENTER', '2024-09-01 08:00:00'),
('EQUIP2', 2, 'OUT', '2024-09-01 09:00:00');

INSERT INTO responsible (responsible_name, edv, id_classes, id_user, operative) VALUES
('Responsible 1', 'EDV123', 1, 1, 1),
('Responsible 2', 'EDV456', 2, 2, 1);

INSERT INTO equipment_responsible (id_equipment, id_responsible, start_usage, end_usage, operative) VALUES
('EQUIP1', 1, '2024-09-01', NULL, 1),
('EQUIP2', 2, '2024-09-02', NULL, 1);

INSERT INTO user_log (id_user, altered_table, id_altered, field, description, datetime, action) VALUES
(1, 'equipment', 'EQUIP1', 'rfid', 'Updated RFID to RFID789', '2024-09-01 10:00:00', 'UPDATE'),
(2, 'user_log', '1', 'description', 'Added new log entry', '2024-09-01 11:00:00', 'CREATE');