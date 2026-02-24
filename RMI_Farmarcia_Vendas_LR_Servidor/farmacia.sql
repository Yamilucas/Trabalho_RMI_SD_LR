create database farmacia;
use farmacia;

create table cliente (
    id int primary key auto_increment,
    nome varchar(100) unique not null,
    cpf varchar(20) unique not null,
    telefone varchar(15),
    endereco varchar(200)
);

create table funcionario (
    id int primary key auto_increment,
    nome varchar(100) unique not null,
    cpf varchar(20) unique not null,
    telefone varchar(15),
    cargo varchar(50)
);

create table produto (
    id int primary key auto_increment,
    nome varchar(100) unique not null,
    preco decimal(10,2) not null,
    quantidade int not null
);

create table venda (
    id int primary key auto_increment,
    idcliente int,
    idfuncionario int,
    idproduto int,
    quantidade int not null,
    data datetime not null,
    foreign key (idcliente) references cliente(id) on delete set null,
    foreign key (idfuncionario) references funcionario(id) on delete set null,
    foreign key (idproduto) references produto(id) on delete set null
);