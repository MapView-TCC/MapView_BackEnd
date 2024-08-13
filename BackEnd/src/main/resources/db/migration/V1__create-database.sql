
-- Criando a tabela Usuário
CREATE TABLE IF NOT EXISTS usuario(
	id_user INT AUTO_INCREMENT PRIMARY KEY,
    role ENUM('admin', 'suporte', 'usuario') NOT NULL,
    ativo TINYINT NOT NULL
);

-- Criando a tabela Histórico de Acessos
CREATE TABLE IF NOT EXISTS historico_acesso(
	id_historico INT PRIMARY KEY AUTO_INCREMENT,
    id_user INT NOT NULL,
    dataHora_login DATETIME,
    dataHora_logout DATETIME,
    FOREIGN KEY(id_user) REFERENCES usuario(id_user)
);

-- Criando a tabela Turmas
CREATE TABLE IF NOT EXISTS turma(
	id_turma INT AUTO_INCREMENT PRIMARY KEY,
    nome_curso ENUM('Administração', 'Digital Solutions', 'Manufatura Digital', 'Mecatrônica') NOT NULL,
    turma VARCHAR(255) NOT NULL,
    id_user INT NOT NULL,
    data_criacao DATE NOT NULL,
    ativo TINYINT NOT NULL,
    FOREIGN KEY(id_user) REFERENCES usuario(id_user)
);

-- Criando tabela predio
CREATE TABLE IF NOT EXISTS predio(
	id_predio INT AUTO_INCREMENT PRIMARY KEY,
    cod_predio VARCHAR(255) UNIQUE NOT NULL,
    ativo TINYINT NOT NULL
);

-- Criando a tabela área
CREATE TABLE IF NOT EXISTS area(
	id_area INT AUTO_INCREMENT PRIMARY KEY,
    cod_area VARCHAR(255) UNIQUE NOT NULL,
    nome_area VARCHAR(255) NOT NULL,
    ativo TINYINT NOT NULL
);

-- Criando a tabela Raspberry
CREATE TABLE IF NOT EXISTS raspberry(
	id_rasp INT AUTO_INCREMENT PRIMARY KEY,
    nome_rasp VARCHAR(255) UNIQUE NOT NULL,
    id_predio INT NOT NULL,
    id_area INT NOT NULL,
    ativo TINYINT NOT NULL,
    FOREIGN KEY  (id_predio) REFERENCES predio (id_predio),
    FOREIGN KEY (id_area) REFERENCES area(id_area)
);

-- Criando tabela Ambiente
CREATE TABLE IF NOT EXISTS ambiente(
	id_ambiente INT AUTO_INCREMENT PRIMARY KEY,
    nome_ambiente VARCHAR(255) NOT NULL,
    id_rasp INT NOT NULL,
    ativo TINYINT NOT NULL,
    FOREIGN KEY (id_rasp) REFERENCES raspberry(id_rasp)
);

-- Criando tabela posto
CREATE TABLE IF NOT EXISTS posto(
	id_posto INT AUTO_INCREMENT PRIMARY KEY,
    posto VARCHAR(255) NOT NULL,
    ativo TINYINT NOT NULL
);

-- Criando tabela auxiliar posição
CREATE TABLE IF NOT EXISTS posicao(
	id_posicao INT AUTO_INCREMENT PRIMARY KEY,
    id_posto INT NOT NULL,
    id_ambiente INT NOT NULL,
    FOREIGN KEY (id_posto) REFERENCES posto(id_posto),
    FOREIGN KEY (id_ambiente) REFERENCES ambiente(id_ambiente)
);

-- Criando a tabela Centro de Custos
CREATE TABLE IF NOT EXISTS centro_de_custos(
	id_centroCustos INT AUTO_INCREMENT PRIMARY KEY,
    centro_custos VARCHAR(255) UNIQUE NOT NULL,
    ativo TINYINT NOT NULL
);

-- Criando a tabela Dono principal
CREATE TABLE IF NOT EXISTS dono_principal(
	id_dono VARCHAR(255) PRIMARY KEY,
    nome_dono VARCHAR(255) NOT NULL,
    id_centroCustos INT NOT NULL,
    ativo TINYINT NOT NULL,
    FOREIGN KEY (id_centroCustos) REFERENCES centro_de_custos(id_centroCustos)
);

-- Criando a tabela Equipamento
CREATE TABLE IF NOT EXISTS equipamento(
	id_equipamento VARCHAR(255) PRIMARY KEY,
    rfid VARCHAR(255) UNIQUE,
    tipo VARCHAR(255) NOT NULL,
    modelo VARCHAR(255) NOT NULL,
    validade VARCHAR(255) NOT NULL,
    admin_rigths VARCHAR(255) NOT NULL,
    observacao TEXT,
    id_posicao INT NOT NULL,
    id_dono VARCHAR(255) NOT NULL,
    ativo TINYINT NOT NULL,
    FOREIGN KEY (id_posicao) REFERENCES posicao(id_posicao),
    FOREIGN KEY (id_dono) REFERENCES dono_principal(id_dono)
);

-- Criando a tabela Histórico de Rastreio
CREATE TABLE IF NOT EXISTS historico_rastreio(
	id_rastreio INT AUTO_INCREMENT PRIMARY KEY,
    id_equipamento VARCHAR(255) NOT NULL,
    id_ambiente INT NOT NULL,
    ativo TINYINT NOT NULL,
    FOREIGN KEY (id_equipamento) REFERENCES equipamento(id_equipamento),
    FOREIGN KEY (id_ambiente) REFERENCES ambiente(id_ambiente)
);

-- Criando a tabela Responsável
CREATE TABLE IF NOT EXISTS responsavel(
	id_responsavel INT AUTO_INCREMENT PRIMARY KEY,
    nome_responsavel VARCHAR(255) NOT NULL,
    edv VARCHAR(255) UNIQUE NOT NULL,
    id_turma INT,
    id_user INT NOT NULL,
    ativo TINYINT NOT NULL,
    FOREIGN KEY (id_turma) REFERENCES turma (id_turma),
    FOREIGN KEY(id_user) REFERENCES usuario(id_user)
);

-- Criando a tabela auxiliar Equipamento_Responsável
CREATE TABLE IF NOT EXISTS equipamento_responsavel(
	id_equiRspo INT AUTO_INCREMENT PRIMARY KEY,
    id_equipamento VARCHAR(255) NOT NULL,
    id_responsavel INT NOT NULL,
    inicio_utilizacao DATE NOT NULL,
    final_utilizacao DATE,
    ativo TINYINT NOT NULL,
    FOREIGN KEY (id_equipamento) REFERENCES equipamento(id_equipamento),
    FOREIGN KEY (id_responsavel) REFERENCES responsavel(id_responsavel)
);

-- Criando a tabela Log de Usuário (tem relação com CRUD)
CREATE TABLE IF NOT EXISTS log_usuario(
	id_log INT AUTO_INCREMENT PRIMARY KEY,
    id_user INT NOT NULL,
    tabela VARCHAR(255),
    id_alterado VARCHAR(255),
    acao ENUM('create', 'update', 'read', 'delete'),
    FOREIGN KEY (id_user) REFERENCES usuario(id_user)
);