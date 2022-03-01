--Criar banco
CREATE DATABASE "curso-jsp"
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'pt_BR.UTF-8'
    LC_CTYPE = 'pt_BR.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

--Criar sequencia
CREATE SEQUENCE tb_usuario_id_seq;

--Criar tabela
CREATE TABLE tb_usuario
(
  id integer NOT NULL DEFAULT nextval('tb_usuario_id_seq'::regclass),
  nome character varying(60) NOT NULL,
  email character varying(60) NOT NULL,
  login character varying(40) NOT NULL,
  senha character varying(10) NOT NULL,
  CONSTRAINT tb_usuario_pkey PRIMARY KEY (id),
  CONSTRAINT login_unique UNIQUE (login)
)

--Inserir
INSERT INTO tb_usuario(
	nome, email, login, senha)
	VALUES ('Maria', 'maria@gmail.com', 'maria', '123456');

--Selecionar
SELECT * FROM tb_usuario;

--Dá permissão ao usuario
GRANT ALL ON SEQUENCE public.tb_usuario_id_seq TO robson;

GRANT ALL ON TABLE public.tb_usuario TO robson;

--Inserindo coluna user_admin para definir quem é administrador
ALTER TABLE tb_usuario ADD COLUMN user_admin BOOLEAN NOT NULL DEFAULT FALSE;

--Setando o primeiro administrador
UPDATE tb_usuario SET user_admin=TRUE WHERE id = 1;

--Inserindo coluna tb_usuario para definir quem é dono dos registros
ALTER TABLE tb_usuario ADD COLUMN usuario_id BIGINT NOT NULL DEFAULT 1;

--Adicionando constraint usuario_id
ALTER TABLE tb_usuario ADD CONSTRAINT usuario_fk FOREIGN KEY (usuario_id) REFERENCES tb_usuario (id);


