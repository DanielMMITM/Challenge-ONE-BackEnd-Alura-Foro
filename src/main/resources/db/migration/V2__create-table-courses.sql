create table users(
    id bigint not null auto_increment,
    name varchar(30) not null,
    category varchar(30) not null,

    primary key(id));