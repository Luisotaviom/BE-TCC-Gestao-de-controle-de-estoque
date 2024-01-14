-- liquibase formatted sql
-- changeset luis:criar-tabela-fornecedor
create table if not exists fornecedores
(
    id serial primary key,
    nome varchar not null
);

-- changeset luis:criar-tabela-produto
create table if not exists produtos
(
    id serial primary key,
    nome varchar not null,
    fornecedor_id int references fornecedores(id)
);

-- changeset luis:criar-tabela-movimentacao
create table if not exists movimentacoes
(
    id serial primary key,
    data_registro timestamp(0) not null,
    preco numeric(9,2) not null,
    tipo varchar(1),
    produto_id int references produtos(id)
)

-- changeset luis:alterar-tabela-fornecedores
ALTER TABLE fornecedores
    ADD cidade VARCHAR(255),
    ADD celular VARCHAR(20),
    ADD email VARCHAR(255);

-- changeset luis:alterar-tabela-produtos
ALTER TABLE produtos
    ADD categoria VARCHAR(255);

-- changeset luis:alterar-tabela-fornecedores2
ALTER TABLE fornecedores
    ALTER COLUMN cidade DROP NOT NULL,
    ALTER COLUMN celular DROP NOT NULL,
    ALTER COLUMN email DROP NOT NULL;

-- changeset luis:alterar-tabela-fornecedores3
ALTER TABLE fornecedores
    ALTER COLUMN celular DROP NOT NULL;

-- changeset luis:alterar-tabela-fornecedores4
ALTER TABLE fornecedores
    ALTER COLUMN cidade DROP NOT NULL;