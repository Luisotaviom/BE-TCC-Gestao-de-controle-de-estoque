# Sistema de Gestão de Estoque - Controlex

## Descrição

Este é o backend do Sistema de Gestão de Estoque - Controlex, desenvolvido especificamente para microempresas, com o objetivo de oferecer uma solução integrada para gerenciamento eficiente de estoque. Utilizando **Java versão 17** para a lógica de negócios e **Spring Boot versão 3.1.2** para o framework de aplicação, juntamente com **PostgreSQL** para gestão de dados, este sistema visa facilitar operações de estoque, vendas e análise de dados, promovendo eficiência e decisões estratégicas informadas. **Este projeto foi desenvolvido inteiramente em ambiente Linux (Ubuntu)**, garantindo compatibilidade e otimização para este sistema operacional.

## Pré-requisitos

Antes de iniciar, certifique-se de cumprir os seguintes pré-requisitos:

### Docker

Você precisará do Docker instalado em sua máquina para rodar o ambiente de contêineres. Se não tiver o Docker, visite [Instalação do Docker](https://docs.docker.com/get-docker/) para guias específicos do seu sistema operacional.

### Java 17 com SDKMAN!

Este projeto utiliza Java versão 17. Recomendamos o uso do [SDKMAN](https://sdkman.io/)! para instalar e gerenciar versões do Java, o que facilita a seleção da versão correta para este projeto.


## Instalação e Configuração

### Clonando o projeto

Clone o repositório do projeto, se ainda não o fez:

```bash
git clone https://github.com/Luisotaviom/BE-TCC-Gestao-de-controle-de-estoque.git
```

### Configurando o ambiente com Docker

O projeto utiliza Docker para garantir que o ambiente de desenvolvimento seja fácil de configurar e replicar. Siga os passos abaixo para preparar seu ambiente.

1. **Build do Container**

   Dentro da pasta do projeto, crie uma imagem Docker do seu backend utilizando o seguinte comando:

```bash
docker build -t controlex-backend .
```

Este comando constrói uma imagem Docker baseada no Dockerfile localizado na raiz do projeto.

2. **Iniciando os Containers**

   No terminal da IDE, navegue até a raiz do projeto backend e execute o comando:

```bash
docker-compose up
```

Este comando irá iniciar todos os serviços definidos no arquivo `docker-compose.yml`, incluindo o backend e o banco de dados PostgreSQL. Os logs de cada serviço serão exibidos no terminal, permitindo o acompanhamento em tempo real.

Para interromper a execução dos containers, utilize `CTRL+C` no terminal onde o comando está sendo executado.


### Configurando o Banco de Dados PostgreSQL com Docker

Para subir o PostgreSQL no Docker e integrá-lo ao backend, siga os passos:

1. **Subindo o PostgreSQL**

Execute o seguinte comando para criar e iniciar um container PostgreSQL:

```bash
docker run --name controlador_estoque_user -e POSTGRES_PASSWORD=sua_senha -d -p 5432:5432 postgres
```

Altere `sua_senha` para uma senha segura de sua escolha.

2. **Conexão com o Banco de Dados**

Certifique-se de atualizar o arquivo de configuração `application.properties` do Spring Boot no backend para incluir as credenciais corretas do PostgreSQL:

```bash
spring.datasource.url=jdbc:postgresql://localhost:5433/controlador_estoque_db?escapeSyntaxCallMode=callIfNoReturn
spring.datasource.username=controlador_estoque_user
spring.datasource.password=sua_senha
```

**Nota:** Será necessário criar um banco de dados chamado `controlador_estoque_db` no PostgreSQL. Isso pode ser feito acessando o PostgreSQL através de um cliente de sua escolha e executando `CREATE DATABASE controlador_estoque_db;`.

## Executando a Aplicação

Com os containers devidamente iniciados, você agora precisa subir a aplicação backend. Para isso, utilize a IDE de sua escolha para encontrar e executar o arquivo principal do Spring Boot (normalmente nomeado `Application.java` ou algo similar) como uma aplicação Java. 

Este procedimento inicializa o servidor backend do Spring Boot, tornando a aplicação acessível.

**Nota Importante:** Assegure-se de que o arquivo `docker-compose.yml` esteja configurado corretamente com os serviços do backend e PostgreSQL, incluindo todas as variáveis de ambiente necessárias para a conexão com o banco de dados.

Após seguir estes passos, seu ambiente de backend estará configurado e em execução, pronto para ser integrado ao frontend ou para realização de testes através do Postman, curl, ou qualquer cliente HTTP de sua preferência.

## Inserindo o Código do Banco

```sql
CREATE TABLE fornecedores(
    id serial primary key,
    nome varchar not null,
    cidade VARCHAR(255),
    celular VARCHAR(20),
    email VARCHAR(255),
    ativo boolean NOT NULL DEFAULT true
);

CREATE TABLE produtos(
    id serial primary key,
    nome varchar not null,
    fornecedor_id int references fornecedores(id),
    categoria VARCHAR(255),
    ativo boolean NOT NULL DEFAULT true
);

CREATE TABLE movimentacoes(
    id serial primary key,
    data_registro timestamp(0) not null,
    quantidade int NOT NULL,
    valor numeric(9,2) NOT NULL,
    tipo varchar(1),
    produto_id int references produtos(id),
    fornecedor_id int,
    FOREIGN KEY (fornecedor_id) REFERENCES fornecedores(id)
);

CREATE TABLE users (
       id TEXT PRIMARY KEY UNIQUE NOT NULL,
       Username TEXT NOT NULL UNIQUE,
       password TEXT NOT NULL,
       role TEXT NOT NULL
);
