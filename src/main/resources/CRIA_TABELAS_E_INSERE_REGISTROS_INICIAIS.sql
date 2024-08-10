-- CREATE TABLE exame (rowid bigint auto_increment, nm_exame VARCHAR(255));
-- INSERT INTO exame (nm_exame) VALUES ('Acuidade Visual'), ('Urina'), ('Clinico'), ('Sangue');


-- MEUS SCRIPTS

-- CREATE TABLE exame (
--rowid BIGINT AUTO_INCREMENT, 
--nm_exame VARCHAR(255),
--PRIMARY KEY(rowid)
--);

--CREATE TABLE funcionario (
--rowid BIGINT AUTO_INCREMENT, 
--nm_funcionario VARCHAR(255),
--PRIMARY KEY(rowid)
--);

--CREATE TABLE exame_funcionarios(
--	rowid INT AUTO_INCREMENT PRIMARY KEY,
--    dataExame DATE NOT NULL,
--    cd_funcionario BIGINT NOT NULL,
--    cd_exame BIGINT NOT NULL,
--    FOREIGN KEY(cd_funcionario) REFERENCES funcionario(rowid),
--    FOREIGN KEY(cd_exame) REFERENCES exame(rowid)
--);