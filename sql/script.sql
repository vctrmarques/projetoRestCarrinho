CREATE DATABASE db_carrinho;

CREATE TABLE public.produto (
  id bigint NOT NULL,
  nome character varying(255) NOT NULL,
  valor numeric(13,2) NOT NULL,
  data_exclusao date DEFAULT NULL,
  data_criacao date NOT NULL,
  CONSTRAINT produto_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

