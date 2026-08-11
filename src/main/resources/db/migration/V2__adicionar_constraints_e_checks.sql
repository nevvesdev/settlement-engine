-- Garantir que o valor da transação seja sempre positivo
ALTER TABLE settlement.transacoes
    ADD CONSTRAINT chk_transacoes_valor_positivo CHECK (valor > 0);

-- Garantir que a moeda tenha exatamente 3 caracteres
ALTER TABLE settlement.transacoes
    ADD CONSTRAINT chk_transacoes_moeda_formato CHECK (char_length(moeda) = 3);

-- Garantir que o status da transação seja um valor válido do enum
ALTER TABLE settlement.transacoes
    ADD CONSTRAINT chk_transacoes_status_valido
    CHECK (status IN ('PENDENTE', 'EM_PROCESSAMENTO', 'LIQUIDADA', 'CANCELADA', 'FALHA'));

-- Garantir que o status da liquidação seja um valor válido do enum
ALTER TABLE settlement.liquidacoes
    ADD CONSTRAINT chk_liquidacoes_status_valido
    CHECK (status IN ('PENDENTE', 'EM_PROCESSAMENTO', 'CONCLUIDA', 'FALHA'));

-- Garantir que liquidado_em só seja preenchido quando status for CONCLUIDA
ALTER TABLE settlement.liquidacoes
    ADD CONSTRAINT chk_liquidacoes_data_conclusao
    CHECK (
        (status = 'CONCLUIDA' AND liquidado_em IS NOT NULL) OR
        (status != 'CONCLUIDA' AND liquidado_em IS NULL)
    );
