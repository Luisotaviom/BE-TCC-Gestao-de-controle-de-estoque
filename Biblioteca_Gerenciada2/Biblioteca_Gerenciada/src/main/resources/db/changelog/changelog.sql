-- liquibase formatted sql
-- changeset Luisotaviom:criar-tabela-livro
create table Livro
(
    id int primary key auto_increment,
    titulo varchar,
    categoria varchar,
    anopubli int,
    autor varchar,
    isbn LONG
);

-- changeset Luisotaviom:criar-tabela-Biblioteca
create table Biblioteca
(
    id int primary key auto_increment,
    nome varchar
);

-- changeset Luisotaviom:criar-usuario
create table Cadastros
(
    id_user int primary key auto_increment,
    nome varchar,
    email varchar,
    senha varchar
);

-- changeset Luisotaviom:inserir-livros
INSERT INTO Livro (titulo, categoria, anopubli, autor, isbn) VALUES
 ('A Máquina do Tempo', 'Ficção Científica', 1895, 'H.G. Wells', 9788563560235),
 ('Cem Anos de Solidão', 'Realismo Mágico', 1967, 'Gabriel García Márquez', 9788532509490),
 ('A Ilha do Tesouro', 'Aventura', 1883, 'Robert Louis Stevenson', 9788563560099),
 ('A Menina que Roubava Livros', 'Drama', 2005, 'Markus Zusak', 9788535911396),
 ('A Arte da Guerra', 'Filosofia/Militar', -500, 'Sun Tzu', 9788566997033),
 ('A Metamorfose', 'Ficção', 1915, 'Franz Kafka', 9788573260730),
 ('O Código Da Vinci', 'Suspense', 2003, 'Dan Brown', 9788575423819),
 ('A Odisséia', 'Épico', -800, 'Homero', 9788525422643),
 ('O Principezinho', 'Infantil', 1943, 'Antoine de Saint-Exupéry', 9788572327747),
 ('A Divina Comédia', 'Épico', 1320, 'Dante Alighieri', 9788572328805),
 ('O Hobbit', 'Fantasia', 1937, 'J.R.R. Tolkien', 9788578277109),
 ('O teste', 'teste', 1937, 'teste', 9788578277109);


-- changeset Luisotaviom:inserir-bibliotecas
INSERT INTO Biblioteca (nome) VALUES
('Biblioteca Municipal Central'),
('Biblioteca do Bairro A'),
('Biblioteca do Bairro B'),
('Biblioteca Universitária XYZ'),
('Biblioteca Infantil Feliz'),
('Biblioteca Comunitária ABC'),
('Biblioteca Escolar 123'),
('Biblioteca Tecnológica Inova'),
('Biblioteca Cultural Arte Livre'),
('Biblioteca teste'),
('Biblioteca da Cidade Nova');



-- changeset Luisotaviom:criar-tabela-Livro_Biblio
CREATE TABLE Livro_Biblio (

      id INT AUTO_INCREMENT PRIMARY KEY not null,
      livro_FK INT,
      biblioteca_FK INT,
      FOREIGN KEY (livro_fk) REFERENCES Livro(id),
      FOREIGN KEY (biblioteca_FK) REFERENCES Biblioteca(id)
);

-- Inserir conexões entre livros e bibliotecas na tabela Livro_Biblio
INSERT INTO Livro_Biblio (Livro_FK, Biblioteca_FK) VALUES
(1, 1), -- Livro: O Senhor dos Anéis, Biblioteca: Biblioteca Municipal Central
(2, 2), -- Livro: Harry Potter e a Pedra Filosofal, Biblioteca: Biblioteca do Bairro A
(3, 3), -- Livro: 1984, Biblioteca: Biblioteca do Bairro B
(4, 4), -- Livro: A Revolução dos Bichos, Biblioteca: Biblioteca Universitária XYZ
(5, 5), -- Livro: Dom Quixote, Biblioteca: Biblioteca Infantil Feliz
(6, 1), -- Livro: A Máquina do Tempo, Biblioteca: Biblioteca Municipal Central
(7, 2), -- Livro: Cem Anos de Solidão, Biblioteca: Biblioteca do Bairro A
(8, 3), -- Livro: A Ilha do Tesouro, Biblioteca: Biblioteca do Bairro B
(9, 4), -- Livro: A Menina que Roubava Livros, Biblioteca: Biblioteca Universitária XYZ
(10, 5); -- Livro: A Arte da Guerra, Biblioteca: Biblioteca Infantil Feliz
