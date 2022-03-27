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

--Inserindo coluna usuario_id para definir quem é dono dos registros
ALTER TABLE tb_usuario ADD COLUMN usuario_id BIGINT NOT NULL DEFAULT 1;

--Adicionando constraint usuario_id
ALTER TABLE tb_usuario ADD CONSTRAINT usuario_fk FOREIGN KEY (usuario_id) REFERENCES tb_usuario (id);

--Inserindo coluna perfil
ALTER TABLE tb_usuario ADD COLUMN perfil character varying(25) NOT NULL DEFAULT 'AUXILIAR';

--Inserindo coluna perfil
ALTER TABLE tb_usuario ADD COLUMN sexo character varying(20);

--Inserindo coluna foto e extensao_foto
ALTER TABLE tb_usuario ADD COLUMN foto text;
ALTER TABLE tb_usuario ADD COLUMN extensao_foto CHARACTER VARYING(5);

--Criar sequencia tb_endereco
CREATE SEQUENCE tb_endereco_id_seq;

 --Criar tabela endereco
CREATE TABLE tb_endereco(
	id BIGINT NOT NULL DEFAULT nextval('tb_endereco_id_seq'::REGCLASS),
	logradouro VARCHAR(60) NOT NULL,
	bairro VARCHAR(45) NOT NULL,
	cidade VARCHAR(45) NOT NULL,
    uf VARCHAR(2) NOT NULL,
	cep NUMERIC(8) NOT NULL,
	numero NUMERIC(5) NOT NULL,
	complemento VARCHAR(25),
	usuario_id BIGINT NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT usuario_fk
      FOREIGN KEY(usuario_id) 
	  REFERENCES tb_usuario(id)
	  ON DELETE CASCADE
)

--Dá permissão ao usuario na tb_endereco
GRANT ALL ON SEQUENCE public.tb_endereco_id_seq TO robson;

GRANT ALL ON TABLE public.tb_endereco TO robson;

--Criar sequencia telefone
CREATE SEQUENCE tb_telefone_id_seq;

 --Criar tabela telefone
CREATE TABLE tb_telefone(
	id BIGINT NOT NULL DEFAULT nextval('tb_telefone_id_seq'::REGCLASS),
	numero NUMERIC(11, 0) NOT NULL,
	usuario_id BIGINT NOT NULL,
	usuario_cad_id BIGINT NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT usuario_fk
      FOREIGN KEY(usuario_id) 
	  REFERENCES tb_usuario(id)
	  ON DELETE CASCADE,
	CONSTRAINT usuario_cad_fk
      FOREIGN KEY(usuario_cad_id) 
	  REFERENCES tb_usuario(id)
	  ON DELETE SET NULL
)

--Dá permissão ao usuario na tb_telefone
GRANT ALL ON SEQUENCE public.tb_telefone_id_seq TO robson;

GRANT ALL ON TABLE public.tb_telefone TO robson;



