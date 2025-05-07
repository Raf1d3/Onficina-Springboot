/*
CREATE TABLE public.vacina
(
    codigo bigserial NOT NULL,
    nome text,
    descricao text,
    PRIMARY KEY (codigo)
);
*/

CREATE TABLE public.usuario (
    id BIGSERIAL PRIMARY KEY,
    nome TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    senha TEXT NOT NULL,
    tipo TEXT NOT NULL
);
