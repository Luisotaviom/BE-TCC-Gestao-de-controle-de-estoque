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
