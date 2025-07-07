CREATE TABLE public.papel
(
    id bigserial PRIMARY KEY,
    nome text NOT NULL UNIQUE
);

CREATE TABLE public.usuario (
    id BIGSERIAL PRIMARY KEY,
    nome TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    senha TEXT NOT NULL,
    ativo boolean,
    id_papel bigint NOT NULL,
    CONSTRAINT fk_papel FOREIGN KEY (id_papel) REFERENCES public.papel (id) ON DELETE CASCADE
);


CREATE TABLE public.veiculo (
    id BIGSERIAL PRIMARY KEY,
    placa TEXT NOT NULL UNIQUE,
    modelo TEXT NOT NULL,
    marca TEXT NOT NULL,
    cor TEXT NOT NULL,
    ano INT NOT NULL,
    usuario_id BIGINT NOT NULL,
    CONSTRAINT fk_usuario FOREIGN KEY(usuario_id) REFERENCES public.usuario(id)
);

CREATE TABLE public.oficina (
    id BIGSERIAL PRIMARY KEY,
    nome TEXT NOT NULL,
    cnpj TEXT NOT NULL,
    endereco TEXT NOT NULL,
    telefone TEXT NOT NULL,
    nota_media DOUBLE PRECISION
);

CREATE TABLE public.manutencao (
    id BIGSERIAL PRIMARY KEY,
    data_inicio_manutencao DATE,
    descricao TEXT,
    observacao TEXT,
    tipo_manutencao TEXT NOT NULL,
    status_manutencao TEXT NOT NULL,
    tipo_servico TEXT NOT NULL,
    descricao_outro_servico TEXT,
    valor_servico BIGINT,
    data_proxima_manutencao DATE,
    veiculo_id BIGINT NOT NULL,
    oficina_id BIGINT,
    CONSTRAINT fk_veiculo FOREIGN KEY(veiculo_id) REFERENCES public.veiculo(id),
    CONSTRAINT fk_oficina FOREIGN KEY(oficina_id) REFERENCES public.oficina(id)
);

CREATE TABLE public.avaliacao (
    id BIGSERIAL PRIMARY KEY,
    nota DOUBLE PRECISION NOT NULL,
    comentario TEXT,
    data_avaliacao DATE,
    oficina_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    manutencao_id BIGINT NOT NULL,
    CONSTRAINT fk_oficina FOREIGN KEY(oficina_id) REFERENCES public.oficina(id),
    CONSTRAINT fk_usuario FOREIGN KEY(usuario_id) REFERENCES public.usuario(id), 
    CONSTRAINT fk_manutencao FOREIGN KEY(manutencao_id) REFERENCES public.manutencao(id)
);

