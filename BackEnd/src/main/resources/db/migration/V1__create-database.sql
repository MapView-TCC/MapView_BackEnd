-- Creating the User table
CREATE TABLE IF NOT EXISTS area (
    id_area INT AUTO_INCREMENT PRIMARY KEY,
    area_code VARCHAR(255) UNIQUE NOT NULL,
    area_name VARCHAR(255) NOT NULL,
    operative TINYINT NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    id_area int,
    FOREIGN KEY (id_area) REFERENCES area(id_area),
    operative TINYINT NOT NULL
);

CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE user_roles (
    user_roles_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    role_id INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);


-- Creating the Access History table
CREATE TABLE IF NOT EXISTS access_history (
    id_history INT PRIMARY KEY AUTO_INCREMENT,
    id_user INT NOT NULL,
    login_datetime TIMESTAMP,
    logout_datetime DATETIME,
    FOREIGN KEY(id_user) REFERENCES users(user_id)
);

-- Creating the Classes table
CREATE TABLE IF NOT EXISTS classes (
    id_classes INT AUTO_INCREMENT PRIMARY KEY,
    course_name ENUM('ADMINISTRACAO', 'DIGITAL_SOLUTIONS', 'MANUFATURA_DIGITAL', 'MECATRONICA') NOT NULL,
    classes VARCHAR(255) unique NOT NULL,
    id_user INT NOT NULL,
    creation_date DATE NOT NULL,
    operative TINYINT NOT NULL,
    FOREIGN KEY(id_user) REFERENCES users(user_id)
);

-- Creating the Building table (é a tabela Predio)
CREATE TABLE IF NOT EXISTS building (
    id_building INT AUTO_INCREMENT PRIMARY KEY,
    building_code VARCHAR(255) UNIQUE NOT NULL,
    operative TINYINT NOT NULL
);

-- Creating the Area table


-- Creating the Raspberry table
CREATE TABLE IF NOT EXISTS raspberry (
    id_raspberry VARCHAR(255) PRIMARY KEY UNIQUE NOT NULL,
    id_building INT NOT NULL,
    id_area INT NOT NULL,
    operative TINYINT NOT NULL,
    FOREIGN KEY(id_building) REFERENCES building(id_building),
    FOREIGN KEY(id_area) REFERENCES area(id_area)
);

-- Creating the Environment table (Tabela de ambiente)
CREATE TABLE IF NOT EXISTS environment (
    id_environment INT AUTO_INCREMENT PRIMARY KEY,
    environment_name VARCHAR(255) unique NOT NULL,
    id_raspberry VARCHAR(255) NOT NULL,
    operative TINYINT NOT NULL,
    FOREIGN KEY(id_raspberry) REFERENCES raspberry(id_raspberry)
);

-- Creating the Post table (Tabela de posto)
CREATE TABLE IF NOT EXISTS post (
    id_post INT AUTO_INCREMENT PRIMARY KEY,
    post VARCHAR(255) UNIQUE NOT NULL,
    operative TINYINT NOT NULL
);

-- Creating the Position auxiliary table (posição)
CREATE TABLE IF NOT EXISTS location (
    id_location INT AUTO_INCREMENT PRIMARY KEY,
    id_post INT NOT NULL,
    id_environment INT NOT NULL,
    FOREIGN KEY(id_post) REFERENCES post(id_post),
    FOREIGN KEY(id_environment) REFERENCES environment(id_environment),
    UNIQUE (id_post, id_environment)
);

-- Creating the Cost Center table (centro de custo)
CREATE TABLE IF NOT EXISTS cost_center (
    id_cost_center INT AUTO_INCREMENT PRIMARY KEY,
    cost_center_name VARCHAR(255) unique NOT NULL,
    operative TINYINT NOT NULL
);

-- Creating the Main Owner table (dono principal)
CREATE TABLE IF NOT EXISTS main_owner (
    id_owner INT AUTO_INCREMENT PRIMARY KEY,
    cod_owner VARCHAR(255) UNIQUE,
    id_cost_center INT NOT NULL,
    operative TINYINT NOT NULL,
    FOREIGN KEY(id_cost_center) REFERENCES cost_center(id_cost_center)
);

CREATE TABLE IF NOT EXISTS image (
    id_image INT AUTO_INCREMENT PRIMARY KEY,
    image VARCHAR(255),
    model ENUM('DESKTOP_TINK','NOTEBOOK_STANDARD','DESKTOP_EXTERNO', 'NOTEBOOK_ENHANCED')
);

-- Creating the Equipment table, para teste eu tirei o unique do rfid
CREATE TABLE IF NOT EXISTS equipment (
    id_equipment INT AUTO_INCREMENT PRIMARY KEY,
    cod_equipment VARCHAR(255) unique,
    name_equipment VARCHAR(255),
    rfid BIGINT unique,
    type VARCHAR(255),
    model ENUM('DESKTOP_TINK','NOTEBOOK_STANDARD','DESKTOP_EXTERNO', 'NOTEBOOK_ENHANCED'),
    validity DATE,
    admin_rights VARCHAR(255),
    observation TEXT,
    id_location INT,
    id_owner INT,
    id_image int,
    operative TINYINT,
    FOREIGN KEY(id_location) REFERENCES location(id_location),
    FOREIGN KEY(id_image) REFERENCES image(id_image),
    FOREIGN KEY(id_owner) REFERENCES main_owner(id_owner)
);

-- Creating the Notification table
CREATE TABLE IF NOT EXISTS notification (
    id_notification INT AUTO_INCREMENT PRIMARY KEY,
    id_equipment INT,
    date_notification DATE,
    FOREIGN KEY(id_equipment) REFERENCES equipment(id_equipment)
);

-- Creating the Tracking History table (historico de rastreio)
CREATE TABLE IF NOT EXISTS tracking_history (
    id_tracking INT AUTO_INCREMENT PRIMARY KEY,
    id_equipment INT,
    id_environment INT,
    action ENUM ('ENTER','OUT'),
    warning ENUM ('RED', 'YELLOW', 'GREEN'),
    dateTime TIMESTAMP,
    FOREIGN KEY(id_equipment) REFERENCES equipment(id_equipment),
    FOREIGN KEY(id_environment) REFERENCES environment(id_environment)
);

-- Creating the Responsible table
CREATE TABLE IF NOT EXISTS responsible (
    id_responsible INT AUTO_INCREMENT PRIMARY KEY,
    responsible_name VARCHAR(255) NOT NULL,
    edv VARCHAR(255) UNIQUE NOT NULL,
    id_classes INT,
    id_user INT NOT NULL,
    operative TINYINT NOT NULL,
    FOREIGN KEY(id_classes) REFERENCES classes(id_classes),
    FOREIGN KEY(id_user) REFERENCES users(user_id)
);

-- Creating the auxiliary Equipment_Responsible table
CREATE TABLE IF NOT EXISTS equipment_responsible (
    id_equip_resp INT AUTO_INCREMENT PRIMARY KEY,
    id_equipment INT NOT NULL,
    id_responsible INT NOT NULL,
    start_usage DATE NOT NULL,
    end_usage DATE,
    operative TINYINT NOT NULL,
    FOREIGN KEY(id_equipment) REFERENCES equipment(id_equipment),
    FOREIGN KEY(id_responsible) REFERENCES responsible(id_responsible),
    UNIQUE (id_equipment,id_responsible)
);

-- Creating the User Log table (related to CRUD operations)
CREATE TABLE IF NOT EXISTS user_log (
    id_log INT AUTO_INCREMENT PRIMARY KEY,
    id_user INT NOT NULL,
    altered_table VARCHAR(255),
    id_altered VARCHAR(255),
    field TEXT,
    description TEXT,
    datetime TIMESTAMP,
    action ENUM('CREATE', 'UPDATE', 'READ', 'DELETE'),
    FOREIGN KEY(id_user) REFERENCES users(user_id)
);

