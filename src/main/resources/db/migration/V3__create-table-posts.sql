create table posts(
    id bigint not null auto_increment,
    title varchar(80) not null,
    text text not null,
    post_date datetime not null,
    status_post tinyint not null,
    author varchar(80) not null,
    course varchar(80),

    primary key(id));