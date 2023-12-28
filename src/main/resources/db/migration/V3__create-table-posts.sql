create table posts(
    id bigint not null auto_increment,
    title varchar(80) not null,
    text text not null,
    status_post varchar(20) not null,
    user_id bigint not null,
    course_id bigint not null,
    post_date datetime not null,

    primary key(id),

    constraint fk_posts_course_id foreign key(course_id) references courses(id),
    constraint fk_posts_user_id foreign key(user_id) references users(id)

    );