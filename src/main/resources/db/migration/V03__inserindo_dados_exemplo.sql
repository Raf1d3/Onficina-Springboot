-- =======================================================================
-- SCRIPT DE INSERÇÃO DE DADOS FICTÍCIOS PARA O PROJETO ONFICINA
-- Ordem de execução: usuario -> oficina -> veiculo -> manutencao -> avaliacao
-- =======================================================================


-- -----------------------------------------------------
-- Tabela: usuario
-- Criando 2 administradores e 8 clientes.

-- -----------------------------------------------------
INSERT INTO public.usuario (id, nome, email, senha, ativo, id_papel) VALUES
(1, 'Admin Geral', 'admin@onficina.com', '{argon2}$argon2id$v=19$m=16384,t=2,p=1$sS3xuRLJhn+B9DfIJWOxUA$N3NqcyICkpk/EMLuMl5JhFHxPKcuVOOWLTWndbNuA/w', true, 2),
(2, 'Rafael Admin', 'rafael.admin@onficina.com', '{argon2}$argon2id$v=19$m=16384,t=2,p=1$sS3xuRLJhn+B9DfIJWOxUA$N3NqcyICkpk/EMLuMl5JhFHxPKcuVOOWLTWndbNuA/w', true, 2),
(3, 'Ana Clara Souza', 'ana.souza@example.com', '{argon2}$argon2id$v=19$m=16384,t=2,p=1$sS3xuRLJhn+B9DfIJWOxUA$N3NqcyICkpk/EMLuMl5JhFHxPKcuVOOWLTWndbNuA/w', true, 1),
(4, 'Bruno Costa', 'bruno.costa@example.com', '{argon2}$argon2id$v=19$m=16384,t=2,p=1$sS3xuRLJhn+B9DfIJWOxUA$N3NqcyICkpk/EMLuMl5JhFHxPKcuVOOWLTWndbNuA/w', true, 1),
(5, 'Carla Dias', 'carla.dias@example.com', '{argon2}$argon2id$v=19$m=16384,t=2,p=1$sS3xuRLJhn+B9DfIJWOxUA$N3NqcyICkpk/EMLuMl5JhFHxPKcuVOOWLTWndbNuA/w', true, 1),
(6, 'Daniel Martins', 'daniel.martins@example.com', '{argon2}$argon2id$v=19$m=16384,t=2,p=1$sS3xuRLJhn+B9DfIJWOxUA$N3NqcyICkpk/EMLuMl5JhFHxPKcuVOOWLTWndbNuA/w', false, 1), -- Usuário inativo
(7, 'Eduarda Lima', 'eduarda.lima@example.com', '{argon2}$argon2id$v=19$m=16384,t=2,p=1$sS3xuRLJhn+B9DfIJWOxUA$N3NqcyICkpk/EMLuMl5JhFHxPKcuVOOWLTWndbNuA/w', true, 1),
(8, 'Fábio Pereira', 'fabio.pereira@example.com', '{argon2}$argon2id$v=19$m=16384,t=2,p=1$sS3xuRLJhn+B9DfIJWOxUA$N3NqcyICkpk/EMLuMl5JhFHxPKcuVOOWLTWndbNuA/w', true, 1),
(9, 'Gabriela Alves', 'gabriela.alves@example.com', '{argon2}$argon2id$v=19$m=16384,t=2,p=1$sS3xuRLJhn+B9DfIJWOxUA$N3NqcyICkpk/EMLuMl5JhFHxPKcuVOOWLTWndbNuA/w', true, 1),
(10, 'Heitor Gomes', 'heitor.gomes@example.com', '{argon2}$argon2id$v=19$m=16384,t=2,p=1$sS3xuRLJhn+B9DfIJWOxUA$N3NqcyICkpk/EMLuMl5JhFHxPKcuVOOWLTWndbNuA/w', true, 1);


-- -----------------------------------------------------
-- Tabela: oficina
-- Criando 10 oficinas fictícias.
-- -----------------------------------------------------
INSERT INTO public.oficina (id, nome, cnpj, endereco, telefone, nota_media, status) VALUES
(1, 'Auto Center Veloz', '24.540.135/0001-08', 'Rua das Palmeiras, 123, São Paulo, SP', '(11) 98765-4321', 4.8, 'ATIVO'),
(2, 'Oficina do seu Zé', '99.124.123/0001-00', 'Avenida Brasil, 456, Rio de Janeiro, RJ', '(21) 91234-5678', 4.5, 'ATIVO'),
(3, 'Reparos Rápidos Ltda.', '50.828.936/0001-83', 'Rua da Consolação, 789, Belo Horizonte, MG', '(31) 95678-1234', 4.7, 'ATIVO'),
(4, 'Motor Forte Mecânica', '35.043.395/0001-94', 'Avenida das Américas, 101, Curitiba, PR', '(41) 98765-1234', 4.9, 'ATIVO'),
(5, 'Freios & Cia', '30.024.692/0001-79', 'Rua dos Andradas, 212, Porto Alegre, RS', '(51) 94321-8765', 4.6, 'ATIVO'),
(6, 'Elétrica Automotiva Silva', '03.011.861/0001-63', 'Avenida Afonso Pena, 313, Salvador, BA', '(71) 91234-8765', 4.4, 'ATIVO'),
(7, 'Pneu Novo Garagem', '99.547.811/0001-74', 'Rua Augusta, 414, Fortaleza, CE', '(85) 98888-9999', 4.7, 'ATIVO'),
(8, 'Speed Car Service', '63.210.827/0001-40', 'Avenida Boa Viagem, 515, Recife, PE', '(81) 97777-6666', 4.8, 'ATIVO'),
(9, 'Mecânica Confiança', '59.964.200/0001-07', 'Rua 24 de Outubro, 616, Goiânia, GO', '(62) 96666-5555', 4.9, 'INATIVO'), -- Oficina inativa
(10, 'Garagem Central', '78.220.617/0001-00', 'Setor Hoteleiro Sul, 717, Brasília, DF', '(61) 95555-4444', 4.5, 'ATIVO');


-- -----------------------------------------------------
-- Tabela: veiculo
-- Criando 10 veículos e associando aos usuários clientes (IDs 3 a 10).
-- -----------------------------------------------------
INSERT INTO public.veiculo (id, placa, modelo, marca, cor, ano, usuario_id, status) VALUES
(1, 'ABC-1234', 'Corolla', 'Toyota', 'Prata', 2022, 3, 'ATIVO'),
(2, 'DEF-5678', 'Onix', 'Chevrolet', 'Branco', 2023, 4, 'ATIVO'),
(3, 'GHI-9012', 'HB20', 'Hyundai', 'Preto', 2021, 5, 'ATIVO'),
(4, 'JKL-3456', 'Mobi', 'Fiat', 'Vermelho', 2023, 3, 'ATIVO'), -- Ana tem um segundo carro
(5, 'MNO-7890', 'Kwid', 'Renault', 'Azul', 2020, 7, 'ATIVO'),
(6, 'PQR-1234', 'Compass', 'Jeep', 'Cinza', 2024, 8, 'ATIVO'),
(7, 'STU-5678', 'Civic', 'Honda', 'Branco', 2022, 9, 'ATIVO'),
(8, 'VWX-9012', 'Polo', 'Volkswagen', 'Preto', 2023, 10, 'ATIVO'),
(9, 'YZA-3456', 'Creta', 'Hyundai', 'Prata', 2024, 5, 'INATIVO'), -- Veículo inativo
(10, 'BCD-7890', 'Argo', 'Fiat', 'Vermelho', 2021, 4, 'ATIVO');


-- -----------------------------------------------------
-- Tabela: manutencao
-- Criando 10 manutenções, associando a veículos e oficinas.
-- -----------------------------------------------------
INSERT INTO public.manutencao (id, data_inicio_manutencao, descricao, observacao, tipo_manutencao, status_manutencao, tipo_servico, valor_servico, data_proxima_manutencao, veiculo_id, oficina_id, status) VALUES
(1, '2025-06-15', 'Troca de óleo e filtro', 'Utilizado óleo 5W30 sintético.', 'PREVENTIVA', 'FINALIZADA', 'TROCA_OLEO', 250, '2026-06-15', 1, 1, 'ATIVO'),
(2, '2025-07-01', 'Troca dos 4 pneus', 'Pneus novos da marca Pirelli.', 'CORRETIVA', 'FINALIZADA', 'TROCA_PNEUS', 1800, NULL, 2, 7, 'ATIVO'),
(3, '2025-07-10', 'Alinhamento e Balanceamento', 'Feito após a troca de pneus.', 'PREVENTIVA', 'AGENDADA', 'ALINHAMENTO', 150, NULL, 2, 7, 'ATIVO'),
(4, '2025-05-20', 'Reparo no sistema de freios', 'Troca de pastilhas dianteiras.', 'CORRETIVA', 'FINALIZADA', 'REPARO_FREIOS', 450, NULL, 3, 5, 'ATIVO'),
(5, '2025-06-30', 'Troca de bateria', 'Bateria Moura instalada.', 'CORRETIVA', 'FINALIZADA', 'TROCA_BATERIA', 400, NULL, 4, 6, 'ATIVO'),
(6, '2025-07-20', 'Revisão completa de 40.000km', 'Todos os itens verificados conforme manual.', 'PREVENTIVA', 'EM_ANDAMENTO', 'OUTRO', 800, '2026-07-20', 5, 2, 'ATIVO'),
(7, '2025-04-10', 'Verificação do ar condicionado', 'Recarga de gás e limpeza do sistema.', 'CORRETIVA', 'FINALIZADA', 'OUTRO', 300, NULL, 6, 4, 'ATIVO'),
(8, '2025-07-05', 'Troca de óleo do câmbio automático', 'Serviço preventivo.', 'PREVENTIVA', 'FINALIZADA', 'TROCA_OLEO', 600, NULL, 7, 1, 'ATIVO'),
(9, '2025-07-15', 'Balanceamento de rodas', 'Vibração no volante acima de 80km/h.', 'CORRETIVA', 'AGENDADA', 'BALANCEAMENTO', 80, NULL, 8, 8, 'ATIVO'),
(10, '2025-03-01', 'Troca de velas e cabos', 'Manutenção preventiva dos 60.000km.', 'PREVENTIVA', 'FINALIZADA', 'OUTRO', 350, NULL, 10, 2, 'INATIVO'); -- Manutenção inativa


-- -----------------------------------------------------
-- Tabela: avaliacao
-- Criando 10 avaliações para as manutenções finalizadas.
-- -----------------------------------------------------
INSERT INTO public.avaliacao (id, nota, comentario, data_avaliacao, oficina_id, usuario_id, manutencao_id, status) VALUES
(1, 5.0, 'Serviço excelente e rápido! O pessoal da Auto Center Veloz é muito profissional.', '2025-06-18', 1, 3, 1, 'ATIVO'),
(2, 4.5, 'Ficou ótimo, mas o preço foi um pouco acima do esperado. Recomendo a Pneu Novo Garagem.', '2025-07-02', 7, 4, 2, 'ATIVO'),
(3, 4.0, 'Bom serviço, mas a oficina estava um pouco desorganizada. O trabalho nos freios ficou perfeito.', '2025-05-22', 5, 5, 4, 'ATIVO'),
(4, 5.0, 'Atendimento nota 10 na Elétrica Automotiva Silva. Resolveram meu problema na hora.', '2025-07-01', 6, 3, 5, 'ATIVO'),
(5, 4.8, 'Ótimo atendimento e preço justo na Motor Forte. O carro saiu novo!', '2025-04-12', 4, 8, 7, 'ATIVO'),
(6, 5.0, 'Serviço impecável na troca de óleo. A Auto Center Veloz é minha oficina de confiança.', '2025-07-06', 1, 9, 8, 'ATIVO'),
(7, 4.2, 'O serviço foi bom, mas demorou mais que o prometido.', '2025-03-02', 2, 4, 10, 'ATIVO'),
(8, 4.9, 'Equipe muito atenciosa e competente. Recomendo!', '2025-06-20', 1, 3, 1, 'ATIVO'), -- Outra avaliação para a mesma manutenção
(9, 3.5, 'Resolveram o problema, mas o atendimento poderia ser mais cordial.', '2025-05-25', 5, 5, 4, 'INATIVO'), -- Avaliação inativa
(10, 5.0, 'Sempre levo meu carro lá. Serviço de primeira!', '2025-07-03', 7, 4, 2, 'ATIVO');




-- Sincroniza a sequência da tabela 'usuario'
SELECT setval('usuario_id_seq', (SELECT MAX(id) FROM public.usuario));

-- Sincroniza a sequência da tabela 'veiculo'
SELECT setval('veiculo_id_seq', (SELECT MAX(id) FROM public.veiculo));

-- Sincroniza a sequência da tabela 'oficina'
SELECT setval('oficina_id_seq', (SELECT MAX(id) FROM public.oficina));

-- Sincroniza a sequência da tabela 'manutencao'
SELECT setval('manutencao_id_seq', (SELECT MAX(id) FROM public.manutencao));

-- Sincroniza a sequência da tabela 'avaliacao'
SELECT setval('avaliacao_id_seq', (SELECT MAX(id) FROM public.avaliacao));