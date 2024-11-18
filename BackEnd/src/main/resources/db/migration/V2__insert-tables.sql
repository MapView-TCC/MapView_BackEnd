-- Inserir novos usuários
INSERT INTO users (email, role, operative) VALUES
('maria.ferreira121@senaisp.edu.br', 'ADMIN', 1),
('ana.sutero@br.bosch.com', 'ADMIN', 1),
('thayna.quinteiro@example.com', 'ADMIN', 1),
('sarah.santos@br.bosch.com', 'ADMIN', 1),
('bruna.cruz8@senaisp.edu.br', 'ADMIN', 1),
('sarah.cruz@br.bosch.com', 'ADMIN', 1),
('joao.carvalho89@senaisp.edu.br', 'ADMIN', 1);

-- Inserir novas classes
INSERT INTO classes (course_name, classes, id_user, creation_date, operative) VALUES
('DIGITAL_SOLUTIONS', '8', 1, '2023-07-16', 1),
('DIGITAL_SOLUTIONS', '9', 2, '2023-07-16', 1),
('DIGITAL_SOLUTIONS', '10', 3, '2023-07-16', 1);

-- Inserir novos prédios
INSERT INTO building (building_code, operative) VALUES
('Ca100', 1),
('Ca106', 1),
('Ca126', 1),
('Ca129', 1),
('Ca140', 1),
('Ca147', 1),
('Ca148', 1),
('Ca149', 1),
('Ca150', 1),
('Ca151', 1),
('Ca160', 1),
('Ca165', 1),
('Ca169', 1),
('Ca170', 1),
('Ca180', 1),
('Ca183', 1),
('Ca200', 1),
('Ca204', 1),
('Ca205', 1),
('Ca220', 1),
('Ca300', 1),
('Ca320', 1),
('Ca325', 1),
('Ca340', 1),
('Ca341', 1),
('Ca350', 1),
('Ca360', 1),
('Ca370', 1),
('Ca390', 1),
('Ca400', 1),
('Ca401', 1),
('Ca530', 1),
('Ca536', 1),
('Ca540', 1),
('Ca550', 1),
('Ca551', 1),
('Ca560', 1),
('Ca590', 1),
('Ca600', 1);

-- Inserir novas áreas
INSERT INTO area (area_code, area_name, operative) VALUES
('ETS', 'Engineering Technical School', 1),
('BTC', 'Bosch Training Center', 1),
('BD', 'Bosch Digital', 1),
('PT', 'Power Tools', 1),
('BISB', 'Bosch Integrated Solutions Brazil', 1);

-- Inserir novos Raspberry Pi
INSERT INTO raspberry (id_raspberry, id_building, id_area, operative) VALUES
('Rasp. Teste', 11, 2, 1),
('rasp1', 1, 1, 1),
('rasp2', 1, 1, 1),
('rasp3', 1, 1, 1);

-- Inserir novos ambientes
INSERT INTO environment (environment_name, id_raspberry, operative) VALUES
('Lab. Soluções Digitais 04 e 5', 'Rasp. Teste', 1),  -- Fixed space
('Lab. Soluções Digitais 03', 'rasp1', 1),
('Lab. Soluções Digitais 02', 'rasp2', 1),
('Lab. Soluções Digitais 01', 'rasp3', 1);

-- Inserir novos postos
INSERT INTO post (post, operative) VALUES
('Mesa 25', 1),
('Mesa 26', 1),
('Mesa 27', 1),
('Mesa 28', 1),
('Mesa 29', 1),
('Mesa 30', 1),
('Mesa 31', 1);

-- Inserir novas localizações
INSERT INTO location (id_post, id_environment) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(6, 1),
(7, 1);

-- Inserir novos centros de custo
INSERT INTO cost_center (cost_center_name, operative) VALUES
('Cost Center 1', 1);

-- Inserir novos donos principais
INSERT INTO main_owner (cod_owner, id_cost_center, operative) VALUES
('FMA6CA', 1, 1);

-- Inserir novos equipamentos
INSERT INTO equipment (cod_equipment, name_equipment, rfid, type, model, validity, admin_rights, observation, id_location, id_owner, operative) VALUES
('JV-L C00A0', 'Notebook 25', 202105091004720, 'Notebook', 'NOTEBOOK_STANDARD', '2025-01-01', 'RITM003011184', 'Boa condição', 1, 1,  1),
('CA-C 005A0', 'Notebook 26', 202105091004731, 'Notebook', 'NOTEBOOK_STANDARD', '2025-01-01', 'RITM003011185', 'Boa condição', 2, 1,  1),
('CA-C 0057K', 'Notebook 27', 202105091004732, 'Notebook', 'NOTEBOOK_STANDARD', '2025-01-01', 'RITM003011186', 'Boa condição', 3, 1,  1),
('JVL-C 0008M', 'Notebook 28', 200000000020249, 'Notebook', 'NOTEBOOK_STANDARD', '2025-01-01', 'RITM003011187', 'Boa condição', 4, 1,  1),
('JVL-C 0009X', 'Notebook 29', 200000000020248, 'Notebook', 'NOTEBOOK_STANDARD', '2025-01-01', 'RITM003011188', 'Boa condição', 5, 1,  1),
('CA-C 005HG', 'Notebook 30', 202105091004735, 'Notebook', 'NOTEBOOK_STANDARD', '2025-01-01', 'RITM003011189', 'Boa condição', 6, 1,  1),
('CA-C 006E1', 'Notebook 31', 200000000020247, 'Notebook', 'NOTEBOOK_STANDARD', '2025-01-01', 'RITM003011190', 'Boa condição', 7, 1,  1);

-- Inserir novos tracking history
INSERT INTO tracking_history (datetime, id_equipment, id_environment, action, warning) VALUES
(CURRENT_TIMESTAMP, 1, 1, 'ENTER', 'GREEN'),
(CURRENT_TIMESTAMP, 2, 1, 'ENTER', 'GREEN'),
(CURRENT_TIMESTAMP, 3, 1, 'ENTER', 'GREEN'),
(CURRENT_TIMESTAMP, 4, 1, 'ENTER', 'GREEN'),
(CURRENT_TIMESTAMP, 5, 1, 'ENTER', 'GREEN'),
(CURRENT_TIMESTAMP, 6, 1, 'ENTER', 'GREEN'),
(CURRENT_TIMESTAMP, 7, 1, 'ENTER', 'GREEN');

-- Inserir novos responsáveis
INSERT INTO responsible (responsible_name, edv, id_classes, id_user, operative) VALUES
('Maria Eduarda de Souza Ferreira', '92903533', 1, 1, 1),
('Ana Mária Sutero', '92903532', 1, 2, 1),
('Thayná Alves Quinteiro', '92903470', 1, 3, 1),
('Sarah da Silva dos Santos', '92903515', 1, 4, 1),
('Bruna Nunes Lima da Cruz', '92903571', 1, 5, 1),
('Sarah Rodrigues da Cruz', '92903525', 1, 6, 1),
('João Victor Borges de Carvalho', '92903549', 1, 7, 1);

-- Inserir novas associações entre equipamentos e responsáveis
INSERT INTO equipment_responsible (id_equipment, id_responsible, start_usage, end_usage, operative) VALUES
(1, 7, '2024-06-01', NULL, 1),
(2, 1, '2024-09-02', NULL, 1),
(3, 5, '2024-09-03', NULL, 1),
(4, 6, '2024-09-04', NULL, 1),
(5, 4, '2024-09-05', NULL, 1),
(6, 3, '2024-09-05', NULL, 1),
(7, 2, '2024-09-05', NULL, 1);
