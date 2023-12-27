create table users(
    id bigint not null auto_increment,
    name varchar(30) not null,
    email varchar(30) not null,
    password varchar (30) not null,

    primary key(id));