CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE SCHEMA IF NOT EXISTS settlement;

-- Tabela de transações
CREATE TABLE settlement.transacoes (
    id            UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_externo    VARCHAR(255) NOT NULL UNIQUE,
    valor         NUMERIC(19, 4) NOT NULL,
    moeda         VARCHAR(3) NOT NULL DEFAULT 'BRL',
    status        VARCHAR(50) NOT NULL DEFAULT 'PENDENTE',
    criado_em     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    atualizado_em TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- Tabela de liquidações
CREATE TABLE settlement.liquidacoes (
    id            UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    transacao_id  UUID NOT NULL REFERENCES settlement.transacoes(id),
    liquidado_em  TIMESTAMP WITH TIME ZONE,
    status        VARCHAR(50) NOT NULL DEFAULT 'PENDENTE',
    criado_em     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- Índices
CREATE INDEX idx_transacoes_id_externo ON settlement.transacoes(id_externo);
CREATE INDEX idx_transacoes_status ON settlement.transacoes(status);
CREATE INDEX idx_liquidacoes_transacao_id ON settlement.liquidacoes(transacao_id);
