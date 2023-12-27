create table posts(
    id bigint not null auto_increment,
    title varchar(80) not null,
    content text not null,
    fecha_creacion datetime not null,
    status_topico tinyint not null,
    author varchar(80) not null,
    course varchar(80),

    primary key(id));